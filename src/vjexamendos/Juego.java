package vjexamendos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

public class Juego extends JFrame implements Runnable, KeyListener, MouseListener {

    private static final long serialVersionUID = 1L;
    private String[] arr;    //Arreglo del archivo divido.
    private Flappy fish;
    private ArrayList<PipeSet> medusas;
    private HighScore high;
    private boolean pausa;
    private boolean sound;
    private boolean lost;
    private int score;
    private int index;
    private int maxScore;
    private final int MAXINDEX = 3;
    private Image dbImage;
    private Image[] gameBG;
    private Image charSelBG;
    private Image pause;
    private Graphics dbg;
    private CharSel charSel;
    private GameOver gameOver;
    private SoundClip bang;
    private SoundClip shoot;
    private long tiempoActual;

    public static enum STATE {
        CHARSEL,
        GAME,
        GAMEOVER,
    };
    public static STATE State;
    public static int distX;
    public static int distY;
    public static int nivel;
    public static int jugador = -1;
    public static boolean jugando = true;
    public static boolean empezar = false;
    
    /**
     * Método constructor de la clase <code>JFrameExamen</code>.
     */
    public Juego() {
        setTitle("FlappyMemilio");
        setSize(600, 894);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        init();
        start();
    }

    /**
     * Metodo <I>init</I> sobrescrito de la clase <code>Applet</code>.<P>
     * En este metodo se inizializan las variables o se crean los objetos a
     * usarse en el <code>Applet</code> y se definen funcionalidades.
     */
    public void init() {
        addKeyListener(this);
        addMouseListener(this);
        Base.setW(getWidth());
        Base.setH(getHeight());
        
        distX = 500;
        distY = 650;
        maxScore = 0;
        fish = new Flappy(0, 0);
        fish.setX(getWidth()/5);
        fish.setY(getHeight()/2);
        medusas = new ArrayList();
        for (int i = 0; i < 2*getWidth()/distX; i++) {
            medusas.add(new PipeSet(2*getWidth() + i*distX));
        }
        gameBG = new Image[MAXINDEX];
        for (int i = 0; i < MAXINDEX; i++) {
            gameBG[i] = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/background" + i + ".jpg"));
        }
        pause = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/pause.png"));
        charSelBG = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/charSelectN.jpg"));
        charSel = new CharSel(charSelBG);
        gameOver = new GameOver();
        pausa = false;
        sound = true;
        lost = false;
        score = 0;
        State = STATE.CHARSEL;
        
        //Pinta el fondo del Applet de color blanco
        setBackground(Color.white);
        //shoot = new SoundClip("Sounds/failS.wav");
        //bang = new SoundClip("Sounds/hoopS.wav");

    }

    /**
     * Metodo <I>start</I> sobrescrito de la clase <code>Applet</code>.<P>
     * En este metodo se crea e inicializa el hilo para la animacion este metodo
     * es llamado despues del init o cuando el usuario visita otra pagina y
     * luego regresa a la pagina en donde esta este <code>Applet</code>
     *
     */
    public void start() {

        //Crea el thread
        Thread th = new Thread(this);
        //Inicializa el thread
        th.start();
    }

    /**
     * Metodo <I>run</I> sobrescrito de la clase <code>Thread</code>.<P>
     * En este metodo se ejecuta el hilo, es un ciclo indefinido donde se
     * incrementa la posicion en x o y dependiendo de la direccion, finalmente
     * se repinta el <code>Applet</code> y luego manda a dormir el hilo.
     *
     */
    public void run() {
        //Guarda el tiempo actual del sistema
        tiempoActual = System.currentTimeMillis();
        //Ciclo principal del Applet. Actualiza y despliega en pantalla la 
        //animacion hasta que el Applet sea cerrado
        while (true) {
            if (!jugando) {
                setVisible(false);
                System.exit(0);
            }
            if (empezar) {
                empezarJuego();
                State = STATE.GAME;
                empezar = false;
            }
            if (!(State == STATE.CHARSEL)) {
                if (!pausa) {
                    //Actualiza la animacion
                    actualiza();
                    checaColision();
                }
            }
            //Manda a llamar al metodo paint() para mostrar en pantalla la animación
            repaint();
            //Hace una pausa de 20 milisegundos
            try {
                Thread.sleep(20);
            } catch (InterruptedException ex) {
                System.out.println("Error en " + ex.toString());
            }
        }

    }

    /**
     * Metodo que lee a informacion de un archivo y lo agrega a un vector.
     *
     * @throws IOException
     */




    /**
     * El método actualiza() actualiza la animación
     */
    public void actualiza() {

        //Determina el tiempo que ha transcurrido desde que el Applet inicio su ejecución
        long tiempoTranscurrido = System.currentTimeMillis() - tiempoActual;

        //Guarda el tiempo actual
        tiempoActual += tiempoTranscurrido;

        fish.avanza();
        if (!lost && fish.getMov()) {
            for (PipeSet medusa : medusas) {
                medusa.setPosX(medusa.getPosX() - PipeSet.getSpeed());
            }
        }

        //Actualiza la animación en base al tiempo transcurrido
        fish.actualiza(tiempoTranscurrido);
        
        if (State == STATE.GAMEOVER) {
            gameOver.actualiza();
        }
    }

