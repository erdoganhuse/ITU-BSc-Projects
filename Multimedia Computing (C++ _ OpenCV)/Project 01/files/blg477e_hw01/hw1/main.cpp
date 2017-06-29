#include <iostream>
#include <cstdio>
#include <math.h> 
#include <opencv2/core/core.hpp>
#include <opencv2/highgui/highgui.hpp>
#include <opencv2/imgproc/imgproc.hpp>

using namespace cv;
using namespace std;
//In this function image is converted to grayscale 
IplImage *MyGreyScaleFunction(IplImage *image){
	if (image->nChannels < 3){ return image; }
	
	IplImage *greyImage = cvCreateImage(cvGetSize(image), IPL_DEPTH_8U, 1);
	
	int indexRGB = 0;
	int indexGREY = 0;
	for (int i = 0; i < image->width; i++){
		for (int j = 0; j < image->height; j++){
			uchar greyScaleValue = 0;
			for (int k = 0; k < image->nChannels; k++){
				if (k == 0){
					greyScaleValue = greyScaleValue + 0.299 * uchar(image->imageData[indexRGB]);
				}
				else if (k == 1){
					greyScaleValue = greyScaleValue + 0.587 * uchar(image->imageData[indexRGB]);
				}
				else if (k == 2){
					greyScaleValue = greyScaleValue + 0.114 * uchar(image->imageData[indexRGB]);
				}
				indexRGB++;
			}
			greyScaleValue = greyScaleValue + 0.5;
			greyImage->imageData[indexGREY] = (uchar)(greyScaleValue);
			indexGREY++;
		}
	}
	return greyImage;
}
//In this function image is blurred to remove noise 
IplImage *MySmoothingFunction(IplImage *image){
	IplImage *smoothImage = cvCreateImage(cvGetSize(image), IPL_DEPTH_8U, 1);
	double gaussianKernel[9] = { 1, 2, 1, 2, 4, 2, 1, 2, 1};

	int index = 0;
	for (int i = 0; i < image->width; i++){
		for (int j = 0; j < image->height; j++){
			if (i == 511 && j == 511){
				cout << endl;
			}
			double smoothValue = 0;
			int imagePosition[9];
			imagePosition[3] = index - 1;
			imagePosition[4] = index;
			imagePosition[5] = index + 1;
			imagePosition[0] = imagePosition[3] - image->width;
			imagePosition[1] = imagePosition[4] - image->width;
			imagePosition[2] = imagePosition[5] - image->width;
			imagePosition[6] = imagePosition[3] + image->width;
			imagePosition[7] = imagePosition[4] + image->width;
			imagePosition[8] = imagePosition[5] + image->width;
			uchar valueOfIndex;
			for (int k = 0; k < 9 ; k++){
				double valueOfIndex;
				if (imagePosition[k]<0 || imagePosition[k] > image->width*image->height){
					valueOfIndex = uchar(0);
				}
				else{
					valueOfIndex = uchar(image->imageData[ imagePosition[k] ]);
				}
				smoothValue = smoothValue + (double)valueOfIndex*(gaussianKernel[k]);
			}
			smoothImage->imageData[index] = (uchar)(smoothValue/16);
			index++;
		}
	}
	return smoothImage;
}
//In this function image's gradient values and angles are founded
IplImage *MyGradientFunction(IplImage *image, vector<double> &angles){
	IplImage *sobelImage = cvCreateImage(cvGetSize(image), IPL_DEPTH_8U, 1);
	double sobelXKernel[9] = { -1, 0, 1, -2, 0, 2, -1, 0, 1 };
	double sobelYKernel[9] = { 1, 2, 1, 0, 0, 0, -1, -2, -1 };

	int index = 0;
	for (int i = 0; i < image->width; i++){
		for (int j = 0; j < image->height; j++){
			double sobelXValue = 0;
			double sobelYValue = 0;
			int imagePosition[9];
			imagePosition[3] = index - 1;
			imagePosition[4] = index;
			imagePosition[5] = index + 1;
			imagePosition[0] = imagePosition[3] - image->width;
			imagePosition[1] = imagePosition[4] - image->width;
			imagePosition[2] = imagePosition[5] - image->width;
			imagePosition[6] = imagePosition[3] + image->width;
			imagePosition[7] = imagePosition[4] + image->width;
			imagePosition[8] = imagePosition[5] + image->width;
			uchar valueOfIndex;

			for (int k = 0; k < 9; k++){
				double valueOfIndex;
				if (imagePosition[k]<0 || imagePosition[k] > image->width*image->height){
					valueOfIndex = uchar(0);
				}
				else{
					valueOfIndex = uchar(image->imageData[imagePosition[k]]);
				}
				sobelXValue = sobelXValue + (double)(valueOfIndex*(sobelXKernel[k]));
				sobelYValue = sobelYValue + (double)(valueOfIndex*(sobelYKernel[k]));
			}
			sobelXValue = sobelXValue/8;
			sobelYValue = sobelYValue/8;
			sobelImage->imageData[index] = uchar( sqrt( pow(sobelXValue,2) + pow(sobelYValue,2) ) );
			
			if (uchar(sobelImage->imageData[imagePosition[4]]) < 0){
				cout << "Error" << endl;
			}
			if (uchar(sobelImage->imageData[imagePosition[4]]) > 255){
				cout << "Error" << endl;
			}

			double theta = (atan2(sobelXValue, sobelYValue) / 3.14) * 180.0;
			if (theta < 0){ theta = theta + 360; }

			double newTheta;

			if ( ((theta > 0) && (theta < 22.5)) || ((theta > 157.5) && (theta < 202.5)) || ((theta > 337.5 ) && (theta < 360)) ){
				newTheta = 0; 
			}
			else if ( ((theta > 22.5) && (theta < 67.5)) || ((theta > 202.5) && (theta < 247.5)) ){ 
				newTheta = 45; 
			}
			else if ( ((theta > 67.5) && (theta < 112.5)) || ((theta > 247.5) && (theta < 292.5)) ){ 
				newTheta = 90; 
			}
			else if ( ((theta > 112.5) && (theta < 157.5)) || ((theta > 292.5) && (theta < 337.5)) ){ 
				newTheta = 135; 
			}
	
			angles.push_back(newTheta);
			index++;
		}
	}
	return sobelImage;
}
//In this function local maxima values of image are marked as edges
IplImage *MyNonMaximumSuppressionFunction(IplImage *image, vector<double> angles){
	IplImage *nonMaximumSuppressionImage = cvCreateImage(cvGetSize(image), IPL_DEPTH_8U, 1);
	cvCopy(image,nonMaximumSuppressionImage);

	int index = 0;
	for (int i = 0; i < image->width; i++){
		for (int j = 0; j < image->height; j++){
			int imagePosition[9];
			imagePosition[3] = index - 1;
			imagePosition[4] = index;
			imagePosition[5] = index + 1;
			imagePosition[0] = imagePosition[3] - image->width;
			imagePosition[1] = imagePosition[4] - image->width;
			imagePosition[2] = imagePosition[5] - image->width;
			imagePosition[6] = imagePosition[3] + image->width;
			imagePosition[7] = imagePosition[4] + image->width;
			imagePosition[8] = imagePosition[5] + image->width;
			
			uchar valueOfIndexes[9];
			for (int k = 0; k < 9; k++){
				if (imagePosition[k]<0 || imagePosition[k] > image->width*image->height){
					valueOfIndexes[k] = uchar(0);
				}
				else{
					valueOfIndexes[k] = uchar(image->imageData[imagePosition[k]]);
				}
			}
			if (angles[index] == 0){
				if (valueOfIndexes[3] > valueOfIndexes[4] || valueOfIndexes[5] > valueOfIndexes[4] ){
					nonMaximumSuppressionImage->imageData[index] = 0;
				}
			}
			else if (angles[index] == 45){
				if (valueOfIndexes[2] > valueOfIndexes[4] || valueOfIndexes[6] > valueOfIndexes[4] ){
					nonMaximumSuppressionImage->imageData[index] = 0;
				}
			}
			else if (angles[index] == 90){
				if (valueOfIndexes[1] > valueOfIndexes[4] || valueOfIndexes[7] > valueOfIndexes[4] ){
					nonMaximumSuppressionImage->imageData[index] = 0;
				}
			}
			else if (angles[index] == 135){
				if (valueOfIndexes[0] > valueOfIndexes[4] || valueOfIndexes[8] > valueOfIndexes[4] ){
					nonMaximumSuppressionImage->imageData[index] = 0;
				}
			}
			index++;
		}
	}
	return nonMaximumSuppressionImage;
}
//In this function Double Thresholding and Edge Tracking by Hysteresis methods are used on image
IplImage *MyHysteresisThresholdingFunction(IplImage *image, double lowValue, double highValue){
	IplImage *doubleThresholdingImage = cvCreateImage(cvGetSize(image), IPL_DEPTH_8U, 1);

	int index = 0;
	for (int i = 0; i < image->width; i++){
		for (int j = 0; j < image->height; j++){
			int imagePosition[9];
			imagePosition[3] = index - 1;
			imagePosition[4] = index;
			imagePosition[5] = index + 1;
			imagePosition[0] = imagePosition[3] - image->width;
			imagePosition[1] = imagePosition[4] - image->width;
			imagePosition[2] = imagePosition[5] - image->width;
			imagePosition[6] = imagePosition[3] + image->width;
			imagePosition[7] = imagePosition[4] + image->width;
			imagePosition[8] = imagePosition[5] + image->width;

			uchar pixelValue = uchar(image->imageData[index]);
			if ( pixelValue < lowValue){
				//Discard
				doubleThresholdingImage->imageData[index] = uchar(0);
			}
			else if ( pixelValue > highValue){
				//Keep
				doubleThresholdingImage->imageData[index] = uchar(255);
			}
			else{
				bool isNeighborsHigh = false;
				bool isNeighborsMiddle = false;
				for (int n = 0; n < 9; n++){
					if ((uchar(image->imageData[imagePosition[n]]) > highValue)){ isNeighborsHigh = true; }
					if ((uchar(image->imageData[imagePosition[n]]) > lowValue) && (uchar(image->imageData[imagePosition[n]]) < highValue)){ isNeighborsMiddle = true; }
				}
				if (isNeighborsHigh){
					//Keep
					doubleThresholdingImage->imageData[index] = uchar(255);
				}
				else if (isNeighborsMiddle){
					int newImagePosition[25];

					newImagePosition[10] = index - 2;
					newImagePosition[11] = index - 1;
					newImagePosition[12] = index;
					newImagePosition[13] = index + 1;
					newImagePosition[14] = index + 2;

					newImagePosition[0] = newImagePosition[10] - image->width * 2;
					newImagePosition[1] = newImagePosition[11] - image->width * 2;
					newImagePosition[2] = newImagePosition[12] - image->width * 2;
					newImagePosition[3] = newImagePosition[13] - image->width * 2;
					newImagePosition[4] = newImagePosition[14] - image->width * 2;

					newImagePosition[5] = newImagePosition[10] - image->width;
					newImagePosition[6] = newImagePosition[11] - image->width;
					newImagePosition[7] = newImagePosition[12] - image->width;
					newImagePosition[8] = newImagePosition[13] - image->width;
					newImagePosition[9] = newImagePosition[14] - image->width;

					newImagePosition[15] = newImagePosition[10] + image->width;
					newImagePosition[16] = newImagePosition[11] + image->width;
					newImagePosition[17] = newImagePosition[12] + image->width;
					newImagePosition[18] = newImagePosition[13] + image->width;
					newImagePosition[19] = newImagePosition[14] + image->width;

					newImagePosition[20] = newImagePosition[10] + image->width * 2;
					newImagePosition[21] = newImagePosition[11] + image->width * 2;
					newImagePosition[22] = newImagePosition[12] + image->width * 2;
					newImagePosition[23] = newImagePosition[13] + image->width * 2;
					newImagePosition[24] = newImagePosition[14] + image->width * 2;

					bool isNeighborsHighSecond = false;
					for (int m = 0; m < 25; m++){
						if ((uchar(image->imageData[newImagePosition[m]]) > highValue)){ isNeighborsHighSecond = true; }
					}

					if (isNeighborsHighSecond){
						//Keep
						doubleThresholdingImage->imageData[index] = uchar(255);
					}

				}
				else{
					//Discard
					doubleThresholdingImage->imageData[index] = uchar(0);
				}

			}
			index++;
		}
	}
	return doubleThresholdingImage;
}
//In this function all of the functions are united for reaching Canny Edge Detector
void MyCannyEdgeDetectionFunction(IplImage *originalImage, double lowValue, double highValue){
	cvNamedWindow("Original Image", WINDOW_AUTOSIZE);
	cvShowImage("Original Image", originalImage);

	IplImage *greyImage = cvCreateImage(cvGetSize(originalImage), IPL_DEPTH_8U, 1);
	greyImage = MyGreyScaleFunction(originalImage);
	cvNamedWindow("GreyScale Image", WINDOW_AUTOSIZE);
	cvShowImage("GreyScale Image", greyImage);

	IplImage *smoothImage = cvCreateImage(cvGetSize(greyImage), IPL_DEPTH_8U, 1);
	smoothImage = MySmoothingFunction(greyImage);
	cvNamedWindow("Smoothing Image", WINDOW_AUTOSIZE);
	cvShowImage("Smoothing Image", smoothImage);

	vector<double> angles;
	IplImage *gradientImage = cvCreateImage(cvGetSize(smoothImage), IPL_DEPTH_8U, 1);
	gradientImage = MyGradientFunction(smoothImage, angles);
	cvNamedWindow("Gradient Image", WINDOW_AUTOSIZE);
	cvShowImage("Gradient Image", gradientImage);

	IplImage *nonMaximumSuppressionImage = cvCreateImage(cvGetSize(gradientImage), IPL_DEPTH_8U, 1);
	nonMaximumSuppressionImage = MyNonMaximumSuppressionFunction(gradientImage, angles);
	cvNamedWindow("Non Maximum Suppression Image", WINDOW_AUTOSIZE);
	cvShowImage("Non Maximum Suppression Image", nonMaximumSuppressionImage);

	IplImage *hysteresisThresholdingImage = cvCreateImage(cvGetSize(nonMaximumSuppressionImage), IPL_DEPTH_8U, 1);
	hysteresisThresholdingImage = MyHysteresisThresholdingFunction(nonMaximumSuppressionImage, lowValue, highValue);
	cvNamedWindow("Hysteresis Thresholding Image", WINDOW_AUTOSIZE);
	cvShowImage("Hysteresis Thresholding Image", hysteresisThresholdingImage);

	IplImage *cannyImage = cvCreateImage(cvGetSize(hysteresisThresholdingImage), IPL_DEPTH_8U, 1);
	cvCopy(hysteresisThresholdingImage, cannyImage);
	cvNamedWindow("Canny Image", WINDOW_AUTOSIZE);
	cvShowImage("Canny Image", cannyImage);

	cvWaitKey(0);
	cvDestroyWindow("Original Image");
	cvDestroyWindow("GreyScale Image");
	cvDestroyWindow("Smoothing Image");
	cvDestroyWindow("Gradient Image");
	cvDestroyWindow("Non Maximum Suppression Image");
	cvDestroyWindow("Hysteresis Thresholding Image");
	cvDestroyWindow("Canny Image");
}

int main(int argc, char** argv)
{
	cout << endl << "--- CANNY EDGE DETECTOR ---" << endl<<endl;
	cout << "Enter Image Name :";
	string temp;
	cin >> temp;
	char imageName[sizeof(temp) + 1];
	strcpy_s(imageName, temp.c_str());
	int highValue,lowValue;
	cout << "Enter Thresholding Values for Hyteresis Thresholding Function "<<endl;
	cout << "Low Thresholding Value :";
	cin >> lowValue;
	cout << "High Thresholding Value :";
	cin >> highValue;
	IplImage *originalImage;
	originalImage = cvLoadImage(imageName);
	MyCannyEdgeDetectionFunction(originalImage, lowValue, highValue);

	return 0;
}
