package by.bsuir.ir._1_contour;

import by.bsuir.ir.AbstractOpenCV;
import java.net.URL;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.HighGui;
import org.opencv.imgproc.Imgproc;

/**
 * @author <a href="mailto:roman.gotovko@pstlabs.by">Roman Gotovko</a>
 * @since 2020-04-09
 */
public class _3ContourCanny extends AbstractOpenCV {

  private static final String SOURCE_IMAGE_TEXT = "Исходное изображение";
  public static final String WINDOW_NAME = "Детектор границ Канни";
  private static final String RESULT = "Результат";
  private static final int THRESHOLD_1 = 0; //нижний порог гистерезиса (отклика системы)

  public static void main(String[] args) {
    // Чтение изображения
    URL resource = _3ContourCanny.class.getResource("/colored.jpg");
    Mat src = getImage(resource);

    // Создание матрицы, аналогичной исходной (для отображения границ)
    // Заполн. изображ. нулями (черным цветом)
    Mat dst = new Mat(src.size(), src.type(), Scalar.all(0));
    dst.create(src.size(), src.type());

    // Конверт. матрицы в оттенки серого
    Mat src_g = new Mat();
    Imgproc.cvtColor(src, src_g, Imgproc.COLOR_BGR2GRAY);
    HighGui.namedWindow(WINDOW_NAME, HighGui.WINDOW_AUTOSIZE);
//    trackbar
    fCanny(src_g, dst, src);
    HighGui.waitKey();
  }

  public static void fCanny(Mat src_g, Mat dst, Mat src) {
    Mat src_edge = new Mat();
    // Размытие изображения я ядром 3х3
    Imgproc.blur(src_g, src_edge, new Size(3, 3));
    // Детектор границ Канни
    Imgproc.Canny(src_edge, src_edge, THRESHOLD_1, THRESHOLD_1 * 3, 3);
    src.copyTo(dst, src_edge);
    HighGui.imshow(WINDOW_NAME, dst);
  }

}
