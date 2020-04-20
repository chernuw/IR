package by.bsuir.ir;

import java.net.URL;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

/**
 * @author <a href="mailto:roman.gotovko@pstlabs.by">Roman Gotovko</a>
 * @since 2020-04-09
 */
public abstract class AbstractOpenCV {

  static {
    System.load("D:\\opencv\\opencv\\build\\java\\x64\\opencv_java430.dll");
  }

  public static Mat getImage(URL url) {
    // Чтение изображения
    Mat src = Imgcodecs.imread(url.getPath().replaceFirst("/", ""), 1);
    if (src.dataAddr() == 0L) {
      throw new RuntimeException("again null");
    }
    return src;
  }
}
