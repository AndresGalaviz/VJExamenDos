/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vjexamendos;

import javax.swing.ImageIcon;

/**
 *
 * @author Alberto
 */
public class PipeSet {
    private Pipe upPipe, downPipe;
    private static ImageIcon upPipeIcon, downPipeIcon;
    public PipeSet(int posX) {
        int minPosY = 0, maxPosY = 10;
        int posY = (int)(Math.random() * (maxPosY - minPosY)) + minPosY;
        upPipe = new Pipe(posX, posY, upPipeIcon);
        downPipe = new Pipe(posX, posY + Juego.distY, downPipeIcon);
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
}
