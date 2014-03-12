/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vjexamendos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author AndresG
 */
public class HighScore {
    
    private static final String nombreArchivo = "score.txt";

    public HighScore(){
        
    }
    /**
     * Metodo que agrega la informacion del vector al archivo.
     *
     * @throws IOException
     */
    public void grabaArchivo(int score) throws IOException {
        //guarda cuando no se encuentra en instrucciones
       
        try {
            
            PrintWriter fileOut = new PrintWriter(new FileWriter(nombreArchivo));

            fileOut.println(String.valueOf(score));
            fileOut.close();
        } catch (FileNotFoundException e) {

        }
        
    }
    public void leeArchivo() throws IOException {
        int maxScore;
        BufferedReader fileIn;
        try {
            fileIn = new BufferedReader(new FileReader(nombreArchivo));
        } catch (FileNotFoundException e) {
            File puntos = new File(nombreArchivo);
            PrintWriter fileOut = new PrintWriter(puntos);
            fileOut.println("0");
            fileOut.close();
            fileIn = new BufferedReader(new FileReader(nombreArchivo));
        }
        String dato = fileIn.readLine();
        maxScore = Integer.parseInt(dato);
        fileIn.close();

    }
}
