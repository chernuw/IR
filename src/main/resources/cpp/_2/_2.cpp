char org[] = "Исходн.";
namedWindow(org, CV_WINDOW_AUTOSIZE);
char org_hist[] = "Исходн. гистограмма";
namedWindow(org_hist, CV_WINDOW_AUTOSIZE);
char res[] = "Результ.";
namedWindow(res, CV_WINDOW_AUTOSIZE);
char res_hist[] = "Результ. гистограмма";
namedWindow(res_hist, CV_WINDOW_AUTOSIZE);
Mat scr2, hist_o, hist_r, scr = imread("f://img//img033 G.bmp", 1);
if (!scr.data)
  return 1;
cvtColor(scr, scr, CV_RGB2GRAY);
imshow(org, scr);
equalizeHist(scr,scr2);
imshow(res, scr2);
int ks = 32;
// Интервал изменения значений
float range[] = {0, 255};
const float* histRange = {range};
// Размер гистограммы
int Width = 600, Height = 400 ;
Mat histImg1 = Mat(Height, Width, CV_8UC3, Scalar(255, 255, 255));
Mat histImg2 = Mat(Height, Width, CV_8UC3, Scalar(255, 255, 255));
// Вычисление и нормализация гистограмм
calcHist(&scr, 1, 0, Mat(), hist_o, 1, &ks, &histRange);
normalize(hist_o, hist_o, 0, Height, NORM_MINMAX);
calcHist(&scr2, 1, 0, Mat(), hist_r, 1, &ks, &histRange);
normalize(hist_r, hist_r, 0, Height, NORM_MINMAX);
// Количество пикселей на один столбец
int pst = cvRound((double)Width / ks);
for (int i = 1; i < ks; i++) {
  rectangle(
    histImg1,
    Point(pst * (i-1), Height - cvRound(hist_o.at<float>(i-1))),
    Point(pst * i, Height),
    Scalar(255, 0, 0),
    -1
  );
  rectangle(
    histImg2,
    Point(pst * (i-1), Height-cvRound(hist_r.at<float>(i-1))),
    Point(pst * i, Height),
    Scalar(0, 255, 0),
    -1
  );
}
imshow(org_hist, histImg1);
imshow(res_hist, histImg2);