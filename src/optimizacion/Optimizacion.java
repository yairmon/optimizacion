/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package optimizacion;

import java.awt.Point;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *  Se encarga de hacer el proceso de seleccionar la mejor posicion
 * para el colegio
 */
public class Optimizacion {
    
    private final HashMap<String, Point> matriz_ciudades;
    private int xMax;
    private int xMin;
    private int yMax;
    private int yMin;

    public Optimizacion(HashMap matriz) {
        this.matriz_ciudades = matriz;
        xMax = Integer.MIN_VALUE;
        yMax = Integer.MIN_VALUE;
        xMin = Integer.MAX_VALUE;
        yMin = Integer.MAX_VALUE;
        
        Iterator it = matriz.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry item = (Map.Entry)it.next();
            int x = ((Point) item.getValue()).x;
            int y = ((Point) item.getValue()).y;
            if(xMax < x) xMax = x;
            if(xMin > x) xMin = x;
            if(yMax < y) yMax = y;
            if(yMin > y) yMin = y;
        }
    }
    
    /**
     * Devuelve el punto donde debe ir el colegio
     * 
     * @return Mejor punto
     */
    public Point obtenerUbicacion(){
        int distancia_menor = Integer.MAX_VALUE;
        Point salida = new Point(0, 0);
        /*
        i = posX del colegio
        j = posY del colegio
        x = posX de la ciudad
        y = posY de la ciudad
        sum = sumatoria de las distancias entre el colegio y las ciudades
        */
        for (int i = xMin; i <= xMax; i++) {
            for (int j = yMin; j < yMax; j++) {
                int sum = 0;
                int x = 0;
                int y = 0;
                for (Map.Entry item : matriz_ciudades.entrySet()) {
                    x = ((Point) item.getValue()).x;
                    y = ((Point) item.getValue()).y;
                    sum += Math.abs(i - x) + Math.abs(j - y);
                }
                if(sum < distancia_menor){
                    distancia_menor = sum;
                    salida.x = i;
                    salida.y = j;
                }
            }
        }
        return salida;
    }
    
    /*
    //TEST
    public static void main(String[] args) {
        HashMap<String, Point> m = new HashMap<>();
//        m.put("Palmira", new Point(2,  3));
//        m.put("Cali",    new Point(10, 2));
//        m.put("Buga",    new Point(11, 0));
//        m.put("Tulua",   new Point(0,  3));
//        m.put("RioFrio", new Point(1,  2));
        m.put("Palmira", new Point(1, 4));
        m.put("Cali",    new Point(2, 7));
        m.put("Buga",    new Point(1, 9));
        m.put("Tulua",   new Point(3, 9));
        Optimizacion o = new Optimizacion(m);
        Point p = o.obtenerUbicacion();
        System.out.println("Punto: (" + p.x + "," + p.y + ")");
    }
    
    */
}
