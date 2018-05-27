/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Conexion;
import java.io.File;
import java.io.FilenameFilter;
import java.nio.IntBuffer;
import org.bytedeco.javacpp.opencv_face.FaceRecognizer;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import static org.opencv.core.Core.FONT_ITALIC;
import org.opencv.imgcodecs.Imgcodecs;
import static org.bytedeco.javacpp.opencv_core.CV_32SC1;
import static org.bytedeco.javacpp.opencv_core.CV_8UC1;
import static org.bytedeco.javacpp.opencv_imgcodecs.imread;
import static org.bytedeco.javacpp.opencv_imgcodecs.CV_LOAD_IMAGE_GRAYSCALE;

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.javacpp.opencv_core;
import static org.bytedeco.javacpp.opencv_core.CV_32SC1;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_face.FaceRecognizer;
import org.bytedeco.javacpp.opencv_face.FisherFaceRecognizer;
import org.bytedeco.javacpp.opencv_face.EigenFaceRecognizer;
import org.bytedeco.javacpp.opencv_face.LBPHFaceRecognizer;
import org.bytedeco.javacpp.opencv_core.MatVector;
import static org.bytedeco.javacpp.opencv_imgcodecs.CV_LOAD_IMAGE_GRAYSCALE;
import static org.bytedeco.javacpp.opencv_imgcodecs.cvLoadImage;
import static org.bytedeco.javacpp.opencv_imgcodecs.imread;

/**
 *
 * @author Acer
 */
public class Detectar {

    private CascadeClassifier objClasificador;
    private String sPath;
    private FaceRecognizer faceRecognizer;

    public Detectar() {
        try {
            File miDir = new File(".");
            sPath = miDir.getCanonicalPath();
            String sRuta = miDir.getCanonicalPath() + "\\algoritmo\\haarcascades\\haarcascade_frontalface_alt.xml";
//            String sRuta = miDir.getCanonicalPath()+ "\\algoritmo\\lbpcascades\\lbpcascade_frontalface.xml";
            sRuta = sRuta.replace("\\", "/");
            //Se lee el archivo Haar que le permite a OpenCV detectar rostros frontales en una imagen
            objClasificador = new CascadeClassifier(sRuta);
            faceRecognizer = LBPHFaceRecognizer.create();
            this.fnEntrenar();
            File sDirectory2 = new File(sPath + "\\prueba\\");
            sDirectory2.mkdirs();
            if (objClasificador.empty()) {
                System.out.println("Error al cargar el algoritmo de lectura.");
                return;
            } else {
                System.out.println("Se cargo correctamente el algoritmo de deteccion.");
            }
        } catch (Exception e) {
        }
    }

    public Object[] detecta(Mat frameDeEntrada, boolean guardar, String sName, String iUltimo, Conexion objConexion) {
        Object[] oObject = new Object[4];
        try {
            Mat mRgba = new Mat();
            Mat mGrey = new Mat();
            MatOfRect rostros = new MatOfRect();
            frameDeEntrada.copyTo(mRgba);
            frameDeEntrada.copyTo(mGrey);
            Imgproc.cvtColor(mRgba, mGrey, Imgproc.COLOR_BGR2GRAY);
            Imgproc.equalizeHist(mGrey, mGrey);
            objClasificador.detectMultiScale(mGrey, rostros);
            oObject[1] = "";
            oObject[2] = "-1";
            oObject[3] = "";
            if (rostros.toArray().length > 0) {
                String sPrueba = sPath + "\\prueba\\prueba.jpg";
                Imgcodecs.imwrite(sPrueba, frameDeEntrada);
                opencv_core.Mat img = imread(sPrueba, CV_LOAD_IMAGE_GRAYSCALE);
                int[] id = new int[1];
                double[] distancia = new double[1];
                int result = -1;
                faceRecognizer.predict(img, id, distancia);
                result = id[0];
                System.out.println("Distancia: " + distancia[0]);
                if (result > -1 && distancia[0] < 48) {
                    System.out.println("Distancia: " + distancia[0]);
                    oObject[3] = objConexion.getById(String.valueOf(result));
                } else {
                    oObject[3] = "Desconocido";
                }
            }
            if (!sName.equals("")) {
                oObject[3] = sName;
            }
            for (Rect rect : rostros.toArray()) {
                //Se dibuja un rectángulo donde se ha encontrado el rostro
                Imgproc.putText(mRgba, oObject[3].toString(), new Point(rect.x, rect.y), FONT_ITALIC, 1.0, new Scalar(255, 255, 255));
                Imgproc.rectangle(mRgba, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(255, 255, 255));
            }
            oObject[0] = mRgba;
            if (rostros.toArray().length > 0) {
                if (guardar) {
                    String subDirectory = sPath + "\\fotos\\";
                    File sDirectory = new File(subDirectory);
                    sDirectory.mkdirs();
                    if (sDirectory.isDirectory() == true && sDirectory.exists() == true) {
                        int count = 0;
                        String sImgName = subDirectory + iUltimo + "-" + count + ".jpg";
                        File sArchivo = new File(sImgName);
                        do {
                            if (sArchivo.exists()) {
                                sImgName = subDirectory + iUltimo + "-" + count + ".jpg";
                                sArchivo = new File(sImgName);
                                count++;
                            }
                        } while (sArchivo.exists());
                        oObject[3] = sImgName;
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

    private void fnEntrenar() {
        String trainingDir = sPath + "\\fotos\\";
        File root = new File(trainingDir);
        FilenameFilter imgFilter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                name = name.toLowerCase();
                return name.endsWith(".jpg") || name.endsWith(".pgm") || name.endsWith(".png");
            }
        };
        File[] imageFiles = root.listFiles(imgFilter);
        MatVector images = new MatVector(imageFiles.length);
        opencv_core.Mat labels = new opencv_core.Mat(imageFiles.length, 1, CV_32SC1);
        IntBuffer labelsBuf = labels.createBuffer();
        int counter = 0;
        for (File image : imageFiles) {
            opencv_core.Mat img = imread(image.getAbsolutePath(), CV_LOAD_IMAGE_GRAYSCALE);
            int label = Integer.parseInt(image.getName().split("\\-")[0]);
            images.put(counter, img);
            labelsBuf.put(counter, label);
            counter++;
        }
        faceRecognizer.train(images, labels);
    }

}
