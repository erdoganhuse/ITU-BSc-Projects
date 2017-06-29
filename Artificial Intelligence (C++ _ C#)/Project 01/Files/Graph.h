#include <iostream>
#include <ctime>
#include <fstream>
#include <malloc.h>

using namespace std;

class node{
public:
	int info;
	node *next;
};

class Queue {
private:
	node *front;
	node *rear;
public:
	Queue(){
		front = NULL;
		rear = NULL;
	}
	~Queue() {
		delete front;
	}
	bool isEmpty(){
		return (front == NULL);	
	}
	void enqueue(int data) {
		node *temp = new node();
		temp->info = data;
		temp->next = NULL;
		if(front == NULL){
			front = temp;
		}
		else{
			rear->next = temp;
		}
		rear = temp;
	}
	int dequeue() {
		node *temp = new node();
		int value;
		if(front == NULL){
			cout<<"\nQueue is Emtpty\n";
		}
		else{
			temp = front;
			value = temp->info;
			front = front->next;
			delete temp;
		}
		return value;
	}
	void display(){
		node *p = new node;
		p = front;
		if(front == NULL){
			cout<<"\nNothing to Display\n";
		}
		else{
			while(p!=NULL){
				cout<<endl<<p->info;
				p = p->next;
			}
		}
	}
};

class stack{
private:
	node *top;
public:
	stack(){
		top = NULL;
	} 
	void push(int data){
		node *p;
		if((p=(node*)malloc(sizeof(node)))==NULL){
			cout<<"Memory Exhausted";
			exit(0);
		}
		p = new node;
		p->info = data;
		p->next = NULL;
		if(top!=NULL){
			p->next = top;
		}
		top = p;
	} 
	int pop(){
		struct node *temp;
		int value;
		if(top==NULL){
			cout<<"\nThe stack is Empty"<<endl;
		}
		else{
			temp = top;
			top = top->next;
			value = temp->info;
			delete temp;
		}
		return value;
	} 
	bool isEmpty(){
		return (top == NULL);
	} 
	void display(){
		struct node *p = top;
		if(top==NULL){
			cout<<"\nNothing to Display\n";
		}
		else{
			cout<<"\nThe contents of Stack\n";
			while(p!=NULL){
				cout<<p->info<<endl;
				p = p->next;
			}
		}
	} 
}; 

class Graph{
private:
	int n;
	int **A;
public:
	Graph(int size) {
		int i, j;
		if (size < 2) n = 2;
		else n = size;
		A = new int*[n];
		for (i = 0; i < n; ++i)
			A[i] = new int[n];
		for (i = 0; i < n; ++i)
			for (j = 0; j < n; ++j)
				A[i][j] = 0;
	}
	~Graph() {
		for (int i = 0; i < n; ++i)
		delete [] A[i];
		delete [] A;
	}
	bool isConnected(int u, int v) {
		return (A[u-1][v-1] == 1);
	}
	void addEdge(int u, int v) {
		A[u-1][v-1] = A[v-1][u-1] = 1;
	}
	void BFS(int s,int required) {
		Queue Q;
		Q.enqueue(s);
		cout << "*****BFS*****"<<endl<<"Starting from vertex ";
		cout << s <<" to "<<required<< " : " << endl;
		while (!Q.isEmpty()) {
			int v = Q.dequeue();

			if(v==12){ //12 is index of node named "10"
				cout<<"10 ";
			}
			else if(v==13){//13 is index of node named "4"
				cout<<"4 ";
			}
			else{
				cout << v << " ";
			}
			
			if(v == required){ 
				break;
			}

			for (int w = 1; w <= n; ++w)
				if (isConnected(v, w)) {
					Q.enqueue(w);
				}
		}
		cout << endl;
	}
	void DFS(int x, int required){
		stack s;
		int i,j=0;
		s.push(x);
		if(x == required) return;
		cout << "*****DFS*****"<<endl<<"Starting from vertex ";
		cout << x <<" to "<<required<< " : " << endl;
		while(!s.isEmpty()){
			int k = s.pop();
			if(k == required){ 
				cout<<k<<" ";
				break;
			}
			if(k==12){
				cout<<"10 ";
			}
			else if(k==13){
				cout<<"4 ";
			}
			else{
				cout<<k<<" ";
			}
			j++;
			if(j > 500){
				cout<<endl<<"ERROR!!! DFS entered in a infinite loop."<<endl;
				break;
			}
			for (i = n; i >= 0 ; --i)
				if (isConnected(k, i)) {
					s.push(i);
				}
		}
		cout<<endl;
	}
	void readfromFile(char *file_name){
		fstream my_file;
		my_file.open(file_name);
		if (my_file.is_open()) {
			while (!my_file.eof()) {
				int vertex1_index,vertex2_index;
				double dis;
				my_file>>vertex1_index>>vertex2_index>>dis;
				//cout<<vertex1_index<<" "<<vertex2_index<<" "<<dis<<endl;
				//vertex1_index--;
				//vertex2_index--;
				addEdge(vertex1_index,vertex2_index);
			}
		}
		else{ cout << "File could not be opened." << endl; }
		my_file.close();	
	}
};