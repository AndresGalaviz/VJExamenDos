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

/**
 *
 * @author Alberto
 */
public class Number {
    private static final Image[] numbers = getNumbers();
    /**
     * Dibuja un numero
     * @param g 
     * @param juego
     * @param N
     * @param X
     * @param Y 
     */
    public static void draw (Graphics g, Juego juego, int N, int X, int Y) {
        int digits[] = new int[10];
        int n = N, d = 0;
        if (n == 0) {
            digits[d++] = 0;
        }
        while (n > 0) {
            digits[d++] = n%10;
            n /= 10;
        }
        int totalLength = d*numbers[2].getWidth(juego) + (d-1)*10;
        for (int i = 0; i < d; i++) {
            //System.out.println(".." + digits[i]);
            int posX = X - totalLength/2 + i*numbers[2].getWidth(juego) + (i)*10;
            int posY = Y - numbers[2].getWidth(juego)/2;
            g.drawImage(numbers[digits[d-i-1]], posX, posY, juego);
        }
    }
    
    private static Image[] getNumbers() {
        Image[] imgs = new Image[10];
        for (int j = 0; j < 10; j++) {
            //System.out.println(j);
            imgs[j] = Toolkit.getDefaultToolkit().getImage(Number.class.getResource("Images/" + j + ".png"));
        }
        return imgs;
    }
}
