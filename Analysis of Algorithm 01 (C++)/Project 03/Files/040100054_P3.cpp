/****************
Hüseyin Erdoðan
040100054
*****************/

#include <iostream>
#include <fstream>
#include <string>
#include <math.h>  
#include <stdlib.h>
#include <time.h>
#include <iomanip>

using namespace std;

class my_hash{
private:
	int *hash_array;  //used for storing elements 
	int sizeofarray;  
public:
	int insert_collision;  //used for storing insert collision
	int search_collision;  //used for storing search collision
	int a0;  //random number that is used in universal hashing 
	int a1;  //random number that is used in universal hashing 
	int a2;  //random number that is used in universal hashing 

	my_hash(int t_size){
		sizeofarray=t_size;
		hash_array =new int[sizeofarray];

		insert_collision=0;
		search_collision=0;

		srand (time(NULL)); 
		a0=rand()%sizeofarray;
		a1=rand()%sizeofarray;
		a2=rand()%sizeofarray;

		for(int i=0;i<sizeofarray;i++){
			hash_array[i]=-1;  //all elements are -1 so we will know which element of array is empty
		}
	}
	~my_hash(){
		delete[] hash_array;
	}
	void insert_lineer_hashing(int element){
		int i=0;
		bool in_location=false;
		while(i<sizeofarray && in_location==false){

			int location =(element+i)%sizeofarray;

			if(hash_array[location]==-1){ //controlling location is emty
				hash_array[location]=element;
				in_location=true;
			}
			else if(in_location==false){ // if location is naot empty insert_collision is increased
				insert_collision++;
			}
			i++;
		}
	}
	void insert_double_hashing(int element){
		int i=0;
		bool in_location=false;
		while(i<sizeofarray && in_location==false){

			int location1 = element%sizeofarray;
			int location2 = 1 + element%(sizeofarray-1);
			int location = (location1 +i*location2)%sizeofarray;

			if(hash_array[location]==-1){//controlling location is emty
				hash_array[location]=element;
				in_location=true;
			}
			else if(in_location==false){// if location is naot empty insert_collision is increased
				insert_collision++;
			}
			i++;
		}

	}
	void insert_universal_hashing(int element){

		int k_2=element%1000;
		int k_1=(element/1000)%1000;
		int k_0=(element/1000000)%1000;

		int location=(k_0*a0 + k_1*a1 + k_2*a2)%sizeofarray;
		if(hash_array[location]==-1){//controlling location is emty
			hash_array[location]=element;
		}
		else{// if location is naot empty insert_collision is increased
			insert_collision++;
			this->insert_double_hashing(element);
		}
	}
	void search_lineer_hashing(char *filename){
		string line;
		ifstream myfile(filename);
		if(myfile.is_open())
		{
			int i=0;	
			while (getline(myfile, line) && i<sizeofarray)
			{
				unsigned pos = line.find("\t"); 
				int element = atof(line.substr(0,pos).c_str());
				
				bool in_location=false;
				int j=0;
				while(j<sizeofarray && in_location==false){
					int location =(element+j)%sizeofarray;
					if(hash_array[location]==element){ //if element is found 
						in_location=true;
					}
					else if(in_location==false){ // element is not found search_collision is increased
						search_collision++;
					}
					j++;
				}
				i++;
			}
			myfile.close();
		}
		else{ 
			cout << "File could not be opened." << endl; 
		} 		
	}
	void search_double_hashing(char *filename){
		string line;
		ifstream myfile(filename);
		if(myfile.is_open())
		{
			int i=0;	
			while (getline(myfile, line) && i<sizeofarray)
			{
				unsigned pos = line.find("\t"); 
				int element = atof(line.substr(0,pos).c_str());
				
				bool in_location=false;
				int j=0;
				while(j<sizeofarray && in_location==false){
					int location1 = element%sizeofarray;
					int location2 = 1 + element%(sizeofarray-1);
					int location = (location1 +j*location2)%sizeofarray;
					if(hash_array[location]==element){//if element is found 
						in_location=true;
					}
					else if(in_location==false){// element is not found search_collision is increased
						search_collision++;
					}
					j++;
				}
				i++;
			}
			myfile.close();
		}
		else{ 
			cout << "File could not be opened." << endl; 
		} 		
	}
	void search_universal_hashing(char *filename){
		string line;
		ifstream myfile(filename);
		if(myfile.is_open())
		{
			int i=0;	
			while (getline(myfile, line) && i<sizeofarray)
			{
				unsigned pos = line.find("\t"); 
				int element = atof(line.substr(0,pos).c_str());
				
				bool in_location=false;
				int j=0;
				while(j<sizeofarray && in_location==false){//if element is found 
					int k_2=element%1000;
					int k_1=(element/1000)%1000;
					int k_0=(element/1000000)%1000;

					int location=(k_0*a0 + k_1*a1 + k_2*a2)%sizeofarray;
					if(hash_array[location]==element){//if element is found 
						in_location=true;
					}
					else{ //if element is not found, do double hashing for searching
						search_collision++;
						int location1 = element%sizeofarray;
						int location2 = 1 + element%(sizeofarray-1);
						int location = (location1 +j*location2)%sizeofarray;
						if(hash_array[location]==element){
							in_location=true;
						}
						else if(in_location==false){
							search_collision++;
						}
					}						
					j++;
				}
				i++;
			}
			myfile.close();
		}
		else{ 
			cout << "File could not be opened." << endl; 
		} 		
	}
};

int main(){

	int t_size,hash_type;
	cout<<"Enter size of array : ";
	cin>>t_size;
	cout<<"Enter type of hash (lineer->1  double->2  universal->3) : ";
	cin>>hash_type;

	my_hash t_array(t_size);
	string line;
	ifstream myfile("insert.txt");
	if(myfile.is_open())
	{
		int i=0;	
		while (getline(myfile, line) && i<t_size)
		{
			unsigned pos = line.find("\t"); 
			int k = atof(line.substr(0,pos).c_str());
			if(hash_type==1){
				t_array.insert_lineer_hashing(k);
			}
			else if(hash_type==2){
				t_array.insert_double_hashing(k);
			}
			else if(hash_type==3){
				t_array.insert_universal_hashing(k);
			}
		    i++;
		}
		myfile.close();
	}
	else{ 
		cout << "File could not be opened." << endl; 
	} 

	if(hash_type==1){
		t_array.search_lineer_hashing("search.txt");
	}
	else if(hash_type==2){
		t_array.search_double_hashing("search.txt");
	}
	else if(hash_type==3){
		t_array.search_universal_hashing("search.txt");
	}
	cout<<"insert collosion:"<<t_array.insert_collision<<"  search collosion:"<<t_array.search_collision<<endl;
	
	system("PAUSE");
	return  0;
} 


