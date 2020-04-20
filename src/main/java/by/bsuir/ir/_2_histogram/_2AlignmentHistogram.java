package by.bsuir.ir._2_histogram;

import by.bsuir.ir.AbstarctOpenCV;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
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
public class _2AlignmentHistogram extends AbstarctOpenCV {

  private static final String SOURCE_IMAGE_TEXT = "Исходное изображение";
  private static final String SOURCE_HISTOGRAM_TEXT = "Исходн. гистограмма";
  private static final String RESULT_IMAGE_TEXT = "Результат";
  private static final String RESULT_HGISTOGRAM_TEXT = "Результ. гистограмма";

  public static void main(String[] args) {
    URL resource = _2AlignmentHistogram.class.getResource("/colored.jpg");
    Mat src = getImage(resource);

    Imgproc.cvtColor(src, src, Imgproc.COLOR_RGB2GRAY);
    HighGui.imshow(SOURCE_IMAGE_TEXT, src);

    Mat src2 = new Mat();
    Imgproc.equalizeHist(src, src2);
    HighGui.imshow(RESULT_IMAGE_TEXT, src2);

    int histSize = 32;

    float[] range = {0, 256};
    MatOfFloat histRange = new MatOfFloat(range);

    int histW = 600;
    int histH = 400;

    Mat histImg1 = new Mat(histH, histW, CvType.CV_8UC3, new Scalar(255, 255, 255));
    Mat histImg2 = new Mat(histH, histW, CvType.CV_8UC3, new Scalar(255, 255, 255));

    Mat hist_o = new Mat();
    Imgproc.calcHist(
        Collections.singletonList(src),
        new MatOfInt(0),
        new Mat(),
        hist_o,
        new MatOfInt(histSize),
        histRange
    );
    Core.normalize(hist_o, hist_o, 0, histH, Core.NORM_MINMAX);
    float[] hist_oData = new float[(int) (hist_o.total() * hist_o.channels())];
    hist_o.get(0,0, hist_oData);

    Mat hist_r = new Mat();
    Imgproc.calcHist(Collections.singletonList(src2), new MatOfInt(0), new Mat(), hist_r,
        new MatOfInt(histSize), histRange);
    Core.normalize(hist_r, hist_r, 0, histH, Core.NORM_MINMAX);
    float[] hist_rData = new float[(int) (hist_r.total() * hist_r.channels())];
    hist_r.get(0,0, hist_rData);


    int pst = (int) Math.round((double) (histW / histSize));

    for (int i = 1; i < histSize; i++) {
      Imgproc.rectangle(
          histImg1,
          new Point(pst * (i - 1), histH - Math.round((hist_oData[i - 1]))),
          new Point(pst * i, histH),
          new Scalar(255, 0, 0),
          -1
      );
      Imgproc.rectangle(
          histImg2,
          new Point(pst * (i - 1), histH - Math.round((hist_rData[i - 1]))),
          new Point(pst * i, histH),
          new Scalar(0, 255, 0),
          -1
      );
    }

    HighGui.imshow(SOURCE_HISTOGRAM_TEXT, histImg1);
    HighGui.imshow(RESULT_HGISTOGRAM_TEXT, histImg2);

    HighGui.waitKey();
    HighGui.destroyAllWindows();
  }
}
