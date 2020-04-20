#include <opencv2/opencv.hpp>
#include <iostream>

using namespace cv;
using namespace std;

int main() {
  // Загрузка изображения
  Mat scr = imread("f://img//img02.bmp", 1);
  if (!scr.data) return 1;
  char sou[] = "Исходное изображение";
  char dis[] = "Результат";
  namedWindow(sou, WINDOW_AUTOSIZE);
  imshow(sou, scr);
  // Создание копии изображения
  Mat scr_b = scr.clone();
  // Конвертирование изображ. в оттенки серого
  cvtColor(scr, scr_b, COLOR_RGB2GRAY);
  // Конвертирование в бинарное изображение
  threshold(scr_b, scr_b, 220, 255, THRESH_BINARY);
  // Поиск контуров
  vector<vector<Point> > contours;
  findContours(scr_b, contours, RETR_CCOMP, CHAIN_APPROX_SIMPLE);
  // Отображение контуров
  for (int i = 0; i < contours.size(); i++) {
    Scalar color(rand() & 255, rand() & 255, rand() & 255);
    drawContours(scr, contours, i, color, 4);
  }

  // Вывод изображения с контурами на экран
  namedWindow(dis, WINDOW_AUTOSIZE);
  imshow(dis, scr);
  waitKey();
  return 0;
}