#include <opencv2/opencv.hpp>
#include <iostream>

using namespace cv;
using namespace std;

int main() {
  // Чтение картинки
  Mat img = imread("F:\\VC++\\faces.png", 1);
  // Матрица изображения в градациях серого
  Mat img_gray(img.size(), CV_8UC1);
  cvtColor(img, img_gray, COLOR_BGR2GRAY, 1);
  // Выравнивание гисторграммы
  equalizeHist(img_gray, img_gray);
  // Загрузка классификатора для поиска лиц
  Ptr<CascadeClassifier> cascade(
    new CascadeClassifier(
      "F:\\VC++\\haarcascade_frontalface_alt2.xml"));
  //Вектор координинат квадратов (диагональные точки)
  vector<Rect> objects;
  //Обнаружение объектов
  cascade -> detectMultiScale(img_gray, objects, 1.1, 3, 0, Size(30, 30));
  vector<Rect>::iterator ob;
  for (ob=objects.begin(); ob != objects.end(); ob++)
    rectangle(img, *ob, Scalar(0, 0, 255));
  // Загрузка классификатора для поиска глаз
  Ptr<CascadeClassifier> cascade2(
    new CascadeClassifier( "F:\\VC++\\haarcascade_eye.xml"));
  vector<Rect> objects2;
  cascade2 -> detectMultiScale(img_gray, objects2, 1.1, 5, 0, Size(2, 2));
  vector<Rect>::iterator ob2;
  for (ob2 = objects2.begin(); ob2 != objects2.end(); ob2++)
    rectangle(img, *ob2, Scalar(0, 255, 0));
  imshow("Result face and eve", img);
  waitKey(0);
  return 0;
}