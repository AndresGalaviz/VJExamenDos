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
public class GameOver implements MouseListener {
    private final Boton PLAY, RETURN, GAMEOVER;
    
    /**
     * Metodo constructor
     * @param background imagen de fondo
     */
    public GameOver() {
        PLAY = new Boton(Base.getW()/3, 4*Base.getH()/5, "Images/Buttons/play.png");
        PLAY.setPosX(PLAY.getPosX() - PLAY.getAncho()/2);
        PLAY.setPosY(PLAY.getPosY() - PLAY.getAlto()/2);
        RETURN = new Boton(Base.getW()/2, 5*Base.getH()/6, "Images/Buttons/quit.png");
        RETURN.setPosX(RETURN.getPosX() - RETURN.getAncho()/2);
        RETURN.setPosY(RETURN.getPosY() - RETURN.getAlto()/2);
        GAMEOVER = new Boton(Base.getW()/2, 5*Base.getH()/6, "Images/Buttons/gameover.png");
        GAMEOVER.setPosX(GAMEOVER.getPosX() - GAMEOVER.getAncho()/2);
        GAMEOVER.setPosY(GAMEOVER.getPosY() - GAMEOVER.getAlto()/2);
    }
    
    public void actualiza () {
        
    }
    
    /**
     * Dibuja la pantalla de seleccion de personajes
     * @param g
     * @param juego
     */
    public void render(Graphics g, Juego juego) {
        
        g.drawImage(RETURN.getImagenI(), RETURN.getPosX(), RETURN.getPosY(), juego);
        g.drawImage(PLAY.getImagenI(), PLAY.getPosX(), PLAY.getPosY(), juego);
        g.drawImage(GAMEOVER.getImagenI(), GAMEOVER.getPosX(), GAMEOVER.getPosY(), juego);
    }

    /**
     * Revisa clicks en los botones
     * @param e 
     */
    @Override
    public void mouseClicked (MouseEvent e) {
        if (Juego.State == Juego.STATE.GAMEOVER) {
            
            if (RETURN.contiene (e.getX(), e.getY())) {
                Juego.State = Juego.STATE.CHARSEL;
            } if (PLAY.contiene (e.getX(), e.getY())) {
                Juego.empezar = true;
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
