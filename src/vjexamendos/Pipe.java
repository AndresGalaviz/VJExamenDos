/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vjexamendos;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.net.URL;
import javax.swing.ImageIcon;

/**
 *
 * @author Alberto
 */
public class Pipe {
    private int posX;
    private int posY;
    private ImageIcon icono;
    
    /**
     * Metodo constructor usado para crear el objeto
     * @param posX es la <code>posicion en x</code> del objeto.
     * @param posY es la <code>posicion en y</code> del objeto.
     * @param image es la <code>imagen</code> del objeto.
     */
    public Pipe(int posX, int posY, Image image) {
        this.posX = posX;
        this.posY = posY;
        icono = new ImageIcon(image);
    }
    
    /**
     * Metodo constructor usado para crear el objeto
     * @param posX coordenada X
     * @param posY coordenada Y
     * @param fileName nombre del archivo donde se encuentra la imagen
     */
    public Pipe(int posX, int posY, String fileName) {
        this.posX = posX;
        this.posY = posY;
        URL url = this.getClass().getResource(fileName);
        Image image = Toolkit.getDefaultToolkit().getImage(url);
        icono = new ImageIcon(image);
    }
    
    /**
     * Metodo modificador usado para cambiar la posicion en x del objeto 
     * @param posX es la <code>posicion en x</code> del objeto.
     */
    public void setPosX(int posX) {
        this.posX = posX;
    }
	
    /**
     * Metodo de acceso que regresa la posicion en x del objeto 
     * @return posX es la <code>posicion en x</code> del objeto.
     */
    public int getPosX() {
        return posX;
    }

    /**
     * Metodo modificador usado para cambiar la posicion en y del objeto 
     * @param posY es la <code>posicion en y</code> del objeto.
     */
    public void setPosY(int posY) {
        this.posY = posY;
    }

    /**
     * Metodo de acceso que regresa la posicion en y del objeto 
     * @return posY es la <code>posicion en y</code> del objeto.
     */
    public int getPosY() {
        return posY;
    }

    /**
     * Metodo modificador usado para cambiar el icono del objeto 
     * @param icono es el <code>icono</code> del objeto.
     */
    public void setImageIcon(ImageIcon icono) {
        this.icono = icono;
    }
    
    /**
     * Metodo de acceso que regresa el icono del objeto 
     * @return icono es el <code>icono</code> del objeto.
     */
    public ImageIcon getImageIcon() {
        return icono;
    }
    
    /**
     * Metodo de acceso que regresa el ancho del icono 
     * @return un objeto de la clase <code>ImageIcon</code> que es el ancho del icono.
     */
    public int getAncho() {
        return icono.getIconWidth();
    }
    
    /**
     * Metodo de acceso que regresa el alto del icono 
     * @return un objeto de la clase <code>ImageIcon</code> que es el alto del icono.
     */
    public int getAlto() {
        return icono.getIconHeight();
    }
    
    /**
     * Metodo que regresa un nuevo rectangulo
     * @return un objeto de la clase <code>Rectangle</code> que es el perimetro 
     * del rectangulo
     */
    public Rectangle getPerimetro() {
        return new Rectangle(getPosX(),getPosY(),getAncho(),getAlto());
    }
    
    /**
     * Metodo que indica si un punto está dentro del <code>Boton</code>
     * @param x coordenada X
     * @param y coordenada Y
     * @return <code>true</code> o <code>false</code>
     */
    public boolean contiene(int x, int y) {
        return getPerimetro().contains(x, y);
    }
    
    /**
     * Metodo de acceso que regresa la imagen del icono 
     * @return un objeto de la clase <code>Image</code> que es la imagen del icono.
     */
    public Image getImagenI() {
        return icono.getImage();
    }
}
