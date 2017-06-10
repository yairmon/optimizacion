/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package optimizacion;

import java.awt.Point;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

/**
 *  Convierte los datos de entrada en una matriz
 */
public class Controlador {
    
    private int tamMatriz;
    private final HashMap<String, Point> matriz;

    public Controlador(String entrada) {
        matriz = new HashMap();
        cargarEntrada(entrada);
    }
    
    private void cargarEntrada(String entrada){
        StringTokenizer st = new StringTokenizer(entrada,"\n");
        try {
            tamMatriz = Integer.parseInt(st.nextToken());
            int ciudades = Integer.parseInt(st.nextToken());
            for (int i = 0; i < ciudades; i++) {
                StringTokenizer st2 = new StringTokenizer(st.nextToken());
                String nombre = st2.nextToken();
                int x = Integer.parseInt(st2.nextToken());
                int y = Integer.parseInt(st2.nextToken());
                matriz.put(nombre, new Point(x, y));
            }
            System.out.println("Se han cargado todos los datos");
        } catch (NoSuchElementException | NumberFormatException e) {
            System.out.println("El archivo no tiene el formato adecuado");
            System.out.println("Error: " + e.getMessage());
        }
    }

    public int getTamMatriz() {
        return tamMatriz;
    }

    public void setTamMatriz(int tamMatriz) {
        this.tamMatriz = tamMatriz;
    }
    
    
    
    
}