    /**
     * Metodo usado para checar las colisiones de los personajes con las orillas
     * del <code>Applet</code> y entre si.
     */
    public void checaColision() {
        // Colision flappy con JFrame
        if (fish.getPosY() + fish.getAlto() > getHeight()) {
            lost = true;
            
//            try {
//                high.grabaArchivo(score);
//            } catch (IOException ex) {
//                    Logger.getLogger(Juego.class.getName()).log(Level.SEVERE, null, ex);
//            }
            
            
            State = STATE.GAMEOVER;
            fish.setPosY(getHeight() - fish.getAlto());
        } else if (fish.getPosY() <= 20) {
            fish.setPosY(20);
            lost = true;

//          
//            try {
//                high.grabaArchivo(score);
//            } catch (IOException ex) {
//                Logger.getLogger(Juego.class.getName()).log(Level.SEVERE, null, ex);
//            
//            }
            
            State = STATE.GAMEOVER;
        } else {
            fish.setInside(true);
        }

        // Colision medusa con JFrame
        for (int i = 0; i < medusas.size(); i++) {
            PipeSet medusa = medusas.get(i);
            if (medusa.getPosX() + medusa.getAncho() < 0) {
                int newPosX = medusas.get((i + medusas.size() - 1)%medusas.size()).getPosX() + distX;
                medusa.reaparece(newPosX);
            }
        }
        
        // Colision fish con medusa
        for (PipeSet medusa : medusas) {
            if (fish.colisiona(medusa)) {
                if(!lost) {
                    fish.setVy(0);
                   

//                    try {
//                        high.grabaArchivo(score);
//                    } catch (IOException ex) {
//                        Logger.getLogger(Juego.class.getName()).log(Level.SEVERE, null, ex);
//                    }
                    
                }
                lost = true;
                

            } else {
                int dif = (fish.getPosX() + fish.getAncho()/2) - (medusa.getPosX() - medusa.getAncho()/2);
                // Atraviesa una medusa
                if (0 <= dif && dif < PipeSet.getSpeed()) {
                    score++;
                    if (score%10 == 0) {
                        nivel++;
                        if(distY >=300) {
                            distX -= 50;
                        }
                        if(distY >=400) {
                            distY -= 10;
                        }
                    }
                }
            }
        }

    }
    
    private void empezarJuego() {
        index = (int)(Math.random() * MAXINDEX);
        fish.reaparecer();
        lost = false;
        score = 0;
        nivel = 0;
    }

    /**
     * Metodo <I>update</I> sobrescrito de la clase <code>Applet</code>,
     * heredado de la clase Container.<P>
     * En este metodo lo que hace es actualizar el contenedor
     *
     * @param g es el <code>objeto grafico</code> usado para dibujar.
     */
    public void paint(Graphics g) {
        // Inicializan el DoubleBuffer
        if (dbImage == null) {
            dbImage = createImage(this.getSize().width, this.getSize().height);
            dbg = dbImage.getGraphics();
        }

        // Actualiza la imagen de fondo.
        dbg.setColor(getBackground());
        dbg.fillRect(0, 0, this.getSize().width, this.getSize().height);

        // Actualiza el Foreground.
        dbg.setColor(getForeground());
        paint1(dbg);

        // Dibuja la imagen actualizada
        g.drawImage(dbImage, 0, 0, this);
    }

    /**
     * Metodo <I>paint</I> sobrescrito de la clase <code>Applet</code>, heredado
     * de la clase Container.<P>
     * En este metodo se dibuja la imagen con la posicion actualizada, ademas
     * que cuando la imagen es cargada te despliega una advertencia.
     *
     * @param g es el <code>objeto grafico</code> usado para dibujar.
     */
    public void paint1(Graphics g) {
        if (!(State == STATE.CHARSEL)) {
            // Muestra en pantalla el cuadro actual de la animación
            g.drawImage(gameBG[index], 0, 0, this);    // Imagen de background


            for (PipeSet medusa : medusas) {
                if (medusa != null) {
                    medusa.draw(g, this);
                }
            }
            if (fish != null && fish.getImagenI() != null) {
                g.drawImage(fish.getImagenI(), fish.getPosX(), fish.getPosY(), this);
            }

            g.setFont(new Font("default", Font.BOLD, 16));
            if (pausa) { // mensaje de pausa
                g.setColor(Color.white);
                g.drawImage(pause, fish.getPosX() + fish.getAncho()/2 - pause.getWidth(this),
                        fish.getPosY() + fish.getAlto()/2 - pause.getHeight(this), this);
            }
            if(State == STATE.GAMEOVER) {
                gameOver.render(g, this);
            } else {
//                g.setColor(Color.green);
//                g.drawString("Score: " + score, 20, 55);
                Number.draw(g, this, score, getWidth()/2, getHeight()/4);
            }

        } else {
            charSel.render(g, this);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Define el sentido del movimiento de <code>canasta</code>
     *
     * @param e
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if(!lost) {
            if (State == STATE.GAME) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE && fish.getInside()) {
                    fish.lanzar();
                } else if (e.getKeyCode() == KeyEvent.VK_P) {
                    pausa = !pausa;
                } else if (e.getKeyCode() == KeyEvent.VK_S) {
                    sound = !sound;
                } 
            }
        } else if(State == STATE.GAMEOVER) {
            gameOver.keyPressed(e);
        } 
        
    }

    /**
     * Define el sentido del movimiento de <code>canasta</code>
     *
     * @param e
     */
    @Override
    public void keyReleased(KeyEvent e) {}

@Override
    public void mouseClicked(MouseEvent e) {
        if(!lost) {
            if (State == STATE.GAME) {
                if (fish.getInside()) {
                    fish.lanzar();
                }
            }
        } else if (State == STATE.CHARSEL ) {
            charSel.mouseClicked(e);
        } 
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

}
