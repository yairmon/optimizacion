/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package optimizacion;

import java.awt.Point;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import vista.Dibujar;

/**
 *  Convierte los datos de entrada en una matriz 
 *      {nombre_ciudad, (x,y)}
 */
public class Controlador {
    
    private int tamMatriz;
    private final HashMap<String, Point> matriz;

    public Controlador(String entrada) throws FileNotFoundException {
        matriz = new HashMap();
        cargarEntrada(entrada);
    }
    
    /**
     * Se encarga de asignar los valores del archivo en una matriz
     *      {nombre_ciudad, (x,y)}
     * @param entrada El archivo de texto en forma de String
     * @throws FileNotFoundException Cuando el archivo no contiene el formato correcto
     */
    private void cargarEntrada(String entrada) throws FileNotFoundException {
        StringTokenizer st = new StringTokenizer(entrada,"\n");
        try {
            tamMatriz = Integer.parseInt(st.nextToken());
            int ciudades = Integer.parseInt(st.nextToken());
            for (int i = 0; i < ciudades; i++) {
                StringTokenizer st2 = new StringTokenizer(st.nextToken());
                String nombre = st2.nextToken();
                int x = Integer.parseInt(st2.nextToken());
                int y = Integer.parseInt(st2.nextToken());
                if(x < tamMatriz && y < tamMatriz)
                    matriz.put(nombre, new Point(x, y));
                else throw new IllegalAccessError("Existen ciudades por fuera del rango...\nRango actual: " + tamMatriz);
            }
//            System.out.println("Se han cargado todos los datos");
        } catch (NoSuchElementException | NumberFormatException e) {
            System.out.println("El archivo no tiene el formato adecuado");
            System.out.println("Error: " + e.getMessage());
            throw new FileNotFoundException("El archivo no tiene el formato adecuado");
        } catch (IllegalAccessError e) {
            throw new FileNotFoundException(e.getMessage());
        }
    }

    public int getTamMatriz() {
        return tamMatriz;
    }

    public void setTamMatriz(int tamMatriz) {
        this.tamMatriz = tamMatriz;
    }
    
    /**
     * Obtiene la ubicacion del colegio
     * @return Devuelve "El colegio debe ser ubicado en (x,y)"
     */
    public String obtenerUbicacion(){
        try {
            Optimizacion o = new Optimizacion(matriz);
            Point p = o.obtenerUbicacion();
            Dibujar.dibuja(matriz, tamMatriz, p);
            return "El colegio debe ser ubicado en ("+ p.x + ", " + p.y + ")";
        } catch (Exception e) {
            System.out.println("Error desconocido: " + e.getMessage() + ".... " + e.toString());
            return "No se puede calcular...";
        }
    }
    
    
    
    
}
