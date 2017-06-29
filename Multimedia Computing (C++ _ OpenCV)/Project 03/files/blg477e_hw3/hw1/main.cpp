#include <iostream>
#include <cstdio>
#include <vector>
#include <math.h> 
#include <opencv2/core/core.hpp>
#include <opencv2/highgui/highgui.hpp>
#include <opencv2/imgproc/imgproc.hpp>
#include <opencv2/ml/ml.hpp>

using namespace cv;
using namespace std;


class CountingObjectsClass{
private:
	Mat originalImage;
	Mat otsuThresholdImage;
	Mat morphologicalImage;

	vector<vector<int>> labelsOfRegions;
	vector<int> directionXNeigbours;
	vector<int> directionYNeigbours;
	int numberOfObjects;
public:
	//Constructor for class 
	CountingObjectsClass(Mat _originalImage){
		originalImage = _originalImage;
		otsuThresholdImage.create(originalImage.rows, originalImage.cols, CV_8UC(1));
		morphologicalImage.create(originalImage.rows, originalImage.cols, CV_8UC(1));

		directionXNeigbours.resize(4);
		directionYNeigbours.resize(4);
		directionXNeigbours = { 1, 0, -1, 0 };
		directionYNeigbours = { 0, 1, 0, -1 };

		for (int i = 0; i < originalImage.rows; i++){
			vector<int> _tempVector;
			for (int j = 0; j < originalImage.cols; j++){
				_tempVector.push_back(0);
			}
			labelsOfRegions.push_back(_tempVector);
		}
	}

	//Used for showing images
	void ShowImages(){
		namedWindow("Original Image");
		imshow("Original Image", originalImage);
		namedWindow("Otsu Treshold Image");
		imshow("Otsu Treshold Image", otsuThresholdImage);
		namedWindow("Morphological Image");
		imshow("Morphological Image", morphologicalImage);
	}

