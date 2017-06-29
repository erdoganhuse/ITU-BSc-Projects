/************************************************************
*	Name Surname		: Huseyin Erdogan					*
*   Number				: 040100054							*
*	Lecture				: Analsys of Algorithm II			*
*   Project No          : 03                                *
*************************************************************/
#include<iostream>
#include<string>
#include<cstdio>
#include<map>
#include<vector>
#include<fstream>
#include <stdlib.h>

using namespace std;

class Customer{
public:
	int id;
	int pair;
	vector<int> apartment_list;
};

class Agent{
public:
	vector<vector<bool> > Graph;
	vector<Customer> pair_list;
	vector<Customer> customer_list;
	int global_v;
	int global_u;
	int customer_number;
	int apartment_number;

	Agent(){
	}
	void create_matrix(){
		apartment_number = find_apartment_number();
		customer_number= find_customer_number();
		for(int i=0;i<customer_number;i++){
			vector<bool> temp;
			for(int j=0;j<apartment_number;j++){
				temp.push_back(0);
			}		
			Graph.push_back(temp);
		}
		for(int i=0;i<customer_number;i++){
			for(int j=0;j<customer_list[i].apartment_list.size();j++){
				int k = customer_list[i].apartment_list[j];
				Graph[i][k-1] = 1;
			}
		}
	}
	void read_from_file(char *file_name){
		string line;
		fstream myfile;
		myfile.open(file_name);
		if(myfile.is_open()){
			while (getline(myfile, line)){
				int position,temp;
				Customer t_suctomer;
				do{
					position = line.find(" ");
					temp=atoi(line.substr(0,position).c_str());
					t_suctomer.apartment_list.push_back(temp);
					line = line.substr(position+1);
				}while(position <= line.length());
				customer_list.push_back(t_suctomer);
			}
			myfile.close();
		}
		else{ 
			cout << "File could not be opened." << endl; 
		}
	}
	int find_customer_number(){
		return customer_list.size();
	}
	int find_apartment_number(){
		int max = 0;
		for(int i=0;i<customer_list.size();i++){
			for(int j=0;j<customer_list[i].apartment_list.size();j++){
				if( max < customer_list[i].apartment_list[j]){
					max = customer_list[i].apartment_list[j];
				}	
			}
		}
		return max;
	}
	bool is_exist(int u,vector<bool> is_seen,vector<int> match_r){
		for(int v=0;v<apartment_number;v++){
			if(Graph[u][v] && !is_seen[v]){
				is_seen[v] = true;
				if(match_r[v]<0 || is_exist(match_r[v],is_seen,match_r)){
					match_r[v]=u;
					global_v=v;
					return true;
				}
			}
		}
		return false;
	}
	void max_bipartate_maching(){
		vector<int> match_r;
		for(int i=0;i<apartment_number;i++){
			match_r.push_back(-1);
		}
		int result = 0;
		for(int u=0; u<customer_number; u++){
			vector<bool> is_seen;
			for(int i=0;i<apartment_number;i++){
				is_seen.push_back(0);
			}
			if(is_exist(u,is_seen,match_r)){
				global_u= u; 
				Customer t_customer;
				t_customer.id==global_u;
				t_customer.pair=global_v;
				pair_list.push_back(t_customer);
			}		
		}
	}
	void write_input_file(){
		cout<<"----Input File----"<<endl;
		for(int i=0;i<customer_list.size();i++){
			for(int j=0;j<customer_list[i].apartment_list.size();j++){
				cout<<customer_list[i].apartment_list[j]<<" ";
			}		
			cout<<endl;
		}
	}
	void write_matrix(){
		cout<<"----Matrix----"<<endl;
		for(int i=0;i<customer_number;i++){
			for(int j=0;j<apartment_number;j++){
				cout<<Graph[i][j]<<" ";
			}		
			cout<<endl;
		}
	}
	void write_pairs(){
		cout<<"----Pairs----"<<endl;
		for(int i=0;i<pair_list.size();i++){		
			cout<<i+1<<". pair "<<pair_list[i].id + 1<<". customer  and "
				<<pair_list[i].pair + 1<<". apartment"<<endl;
		}
	}

};

int main(int argc, char* argv[]){

	char *inputFileName = argv[1];
	Agent my_agent;
	my_agent.read_from_file(inputFileName);
	my_agent.create_matrix();
	my_agent.write_input_file();
	my_agent.write_matrix();
	my_agent.max_bipartate_maching();
	my_agent.write_pairs();
    return 0;
}
