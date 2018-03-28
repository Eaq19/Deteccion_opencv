/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

/**
 *
 * @author Acer
 */
public class HiloDetectar extends Thread {

    private Detectar objDetectar = null;
    private PanelCamara ObjPanel = null;
    private boolean ejecucion = true;

    public HiloDetectar() {
        // Leyendo librería nativa
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        this.objDetectar = new Detectar();
        this.ObjPanel = new PanelCamara();
    }
    
    @Override
    public void run() {
        try {
            JFrame frame = new JFrame("Detección de rostros");
            frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            frame.setSize(400, 400);
            frame.setBackground(Color.BLUE);
            frame.add(this.ObjPanel, BorderLayout.CENTER);
            frame.setVisible(true);

            // Se crea una matriz que contendrá la imagen
            Mat imagenDeWebCam = new Mat();
            VideoCapture webCam = new VideoCapture(0);

            if (webCam.isOpened()) {
//                Thread.sleep(500); // Se interrumpe el thread para permitir que la webcam se inicialice
                while (this.ejecucion) {
                    webCam.read(imagenDeWebCam);
                    if (!imagenDeWebCam.empty()) {
                        Thread.sleep(100); // Permite que la lectura se complete
                        frame.setSize(imagenDeWebCam.width() + 10, imagenDeWebCam.height() + 10);
                        // Invocamos la rutina de opencv que detecta rostros sobre la imagen obtenida por la webcam
                        imagenDeWebCam = this.objDetectar.detecta(imagenDeWebCam);
                        // Muestra la imagen
                        this.ObjPanel.convierteMatABufferedImage(imagenDeWebCam);
                        this.ObjPanel.repaint();
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
        System.out.println("Detectar ejecucion");
    }
}
