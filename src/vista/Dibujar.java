/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.awt.Point;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * Se encarga de dibujar un txt con las ciudades y el colegio
 */
public class Dibujar {

    public static void dibuja(HashMap<String, Point> matriz_ciudades, int tamMatriz, Point pos_escuela) {
        int tamMaxString = 0;

        String[][] nombres = new String[tamMatriz][tamMatriz];
        for (Map.Entry item : matriz_ciudades.entrySet()) {
            String key = item.getKey().toString();
            Point p = ((Point) item.getValue());
            if (tamMaxString < key.length()) {
                tamMaxString = key.length();
            }
            nombres[p.x][p.y] = key;
        }
        String fin_linea = "";
        String espacio_palabra = "";
        String nombre_escuela = "";
        
        boolean puesto_asterisco = false;
        for (int i = 0; i < tamMaxString; i++) {
            espacio_palabra += " ";
            fin_linea += "-";
            if(i < (tamMaxString / 2)) nombre_escuela += " ";
            else if(!puesto_asterisco) {
                nombre_escuela += "*";
                puesto_asterisco = !puesto_asterisco;
            }
            else nombre_escuela += " ";
        }
        
        
        
        Writer writer = null;

        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("dibujo.txt"), "utf-8"));
            writer.write("La escuela debe estar ubicada en (" + pos_escuela.x + ", " + pos_escuela.y + ")\n\n");
            
            // Primeros 4 espacios del numero
            writer.write("     ");
            for (int i = 0; i < tamMatriz-1; i++) { writer.write(fin_linea + "-");}
            writer.write(fin_linea+"\n");
            
            for (int i = tamMatriz-1; i >= 0; i--) {
                if(i > 999) writer.write(i);
                else if(i > 99) writer.write(i+" ");
                else if(i > 9) writer.write(i+"  ");
                else writer.write(i+"   ");
                for (int j = 0; j < tamMatriz; j++) {
                    writer.write("|");
                    if(j == pos_escuela.x && i == pos_escuela.y)
                        writer.write(nombre_escuela);
                    else if(nombres[j][i] != null){
                        writer.write(nombres[j][i]);
                        if(nombres[j][i].length() < tamMaxString){
                            int espacios = tamMaxString - nombres[j][i].length();
                            for (int k = 0; k < espacios; k++) {writer.write(" ");}
                        }
                    }else{
                        writer.write(espacio_palabra);
                    }
                }
                writer.write("|\n");
            }
            // Primeros 4 espacios del numero
            writer.write("    -");
            for (int i = 0; i < tamMatriz; i++) {
                if(tamMaxString < 2){
                    if(i > 9) {
                        writer.write(String.valueOf(i % 10));
                    }
                    else {
                        writer.write(String.valueOf(i));
                    }
                }
                else if(tamMaxString < 3){
                    if(i > 99) {
                        writer.write(String.valueOf(i % 100));
                    }
                    else if(i > 9){
                        writer.write(String.valueOf(i));
                    }
                    else {
                        writer.write(i + "-");
                    }
                }
                else if(tamMaxString < 4){
                    if(i > 999) {
                        writer.write(String.valueOf(i % 1000));
                    }
                    else if(i > 99) {
                        writer.write(i);
                    }
                    else if(i > 9){
                        writer.write(i + "-");
                    }
                    else {
                        writer.write(i + "--");
                    }
                }
                else {
                    writer.write(String.valueOf(i));
                    if(i > 99) for (int j = 0; j < tamMaxString - 3; j++) { writer.write("-"); }
                    else if(i > 9) for (int j = 0; j < tamMaxString - 2; j++) { writer.write("-"); }
                    else for (int j = 0; j < tamMaxString - 1; j++) { writer.write("-"); }
                }
                writer.write("-");
            }
            
            writer.write("\n\n\nNota: La escuela se denota con un asterisco (*)");
        } catch (IOException ex) {
            System.out.println("Error ex: " + ex.getMessage());
        } finally {
            try {
                writer.close();
            } catch (Exception ex) {/*ignore*/}
        }

    }
    
    /*
    // TEST
    public static void main(String[] args) {
        
        HashMap<String, Point> m = new HashMap<>();
        m.put("Palmira", new Point(2,  3));
        m.put("Cali",    new Point(10, 2));
        m.put("Buga",    new Point(11, 0));
        m.put("Tulua",   new Point(0,  3));
        m.put("Rio frio", new Point(1,  2));
        dibuja(m, 12, new Point(2, 2));
    }*/

}
