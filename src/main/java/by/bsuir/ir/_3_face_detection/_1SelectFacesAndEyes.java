package by.bsuir.ir._3_face_detection;

import by.bsuir.ir.AbstarctOpenCV;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.HighGui;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

/**
 * @author <a href="mailto:roman.gotovko@pstlabs.by">Roman Gotovko</a>
 * @since 2020-04-18
 */
public class _1SelectFacesAndEyes extends AbstarctOpenCV {

  private static final String FACE_CLASSIFIER_PATH = "D:\\opencv\\opencv\\build\\etc\\haarcascades\\haarcascade_frontalface_alt2.xml";
  private static final String EYE_CLASSIFIER_PATH = "D:\\opencv\\opencv\\build\\etc\\haarcascades\\haarcascade_eye.xml";

  public static void main(String[] args) {
    URL resource = _1SelectFacesAndEyes.class.getResource("/faces.jpg");
    Mat img = getImage(resource);
    // Матрица изображения в градациях серого
    Mat img_gray = new Mat(img.size(), CvType.CV_8UC1);
    Imgproc.cvtColor(img, img_gray, Imgproc.COLOR_BGR2GRAY, 1);
    // Выравнивание гисторграммы
    Imgproc.equalizeHist(img_gray, img_gray);
    // Загрузка классификатора для поиска лиц
    CascadeClassifier cascade = new CascadeClassifier(FACE_CLASSIFIER_PATH);
    //Вектор координинат квадратов (диагональные точки)
    MatOfRect objects = new MatOfRect();
    //Обнаружение объектов
    cascade.detectMultiScale(img_gray, objects, 1.1, 3, 0, new Size(30, 30));
    objects
        .toList()
        .forEach(rect -> Imgproc.rectangle(img, rect, new Scalar(0, 0, 255)));
    // Загрузка классификатора для поиска глаз
    CascadeClassifier cascade2 = new CascadeClassifier(EYE_CLASSIFIER_PATH);
    MatOfRect objects2 = new MatOfRect();
    cascade2.detectMultiScale(img_gray, objects2, 1.1, 5, 0, new Size(2, 2));
    objects2
        .toList()
        .forEach(rect -> Imgproc.rectangle(img, rect, new Scalar(0, 255, 0)));
    HighGui.imshow("Result face and eve", img);
    HighGui.waitKey();
  }

}
