/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.File;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

/**
 *
 * @author Acer
 */
public class Detectar {

    private CascadeClassifier objClasificador;

    public Detectar() {
        try {
            File miDir = new File(".");
            String sRuta = miDir.getCanonicalPath()+ "\\algoritmo\\haarcascades\\haarcascade_frontalface_alt.xml";
            sRuta = sRuta.replace("\\", "/");
            //Se lee el archivo Haar que le permite a OpenCV detectar rostros frontales en una imagen
            objClasificador = new CascadeClassifier(sRuta);
            if (objClasificador.empty()) {
                System.out.println("Error al cargar el algoritmo de lectura.");
                return;
            } else {
                System.out.println("Se cargo correctamente el algoritmo de deteccion.");
            }
        } catch (Exception e) {
        }
    }

    public Mat detecta(Mat frameDeEntrada) {
        try {
            Mat mRgba = new Mat();
            Mat mGrey = new Mat();
            MatOfRect rostros = new MatOfRect();
            frameDeEntrada.copyTo(mRgba);
            frameDeEntrada.copyTo(mGrey);
            Imgproc.cvtColor(mRgba, mGrey, Imgproc.COLOR_BGR2GRAY);
            Imgproc.equalizeHist(mGrey, mGrey);
            objClasificador.detectMultiScale(mGrey, rostros);
            System.out.println(String.format("El sistema detecto %s rostros", rostros.toArray().length));
            for (Rect rect : rostros.toArray()) {
                //Se dibuja un rectángulo donde se ha encontrado el rostro
                Imgproc.rectangle(mRgba, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(255, 0, 0));

            }
            return mRgba;
        } catch (Exception e) {
        }
        return new Mat();
    }
}
