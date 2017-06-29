//Hüseyin Erdoðan 040100054
#define MAX_CHILD 5  //Maximum coçuk sayýsý
#define NAME_LENGTH 10 // Maximumisim uzunlugu
#include<iostream>
#include<cstdio>
#include<stdlib.h>
#include<stdio.h>
#include<string.h>
#include<string>
#include<iomanip>
#include <stdlib.h>
#include<fstream>

using namespace std ;

struct spouse{
	char *s_name; //spouse un ismi
	int bearth_year2; //spouse un dogum yýlý
};

struct individual{
	char *n_name; //individual un ismi
	int birth_year;  //individual un dogum tarihi
	char gender;  //individual un cinsiyeti
	individual **children;  //individual cocuklarý
	int node_id;  //individual un kendi numarasý
	int parent_id;  //individual un parent numarasý
	spouse *partner;  //individual un partneri
};

struct list{
	individual *root;
	void create(); // agacý oluþturmak için
	void empty(); // //agacý boþaltmak için ana fonksiyon
	void Preorder_isearch(individual *,individual *,bool); // individual u agaca eklemek için yan fonksiyon
	void add_s(spouse *,int); //spouse u agaca eklemek için kullandýgým ana fonksiyon
	void add_spouse(individual *,spouse *,int,bool); // spouse u agaca eklemek için kullandýgým yan fonksiyon
    void add(individual *); // individual u agaca eklemek için ana fonksiyon
	void make_empty(individual *); //agacý boþaltmak için yan fonksiyon
	individual* search_node(char *,individual *,individual *); // individual tipindeki degiþkenin agactaki yerini bulmak için
	individual* search_parent(individual *,individual *,individual *); // individual tipindeki degiþkenin parent ýný bulmak için
	individual* search_grantparent(char *,individual *); // individual tipindeki degiþkenin grantparent ýný bulmak için
	void print(individual *); // agacý yazdýrmak için
};

void list::create(){
	root=NULL;
}
void list::make_empty(individual *p){
	if(p){
		for(int i=0;i<MAX_CHILD;i++){
			if (p ->children[i] != NULL){
				make_empty (p ->children[i]);
				p ->children[i] = NULL;
   			}
		}
		delete p;
	}
}
void list::empty(){
	make_empty(root);
}
void list::Preorder_isearch(individual *nptr,individual *temp,bool t){
	if(nptr && t==false){ 
		if(nptr->node_id==temp->parent_id){               //bu fonksiyonda add()fonksiyonundan
			int j=0;                                      //gelen individual tipindeki degeri agaca yerleþtiriyorum     
			while(nptr->children[j] != NULL){
				j++;
			}
			nptr->children[j] = temp;
			t=true;
		}
		if(t==false){
			for(int i=0;i<MAX_CHILD;i++){
				Preorder_isearch(nptr->children[i],temp,t); 
			}
		}
	}
}
void list::add(individual *toadd){
	bool eklendi = false;
	toadd->partner=NULL;
	toadd->children = new individual*[MAX_CHILD];
	for(int i=0;i<MAX_CHILD;i++){ //burada individual tipindeki degiþkenin bütün
		toadd->children[i]=NULL;  //cocuklarýný NULL yapýyorum
	}
	if(root==NULL){
		root=toadd;    //root a eklerken
		eklendi = true;
	}
	else{
		Preorder_isearch(root,toadd,eklendi);  //eger root degilse Preorder_isearch()	
		eklendi=true;                          //fonksiyonuna gönderiyorum        
	}
}
void list::add_s(spouse *toadd,int no){
	bool t1 = false; // burada add_spouse() fonksiyonunda spouse tipindeki degiþkeni  
	add_spouse(root,toadd,no,t1); //agaca yerleþtirdikten sonra fonk. sonlandýrmak için "t1" i tanýmlýyorum
}
void list::add_spouse(individual *nptr,spouse *toadd,int spouse_id,bool t){
	if(nptr && t==false){ //burada degiþkenin eþini bulup ona ekliyorum
		if(nptr->node_id==spouse_id){
			nptr->partner=toadd;
			t=true;
		}
		if(t==false){
			for(int i=0;i<MAX_CHILD;i++){
			add_spouse(nptr->children[i],toadd,spouse_id,t);
			}
		}
	}
}
individual *list::search_node(char *temp,individual *t,individual *t2){
	if(t){ //bu fonksiyonda kullanýcýda aldýgým ismin agactaki yerini bulup onu geri gönderiyorum
		if(strcmp(t->n_name,temp)==0){
			t2=t;
		}
		else{
			for(int i=0;i<MAX_CHILD;i++){
				t2=search_node(temp,t->children[i],t2);
			}
		}
	}
	return t2;
}
individual *list::search_parent(individual *nptr1,individual *nptr2,individual *nptr3){
	if(nptr1){ //burada individual tipindeki degiþkenin parent ýný bulup geri geri gönderiyorum
		if(nptr1->node_id==nptr2->parent_id){
			nptr3=nptr1;
		}
		else{
			for(int i=0;i<MAX_CHILD;i++){
				nptr3=search_parent(nptr1->children[i],nptr2,nptr3);
			}
		}
	}
	return nptr3;
}
void list::print(individual *nptr){ // bu fonksiyonu agacý kat kat yazdýrmak için ana
	for(int i=0;i<MAX_CHILD;i++){   // fonksiyon içindeki whole_family() fonksiyonun içinde kullanýyorum
		if(nptr->children[i]){
			cout<<nptr->children[i]->n_name<<endl;
		}
	}
}

