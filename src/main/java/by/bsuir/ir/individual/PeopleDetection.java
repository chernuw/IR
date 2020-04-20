package by.bsuir.ir.individual;

import by.bsuir.ir.AbstractOpenCV;
import java.util.stream.Collectors;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDouble;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfRect;
import org.opencv.core.MatOfRect2d;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Rect2d;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.dnn.Dnn;
import org.opencv.highgui.HighGui;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.HOGDescriptor;

/**
 * Разработка программы детектирование людей на изображениях
 * @author <a href="mailto:roman.gotovko@pstlabs.by">Roman Gotovko</a>
 * @since 2020-04-19
 */
public class PeopleDetection extends AbstractOpenCV {

  public static void main(String[] args) {

    HOGDescriptor hog = new HOGDescriptor();
    hog.setSVMDetector(HOGDescriptor.getDefaultPeopleDetector());

    Mat image = getImage(PeopleDetection.class.getResource("/humans.jpg"));
    Mat orig = image.clone();

    MatOfRect searchedRectangles = new MatOfRect();
    MatOfDouble searchedWeights = new MatOfDouble();
    hog.detectMultiScale(image, searchedRectangles, searchedWeights, 0, new Size(4, 4),
        new Size(8, 8), 1.05);

    int[][] rects = new int[searchedRectangles.toList().size()][4];
    Rect2d[] rect2ds = new Rect2d[searchedRectangles.toList().size()];
    for (int i = 0; i < searchedRectangles.toArray().length; i++) {
      Rect rect = searchedRectangles.toList().get(i);
      int x = rect.x;
      int y = rect.y;
      int xw = x + rect.width;
      int yh = y + rect.height;
      Imgproc.rectangle(orig, new Point(x, y), new Point(xw, yh), new Scalar(0, 0, 255), 2);
      rects[i][0] = x;
      rects[i][1] = y;
      rects[i][2] = xw;
      rects[i][3] = yh;
      rect2ds[i] = new Rect2d(x, y, xw, yh);
    }

    MatOfFloat floatLis = new MatOfFloat();
    floatLis.fromList(searchedWeights.toList().stream().map(Double::floatValue).collect(Collectors.toList()));


    MatOfInt indices = new MatOfInt();
    Dnn.NMSBoxes(new MatOfRect2d(rect2ds), floatLis, 0, 0.4f, indices);


    int[][] picked = new int[indices.toList().size()][4];
    for (int i = 0; i < indices.toArray().length; i++) {
      picked[i] = rects[indices.toArray()[i]];
    }

    for (int[] ints : picked) {
      int xA = ints[0];
      int yA = ints[1];
      int xB = ints[2];
      int yB = ints[3];
      Imgproc.rectangle(image, new Point(xA, yA), new Point(xB, yB), new Scalar(0, 255, 0), 2);
    }


    HighGui.imshow("До", orig);
    HighGui.imshow("После", image);
    HighGui.waitKey();


  }
}
