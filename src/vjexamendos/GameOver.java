package vjexamendos;


import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Alberto
 */
public class GameOver {
    private final Boton[] botones;
    private final int[] startPosY, finalPosY;
    
    /**
     * Metodo constructor
     */
    public GameOver() {
        botones = new Boton[2];
        startPosY = new int[2];
        finalPosY = new int[2];
        botones[1] = new Boton(Base.getW()/2, 0, "Images/Buttons/quit.png");
        botones[1].setPosX(botones[1].getPosX() - botones[1].getAncho()/2);
        startPosY[1] = Base.getH();
        finalPosY[1] = 4*Base.getH()/5 - botones[1].getAlto()/2;
        botones[0] = new Boton(Base.getW()/2, 0, "Images/Buttons/gameover.png");
        botones[0].setPosX(botones[0].getPosX() - botones[0].getAncho()/2);
        startPosY[0] = -botones[0].getAlto()/2;
        finalPosY[0] = Base.getH()/4 - botones[0].getAlto()/2;
        
        reset();
    }
    
    public void actualiza () {
        if (botones[1].getPosY() > finalPosY[1]) {
            botones[1].setPosY(botones[1].getPosY() - 4);
        }
        if (botones[0].getPosY() < finalPosY[0]) {
            botones[0].setPosY(botones[0].getPosY() + 4);
        }
    }
    
    public void reset () {
        for (int i = 0; i < 2; i++) {
            botones[i].setPosY(startPosY[i]);
        }
    }
    
    /**
     * Dibuja la pantalla de seleccion de personajes
     * @param g
     * @param juego
     */
    public void render(Graphics g, Juego juego) {
        
        for (int i = 0; i < 2; i++) {
            g.drawImage(botones[i].getImagenI(), botones[i].getPosX(), botones[i].getPosY(), juego);
        }
    }

    /**
     * Revisa si se presiono Spacebar para reiniciar el juego
     * @param e 
     */
    public void keyPressed (KeyEvent e) {
        if (Juego.State == Juego.STATE.GAMEOVER) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                Juego.State = Juego.STATE.CHARSEL;
                reset();
            }
        }
        
    }
    
    /**
     * Revisa clicks en los botones
     * @param e 
     */
    public void mouseClicked (MouseEvent e) {
        if (Juego.State == Juego.STATE.GAMEOVER) {
            if (botones[1].contiene(e.getX(), e.getY())) {
                Juego.jugando = false;
            } else {
                Juego.State = Juego.STATE.CHARSEL;
                reset();
            }
        }
    }
}
