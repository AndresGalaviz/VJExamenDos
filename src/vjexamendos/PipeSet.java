/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vjexamendos;

import java.awt.Graphics;
import javax.swing.ImageIcon;

/**
 *
 * @author Alberto
 */
public class PipeSet {
    private Pipe upPipe, downPipe;
    private static ImageIcon upPipeIcon, downPipeIcon;
    private static int speed = 2; 
    public PipeSet(int posX) {
        upPipe = new Pipe(0, 0, upPipeIcon);
        downPipe = new Pipe(0, 0, downPipeIcon);
        reaparece(posX);
    }
    
    public void setPosX(int x) {
        upPipe.setPosX(x);
        downPipe.setPosX(x);
    }
    
    public int getPosX() {
        return upPipe.getPosX();
    }
    
    public void setPosY(int y) {
        upPipe.setPosY(y);
        downPipe.setPosY(y);
    }
    
    public int getPosY() {
        return upPipe.getPosY();
    }
    
    public int getAncho() {
        return upPipe.getAncho();
    }
    
    public int getAlto() {
        return upPipe.getAlto();
    }
    
    public void reaparece(int posX) {
        int minPosY = 0, maxPosY = 10;
        int posY = (int)(Math.random() * (maxPosY - minPosY)) + minPosY;
        upPipe.setPosX(posX);
        downPipe.setPosX(posX);
        upPipe.setPosY(posY);
        downPipe.setPosY(posY + Juego.distY);
    }
    
    public void draw (Graphics g, Juego juego) {
        if (upPipe != null && downPipe != null) {
            g.drawImage(upPipe.getImagenI(), upPipe.getPosX(), upPipe.getPosY(), juego);
            g.drawImage(downPipe.getImagenI(), downPipe.getPosX(), downPipe.getPosY(), juego);
        }
    }
    
    public static int getSpeed() {
        return speed;
    }
}
