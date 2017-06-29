#include <iostream>
#include <cstdio>
#include <vector>
#include <math.h> 
#include <opencv2/core/core.hpp>
#include <opencv2/highgui/highgui.hpp>
#include <opencv2/imgproc/imgproc.hpp>

using namespace cv;
using namespace std;

//Used for getting histogram of an image's one channel
vector<int> GetImageHistogram(Mat _image , int _channel){
	vector<int> imageHistogram;
	for (int i = 0; i < 256; i++)
	{
		imageHistogram.push_back(0);
	}

	for (int i = 0; i < _image.rows; i++){
		for (int j = 0; j < _image.cols; j++){
			imageHistogram[ (int)_image.at<Vec3b>(i, j)[_channel] ]++;
		}
	}
	return imageHistogram;
}
//Used for getting Otsu's treshold value from histogram
int GetOtsuTresholdValue(Mat _image, vector<int> _imageHistogram){
	float _totalPixelNumber = _image.rows * _image.cols;
	float _globalMax = 0;
	float _thresholdIndex = 0;

	for (int i = 0; i < 256; i++) {
		float w0 = 0;
		float w1 = 0;
		float m0 = 0;
		float m1 = 0;
		for (int j = 0; j < 256; j++){
			if (j < i){
				w0 = w0 + _imageHistogram[j];
				m0 = m0 + j*(_imageHistogram[j] / _totalPixelNumber);
			}
			else{
				w1 = w1 + _imageHistogram[j];
				m1 = m1 + j*(_imageHistogram[j] / _totalPixelNumber);
			}
		}

		if (w0 != 0 && w1 != 0){
			m0 = m0 / w0;
			m1 = m1 / w1;
		}

		float _localMax = w0*w1*(m0 - m1)*(m0 - m1);
		if (_localMax > _globalMax){
			_globalMax = _localMax;
			_thresholdIndex = i;
		}
	}
	return _thresholdIndex;
}
//Used for using a treshold value to given image's one channel
Mat UseTresholdValue(Mat _image, int _channel, int _tresholdValue){
	Mat _tresholdImage(_image.rows, _image.cols, CV_8UC1);
	for (int i = 0; i < _image.rows; i++)
	{
		for (int j = 0; j < _image.cols; j++)
		{
			if ( (int)_image.at<Vec3b>(i, j)[_channel] >= _tresholdValue) {
				_tresholdImage.at<uchar>(i, j) = 255;
			}
			else {
				_tresholdImage.at<uchar>(i, j) = 0;
			}
		}
	}
	return _tresholdImage;
}
//Used for returning an image that is Otsu method used on it
Mat OtsuTreshold(Mat _image){
	vector<int> _blueImageHistogram = GetImageHistogram(_image, 0);
	vector<int> _greenImageHistogram = GetImageHistogram(_image, 1);
	vector<int> _redImageHistogram = GetImageHistogram(_image, 2);

	int _blueTresholdValue = GetOtsuTresholdValue(_image, _blueImageHistogram);
	int _greenTresholdValue = GetOtsuTresholdValue(_image, _greenImageHistogram);
	int _redTresholdValue = GetOtsuTresholdValue(_image, _redImageHistogram);

	cout << "---Otsu's Segmentation---" << endl;
	cout << "Blue Channel Treshold Value : " << _blueTresholdValue << endl;
	cout << "Green Channel Treshold Value : " << _greenTresholdValue << endl;
	cout << "Red Channel Treshold Value : " << _redTresholdValue << endl;

	Mat _blueTresholdImage(_image.rows, _image.cols, CV_8UC1);
	Mat _greenTresholdImage(_image.rows, _image.cols, CV_8UC1);
	Mat _redTresholdImage(_image.rows, _image.cols, CV_8UC1);

	_blueTresholdImage = UseTresholdValue(_image, 0, _blueTresholdValue);
	_greenTresholdImage = UseTresholdValue(_image, 1, _greenTresholdValue);
	_redTresholdImage = UseTresholdValue(_image, 2, _redTresholdValue);

	Mat _otsuTresholdImage(_image.rows, _image.cols, CV_8UC1);
	for (int i = 0; i < _image.rows; i++){
		for (int j = 0; j < _image.cols; j++){
			if (_blueTresholdImage.at<uchar>(i, j) == 0 && 
				_greenTresholdImage.at<uchar>(i, j) == 0 &&
				_redTresholdImage.at<uchar>(i, j) == 0){
				_otsuTresholdImage.at<uchar>(i, j) = 0;
			}
			else{
				_otsuTresholdImage.at<uchar>(i, j) = 255;
			}
		}
	}
	return _otsuTresholdImage;
}
//Used for getting K-Means treshold value from histogram
int GetKMeansClusteringValue(vector<int> _imageHistogram, int _startingKMeansValue){
	float _kMeansValue1 = 0;
	float _kMeansValue2 = _startingKMeansValue;
	while ( fabs(_kMeansValue1 - _kMeansValue2) > 1 ){
		_kMeansValue1 = _kMeansValue2;
		float _sum1 = 0;
		float _pixelTotal1 = 0;
		float _sum2 = 0;
		float _pixelTotal2 = 0;
		for (int i = 0; i < 256; i++){
			if (i < _kMeansValue1){
				_pixelTotal1 = _pixelTotal1 + _imageHistogram[i];
				_sum1 = _sum1 + i*_imageHistogram[i];
			}
			else{
				_pixelTotal2 = _pixelTotal2 + _imageHistogram[i];
				_sum2 = _sum2 + i*_imageHistogram[i];
			}
		}

		_sum1 = _sum1 / _pixelTotal1;
		_sum2 = _sum2 / _pixelTotal2;
		_kMeansValue2 = (_sum1 + _sum2) / 2;
	}
	return _kMeansValue2;
}
//Used for returning an image that is K-Means method used on it
Mat KMeansClustering(Mat _image){
	vector<int> _blueImageHistogram = GetImageHistogram(_image, 0);
	vector<int> _greenImageHistogram = GetImageHistogram(_image, 1);
	vector<int> _redImageHistogram = GetImageHistogram(_image, 2);

	int _blueTresholdValue = GetKMeansClusteringValue(_blueImageHistogram, 128);
	int _greenTresholdValue = GetKMeansClusteringValue(_greenImageHistogram, 128);
	int _redTresholdValue = GetKMeansClusteringValue(_redImageHistogram, 128);

	cout << "---K-Means Clustering---" << endl;
	cout << "Blue Channel Treshold Value : " << _blueTresholdValue << endl;
	cout << "Green Channel Treshold Value : " << _greenTresholdValue << endl;
	cout << "Red Channel Treshold Value : " << _redTresholdValue << endl;

	Mat _blueTresholdImage(_image.rows, _image.cols, CV_8UC1);
	Mat _greenTresholdImage(_image.rows, _image.cols, CV_8UC1);
	Mat _redTresholdImage(_image.rows, _image.cols, CV_8UC1);

	_blueTresholdImage = UseTresholdValue(_image, 0, _blueTresholdValue);
	_greenTresholdImage = UseTresholdValue(_image, 1, _greenTresholdValue);
	_redTresholdImage = UseTresholdValue(_image, 2, _redTresholdValue);

	Mat _kMeansClusteringImage(_image.rows, _image.cols, CV_8UC1);
	for (int i = 0; i < _image.rows; i++){
		for (int j = 0; j < _image.cols; j++){
			if (_blueTresholdImage.at<uchar>(i, j) == 0 &&
				_greenTresholdImage.at<uchar>(i, j) == 0 &&
				_redTresholdImage.at<uchar>(i, j) == 0){
				_kMeansClusteringImage.at<uchar>(i, j) = 0;
			}
			else{
				_kMeansClusteringImage.at<uchar>(i, j) = 255;
			}
		}
	}
	return _kMeansClusteringImage;
}

int main(int argc, char** argv)
{
	cout << endl << "--- MULTIMEDIA COMPUTING HOMEWORK 02 ---" << endl << endl;
	cout << "Enter Image Name :";
	string imageName;
	cin >> imageName;

	Mat originalImage = imread(imageName);

	Mat otsuTresholdImage(originalImage.rows, originalImage.cols, CV_8UC1);
	otsuTresholdImage = OtsuTreshold(originalImage);

	Mat kMeansClusteringImage(originalImage.rows, originalImage.cols, CV_8UC1);
	kMeansClusteringImage = KMeansClustering(originalImage);

	namedWindow("Original Image");
	imshow("Original Image", originalImage);
	namedWindow("Otsu Treshold Image");
	imshow("Otsu Treshold Image", otsuTresholdImage);
	namedWindow("K-Means Clustering Image");
	imshow("K-Means Clustering Image", kMeansClusteringImage);
	
	waitKey();
	return 0;
}
