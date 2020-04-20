package by.bsuir.ir._2_histogram;

import by.bsuir.ir.AbstarctOpenCV;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.highgui.HighGui;
import org.opencv.imgproc.Imgproc;

/**
 * @author <a href="mailto:roman.gotovko@pstlabs.by">Roman Gotovko</a>
 * @since 2020-04-18
 */
public class _1HueSaturationForColorImage extends AbstarctOpenCV {

  private static final String SOURCE_IMAGE_TEXT = "Исходное изображение";
  private static final String RESULT = "Результат";

  public static void main(String[] args) {
    URL resource = _1HueSaturationForColorImage.class.getResource("/colored.jpg");
    Mat src = getImage(resource);

    List<Mat> bgrPlanes = new ArrayList<>();
    Core.split(src, bgrPlanes);
    int histSize = 256;
    float[] range = {0, 256};
    MatOfFloat histRange = new MatOfFloat(range);
    boolean accumulate = false;
    Mat bHist = new Mat(), gHist = new Mat(), rHist = new Mat();
    Imgproc
        .calcHist(bgrPlanes, new MatOfInt(0), new Mat(), bHist, new MatOfInt(histSize), histRange,
            accumulate);
    Imgproc
        .calcHist(bgrPlanes, new MatOfInt(1), new Mat(), gHist, new MatOfInt(histSize), histRange,
            accumulate);
    Imgproc
        .calcHist(bgrPlanes, new MatOfInt(2), new Mat(), rHist, new MatOfInt(histSize), histRange,
            accumulate);
    int histW = 512, histH = 400;
    int binW = (int) Math.round((double) histW / histSize);
    Mat histImage = new Mat(histH, histW, CvType.CV_8UC3, new Scalar(0, 0, 0));
    Core.normalize(bHist, bHist, 0, histImage.rows(), Core.NORM_MINMAX);
    Core.normalize(gHist, gHist, 0, histImage.rows(), Core.NORM_MINMAX);
    Core.normalize(rHist, rHist, 0, histImage.rows(), Core.NORM_MINMAX);
    float[] bHistData = new float[(int) (bHist.total() * bHist.channels())];
    bHist.get(0, 0, bHistData);
    float[] gHistData = new float[(int) (gHist.total() * gHist.channels())];
    gHist.get(0, 0, gHistData);
    float[] rHistData = new float[(int) (rHist.total() * rHist.channels())];
    rHist.get(0, 0, rHistData);
    for (int i = 1; i < histSize; i++) {
      Imgproc.line(histImage, new Point(binW * (i - 1), histH - Math.round(bHistData[i - 1])),
          new Point(binW * (i), histH - Math.round(bHistData[i])), new Scalar(255, 0, 0), 2);
      Imgproc.line(histImage, new Point(binW * (i - 1), histH - Math.round(gHistData[i - 1])),
          new Point(binW * (i), histH - Math.round(gHistData[i])), new Scalar(0, 255, 0), 2);
      Imgproc.line(histImage, new Point(binW * (i - 1), histH - Math.round(rHistData[i - 1])),
          new Point(binW * (i), histH - Math.round(rHistData[i])), new Scalar(0, 0, 255), 2);
    }

    HighGui.namedWindow(SOURCE_IMAGE_TEXT, HighGui.WINDOW_AUTOSIZE);
    HighGui.imshow(SOURCE_IMAGE_TEXT, src);

    HighGui.namedWindow(RESULT, HighGui.WINDOW_AUTOSIZE);
    HighGui.imshow(RESULT, histImage);
    HighGui.waitKey();
    HighGui.destroyAllWindows();
  }
}