typedef list Datastrucher;
Datastrucher book;

void print_menu();
bool make_chooice(char);
void read_fromfile();
void print_parent();
void print_children();
void print_cousins();
void print_grandchildren();
void print_grandparents();
void print_siblings();
void is_simbling();
void whole_family();

int main(){	
	book.create();
	bool end = false; 
	char chooice; 
	read_fromfile();
	if(book.root!=NULL){
		while (!end) { 
			print_menu(); 
			cin >> chooice;
			end = make_chooice(chooice); 
		}
		book.empty();
	}
	system("PAUSE");
	return EXIT_SUCCESS;
}

void print_menu(){
	cout << endl;
	cout << "Family Tree Application" << endl;
	cout << "A: print parent" << endl;
	cout << "B: print children" << endl;
	cout << "C: print cousins" << endl;
	cout << "D: print grandchildren" << endl;	
	cout << "E: print grandparents" << endl;
	cout << "F: print siblings" << endl;
	cout << "G: is simbling" << endl;
	cout << "W: for whole family" << endl;
	cout << "X: exit" << endl;	
	cout << endl;
	cout << "choose  an operation { A,B,C,D,E,F,G,W,X } : ";	
}
bool make_chooice(char chooice){
	bool end=false;
	switch (chooice) { 
		case 'A': case 'a': 
			print_parent();
			break; 
		case 'B': case 'b': 
			print_children();
			break;
		case 'C': case 'c': 
			print_cousins();
			break;
		case 'D': case 'd': 
			print_grandchildren();
			break;
		case 'E': case 'e':
			print_grandparents();
			break;
		case 'F': case 'f': 
			print_siblings();
			break;
		case 'G': case 'g': 
			is_simbling();
			break;
		case 'W': case 'w': 
			whole_family();
			break;
		case 'X': case 'x': 
			cout << "are you sure? (Y/N):";
			cin >> chooice;
			if(chooice=='Y' || chooice=='y')
				end=true; 
				break; 
		default: 
			cout << "Error!!!" << endl; 
			cout << "try again { A,B,C,D,E,F,G,W,X } :" ;
			cin >> chooice;
			end = make_chooice(chooice);
			break; 
	}
	return end;
}
void read_fromfile(){
	ifstream in_file ;
	cout << "File is opening" << endl ;
	in_file.open( "family.txt" ) ;
	if ( in_file.is_open() ){
			cout << "File is successfully opened" << endl ;
		while( !in_file.eof() ){
			char* p=new char[NAME_LENGTH];
			in_file>>p;
			if(strcmp(p,"spouse")!=0){ //ilk okunan ismi kontrol ediyorum
				individual *temp= new individual;
				temp->n_name=new char[strlen(p)];
				strcpy(temp->n_name,p);
				string temp_gander;
				in_file>>temp->birth_year>>temp_gander>>temp->node_id>>temp->parent_id;
				if(temp_gander.substr(0,1) == "M"){ 
					temp->gender = 'M';
				}	
				else{ 
					temp->gender = 'F';
				}                
				book.add(temp); //degerleri temp e atayýp agaca ekliyorum
			}	
			else{ //eger ilk okunan spouse ise 
				spouse *temp2 = new spouse;
				char* m=new char[NAME_LENGTH];
				int spouse_id;
				in_file>>m>>spouse_id>>temp2->bearth_year2;
				temp2->s_name=new char[strlen(m)];
				strcpy(temp2->s_name,m);
				book.add_s(temp2,spouse_id); //degerleri temp2 ye atayýp agaca ekliyorum		
			}
	
		}
		in_file.close();
		cout<<"Family tree is successfully created"<<endl;
	}
	else{ 
			cout << "Error opening file" << endl ;
	}
}
void print_parent(){
	cout<<"enter name that you want to print its parents:";
	char* temp=new char[NAME_LENGTH];
	cin>>temp;
	individual *nptr=new individual;
	individual *nptr2=new individual;
	individual *nptr3=new individual;
	nptr3=NULL;
	nptr=book.search_node(temp,book.root,nptr3); //temp in agactaki yerini bulup onu nptr ye eþitliyorum
	if(nptr==NULL){ //eger agacta öyle bir kayýt bulunmuyorsa                      
		cout<<"record is not exist"<<endl;
		return;
	}
	if(nptr==book.root){ //eger nptr root a eþitse 
		cout<<"parents are not exist"<<endl;
		return;
	}
	nptr2=book.search_parent(book.root,nptr,nptr3); //nptr nin parent ýný buluyorum
	cout<<"parent 1 :"<<nptr2->n_name<<endl;
	cout<<"parent 2 :"<<nptr2->partner->s_name<<endl;
}
void print_children(){
	cout<<"enter name that you want to print its children:";
	char* temp=new char[NAME_LENGTH];
	cin>>temp;
	individual *nptr=new individual;
	individual *nptr2=new individual;
	nptr2=NULL;
	nptr=book.search_node(temp,book.root,nptr2);
	if(nptr==NULL){ //eger agacta öyle bir kayýt bulunmuyorsa 
		cout<<"record is not exist"<<endl;
		return;
	}
	if(nptr->children[0]==NULL){ //eger cocugu yoksa
		cout<<"record has not children"<<endl;
		return;
	}
	int j=0;
	while(nptr->children[j]){
		cout<<"children "<<j+1<<" :"<<nptr->children[j]->n_name<<endl;
		j++;
	}
}
void print_cousins(){
	cout<<"enter name that you want to print its cousins:";
	char* temp=new char[NAME_LENGTH];
	cin>>temp;
	individual *nptr=new individual;
	individual *nptr2=new individual;
	individual *nptr3=new individual;
	nptr3=NULL;
	individual *nptr4=new individual;

	nptr=book.search_node(temp,book.root,nptr3); //yerini buluyorum
	if(nptr==book.root){ //eger nptr root sa 
		cout<<"record has not cousins"<<endl;
		return;
	}
	if(nptr==NULL){ //eger agacta öyle bir kayýt bulunmuyorsa 
		cout<<"record is not exist"<<endl;
		return;
	}
	nptr2=book.search_parent(book.root,nptr,nptr3); //parent ýný buluyorum
	if(nptr2==book.root){  //eger nptr2 root sa 
		cout<<"record has not cousins"<<endl;
		return;
	}
	nptr=book.search_parent(book.root,nptr2,nptr4); //parent ýný buluyorum
	int j=0;
	while(nptr->children[j]){
		int i=0;
		while(nptr->children[j]->children[i] && nptr->children[j] !=nptr2){
			cout<<"cousin :"<<nptr->children[j]->children[i]->n_name<<endl;		
			i++;
		}
		j++;
	}
}
void print_grandchildren(){
	cout<<"enter name that you want to print its grandchildren:";
	char* temp=new char[NAME_LENGTH];
	cin>>temp;
	individual *nptr=new individual;
	individual *nptr2=new individual;
	nptr2=NULL;
	nptr=book.search_node(temp,book.root,nptr2);
	if(nptr==NULL){ //eger agacta öyle bir kayýt bulunmuyorsa 
		cout<<"record is not exist"<<endl;
		return;
	}
	if(nptr->children[0]==NULL || nptr->children[0]->children[0]==NULL){ //eger kayýt cocugu yada torunu yoksa
		cout<<"record has not grandchildren"<<endl;
		return;
	}
	int j=0;
	while(nptr->children[j]){
		int i=0;
		while(nptr->children[j]->children[i]){
			cout<<"grandchildren :"<<nptr->children[j]->children[i]->n_name<<endl;		
			i++;
		}
		j++;
	}
}
void print_grandparents(){
	cout<<"enter name that you want to print its grandparents:";
	char* temp=new char[NAME_LENGTH];
	cin>>temp;
	individual *nptr=new individual;
	individual *nptr2=new individual;
	individual *nptr3=new individual;
	nptr3=NULL;
	individual *nptr4=new individual;
	nptr=book.search_node(temp,book.root,nptr3);
	if(nptr==NULL){ //eger agacta öyle bir kayýt bulunmuyorsa 
		cout<<"record is not exist"<<endl;
		return;
	}
	nptr2=book.search_parent(book.root,nptr,nptr3);
	if(nptr==book.root || nptr2==book.root){ //eger kayýtýn babsý yada dedesi yoksa
		cout<<"record has not grandparents"<<endl;
		return;
	}
	nptr=book.search_parent(book.root,nptr2,nptr4);
	cout<<"grandparent 1:"<<nptr->n_name<<endl;
	cout<<"grandparent 2:"<<nptr->partner->s_name<<endl;
}
void print_siblings(){
	cout<<"enter name that you want to print its siblings:";
	char* temp=new char[NAME_LENGTH];
	cin>>temp;
	individual *nptr=new individual;
	individual *nptr2=new individual;
	individual *nptr3=new individual;
	nptr3=NULL;
	nptr=book.search_node(temp,book.root,nptr3);
	if(nptr==NULL){ //eger agacta öyle bir kayýt bulunmuyorsa 
		cout<<"record is not exist"<<endl;
		return;
	}
	nptr2=book.search_parent(book.root,nptr,nptr3);
	int j=0;
	if(nptr==book.root && nptr->children[1]==NULL){
		cout<<"record has not siblings"<<endl;
		return;
	}
	while(nptr2->children[j]){
		if(strcmp(nptr->n_name,nptr2->children[j]->n_name) != 0 ){
			cout<<"siblings :"<<nptr2->children[j]->n_name<<endl;		
		}
		j++;
	}
}
void is_simbling(){
	cout<<"enter first name that you want to compare:";
	char* temp=new char[NAME_LENGTH];
	cin>>temp;
	cout<<"enter second name that you want to compare:";
	char* temp2=new char[NAME_LENGTH];
	cin>>temp2;
	individual *nptr=new individual;
	individual *nptr2=new individual;
	individual *nptr3=new individual;
	nptr3=NULL;
	individual *nptr4=new individual;
	individual *nptr5=new individual;
	nptr=book.search_node(temp,book.root,nptr3);
	if(nptr==NULL){ //eger agacta öyle bir kayýt bulunmuyorsa 
		cout<<"record 1 is not exist"<<endl;
		return;
	}
	nptr4=book.search_parent(book.root,nptr,nptr3);
	
	nptr2=book.search_node(temp2,book.root,nptr3);
	if(nptr2==NULL){ //eger agacta öyle bir kayýt bulunmuyorsa 
		cout<<"record 2 is not exist"<<endl;
		return;
	}
	nptr5=book.search_parent(book.root,nptr2,nptr3);

	if(nptr4->node_id==nptr5->node_id){
		cout<<"Persons are siblings"<<endl;
	}
	else{
		cout<<"Persons are not siblings"<<endl;
	}
}
void whole_family(){
	individual *nptr;
	individual *t;
	nptr=book.root;
	cout<<"---root---"<<endl;
	cout<<nptr->n_name<<endl;
	cout<<"---1. generation---"<<endl;
	book.print(nptr);
	cout<<"---2. generation---"<<endl;
	t=nptr;
	int i,j,k;
	for(i=0;i<MAX_CHILD;i++){
		if(nptr->children[i]!=NULL){
			book.print(nptr->children[i]);
		}
	}
	cout<<"---3. generation---"<<endl;
	for(i=0;i<MAX_CHILD;i++){
		if(nptr->children[i]!=NULL){
			for(int j=0;j<MAX_CHILD;j++){
				if(nptr->children[i]->children[j]!=NULL){
					book.print(nptr->children[i]->children[j]);
				}
			}
		}
	}
	cout<<"---4. generation---"<<endl;
	for(i=0;i<MAX_CHILD;i++){
		if(nptr->children[i]!=NULL){
			for(j=0;j<MAX_CHILD;j++){
				if(nptr->children[i]->children[j]!=NULL){
					for(k=0;k<MAX_CHILD;k++){
						if(nptr->children[i]->children[j]->children[k]!=NULL){
							book.print(nptr->children[i]->children[j]->children[k]);
						}
					}
				}
			}
		}
	}
}
