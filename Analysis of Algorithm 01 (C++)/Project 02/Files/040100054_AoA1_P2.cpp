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
#define negative_infinite -9999999999

using namespace std; 

class priority_queue{
public:
	int *pq_array;
	int heap_size;		
public:
	priority_queue(int size_of_array){
		pq_array=new int[size_of_array];
		heap_size=0;
	}
	int parent(int i){
		return i/2 - 0.5;
	};
	int left_child(int i){
		return 2*(i+0.5);
	};
	int right_child(int i){
		return 2*(i+0.5)+1;
	}
	int length(){
		return heap_size;
	}
	void max_heapify(int i){
		int l = left_child(i);
		int r = right_child(i);
		int largest = i;	
		if (l <= heap_size && pq_array[l] > pq_array[i] ){
			largest = l;
		}
		else{
			largest = i;
		}
		if (r <= heap_size && pq_array[r] > pq_array[largest] ){
			largest = r;
		}
		if(largest != i){
			int temp = pq_array[i];
			pq_array[i]=pq_array[largest];
			pq_array[largest]=temp;
			max_heapify(largest);
		}
	}
	void build_max_heap(){
		for (int i=length()/2;i >= 0;i--){
			max_heapify(i);
		}
	}  
	void heapsort(){
		build_max_heap();
		for(int i=length();i>2;i--){
			int temp=pq_array[0];
			pq_array[0]=pq_array[i];
			pq_array[i]=temp;
			heap_size=heap_size-1;
			max_heapify(1);
		}
	}
	int heap_maximum(){
		return pq_array[0];
	}
	int heap_extrac_max(){
		if(heap_size<1){
			cout<<"error"<<endl;
		}
		int max=pq_array[0];
		pq_array[0]=pq_array[heap_size-1];
		heap_size=heap_size-1;
		max_heapify(0);
		return max;
	}
	void heap_increase_key(int i,int key){
		if(key<pq_array[i]){
			cout<<"error new key is smaller than current key"<<endl;
		}
		pq_array[i]=key;
		while(i>0 && pq_array[parent(i)]<pq_array[i]){
			int temp=pq_array[parent(i)];
			pq_array[parent(i)]=pq_array[i];
			pq_array[i]=temp;
			i=parent(i);
		}
	}
	void max_heap_insert(int key){
		heap_size=heap_size+1;
		pq_array[heap_size-1]=negative_infinite;
		heap_increase_key(heap_size-1,key);
	}
};

int main(int argc, char* argv[]){
	
	int m = atoi(argv[1]);
	double p = atoi(argv[2]);

	srand (time(NULL));
	
	priority_queue pq(m);
	ifstream myfile;
	myfile.open("bids.txt");
	ofstream outputfile("output.txt");
	if(myfile.is_open())
	{
		clock_t start,end;
		start = clock();
		
		int new_bid=0;
		int update_bid=0;
		for(int i=0;i<m;i++){
			if( i%100 == 0 && i != 0){ //controlling if there is 100 operation for removing max bid
				int max_bid=pq.heap_extrac_max();
				outputfile<<"Highest bid after "<<i<<" operation is "<<max_bid<<endl; 
			}

			int random = rand()%100 + 1;
			if(random < p*100 && pq.heap_size != 0){ //updating a random bid with increment of 25%
				int location = rand()%(pq.heap_size); // location to update in the range 0 to (heap_size-1)
				int t_key=pq.pq_array[location]*(1.25); // increment of 25% equal to %125 of number
				pq.heap_increase_key(location,t_key);
				update_bid++;
			}
			else{  // adding new bid
				int bid;
				myfile>>bid;
				pq.max_heap_insert(bid);	
				new_bid++;
			}
			
		}
		end=clock();
		double sec=((double)(end-start))/CLOCKS_PER_SEC;
		cout<<"Number of new bids : "<<new_bid<<endl<<"Number of updates : "<<update_bid<<endl; 
		cout<<"Time : "<< fixed << setprecision(10)<<sec<<" Clicks : "<<end-start<<endl;
		
		outputfile<<"Number of new bids : "<<new_bid<<endl<<"Number of updates : "<<update_bid<<endl; 
		outputfile<<"Time : "<< fixed << setprecision(10)<<sec<<" Clicks : "<<end-start<<endl;
		outputfile.close();
		myfile.close();
	}
	else{ 
		cout << "File could not be opened." << endl; 
	}
	system("PAUSE");
    return 0;
}
