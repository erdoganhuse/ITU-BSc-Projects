/*
Hüseyin Erdoðan
040100054 
*/
#include <iostream>
#include <fstream>
#include <string>
#include <math.h>  
#include <stdlib.h>
#include <time.h>
#include <iomanip>

#define world_R 6373 // km
#define PI 3.1415926 // for converting degree to radian

using namespace std; 

class Point{
public:
	string city_name;
	float latitude;
	float longitude;
	float distance; 
};

class sort{
public:
	Point *city_array;
	int size_of_array;

	sort(int N){
		size_of_array=N;
		city_array=new Point[size_of_array];
	} 
	~sort(){
		delete[] city_array;
	}
	void calculate_distance(float t_latitude,float t_longitude){
		float dlon,dlat,t1,t2,a,c;
		t1=(t_latitude*PI)/180;

		for(int i=0;i<size_of_array;i++){
			dlon = ((city_array[i].longitude - t_longitude)*PI)/180;
			dlat = ((city_array[i].latitude - t_latitude)*PI)/180;
	
			t2=(city_array[i].latitude*PI)/180;

			a = sin(dlat/2) * sin(dlat/2) + sin(dlon/2) * sin(dlon/2) *cos(t1) * cos(t2); ;
			c = 2 * atan2( sqrt(a), sqrt(1-a) ) ;
			city_array[i].distance = world_R * c ;
		}
	}
	void insertion_sort(){
		int i, j;
		Point t;
		for (i = 1; i < size_of_array; i++){
			j = i;
			while (j > 0 && city_array[j - 1].distance > city_array[j].distance){
				t = city_array[j];
				city_array[j]= city_array[j - 1];
				city_array[j - 1] = t;
				j--;
			}
		}
	}
	Point* merge_sort(Point *t_array,int sizeofarray){
		if(sizeofarray < 2){
			return t_array;
		}
		int middle = sizeofarray/2;
		Point *array_1 = merge_sort(t_array,middle);
		Point *array_2 = merge_sort(t_array+middle,sizeofarray-middle); 
		return assistant_merge(array_1, middle, array_2, sizeofarray-middle);
	}
	Point* assistant_merge(Point *array_1,int sizeofarray1,Point *array_2,int sizeofarray2){
		int i = 0,j = 0;
		Point* output = new Point[sizeofarray1+sizeofarray2];
	    while((i < sizeofarray1) && (j < sizeofarray2))
		{
			if(array_1[i].distance >= array_2[j].distance)
			{
				output[i+j] = array_2[j];
				++j;
			}
			else
			{
				output[i+j] = array_1[i];
				++i;
			} 
		}
		while(i < sizeofarray1)
		{
			output[i+j] = array_1[i];
			i++;
		}
		while( j < sizeofarray2)
		{
			output[i+j] = array_2[j];
			j++;
		}
		return output;
	}
	void linear_search(){
		Point temp;
		for(int i=0;i<size_of_array;i++){
			for(int j=i;j<size_of_array;j++){
				if(city_array[i].distance > city_array[j].distance){
					temp = city_array[i];
					city_array[i] = city_array[j];
					city_array[j] = temp;
				}
			}
		}
	}
};

int main(int argc, char* argv[]){

	int N = atoi(argv[1]);
	int K = atoi(argv[2]);
	float r_latitude = atof(argv[5]);
	float r_longitude= atof(argv[6]);
	string sort_type = argv[3];

	sort s_array(N); //creating an array that's size is N

	string line;
	ifstream myfile("location.txt");
	if(myfile.is_open())
	{
		int i=0;	
		while (getline(myfile, line) && i<s_array.size_of_array)
		{
			unsigned pos = line.find("\t"); 
			string city = line.substr(0,pos);
			line = line.substr(pos+1);
			pos = line.find("\t");
			float latitude = atof(line.substr(0,pos).c_str());
			line = line.substr(pos+1);
			float longitude = atof(line.substr(0,pos).c_str());

			s_array.city_array[i].city_name=city;
			s_array.city_array[i].latitude=latitude;
			s_array.city_array[i].longitude=longitude;

		    i++;
		}
		myfile.close();
	}
	else{ 
		cout << "File could not be opened." << endl; 
	} 

	ofstream outputfile("location_output.txt");
	s_array.calculate_distance(r_latitude,r_longitude);

	clock_t start,end;
	start = clock();

	Point *temp=new Point[N];
	if(sort_type=="Merge"){
		Point *t;
		t=s_array.merge_sort(s_array.city_array,s_array.size_of_array);
		for(int i=0;i<K;i++){
			temp[i]=t[i];  //after sorting in this line K size array created
		}
	}
	else if(sort_type=="Insertion"){
		s_array.insertion_sort();
		for(int i=0;i<K;i++){
			temp[i]=s_array.city_array[i];  //after sorting in this line K size array created
		}
	}
	else if(sort_type=="Linear"){
		s_array.linear_search();
		for(int i=0;i<K;i++){
			temp[i]=s_array.city_array[i];  //after sorting in this line K sized array created
		}
	}
	end=clock();
	double sec=((double)(end-start))/CLOCKS_PER_SEC;
	cout<<"time : "<< fixed << setprecision(20)<<sec<<" click : "<<end-start<<endl;
	//system("PAUSE"); //to seing the time 
	for(int i=0;i<K;i++){
		outputfile<<temp[i].city_name<<"   "<<temp[i].latitude<<"   "<<temp[i].longitude<<endl;  //K sized array written to file named "location_output.txt"
	}
	outputfile.close();
	return 0;
}