	//Used for getting histogram of an image's one channel
	vector<int> GetImageHistogram(Mat _image, int _channel){
		vector<int> imageHistogram;
		for (int i = 0; i < 256; i++)
		{
			imageHistogram.push_back(0);
		}

		for (int i = 0; i < _image.rows; i++){
			for (int j = 0; j < _image.cols; j++){
				imageHistogram[(int)_image.at<Vec3b>(i, j)[_channel]]++;
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
				if ((int)_image.at<Vec3b>(i, j)[_channel] >= _tresholdValue) {
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
	void UseOtsuTresholdOnImage(){
		vector<int> _blueImageHistogram = GetImageHistogram(originalImage, 0);
		vector<int> _greenImageHistogram = GetImageHistogram(originalImage, 1);
		vector<int> _redImageHistogram = GetImageHistogram(originalImage, 2);

		int _blueTresholdValue = GetOtsuTresholdValue(originalImage, _blueImageHistogram);
		int _greenTresholdValue = GetOtsuTresholdValue(originalImage, _greenImageHistogram);
		int _redTresholdValue = GetOtsuTresholdValue(originalImage, _redImageHistogram);

		Mat _blueTresholdImage(originalImage.rows, originalImage.cols, CV_8UC1);
		Mat _greenTresholdImage(originalImage.rows, originalImage.cols, CV_8UC1);
		Mat _redTresholdImage(originalImage.rows, originalImage.cols, CV_8UC1);

		_blueTresholdImage = UseTresholdValue(originalImage, 0, _blueTresholdValue);
		_greenTresholdImage = UseTresholdValue(originalImage, 1, _greenTresholdValue);
		_redTresholdImage = UseTresholdValue(originalImage, 2, _redTresholdValue);

		for (int i = 0; i < originalImage.rows; i++){
			for (int j = 0; j < originalImage.cols; j++){
				if (_blueTresholdImage.at<uchar>(i, j) == 0 &&
					_greenTresholdImage.at<uchar>(i, j) == 0 &&
					_redTresholdImage.at<uchar>(i, j) == 0){
					otsuThresholdImage.at<uchar>(i, j) = 0;
				}
				else{
					otsuThresholdImage.at<uchar>(i, j) = 255;
				}
			}
		}
	}

	//Morphological Operator Type => 0: Opening, 1: Closing, 2: Gradient, 3: Top Hat, 4: Black Hat
	//Kernel Type => 0: Rect, 1 : Cross, 2 : Ellipse
	//Kernel Size => 2n +1
	Mat MorphologicalImageFunction(Mat _image, int _morphOperatorType, int _kernelType, int _kernelSize){
		Mat _morphologicalImage(_image.rows, _image.cols, CV_8UC1);
		int _operation = _morphOperatorType + 2;
		Mat _element = getStructuringElement(_kernelType, Size(2 * _kernelSize + 1, 2 * _kernelSize + 1), Point(_kernelSize, _kernelSize));

		morphologyEx(_image, _morphologicalImage, _operation, _element);
		return _morphologicalImage;
	}
	
	//Used for using morphological kernel to thresholded image
	void UseMorphologicalFunctionOnImage(int _kernelSize){
		Mat _tempImage(originalImage.rows, originalImage.cols, CV_8UC1);
		_tempImage = MorphologicalImageFunction(otsuThresholdImage, 1, 1, _kernelSize);
		morphologicalImage = MorphologicalImageFunction(_tempImage, 1, 1, _kernelSize);
	}

	//Used for labeling neigbours of selected pixel
	void DfsForConnectedComponent(int i, int j, int _currentLabel){
		if (i < 0 || i == morphologicalImage.rows || j < 0 || j == morphologicalImage.cols) return;
		if (labelsOfRegions[i][j] != 0 || morphologicalImage.at<uchar>(i, j) == 255) return;

		labelsOfRegions[i][j] = _currentLabel;
		for (int _direction = 0; _direction < 4; ++_direction){
			DfsForConnectedComponent(i + directionXNeigbours[_direction], j + directionYNeigbours[_direction], _currentLabel);
		}
	}

	//Used for finding region number from labeled image
	int UseConnectedComponentFunctionOnImage(){
		for (int i = 0; i < morphologicalImage.rows; ++i){
			for (int j = 0; j < morphologicalImage.cols; ++j){
				if (labelsOfRegions[i][j] == 0 && morphologicalImage.at<uchar>(i, j) == 0){
					DfsForConnectedComponent(i, j, ++numberOfObjects);
				}
			}
		}
		return numberOfObjects;
	}
};

class ColorClassificationClass{
private:
	vector<string> imagesNames;
	int numberOfImages;
	int numberOfColors;

	vector<Mat> images;
	vector<Mat> hsvImages;
	Mat svmTrainingMatrix;
	CvSVM *svmSets;
	//const string svmFileNames[7] = {"redSvmFile","orangeSvmFile","yellowSvmFile","greenSvmFile","blueSvmFile","whiteSvmFile","violetSvmFile"};
	//0:red, 1:orange, 2:yellow, 3:green, 4:blue, 5:white, 6:violet
	vector<Mat> colorLabelsForSvm;

public:
	ColorClassificationClass(int _numberOfImages, int _numberOfColors){
		numberOfImages = _numberOfImages;
		numberOfColors = _numberOfColors;
		
		imagesNames.resize(numberOfImages);
		images.resize(numberOfImages);
		hsvImages.resize(numberOfImages);

		colorLabelsForSvm.resize(numberOfColors);
		svmSets = new CvSVM[numberOfColors];

		for (int i = 0; i < numberOfImages; i++){
			imagesNames[i] = "image";
			imagesNames[i].append(to_string(i));
			imagesNames[i].append(".jpg");
			images[i] = imread(imagesNames[i]);
			cvtColor(images[i], hsvImages[i], CV_BGR2HSV);
		}
	}

	void ControlHsvValues(){
		//(int)_image.at<Vec3b>(i, j)[_channel];
		for (int k = 0; k < numberOfImages; k++){
			for (int i = 0; i < images[k].rows; i++){
				for (int j = 0; j < images[k].cols; j++){
					cout <<"bgr : " << (int)images[k].at<Vec3b>(i, j)[0] << endl;
					cout << "bgr : " << (int)images[k].at<Vec3b>(i, j)[1] << endl;
					cout << "bgr : " << (int)images[k].at<Vec3b>(i, j)[2] << endl;
					cout <<"hsv : " << (int)hsvImages[k].at<Vec3b>(i, j)[0] << endl;
					cout << "hsv : " << (int)hsvImages[k].at<Vec3b>(i, j)[1] << endl;
					cout << "hsv : " << (int)hsvImages[k].at<Vec3b>(i, j)[2] << endl;
				}
			}
		}

	}

	void ShowHsvImages(){
		for (int i = 0; i < numberOfImages; i++){
			namedWindow(imagesNames[i]);
			imshow(imagesNames[i], hsvImages[i]);
		}
	}

	void LoadColorLabels(){
		for (int i = 0; i < numberOfColors; i++){
			colorLabelsForSvm[i].create(numberOfImages, 1, CV_32FC1);
		}

		for (int i = 0; i < numberOfColors ; i++){
			cout << "0:red, 1:orange, 2:yellow, 3:green, 4:blue, 5:white, 6:violet" << endl;
			for (int j = 0; j < numberOfImages ; j++){
				cout << "Enter " << j << ". image label for color index " << i << " (1 or -1):"<< endl;
				float _label;
				cin >> _label;
				colorLabelsForSvm[i].at<float>(j, 0) = _label;
			}
		}
	}

	void TrainWithSvm(){
		int imageArea = images[0].cols * images[0].rows;
		svmTrainingMatrix.create(numberOfImages, imageArea, CV_32FC1);
		for (int k = 0; k < numberOfImages; k++){
			int svmMatrivIndex = 0;
			for (int i = 0; i < hsvImages[k].rows; i++){
				for (int j = 0; j < hsvImages[k].cols; j++){
					svmTrainingMatrix.at<float>(k, svmMatrivIndex++) = (float)hsvImages[k].at<Vec3b>(i, j)[0];
				}
			}
		}
		
		CvSVMParams myParameters;
		myParameters.svm_type = CvSVM::C_SVC;
		myParameters.kernel_type = CvSVM::POLY;
		myParameters.gamma = 3;

		LoadColorLabels();

		for (int i = 0; i < numberOfColors ; i++){
			svmSets[i].train(svmTrainingMatrix, colorLabelsForSvm[i], Mat(), Mat(), myParameters);
			//svmSets[i].save(svmFileNames[i]);
			//svmSets[i].load("redSvmFile"); //for loading
		}
		
	}

	void PredictImageColor(string _imageName){
		Mat _image = imread(_imageName);
		Mat _hsvImage;
		cvtColor(_image, _hsvImage, CV_BGR2HSV);
		
		Mat _svmPredictArray;
		_svmPredictArray.create(1, _hsvImage.rows*_hsvImage.cols, CV_32FC1);
		int svmMatrivIndex = 0;
		for (int i = 0; i < _hsvImage.rows; i++){
			for (int j = 0; j < _hsvImage.cols; j++){
				_svmPredictArray.at<float>(0, svmMatrivIndex++) = (float)_hsvImage.at<Vec3b>(i, j)[0];
			}
		}

		cout << "0:red, 1:orange, 2:yellow, 3:green, 4:blue, 5:white, 6:violet" << endl;
		cout << "Predicted color indexes for " << _imageName << " : ";
		for (int i = 0; i < numberOfColors; i++){
			int isInImage = svmSets[i].predict(_svmPredictArray);
			if (isInImage == 1){
				cout << i << " ";
			}
		}
	}
};

int main(int argc, char** argv)
{

	cout << endl << "--- MULTIMEDIA COMPUTING HOMEWORK 03 ---" << endl << endl;
	cout << "--- PART 01 ---" << endl << endl;
	string birdImageNames[3] = { "bird1.jpg", "bird2.jpg", "bird3.bmp"};

	Mat birdImage1 = imread(birdImageNames[0]);
	Mat birdImage2 = imread(birdImageNames[1]);
	Mat birdImage3 = imread(birdImageNames[2]);

	int kernelSize;
	CountingObjectsClass myClassForBirdImage1(birdImage1);
	myClassForBirdImage1.UseOtsuTresholdOnImage();
	kernelSize = 3;
	myClassForBirdImage1.UseMorphologicalFunctionOnImage(kernelSize);
	int birdNumber = myClassForBirdImage1.UseConnectedComponentFunctionOnImage();
	cout << "Bird Number in " << birdImageNames[0] << ": " << birdNumber << endl;
	myClassForBirdImage1.ShowImages();
	waitKey();

	CountingObjectsClass myClassForBirdImage2(birdImage2);
	myClassForBirdImage2.UseOtsuTresholdOnImage();
	kernelSize = 3;
	myClassForBirdImage2.UseMorphologicalFunctionOnImage(kernelSize);
	birdNumber = myClassForBirdImage2.UseConnectedComponentFunctionOnImage();
	cout << "Bird Number in " << birdImageNames[1] << ": " << birdNumber << endl;
	myClassForBirdImage2.ShowImages();
	waitKey();

	CountingObjectsClass myClassForBirdImage3(birdImage3);
	myClassForBirdImage3.UseOtsuTresholdOnImage();
	kernelSize = 1;
	myClassForBirdImage3.UseMorphologicalFunctionOnImage(kernelSize);
	birdNumber = myClassForBirdImage3.UseConnectedComponentFunctionOnImage();
	cout << "Bird Number in " << birdImageNames[2] << ": " << birdNumber << endl;
	myClassForBirdImage3.ShowImages();
	waitKey();

/*
	cout << "--- PART 02 ---" << endl << endl;

	ColorClassificationClass myClassPart2(5, 7);
	myClassPart2.ControlHsvValues();
	myClassPart2.ShowHsvImages();
	myClassPart2.TrainWithSvm();
	myClassPart2.PredictImageColor("image5.jpg");
*/

	return 0;
}