package by.bsuir.ir._1_contour;

import by.bsuir.ir.AbstractOpenCV;
import java.net.URL;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.HighGui;
import org.opencv.imgproc.Imgproc;

/**
 * @author <a href="mailto:roman.gotovko@pstlabs.by">Roman Gotovko</a>
 * @since 2020-04-09
 */
public class _1GetCountours extends AbstractOpenCV {

  private static final String SOURCE_IMAGE_TEXT = "Исходное изображение";
  private static final String RESULT_1 = "Результат 1";
  private static final String RESULT_2 = "Результат 2";
  private static final int DEPTH = CvType.CV_16S;

  public static void main(String[] args) {

    Mat src2 = new Mat();
    Mat src2_gray = new Mat();
    Mat src_sobx = new Mat();
    Mat src_soby = new Mat();
    Mat src_sobs = new Mat();

    // Чтение изображения
    URL resource = _1GetCountours.class.getResource("/zhdun.png");
    Mat src = getImage(resource);

    // Создание окна
    HighGui.namedWindow(SOURCE_IMAGE_TEXT, HighGui.WINDOW_AUTOSIZE);
    // Вывод изображения в окне
    HighGui.imshow(SOURCE_IMAGE_TEXT, src);

    // Создание окон для вывода результатов
//    HighGui.namedWindow(RESULT_1, HighGui.WINDOW_AUTOSIZE);
    HighGui.namedWindow(RESULT_2, HighGui.WINDOW_AUTOSIZE);
    // Использование фильтра Гаусса
    // для удаления шума (размер ядра = 3)
    Imgproc.GaussianBlur(src, src2, new Size(3, 3), 0, 0, Core.BORDER_DEFAULT);
    // Конвертирование изобр. в оттенки серого
    Imgproc.cvtColor(src2, src2_gray, Imgproc.COLOR_BGR2GRAY);
    // Оператор Собеля
    Imgproc.Sobel(src2_gray, src_sobx, DEPTH, 1, 0, 1, 1, 0, Core.BORDER_DEFAULT);
    Imgproc.Sobel(src2_gray, src_soby, DEPTH, 0, 1, 1, 1, 0, Core.BORDER_DEFAULT);
    // Приведение к 8-битному представлению
    Core.convertScaleAbs(src_sobx, src_sobx);
    Core.convertScaleAbs(src_soby, src_soby);
    // Суммирование матриц
    Core.addWeighted(src_sobx, 0.5, src_soby, 0.5, 0, src_sobs);
    // Инвертирование массива
    Core.bitwise_not(src_sobs, src_sobs);
    // Вывод результата
    HighGui.imshow(RESULT_2, src_sobs);

    HighGui.waitKey();
  }

}
