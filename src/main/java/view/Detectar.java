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
import org.bytedeco.javacpp.*;
import org.bytedeco.javacpp.presets.opencv_core.Ptr;

/**
 *
 * @author Acer
 */
public class Detectar {

    private CascadeClassifier objClasificador;
    private String sPath;
    private FaceRecognizer oReconocer;

    public Detectar() {
        try {
            File miDir = new File(".");
            sPath = miDir.getCanonicalPath();
            String sRuta = miDir.getCanonicalPath() + "\\algoritmo\\haarcascades\\haarcascade_frontalface_alt.xml";
//            String sRuta = miDir.getCanonicalPath()+ "\\algoritmo\\lbpcascades\\lbpcascade_frontalface.xml";
            sRuta = sRuta.replace("\\", "/");
            //Se lee el archivo Haar que le permite a OpenCV detectar rostros frontales en una imagen
            objClasificador = new CascadeClassifier(sRuta);
            
//            oReconocer = createLBPHFaceRecognizer();
//            entrenar();
            if (objClasificador.empty()) {
                System.out.println("Error al cargar el algoritmo de lectura.");
                return;
            } else {
                System.out.println("Se cargo correctamente el algoritmo de deteccion.");
            }
        } catch (Exception e) {
        }
    }

    public Object[] detecta(Mat frameDeEntrada) {
        Object[] oObject = new Object[2];

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
                Imgproc.rectangle(mRgba, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(255, 255, 255));
            }
            oObject[0] = mRgba;
            if (rostros.toArray().length > 0) {
                oObject[1] = String.format("El sistema detecto %s rostros", rostros.toArray().length);
            } else {
                oObject[1] = "";
            }
//            IplImage target = new IplImage();
//            target = cvLoadImage(sPath + "prueba.jpg");
//            this.identificarRostro(target);
            return oObject;
        } catch (Exception e) {
        }
        oObject[0] = new Mat();
        oObject[1] = "";
        return oObject;
    }

//    public IplImage[] LeerImg(String sNameFile) {
//        String subDirectory = sPath + "\\fotos\\";
//        File sDirectory = new File(subDirectory);
//        IplImage[] imagenes = null;
//        if (sDirectory.isDirectory() == true && sDirectory.exists() == true) {
//            String[] ficheros = sDirectory.list();
//            imagenes = new IplImage[ficheros.length];
//            for (int i = 0; i < ficheros.length; i++) {
//                // nombre de la imagen id_i.jpg
//                String sName = subDirectory + sNameFile + "_" + i + ".jpg";
//                IplImage img = cvLoadImage(sName);
//                imagenes[i] = img;
//            }
//        }
//        return imagenes;
//    }
//    public void entrenar() {
//        int iNumElements = 10;
//        MatVector imagenesEntrenamiento = new MatVector(iNumElements);
//        CvMat etiquetas = CvMat.create(iNumElements, 1, CV_32SC1);
//        int count = 0;
//        IplImage[] imagenes = LeerImg("1");
//        int ikeyPersona = 1;
//        for (int i = 0; i < imagenes.length; i++) {
//            etiquetas.put(count, 0, ikeyPersona);
//            IplImage gris = IplImage.create(imagenes[i].width(), imagenes[i].height(), IPL_DEPTH_8U, 1);
//            cvCvtColor(imagenes[i], gris, CV_BGR2GRAY);
//            imagenesEntrenamiento.put(count, gris);
//            count++;
//        }
//        this.oReconocer.train(imagenesEntrenamiento, etiquetas);
//    }
//    public String identificarRostro(IplImage oImagen) {
//        String personName = "";
//
//        int[] ids = new int[1];
//        double[] distance = new double[1];
//        int result = -1;
//
//        this.oReconocer.predict(oImagen, ids, distance);
//        result = ids[0];
//
//        if (result > -1 && distance[0] < 70) {
//            personName = (String) Integer.toString(result);
//        }
//
//        return personName;
//    }
}
