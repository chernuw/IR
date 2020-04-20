#include <opencv2/opencv.hpp>
#include <iostream>

using namespace cv;
using namespace std;

int main() {
  Mat scr = imread("f://img//img03.bmp", 1);
  if (!scr.data)
    return 1;
  char sou[] = "Исходное изображение";
  char dis[] = "Результат";
  namedWindow(sou, CV_WINDOW_AUTOSIZE);
  imshow(sou, scr);
  // Разделение изображения на каналы
  Mat Cnls[3];
  split(scr, Cnls);
  // Количество столбцов гистограммы
  int ks = 256;
  // Интервал изменения значений
  float range[] = {0.0, 255.0};
  const float* histRange = {range};
  // Размер гистограммы
  int Width = 512, Height = 400;
  // Количество пикселей на один столбец
  int pst = cvRound((double)Width / ks);
  Scalar colors[] = {Scalar(255, 0, 0), Scalar(0, 255, 0), Scalar(0, 0, 255)};
  Mat hist[3];
  // Вычисл. гистограммы для каждого канала
  for (int i = 0; i < 3; i++)
    calcHist(&Cnls[i], 1, 0, Mat(), hist[i], 1, &ks, &histRange);
  // Построение гистограммы
  Mat histImg = Mat(Height, Width, CV_8UC3, Scalar(0, 0, 0));
  // Нормализация гистограмм в соответствии с размерами окна для отображения
  for (int i = 0; i < 3; i++)
    normalize(hist[i], hist[i], 0, histImg.rows, NORM_MINMAX);
  // отрисовка гистограмм
  for (int j = 1; j < ks; j++)
    for (int i = 0; i < 3; i++)
      line(
        histImg,
        Point(pst * (j-1), Height-cvRound(hist[i].at<float>(j-1))),
        Point(pst * j, Height-cvRound(hist[i].at<float>(j))),
        colors[i],
        2,
        8,
        0
      );
  // Вывод гистограмм
  namedWindow(dis, WINDOW_AUTOSIZE);
  imshow(dis, histImg);
  waitKey();
  // закрытие окон
  destroyAllWindows();
  // осовобождение памяти
  scr.release();
  for (int i = 0; i < 3; i++) {
    Cnls[i].release();
    hist[i].release();
  }
  histImg.release();
  return 0;
}