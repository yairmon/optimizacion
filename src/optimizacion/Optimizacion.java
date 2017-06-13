/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package optimizacion;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import lpsolve.*;

/**
 * Se encarga de hacer el proceso de seleccionar la mejor posicion para el
 * colegio
 */
public class Optimizacion {

    private final HashMap<String, Point> matriz_ciudades;
    private final int cantCiudades;
    private final int tamMatriz;

    public Optimizacion(HashMap matriz, int tamMatriz) {
        this.matriz_ciudades = matriz;
        this.tamMatriz = tamMatriz;
        cantCiudades = matriz.size();
    }

    /**
     * Devuelve el punto donde debe ir el colegio 
     * Usando LP_Solve
     * 
     * @return Mejor punto
     */
    public Point obtenerUbicacion() {
        Point salida = new Point(0, 0);

        try {
            // cada ciudad tiene 4 rest y se suman X y Y
            int totalVariables = (cantCiudades * 4) + 2;
            
            // Poner como funcion objetivo las veces que hayan ciudades (5 ciudades -> 5 veces las variables)
            String r_principal = "";
            for(int i = 0; i < cantCiudades; i++) r_principal += "1 -1 1 -1 ";
            r_principal += "0 0";
            
            String r1_x_inicio = "1 -1 0 0 ";
            String r2_x_inicio = "1 -1 0 0 ";
            String r1_y_inicio = "0 0 1 -1 ";
            String r2_y_inicio = "0 0 1 -1 ";
            
            String r1_x_fin = " -1 0";
            String r2_x_fin = " 1 0";
            String r1_y_fin = " 0 -1";
            String r2_y_fin = " 0 1";
            // Create a problem with 4 variables and 0 constraints
            LpSolve solver = LpSolve.makeLp(0, totalVariables); 
            
            // agregar restricciones
            int contador = 0;
            for (Map.Entry item : matriz_ciudades.entrySet()) {
                int x = ((Point) item.getValue()).x;
                int y = ((Point) item.getValue()).y;
                String r1 = "";
                String r2 = "";
                String r3 = "";
                String r4 = "";
                for (int i = 0; i < contador; i++) {
                    r1 += "0 0 0 0 ";
                    r2 += "0 0 0 0 ";
                    r3 += "0 0 0 0 ";
                    r4 += "0 0 0 0 ";
                }
                
                r1 += r1_x_inicio;
                r2 += r2_x_inicio;
                r3 += r1_y_inicio;
                r4 += r2_y_inicio;
                
                for (int i = contador+1; i < cantCiudades; i++) {
                    r1 += "0 0 0 0 ";
                    r2 += "0 0 0 0 ";
                    r3 += "0 0 0 0 ";
                    r4 += "0 0 0 0 ";
                }
                
                r1 += r1_x_fin;
                r2 += r2_x_fin;
                r3 += r1_y_fin;
                r4 += r2_y_fin;
                
                solver.strAddConstraint(r1, LpSolve.GE, (x * -1));
                solver.strAddConstraint(r2, LpSolve.GE, x);
                solver.strAddConstraint(r3, LpSolve.GE, (y * -1));
                solver.strAddConstraint(r4, LpSolve.GE, y);
                
            }
            
            /*
            Restricciones finales
            x>=0; 
            y>=0; 

            x<=10; 
            y<=10;
            */
            String r_final = "";
            for (int i = 0; i < cantCiudades; i++) {
                r_final += "0 0 0 0 ";
            }
            solver.strAddConstraint(r_final + "1 0", LpSolve.GE, 0); // x>=0;
            solver.strAddConstraint(r_final + "0 1", LpSolve.GE, 0); // y>=0;
            solver.strAddConstraint(r_final + "1 0", LpSolve.LE, tamMatriz); // x<=tamMatriz;
            solver.strAddConstraint(r_final + "0 1", LpSolve.LE, tamMatriz); // y<=tamMatriz;

            // t1-t2+t3-t4 + s1-s2+s3-s4 + m1-m2+m3-m4 + d1-d2+d3-d4 + e1-e2+e3-e4
            solver.strSetObjFn(r_principal);

            // solve the problem
            solver.solve();

            // print solution
            System.out.println("Value of objective function: " + solver.getObjective());
            double[] var = solver.getPtrVariables();
            salida.x = (int) var[var.length-2];
            salida.y = (int) var[var.length-1];
            for (int i = 0; i < var.length; i++) {
                System.out.println("Value of var[" + i + "] = " + var[i]);
            }

            // delete the problem and free memory
            solver.deleteLp();
        } catch (LpSolveException e) {
            e.printStackTrace();
        }
        return salida;
    }

    /*
    //TEST
    public static void main(String[] args) {
        HashMap<String, Point> m = new HashMap<>();
        m.put("Palmira", new Point(2,  3));
        m.put("Cali",    new Point(10, 2));
        m.put("Buga",    new Point(11, 0));
        m.put("Tulua",   new Point(0,  3));
        m.put("RioFrio", new Point(1,  2));
//        m.put("Palmira", new Point(1, 4));
//        m.put("Cali",    new Point(2, 7));
//        m.put("Buga",    new Point(1, 9));
//        m.put("Tulua",   new Point(3, 9));
        Optimizacion o = new Optimizacion(m,12);
        Point p = o.obtenerUbicacion();
        //System.out.println("Punto: (" + p.x + "," + p.y + ")");
    }*/
    
     
}
