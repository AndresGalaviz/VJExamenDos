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
    private final Boton[] botones;
    private final int[] startPosY, finalPosY;
    
    /**
     * Metodo constructor
     * @param background imagen de fondo
     */
    public GameOver() {
        botones = new Boton[3];
        startPosY = new int[3];
        finalPosY = new int[3];
        botones[0] = new Boton(Base.getW()/3, 0, "Images/Buttons/restart.png");
        botones[0].setPosX(botones[0].getPosX() - botones[0].getAncho()/2);
        startPosY[0] = 
        finalPosY[0] = 4*Base.getH()/5 - botones[0].getAlto()/2;
        botones[1] = new Boton(2*Base.getW()/3, 0, "Images/Buttons/quit.png");
        botones[1].setPosX(botones[1].getPosX() - botones[1].getAncho()/2);
        startPosY[1] = 
        finalPosY[1] = 4*Base.getH()/5 - botones[1].getAlto()/2;
        botones[2] = new Boton(Base.getW()/2, 0, "Images/Buttons/gameover.png");
        botones[2].setPosX(botones[2].getPosX() - botones[2].getAncho()/2);
        startPosY[2] = Base.getW()/2 - botones[2].getAncho()/2;
        finalPosY[2] = 5*Base.getH()/6 - botones[2].getAlto()/2;
        
        reset();
    }
    
    public void actualiza () {
        for (int i = 0; i < 2; i++) {
            if (botones[i].getPosY() > finalPosY[i]) {
                botones[i].setPosY(botones[i].getPosY() - 4);
            }
        }
        if (botones[2].getPosY() < finalPosY[2]) {
            botones[2].setPosY(botones[2].getPosY() + 4);
        }
    }
    
    public void reset () {
        for (int i = 0; i < 3; i++) {
            botones[i].setPosY(startPosY[i]);
        }
    }
    
    /**
     * Dibuja la pantalla de seleccion de personajes
     * @param g
     * @param juego
     */
    public void render(Graphics g, Juego juego) {
        
        g.drawImage(botones[1].getImagenI(), botones[1].getPosX(), botones[1].getPosY(), juego);
        g.drawImage(botones[0].getImagenI(), botones[0].getPosX(), botones[0].getPosY(), juego);
        g.drawImage(botones[2].getImagenI(), botones[2].getPosX(), botones[2].getPosY(), juego);
    }

    /**
     * Revisa clicks en los botones
     * @param e 
     */
    @Override
    public void mouseClicked (MouseEvent e) {
        if (Juego.State == Juego.STATE.GAMEOVER) {
            
            if (botones[1].contiene (e.getX(), e.getY())) {
                Juego.State = Juego.STATE.CHARSEL;
                reset();
            } else if (botones[0].contiene (e.getX(), e.getY())) {
                Juego.empezar = true;
                reset();
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
