/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deteccion_opencv;

import org.opencv.core.Core;
import view.Presentacion;

/**
 *
 * @author Acer
 */
public class Deteccion_opencv {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String arg[]) throws InterruptedException {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Presentacion objPresentacion = new Presentacion();
        objPresentacion.setVisible(true);
        objPresentacion.setResizable(false);
    }
}
