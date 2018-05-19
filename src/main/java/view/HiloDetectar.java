/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Conexion;
import org.opencv.core.*;
import org.opencv.videoio.VideoCapture;

/**
 *
 * @author Acer
 */
public class HiloDetectar extends Thread {

    private Detectar objDetectar = null;
    private Presentacion ObjJFrame = null;
    private boolean ejecucion = true;
    private Conexion objConexion;

    public HiloDetectar(Presentacion ObjJFrame, Conexion objConexion) {
        // Leyendo librería nativa
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        this.objDetectar = new Detectar();
        this.ObjJFrame = ObjJFrame;
        this.objConexion = objConexion;
    }

    @Override
    public void run() {
        try {
            boolean bAux = true;
            // Se crea una matriz que contendrá la imagen
            Mat imagenDeWebCam = new Mat();
            VideoCapture webCam = new VideoCapture(0);
            if (webCam.isOpened()) {
//                Thread.sleep(500); // Se interrumpe el thread para permitir que la webcam se inicialice
                while (this.ejecucion) {
                    webCam.read(imagenDeWebCam);
                    Thread.sleep(200);
                    if (!imagenDeWebCam.empty()) {
                        if (bAux) {
                            this.ObjJFrame.setSize(imagenDeWebCam.width() + 170, imagenDeWebCam.height() + 100);
                            bAux = false;
                        }
                        // Invocamos la rutina de opencv que detecta rostros sobre la imagen obtenida por la webcam
                        Object[] oObject = this.objDetectar.detecta(imagenDeWebCam, this.ObjJFrame.bGuardar, this.ObjJFrame.txtNombreImg.getText(), this.ObjJFrame.txtDocumentoImg.getText(), this.objConexion);
                        if (!oObject[1].equals("")) {
                            this.ObjJFrame.txtAreaDeteccion.setText(this.ObjJFrame.txtAreaDeteccion.getText() + oObject[1] + "\n");
                        }
                        if (oObject[2].equals("1")) {
                            this.ObjJFrame.fnSaveParams(this.ObjJFrame.txtNombreImg.getText(), oObject[3].toString());
                        }
                        // Muestra la imagen
                        this.ObjJFrame.setMathImage((Mat) oObject[0]);
                    } else {
                        System.out.println("No se capturó nada");
                        break;
                    }
                }
            }
            webCam.release(); // Se libera el recurso de la webcam
        } catch (Exception e) {
        }
    }

    public void detener() {
        this.ejecucion = false;
        System.out.println("Detener ejecucion");
    }
}
