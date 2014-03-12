package vjexamendos;

import java.awt.Toolkit;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author AndresG
 */
public class Flappy extends Base {
    
    private double vy;
    private int x;
    private int y;
    private boolean mov;
    private boolean inside;
    private static double g = 0.2;
    private static Animacion[] anim = new Animacion[4];
    private static int X = crearAnimacionFlappy();
    
    /**
     * Metodo constructor.
     * @param posX coordenada x inicial.
     * @param posY coordenada y inicial.
     */
    public Flappy(int posX, int posY) {
        super(posX, posY, null);
        x = posX;
        y = posY;
        //reaparecer();
    }

    /**
     * <code>Flappy</code> reaparece en su posicion original.
     */
    public void reaparecer() {
        setAnimacion(anim[Juego.jugador]);
        setPosX(x-getAncho()/2);
        setPosY(y-getAlto()/2);
        mov = false;
        inside = true;
    }
    
    /**
     * Inicia el movimiento de <code>Flappy<code>.
     */
    public void lanzar() {
        mov = true;
        vy = 6.5;
    }
    
    /**
     * La pelota se mueve de acuerdo al tiempo, velocidad en X y Y, y gravedad.
     */
    public void avanza() {
        if (mov) {
            setDoublePosY(getDoublePosY() - vy);
            vy -= g;
        }
    }
    
    /**
     * Dice si el punto (posX, posY) esta dentro de la pelota, no solo del
     * cuadrado que la contiene
     * @param posX coordenada X del punto
     * @param posY coordenada Y del punto
     * @return <code>true</code> si está en el interior, sino <code>false</code>.
     */
    public boolean contiene(int posX, int posY) {
        int distCentroX = (getPosX() + getAncho()/2) - posX;
        int distCentroY = (getPosY() + getAlto()/2) - posY;
        
        return (distCentroX*distCentroX + distCentroY*distCentroY <= getAncho()*getAncho()/4);
    }
    
    /**
     * Indica si la pelota está en movimiento o no
     * @param m valor que tomará <code>mov</code>
     */
    public void setMov(boolean m) {
        mov = m;
    }
    
    /**
     * Regresa si <code>Flappy</code> esta en movimiento o no
     * @return true o false
     */
    public boolean getMov() {
        return mov;
    }
    
    public void setInside(boolean i) {
        inside = i;
    }
    
    public boolean getInside() {
        return inside;
    }
    
    /**
     * Establece la posición inicial en X
     * @param X coordenada X
     */
    public void setX(int X) {
        x = X;
        setPosX(X);
    }
    
    /**
     * Establece la posición inicial en Y
     * @param Y coordenada Y
     */
    public void setY(int Y) {
        y = Y;
        setPosY(Y);
    }
    
    public boolean colisiona(PipeSet medusa) {
        return false;
    }
    
    /**
     * Establece el valor de la gravedad
     * @param a un valor de tipo <code>int</code>
     */
    public static void setG(int a) { 
        g = a;
    }
    
    /**
     * Regresa el valor de la gravedad
     * @return <code>g</code>
     */
    public static double getG() {
        return g;
    }
    
    /**
     * Crea la animación de la pelota para el constructor
     * @return un objeto de tipo <code>Animacion</code>
     */
    private static int crearAnimacionFlappy() {
        for (int i = 0; i < 2; i++) {
            anim[i] = new Animacion();
            for (int j = 0; j < 2; j++) {
                anim[i].sumaCuadro (Toolkit.getDefaultToolkit ().getImage (Flappy.class.getResource ("Images/peces/pez" + i + j + ".png")), 60);
            }
        }
        return 1;
    }

}
