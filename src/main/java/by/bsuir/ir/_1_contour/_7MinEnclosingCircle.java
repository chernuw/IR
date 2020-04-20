package by.bsuir.ir._1_contour;

import by.bsuir.ir.AbstarctOpenCV;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.highgui.HighGui;
import org.opencv.imgproc.Imgproc;

/**
 * @author <a href="mailto:roman.gotovko@pstlabs.by">Roman Gotovko</a>
 * @since 2020-04-09
 */
public class _7MinEnclosingCircle extends AbstarctOpenCV {

  private static final String SOURCE_IMAGE_TEXT = "Исходное изображение";
  private static final String RESULT = "Результат";

  public static void main(String[] args) {
    // Чтение изображения
    URL resource = _7MinEnclosingCircle.class.getResource("/figures2.png");
    Mat src = getImage(resource);

    /*HighGui.namedWindow(SOURCE_IMAGE_TEXT, HighGui.WINDOW_AUTOSIZE);
    HighGui.imshow(SOURCE_IMAGE_TEXT, src);*/

    // Создание копии изображения
    Mat src_b = src.clone();
    // Конвертирование изображ. в оттенки серого
    Imgproc.cvtColor(src, src_b, Imgproc.COLOR_RGB2GRAY);
    // Конвертирование в бинарное изображение
    Imgproc.threshold(src_b, src_b, 220, 255, Imgproc.THRESH_BINARY);
    // Поиск контуров
    List<MatOfPoint> contours = new ArrayList<>();
    Imgproc
        .findContours(src_b, contours, new Mat(), Imgproc.RETR_CCOMP, Imgproc.CHAIN_APPROX_SIMPLE);
    // Отображение контуров
    for (int i = 0; i < contours.size(); i++) {
      Random random = new Random();
      Scalar color = new Scalar(random.nextInt(255), random.nextInt(255), random.nextInt(255));
      Imgproc.drawContours(src, contours, i, color, 3);

      MatOfPoint2f point2f = new MatOfPoint2f(contours.get(i).toArray());
      Point ctr = new Point();
      float[] rad = new float[1];
      Imgproc.minEnclosingCircle(point2f, ctr, rad);
      Imgproc.circle(src, ctr, (int) rad[0], new Scalar(0,0,0), 1, Imgproc.LINE_4);
    }

    // Вывод изображения с контурами на экран
    HighGui.namedWindow(RESULT, HighGui.WINDOW_AUTOSIZE);
    HighGui.imshow(RESULT, src);
    HighGui.waitKey();
  }

}
