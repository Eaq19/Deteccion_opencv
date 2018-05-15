/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.File;
import org.bytedeco.javacpp.opencv_face.FaceRecognizer;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import static org.opencv.core.Core.FONT_ITALIC;
import org.opencv.face.LBPHFaceRecognizer;
import org.opencv.imgcodecs.Imgcodecs;

/**
 *
 * @author Acer
 */
public class Detectar {

    private CascadeClassifier objClasificador;
    private String sPath;

    public Detectar() {
        try {
            File miDir = new File(".");
            sPath = miDir.getCanonicalPath();
            String sRuta = miDir.getCanonicalPath() + "\\algoritmo\\haarcascades\\haarcascade_frontalface_alt.xml";
//            String sRuta = miDir.getCanonicalPath()+ "\\algoritmo\\lbpcascades\\lbpcascade_frontalface.xml";
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

    public Object[] detecta(Mat frameDeEntrada, boolean guardar, String sName) {
        Object[] oObject = new Object[3];

        try {
            Mat mRgba = new Mat();
            Mat mGrey = new Mat();
            MatOfRect rostros = new MatOfRect();
            frameDeEntrada.copyTo(mRgba);
            frameDeEntrada.copyTo(mGrey);
            Imgproc.cvtColor(mRgba, mGrey, Imgproc.COLOR_BGR2GRAY);
            Imgproc.equalizeHist(mGrey, mGrey);
            objClasificador.detectMultiScale(mGrey, rostros);
            for (Rect rect : rostros.toArray()) {
                //Se dibuja un rectángulo donde se ha encontrado el rostro
                Imgproc.putText(mRgba, sName, new Point(rect.x, rect.y), FONT_ITALIC, 1.0, new Scalar(255, 255, 255));
                Imgproc.rectangle(mRgba, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(255, 255, 255));
            }
            oObject[0] = mRgba;
            oObject[1] = "";
            oObject[2] = "-1";
            if (rostros.toArray().length > 0) {
                if (guardar) {
                    String subDirectory = sPath + "\\fotos\\";
                    File sDirectory = new File(subDirectory);
                    sDirectory.mkdirs();
                    if (sDirectory.isDirectory() == true && sDirectory.exists() == true) {
                        String sImgName = subDirectory + sName + ".jpg";
                        File sArchivo = new File(sImgName);
                        int count = 0;
                        do {
                            if (sArchivo.exists()) {
                                sImgName = subDirectory + sName + count + ".jpg";
                                sArchivo = new File(sImgName);
                                count++;
                            }
                        } while (sArchivo.exists());
                        Imgcodecs.imwrite(sImgName, frameDeEntrada);
                        oObject[2] = "1";
                    }
                }
                oObject[1] = String.format("El sistema detecto %s rostros", rostros.toArray().length);
            }
            return oObject;
        } catch (Exception e) {
        }
        oObject[0] = new Mat();
        oObject[1] = "";
        return oObject;
    }

}
