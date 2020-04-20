#include <opencv2/opencv.hpp>
#include <iostream>

using namespace cv;
using namespace std;

int main() {
  setlocale(LC_CTYPE, "rus");
  Mat src_base = imread("f://img//img022.bmp", 1);
  Mat src_test1 = imread("f://img//img09.bmp", 1);
  Mat src_test2 = imread("f://img//img08.bmp", 1);
  Mat src_test3 = imread("f://img//img05.bmp", 1);
  if (src_base.empty() || src_test1.empty() ||
    src_test2.empty() || src_test3.empty())
      return -1;
  // Конвертирование RGB в HSV
  Mat base, b_h, test1, test2, test3;
  cvtColor(src_base, base, COLOR_BGR2HSV);
  cvtColor(src_test1, test1, COLOR_BGR2HSV);
  cvtColor(src_test2, test2, COLOR_BGR2HSV);
  cvtColor(src_test3, test3, COLOR_BGR2HSV);
  // Выделение половины базового изображения
  b_h = base(Range(base.rows / 2, base.rows), Range(0, base.cols));
  // Использование 50 столбцов для цветового тона (H) и 60 для насыщенности (S)
  int hb = 50, sb = 60;
  int hSize[] = { hb, sb };
  // Аргументы для вычисления гистограмм
  float hr[] = { 0, 180 }; // Оттенок
  float sr[] = { 0, 256 }; // Насыщенность
  const float* ranges[] = { hr, sr };
  // Использование двух каналов
  int channels[] = { 0, 1 };
  // Объекты для хранения гистограмм
  Mat hbase, hbase_half, htest1, htest2, htest3;
  // Расчет гистограмм для HSV изображений
  calcHist(&base, 1, channels, Mat(), hbase, 2, hSize, ranges);
  normalize(hbase, hbase, 0, 1, NORM_MINMAX, -1, Mat());
  calcHist(&b_h, 1, channels, Mat(), hbase_half, 2, hSize, ranges);
  normalize(hbase_half, hbase_half, 0, 1, NORM_MINMAX, -1, Mat());
  calcHist(&test1, 1, channels, Mat(), htest1, 2, hSize, ranges);
  normalize(htest1, htest1, 0, 1, NORM_MINMAX, -1, Mat());
  calcHist(&test2, 1, channels, Mat(), htest2, 2, hSize, ranges);
  normalize(htest2, htest2, 0, 1, NORM_MINMAX, -1, Mat());
  calcHist(&test3, 1, channels, Mat(), htest3, 2, hSize, ranges);
  normalize(htest3, htest3, 0, 1, NORM_MINMAX, -1, Mat());
  // Сравнение гистограмм различ. методами
  const char metrika[6][30] = {
    "Корреляция","Хи-квадрат",
    "Пересечение",
    "Расстояние Бхаттачарья",
    "Альтернатива хи-квадрат",
    "Расхожд. Кульбака-Лейблера"};
  for (int compare_method = 0; compare_method < 6; compare_method++) {
    double b_b = compareHist(hbase, hbase, compare_method);
    double b_h = compareHist(hbase, hbase_half, compare_method);
    double b_t1 = compareHist(hbase, htest1, compare_method);
    double b_t2 = compareHist(hbase, htest2, compare_method);
    double b_t3 = compareHist(hbase, htest3, compare_method);
    cout << metrika[compare_method] << ": " << b_b << " " << b_h << " " << b_t1
    << " " << b_t2 << " " << b_t3 << endl;
  }
  waitKey();
return 0;
}