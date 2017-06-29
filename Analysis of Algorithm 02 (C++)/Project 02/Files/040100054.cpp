/************************************************************
*	Name Surname		: Huseyin Erdogan					*
*   Number				: 040100054							*
*	Lecture				: Analsys of Algorithm II			*
*   Project No          : 02                                *
*************************************************************/

#include <iostream>
#include <fstream>
#include <string>
#include <math.h>  
#include <stdlib.h>
#include <time.h>
#include <iomanip>
#include<vector>
#include <map>
#define MAX_FREQUENCY 10000000000

using namespace std;

map<string, int> temp_map; 
map<int, string> frequency; 

struct HuffmanNode{
public:
	int Key;
    string Name;
    string Value;
	string HuffmanCode;

	HuffmanNode *left;
	HuffmanNode *right;
	HuffmanNode (){
		Key = 0;
		HuffmanCode = "";
		left = NULL;
		right = NULL;
	}
   	void Set_Name(){
         if(Value == "ala"){Name ="Alanine";}
         else if(Value == "alg"){Name ="Arginine";} 
         else if(Value == "asn"){Name ="Asparagine";}   
         else if(Value == "asp"){Name ="Aspartic acid";}   
         else if(Value == "cys"){Name ="Cysteine";}   
         else if(Value == "leu"){Name ="Leucine";}   
         else if(Value == "lys"){Name ="Lysine";}   
         else if(Value == "met"){Name ="Methionine";}   
         else if(Value == "phe"){Name ="Phenylalanine";}   
         else if(Value == "pro"){Name ="Proline";}   
         else if(Value == "glu"){Name ="Glutamic acid";}   
         else if(Value == "gln"){Name ="Glutamine";}   
         else if(Value == "gly"){Name ="Glycine";}   
         else if(Value == "his"){Name ="Histidine";}   
         else if(Value == "ile"){Name ="Isoleucine";}   
         else if(Value == "ser"){Name ="Serine";}   
         else if(Value == "thr"){Name ="Threonine";} 
         else if(Value == "thp"){Name ="Tryptophan";} 
         else if(Value == "tyr"){Name ="Tyrosine";} 
         else if(Value == "val"){Name ="Valine";}           
    }
};

struct HuffmanTree{
public:
	vector<HuffmanNode> HuffmanArray;
	vector<HuffmanNode> LookupTable;

