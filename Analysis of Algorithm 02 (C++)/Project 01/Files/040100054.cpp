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
#include<vector>


using namespace std;

class Person{
public:
	int person_id;
	bool is_match;

};

class Lecturer{
public:
	vector<int> assistant_preference_list;
	vector<int> lectures;
	Person matched_assistant;
	bool *is_empty_lecture;
	bool has_empty_lecture();
	int proposal_count;
	bool is_match;
public:
	Lecturer(){
		proposal_count=0;
		matched_assistant.is_match=false;
	}
	~Lecturer(){
		assistant_preference_list.clear();
		lectures.clear(); 
	}
	bool is_giving_lecture(int lecture_id){
		for(int i=0;i<sizeof(lectures);i++){
			if(lecture_id==lectures[i]){
				return true;
			}
		}
	}
	bool has_empty_lecture(){
		for(int i=0;i<lectures.size();i++){
			if(empty_lecture==false)
				return false;
		}
		return true;
	}
	void accept_propose(Person new_assistant){
		matched_assistant.is_match=false;
		matched_assistant=new_assistant;
		matched_assistant.is_match=true;
		proposal_count++;
	}

};

class Assistant{
public:
	vector<int> lecture_preference_list;
	Person matched_lecturer;
	int proposal_count;
	bool is_match;
	bool *match_list;

public:
	Assistant(){
		proposal_count=0;
	}
	~Assistant(){
		lecture_preference_list.clear();
	}
	void accept_propose(Person new_lecturer){
		matched_lecturer.is_match=false;
		matched_lecturer=new_lecturer;
		matched_lecturer.is_match=true;
		proposal_count++;
	}

};

void readfile_assistant(vector<Assistant> &assistant_vector,string file_name){
	string line;
	fstream assistant_file;
	assistant_file.open(file_name);
	if(assistant_file.is_open()){
		while (getline(assistant_file, line)){
			int position,temp;
			Assistant t_assistant;
			do{
				position = line.find(" ");
				temp=atoi(line.substr(0,position).c_str());
				t_assistant.lecture_preference_list.push_back(temp);
				line = line.substr(position+1);
			}while(position <= line.length());
			assistant_vector.push_back(t_assistant);
		}
		assistant_file.close();
	}
	else{ cout << "File could not be opened." << endl; }


}
void readfile_lecturer(vector<Lecturer> &lecturer_vector,string file_name){
	string line;
	fstream lecturer_file;
	lecturer_file.open(file_name);
	if(lecturer_file.is_open()){
		while (getline(lecturer_file, line)){
			int position,temp;
			Lecturer t_lecturer;
			do{
				position = line.find(" ");
				temp=atoi(line.substr(0,position).c_str());
				t_lecturer.assistant_preference_list.push_back(temp);
				line = line.substr(position+1);
			}while(position <= line.length());
			lecturer_vector.push_back(t_lecturer);
		}
		lecturer_file.close();
	}
	else{ cout << "File could not be opened." << endl; }
}
void readfile_lecture(vector<Lecturer> &lecturer_vector,string file_name){
	string line;
	fstream lecture_file;
	lecture_file.open(file_name);
	if(lecture_file.is_open()){
		int i=0;
		while (getline(lecture_file, line)){
			int position,temp;
			int j=0;
			do{
				position = line.find(" ");
				temp=atoi(line.substr(0,position).c_str());
				lecturer_vector[i].lectures.push_back(temp);
				line = line.substr(position+1);
				j++;
			}while(position <= line.length());
			lecturer_vector[i].empty_lecture=new bool[i];
			for(int k=0;k<j;k++){
				lecturer_vector[i].empty_lecture[k]=true;
			}
			i++;
		}
		lecture_file.close();
	}
	else{ cout << "File could not be opened." << endl; }
}

int main(int argc, char* argv[]){


	string argument1 = argv[5];
	string argument2 = argv[4];
	string argument3 = argv[3];
	string argument4 = argv[7];

	vector<Assistant> assistant_vector;
	readfile_assistant(assistant_vector,argument1);
	vector<Lecturer> lecturer_vector;
	readfile_lecturer(lecturer_vector,argument2);
	readfile_lecture(lecturer_vector,argument3);


	for(int i=0;i<lecturer_vector.size();i++){
		for(int j=0;j<lecturer_vector[i].assistant_preference_list.size();j++){
			cout<<lecturer_vector[i].assistant_preference_list[j]<<" ";
		}
		cout<<endl;
	}



	system("PAUSE");
	return 0;	
}

