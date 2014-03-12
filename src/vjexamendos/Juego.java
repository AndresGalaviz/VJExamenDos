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
    private static final String nombreArchivo = "score.txt";
    private String[] arr;    //Arreglo del archivo divido.
    private Flappy flappy;
    private ArrayList<PipeSet> pipes;
    private boolean pausa;
    private boolean instrucciones;
    private boolean sound;
    private int score;
    private int index;
    private Image dbImage;
    private Image background;
    private Image gameover;
    private Image instructionBack;
    private Image pause;
    private Graphics dbg;
    private SoundClip bang;
    private SoundClip shoot;
    private long tiempoActual;

    public static enum STATE {
        CHARSEL,
        GAME
    };
    public static STATE State;
    public static int distX;
    public static int distY;
    public static int jugador = -1;
    public static boolean jugando = true;
    
    /**
     * Método constructor de la clase <code>JFrameExamen</code>.
     */
    public Juego() {
        setTitle("Examen");
        setSize(1600, 900);
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
        flappy = new Flappy(0, 0);
        flappy.setX(getWidth()/5 - flappy.getAncho()/2);
        flappy.setY(getHeight()/2 - flappy.getAlto()/2);
        pipes = new ArrayList();
        for (int i = 0; i < 2*getWidth()/distX; i++) {
            pipes.add(new PipeSet(3*getWidth() + i*distX));
        }
        background = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/background/background.jpg"));
        pause = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/pause.png"));
        instructionBack = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/background/instrucciones.jpg"));
        gameover = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/background/gameover2.jpg"));

        pausa = false;
        instrucciones = false;
        sound = true;
        score = 0;
        //Pinta el fondo del Applet de color blanco
        setBackground(Color.white);
        shoot = new SoundClip("Sounds/failS.wav");
        bang = new SoundClip("Sounds/hoopS.wav");

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
            if (!pausa && !instrucciones) {
                //Actualiza la animacion
                actualiza();
                checaColision();
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
    public void leeArchivo() throws IOException {
        BufferedReader fileIn;
        try {
            fileIn = new BufferedReader(new FileReader(nombreArchivo));
        } catch (FileNotFoundException e) {
            File puntos = new File(nombreArchivo);
            PrintWriter fileOut = new PrintWriter(puntos);
            fileOut.println("5,0,0,0,0,0,0,0,0,0");
            fileOut.close();
            fileIn = new BufferedReader(new FileReader(nombreArchivo));
        }
        String dato = fileIn.readLine();

        arr = dato.split(",");
        pausa = Boolean.parseBoolean(arr[0]);
        vidas = Integer.parseInt(arr[1]);
        score = Integer.parseInt(arr[2]);
        caidas = Integer.parseInt(arr[3]);
        entrando = Boolean.parseBoolean(arr[4]);
        flappy.assingData(arr);
        canasta.assignData(arr);
        sound = Boolean.parseBoolean(arr[14]);
        fileIn.close();

    }

    /**
     * Metodo que agrega la informacion del vector al archivo.
     *
     * @throws IOException
     */
    public void grabaArchivo() throws IOException {
        //guarda cuando no se encuentra en instrucciones
        if (!instrucciones) {
            try {
                PrintWriter fileOut = new PrintWriter(new FileWriter(nombreArchivo));

                fileOut.println(String.valueOf(pausa) + "," + String.valueOf(vidas) + "," + String.valueOf(score) + "," + String.valueOf(caidas) + "," + String.valueOf(entrando) + "," + flappy.getData() + "," + canasta.getData() + "," + String.valueOf(sound));
                fileOut.close();
            } catch (FileNotFoundException e) {

            }
        }
    }

    /**
     * El método actualiza() actualiza la animación
     */
    public void actualiza() {

        //Determina el tiempo que ha transcurrido desde que el Applet inicio su ejecución
        long tiempoTranscurrido = System.currentTimeMillis() - tiempoActual;

        //Guarda el tiempo actual
        tiempoActual += tiempoTranscurrido;

        flappy.avanza();

        //Actualiza la animación en base al tiempo transcurrido
        flappy.actualiza(tiempoTranscurrido);
    }

    /**
     * Metodo usado para checar las colisiones de los personajes con las orillas
     * del <code>Applet</code> y entre si.
     */
    public void checaColision() {
//        if (canasta.getPosX() < getWidth() / 2) {
//            canasta.setPosX(getWidth() / 2);
//        }
//        if (canasta.getPosX() + canasta.getAncho() > getWidth()) {
//            canasta.setPosX(getWidth() - canasta.getAncho());
//        }

//        if (flappy.getPosY() > getHeight() + 10) {
//            if (!entrando && sound) {
//                shoot.play();
//            }
//            flappy.reaparecer();
//            if (entrando) {
//                entrando = false;
//            } else {
//                caidas++;
//                if (caidas % 3 == 0) {
//                    vidas--;
//                    Pelota.setAceleracion(Pelota.getAceleracion() + 400);
//                }
//            }
//        }
        if (flappy.getPosY() <= getHeight()) {
            game
        }
        if (flappy.intersectaCentroSup(canasta) && !entrando) {
            score += 2;
            if (sound) {
                bang.play();
            }
            entrando = true;
        }

    }
    
    private void empezarJuego() {
        index = (int)(Math.random() * 2) + 1;
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
        //g.setColor(Color.RED);
        //g.fillRect(0, 0, getWidth(), getHeight());
        // Muestra en pantalla el cuadro actual de la animación
        g.drawImage(background, 0, 0, this);    // Imagen de background
        if (flappy != null && flappy.getImagenI() != null) {

            g.drawImage(flappy.getImagenI(), flappy.getPosX(), flappy.getPosY(), this);
        }

        if (canasta != null && canasta.getImagenI() != null) {
            g.drawImage(canasta.getImagenI(), canasta.getPosX(), canasta.getPosY(), this);
        }

        g.setFont(new Font("default", Font.BOLD, 16));
        if (pausa) { // mensaje de pausa
            g.setColor(Color.white);
            g.drawImage(pause, canasta.getPosX() - 10, canasta.getPosY() - 37, this);
        }

        g.setColor(Color.green);
        g.drawString("Score: " + score, 20, 55);
//        g.setColor(Color.blue);
//        g.drawString("Caídas: " + caidas, 20, 80);
//        g.setColor(Color.red);
//        g.drawString("Vidas: " + vidas, 20, 105);
//        if (instrucciones) {
//            setBackground(Color.black);
//            g.drawImage(instructionBack, 0, 0, this);    // Imagen de instrucciones
//        }
//        if (vidas <= 0) {
//            pausa = true;
//            g.drawImage(gameover, 0, 0, this);    // Imagen de instrucciones
//        }
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
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            //canasta.serBrinco(true);
        }        
        
        
//        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
//            canasta.setMoveLeft(true);
//        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
//            canasta.setMoveRight(true);
//        } else if (e.getKeyCode() == KeyEvent.VK_P) {
//            if (!pausa) {
//                pausa = true;
//                flappy.freeze();
//            } else {
//                pausa = false;
//                flappy.unfreeze();
//            }
//        } else if (e.getKeyCode() == KeyEvent.VK_C) {
//
//            try {
//                leeArchivo();
//            } catch (IOException ex) {
//                Logger.getLogger(Juego.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        } else if (e.getKeyCode() == KeyEvent.VK_S) {
//            sound = !sound;
//        }

    }

    /**
     * Define el sentido del movimiento de <code>canasta</code>
     *
     * @param e
     */
    @Override
    public void keyReleased(KeyEvent e) {
        
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            //canasta.serBrinco(false);
        }
//        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
//            canasta.setMoveLeft(false);
//        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
//            canasta.setMoveRight(false);
//        } 
//        else if (e.getKeyCode() == KeyEvent.VK_G) {
//            if (!instrucciones) {
//                try {
//                    grabaArchivo();
//                } catch (IOException ex) {
//                    Logger.getLogger(Juego.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//
//        } 
//        else if (e.getKeyCode() == KeyEvent.VK_I) {
//            if (!instrucciones) {
//                instrucciones = true;
//                flappy.freeze();
//            } else {
//                instrucciones = false;
//                flappy.unfreeze();
//            }
//
//        }
    }

    /**
     * Inicia el movimiento de <code>pelota</code>.
     *
     * @param e
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        
        
        //canasta.serBrinco(true);
        
        
//        if (!flappy.getMov() && flappy.contiene(e.getX(), e.getY())) {
//            flappy.lanzar();
//        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //canasta.serBrinco(false);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}
