package vjexamendos;


import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Alberto
 */
public class CharSel implements MouseListener {
//    private final Boton PLAY;
    private final Boton QUIT;
//    private final Boton SELECTION;
    private final Boton[] PERSONAJES;
    private final Image BACKGROUND;
    
    /**
     * Metodo constructor
     * @param background imagen de fondo
     */
    public CharSel(Image background) {
        this.BACKGROUND = background;
//        PLAY = new Boton(Base.getW()/3, 4*Base.getH()/5, "Images/Buttons/play.png");
//        PLAY.setPosX(PLAY.getPosX() - PLAY.getAncho()/2);
//        PLAY.setPosY(PLAY.getPosY() - PLAY.getAlto()/2);
        QUIT = new Boton(Base.getW()/2, 5*Base.getH()/6, "Images/Buttons/quit.png");
        QUIT.setPosX(QUIT.getPosX() - QUIT.getAncho()/2);
        QUIT.setPosY(QUIT.getPosY() - QUIT.getAlto()/2);
//        SELECTION = new Boton(0, 0, "Images/Faces/rect.png");
        PERSONAJES = new Boton[2];
        for (int i = 0; i < 2; i++) {
            PERSONAJES[i] = new Boton((2*i-1)*Base.getW()/4, 2*Base.getH()/5, "Images/Faces/choice" + i + ".png");
            PERSONAJES[i].setPosX(PERSONAJES[i].getPosX() - PERSONAJES[i].getAncho()/2);
            PERSONAJES[i].setPosY(PERSONAJES[i].getPosY() - PERSONAJES[i].getAlto()/2);
        }
    }
    
    /**
     * Dibuja la pantalla de seleccion de personajes
     * @param g
     * @param juego
     */
    public void render(Graphics g, Juego juego) {
        
        g.drawImage(BACKGROUND, 0, 0, juego);
//        g.drawImage(PLAY.getImagenI(), PLAY.getPosX(), PLAY.getPosY(), juego);
        g.drawImage(QUIT.getImagenI(), QUIT.getPosX(), QUIT.getPosY(), juego);
        for (int i = 0; i < 2; i++) {
            g.drawImage(PERSONAJES[i].getImagenI(), PERSONAJES[i].getPosX(), PERSONAJES[i].getPosY(), juego);
        }        
    }

    /**
     * Revisa clicks en los botones
     * @param e 
     */
    @Override
    public void mouseClicked (MouseEvent e) {
        if (Juego.State == Juego.STATE.CHARSEL) {
            
            if (QUIT.contiene (e.getX(), e.getY())) {
                Juego.jugando = false;
            } else {
                for (int i = 0; i < 2; i++) {
                    if (PERSONAJES[i].contiene(e.getX(), e.getY())) {
                        Juego.jugador = i;
                        Juego.empezar = true;
//                        SELECTION.setPosX(PERSONAJES[i].getPosX());
//                        SELECTION.setPosY(PERSONAJES[i].getPosY());
                    }
                }
            }
        
        }
        
    }

    @Override
    public void mousePressed (MouseEvent e) {}

    @Override
    public void mouseReleased (MouseEvent e) {}

    @Override
    public void mouseEntered (MouseEvent e) {}

    @Override
    public void mouseExited (MouseEvent e) {}
}
