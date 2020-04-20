#include <opencv2/opencv.hpp>
#include <iostream>

using namespace cv;
using namespace std;

Mat scr, src_g;
Mat dst, src_edge;
int lowThr = 0;
const int max_lowThr = 100;
const char window[] = "Детектор границ Канни";

static void fCanny(int, void*) {
    // Размытие изображения я ядром 3х3
  blur(src_g, src_edge, Size(3, 3));
  // Детектор границ Канни
  Canny(src_edge, src_edge, lowThr, lowThr*3, 3);
  // Заполн. изображ. нулями (черным цветом)
  dst = Scalar::all(0);
  // Копирование границ на черный фон
  scr.copyTo(dst, src_edge);
  // Вывод изображения
  imshow(window, dst);
}

int main() {
  char img[] = "f://img//a1_proc.bmp";
  scr = imread(img, 1);
  if (scr.empty())
    return -1;
  // Создание матрицы, аналогичной исходной (для отображения границ)
  dst.create(scr.size(), scr.type());
  // Конверт. матрицы в оттенки серого
  cvtColor(scr, src_g, COLOR_BGR2GRAY);
  namedWindow(window, WINDOW_AUTOSIZE);
  // Создание трекбара
  createTrackbar("Нижний порог гистерезиса:", window, &lowThr, max_lowThr, fCanny);
  fCanny(0, 0);
  waitKey();
  return 0;
}