	void Create_Huffman_Tree(){
		map<string, int>::const_iterator iterator1;
		map<int, string>::const_iterator iterator2;
		
		for (iterator1=temp_map.begin(); iterator1 != temp_map.end(); ++iterator1){
			frequency[iterator1->second] = iterator1->first;
		}
	
		for (iterator2=frequency.begin(); iterator2 != frequency.end(); ++iterator2){
			HuffmanNode t_node;
			t_node.Key = iterator2->first;
			t_node.Value = iterator2->second;
			t_node. Set_Name();
			HuffmanArray.push_back(t_node);
		}
		while(!HuffmanArray.empty()){
			HuffmanNode * temp1 = new HuffmanNode;   
			HuffmanNode * temp2 = new HuffmanNode;
			HuffmanNode * temp3 = new HuffmanNode;
			*temp2 = Extract_Min();
			*temp3 = Extract_Min();

			temp1->left= temp2;
			temp1->right = temp3;
			temp1->Key = temp2->Key + temp3->Key;
			HuffmanArray.push_back(*temp1);
			if(HuffmanArray.size() == 1){
				break;
			}
		}
		Create_Huffman_Code(&HuffmanArray[0],"");
	}
	void Create_Huffman_Code(HuffmanNode * temp,string tempHuffmanCode){
		HuffmanNode * rootNode = new HuffmanNode;
		rootNode = temp;
  
		rootNode->HuffmanCode = tempHuffmanCode;
		if(rootNode == NULL){
			return;
		}
		else if(rootNode->left== NULL && rootNode->right == NULL){
			LookupTable.push_back(*rootNode);
		}
		else{
			rootNode->left->HuffmanCode = tempHuffmanCode.append("0");
			tempHuffmanCode.erase(tempHuffmanCode.end()-1);
			rootNode->right->HuffmanCode = tempHuffmanCode.append("1");
			tempHuffmanCode.erase(tempHuffmanCode.end()-1);

			Create_Huffman_Code(rootNode->left,tempHuffmanCode.append("0"));
			tempHuffmanCode.erase(tempHuffmanCode.end()-1);
			Create_Huffman_Code(rootNode->right,tempHuffmanCode.append("1"));
			tempHuffmanCode.erase(tempHuffmanCode.end()-1);
		}
	}
	HuffmanNode Extract_Min(){ 
		int temp = MAX_FREQUENCY;
		int position;
		for(int i=0;i<HuffmanArray.size();i++){
			if(temp > HuffmanArray[i].Key){
				position = i;
				temp = HuffmanArray[i].Key;
			}
		}
		HuffmanNode t_node = HuffmanArray[position];
		HuffmanArray.erase(HuffmanArray.begin() + position);
		return t_node;
	}
	void Write_Huffman_Codes(){
		for(int i=0;i<LookupTable.size();i++){
			cout<<"Protein : "<<LookupTable[i].Name<<endl;
			cout<<"	Frequency : "<<LookupTable[i].Key<<endl;
			cout<<"	Huffman Code : "<<LookupTable[i].HuffmanCode<<endl;
		}
	}
	void Read_From_File(char *file_name){
		string line;
		fstream my_file;
		my_file.open(file_name);
		if(my_file.is_open()){
			while (getline(my_file, line)){
				int position;
				string	temp;
				do{
					position = line.find(" ");
					temp = line.substr(0,position).c_str();
					temp_map[temp]++;
					line = line.substr(position+1);
				}while(position <= line.length());
			}
			my_file.close();
		}
		else{ cout << "File could not be opened." << endl; }
	}
	void Write_To_File(char *file_name1,char *file_name2){
		string line;
		ifstream my_file1;
		ofstream my_file2;
		my_file1.open(file_name1);
		my_file2.open(file_name2);
		if(my_file1.is_open()){
			while (getline(my_file1, line)){
				int position;
				string	temp;
				do{
					position = line.find(" ");
					temp = line.substr(0,position).c_str();
					for(int i=0;i<LookupTable.size();i++){
						if(LookupTable[i].Value == temp){
							my_file2<<LookupTable[i].HuffmanCode;
						}
					}
					line = line.substr(position+1);
				}while(position <= line.length());
				my_file2<<endl;
			}
			my_file1.close();
			my_file2.close();
		}
		else{ cout << "File could not be opened." << endl; }
	}
    void Find_Protein_Name(string t_Huffman_Code){
		 HuffmanNode t_node; 
		 HuffmanNode _node; 
         t_node = HuffmanArray[0];
         int position = 0;                               
         while( position < t_Huffman_Code.size() ){
                            int temp = atoi( t_Huffman_Code.substr(position,1).c_str() );
                            position++;
							if(temp == 0 && t_node.left != NULL){
								t_node = *t_node.left; 
                            }
                            else if(temp == 1 && t_node.right != NULL){
								t_node = *t_node.right;
                            }
                            else{
                                 break;
                            }                                                                                           
         }
         
		 if(t_node.HuffmanCode == t_Huffman_Code && t_node.left == NULL && t_node.right == NULL){
			cout<<"Protein that has "<<t_node.HuffmanCode<<" code : "<<t_node.Name<<endl;                  
         }
         else if( t_Huffman_Code == "-1"){
			return;             
         }
		 else{
			cout<<"Code not founded"<<endl;   
		 }
    }
	void Find_Bits(){
		double HuffmanTreeBits = 0;
		double NormalBits = 0;
		for(int i=0;i<LookupTable.size();i++){
			NormalBits = NormalBits + LookupTable[i].Key*5;
			HuffmanTreeBits = HuffmanTreeBits + (LookupTable[i].Key)*( LookupTable[i].HuffmanCode.size() );
		}
		cout<<"----------------------------------------------"<<endl;
		cout<<"Bits that are fixed length encoding require : "<<NormalBits<<endl;
		cout<<"Bits that are Huffman encoding used : "<<HuffmanTreeBits<<endl;
		cout<<"Compression Rate : "<<100*((NormalBits - HuffmanTreeBits)/(NormalBits))<<endl;
		cout<<"----------------------------------------------"<<endl;
	}
};

int main(int argc, char* argv[]){

	char *inputFileName  = argv[1];
	//inputFileName = "proteins.txt" ;
	char *outputFileName = "encodedProteins.txt" ;

	HuffmanTree ProteinTree;
	ProteinTree.Read_From_File(inputFileName);
	ProteinTree.Create_Huffman_Tree();
	ProteinTree.Write_Huffman_Codes();
	ProteinTree.Write_To_File(inputFileName,outputFileName);
	string tcode ;
	ProteinTree.Find_Bits();
	while(tcode != "-1"){
		cout<<"Enter Code of Protein (-1 for exit) : ";
		cin>>tcode;
		ProteinTree.Find_Protein_Name(tcode);	
	}
    return 0;
}
