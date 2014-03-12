/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vjexamendos;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import javax.swing.ImageIcon;

/**
 *
 * @author Alberto
 */
public class PipeSet {
    private final Pipe upPipe, downPipe;
    private static final ImageIcon upPipeIcon = getUpIcon(),
             downPipeIcon = getDownIcon();
    private static final int speed = 5; 
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
        int minPosY = -150, maxPosY = -10;
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
    
    public Pipe getUpPipe() {
        return upPipe;
    }
    
    public Pipe getDownPipe() {
        return downPipe;
    }
    
    public static int getSpeed() {
        return speed;
    }
    
    private static ImageIcon getUpIcon() {
        URL url = PipeSet.class.getResource("Images/pipe0.png");
        Image image = Toolkit.getDefaultToolkit().getImage(url);
        return new ImageIcon(image);
    }
    
    private static ImageIcon getDownIcon() {
        URL url = PipeSet.class.getResource("Images/pipe1.png");
        Image image = Toolkit.getDefaultToolkit().getImage(url);
        return new ImageIcon(image);
    }
}
