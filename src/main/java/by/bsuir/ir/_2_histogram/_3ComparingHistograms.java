package by.bsuir.ir._2_histogram;

import by.bsuir.ir.AbstractOpenCV;
import java.util.Collections;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.Range;
import org.opencv.imgproc.Imgproc;

/**
 * @author <a href="mailto:roman.gotovko@pstlabs.by">Roman Gotovko</a>
 * @since 2020-04-18
 */
public class _3ComparingHistograms extends AbstractOpenCV {


  public static void main(String[] args) {
    Mat src_base = getImage(_3ComparingHistograms.class.getResource("/for_hist/src_base.jpg"));
    Mat src_test1 = getImage(_3ComparingHistograms.class.getResource("/for_hist/src_test1.jpg"));
    Mat src_test2 = getImage(_3ComparingHistograms.class.getResource("/for_hist/src_test2.jpg"));
    Mat src_test3 = getImage(_3ComparingHistograms.class.getResource("/for_hist/src_test3.jpg"));

    Mat base = new Mat();
    Mat test1 = new Mat();
    Mat test2 = new Mat();
    Mat test3 = new Mat();

    Imgproc.cvtColor(src_base, base, Imgproc.COLOR_BGR2GRAY);
    Imgproc.cvtColor(src_test1, test1, Imgproc.COLOR_BGR2GRAY);
    Imgproc.cvtColor(src_test2, test2, Imgproc.COLOR_BGR2GRAY);
    Imgproc.cvtColor(src_test3, test3, Imgproc.COLOR_BGR2GRAY);

    Mat b_h = new Mat(base, new Range(base.rows() / 2, base.rows()), new Range(0, base.cols()));

    // Использование 50 столбцов для цветового тона (H) и 60 для насыщенности (S)
    int hb = 50, sb = 60;
//    int[] hsize = {hb, sb};
    int[] hsize = {hb};

    // Аргументы для вычисления гистограмм
    float[] hr = {0, 180}; // Оттенок
    float[] sr = {0, 256}; // Насыщенность

//    MatOfFloat ranges = new MatOfFloat(hr[0], hr[1], sr[0], sr[1]);
    MatOfFloat ranges = new MatOfFloat(hr[0], hr[1]);

    // Использование двух каналов
    int channels[] = {0, 1};

    // Объекты для хранения гистограмм
    Mat hbase = new Mat();
    Mat hbase_half = new Mat();
    Mat htest1 = new Mat();
    Mat htest2 = new Mat();
    Mat htest3 = new Mat();

    // Расчет гистограмм для HSV изображений
    Imgproc.calcHist(Collections.singletonList(base), new MatOfInt(0), new Mat(), hbase,
        new MatOfInt(50), ranges);
    Core.normalize(hbase, hbase, 0, 1, Core.NORM_MINMAX, -1, new Mat());

    Imgproc.calcHist(Collections.singletonList(b_h), new MatOfInt(0), new Mat(), hbase_half,
        new MatOfInt(hsize), ranges);
    Core.normalize(hbase_half, hbase_half, 0, 1, Core.NORM_MINMAX, -1, new Mat());

    Imgproc.calcHist(Collections.singletonList(test1), new MatOfInt(0), new Mat(), htest1,
        new MatOfInt(hsize), ranges);
    Core.normalize(htest1, htest1, 0, 1, Core.NORM_MINMAX, -1, new Mat());

    Imgproc.calcHist(Collections.singletonList(test2), new MatOfInt(0), new Mat(), htest2,
        new MatOfInt(hsize), ranges);
    Core.normalize(htest2, htest2, 0, 1, Core.NORM_MINMAX, -1, new Mat());

    Imgproc.calcHist(Collections.singletonList(test3), new MatOfInt(0), new Mat(), htest3,
        new MatOfInt(hsize), ranges);
    Core.normalize(htest3, htest3, 0, 1, Core.NORM_MINMAX, -1, new Mat());

    // Сравнение гистограмм различ. методами
    String[] metrika = {"Корреляция", "Хи-квадрат", "Пересечение", "Расстояние Бхаттачарья",
        "Альтернатива хи-квадрат", "Расхожд. Кульбака-Лейблера"};
    for (int compare_method = 0; compare_method < 6; compare_method++) {
      double b_b = Imgproc.compareHist(hbase, hbase, compare_method);
      double b_h2 = Imgproc.compareHist(hbase, hbase_half, compare_method);
      double b_t1 = Imgproc.compareHist(hbase, htest1, compare_method);
      double b_t2 = Imgproc.compareHist(hbase, htest2, compare_method);
      double b_t3 = Imgproc.compareHist(hbase, htest3, compare_method);
      System.out.println(
          metrika[compare_method] + ": " + b_b + " " + b_h2 + " " + b_t1 + " " + b_t2 + " " + b_t3);
    }
  }
}
