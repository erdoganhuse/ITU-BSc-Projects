#include<iostream>
#include<cstdio>
#include<string>
#include <stdlib.h>
#include <time.h>  
#include<vector>
#include <sstream>
#include<queue>

using namespace std;

template <typename T>
std::string to_string(T value){
		std::ostringstream os;
		os << value;
		return os.str();

}

class packet{
public:
	int packet_id;
	int hop_count;
	
	void set_packet_id(int t_int){
		packet_id = t_int;
	}
	void set_hop_count(int t_int){
		hop_count = t_int;
	}
	void decrease_hop_count(){
		hop_count--;
	}
};

class edge{
public:
	int id;
	int real_degree;
	int current_degree;
	bool is_visited;
	vector<packet> packets;
	vector<edge*> neighbor_vector;

	void set_is_visited(bool t_bool){
		is_visited = t_bool;
	}
	void set_real_degree(int t_int){
		real_degree = t_int;
	}
	void receive_packet(int t_packet_id,int t_hop_count){
		packet t_packet;
		t_packet.set_packet_id(t_packet_id);
		t_packet.set_hop_count(t_hop_count);
		packets.push_back(t_packet);
	}
};

class graph{
public:
	vector<edge> edges;
	int num_edges;

	int find_edge_index(int t_id){
		for(int i=0 ; i<edges.size() ; i++){
			if(edges[i].id == t_id){ return i; }
		}
		return -1;
	}
	bool control_all_degrees_zero(){
		for(int i=0 ; i<edges.size() ; i++){
			if(edges[i].current_degree != 0){ return false; }
		}
		return true;
	}
	bool is_edges_connected(int index1, int index2){
		for(int i=0 ; i<edges[index1].neighbor_vector.size() ; i++){
			if( edges[index1].neighbor_vector[i]->id == index2){ return true; }
		}
		return false;
	}
	void create_graph(){
		for(int i=0 ; i<num_edges ; i++){
			int t_degree = rand()%3 + 2;
			edge t_edge;
			t_edge.id = i;
			t_edge.real_degree = t_degree;
			t_edge.current_degree = t_degree;
			t_edge.is_visited = false;
			edges.push_back(t_edge);
		}
	}
	void insert_edge(int i, int value_of_i, int j, int value_of_j){
		edges[i].neighbor_vector.push_back( &edges[j] );
		edges[i].current_degree--;

		edges[j].neighbor_vector.push_back( &edges[i] );
		edges[j].current_degree--;
	}
	vector<int> create_random_vector(){
		vector<int> t_int_vector;
		for(int i=0 ; i<edges.size() ; i++){
			t_int_vector.push_back(edges[i].id);
		}
		return t_int_vector;
	}
	vector<int> delete_element_in_vector(int t,vector<int> t_vector){
		vector<int> t_int_vector;
		for(int i=0 ; i<t_vector.size() ; i++){
			if(t_vector[i] != t){ t_int_vector.push_back(t_vector[i]); }
		}
		return t_int_vector;
	}
	void random_assignment(){
		for(int i=0 ; i<edges.size() ; i++){
			
			vector<int> t_vector2;
			vector<int> t_vector = create_random_vector();
			//current_degree '0' olana kadar baðlantýlarý yap 
			while( t_vector.size() > 0 ){
				int t = rand()%t_vector.size() + 0;	
				int t_index = t_vector[t];
				t_vector2 = delete_element_in_vector(t_index,t_vector);
				t_vector = t_vector2;
				//eðer kendisini bulduysa yada bulduðunun degree'si '0' sa
				while( edges[t_index].current_degree == 0 || t_index == i || is_edges_connected(i,t_index) == true ){
					if(t_vector.size() != 0){
						t = rand()%t_vector.size() + 0;	
						t_index = t_vector[t];
						t_vector2 = delete_element_in_vector(t_index,t_vector);
						t_vector = t_vector2;
					}
					else{
						break;
					}
				}
				if( edges[i].current_degree > 0 && edges[t_index].current_degree > 0 && i != t_index){
					insert_edge(i,0,t_index,0);
				}
			}
		}	
		
		for(int i=0 ; i<edges.size() ; i++){	
			if( edges[i].current_degree != 0){ 
				edges[i].set_real_degree( edges[i].real_degree - edges[i].current_degree); 
			}
		}
	}
	bool is_in_vector(vector<int> t_vector,int t_int){
		for(int i=0 ; i<t_vector.size() ; i++){
			if(t_vector[i] == t_int){ return true; }
		}
		return false;
	}
	vector<int> bfs_search(int starting_edge){
		vector<int> t_vector1;
		queue<int> t_queue;

		t_queue.push( edges[starting_edge].id );
		t_vector1.push_back( edges[starting_edge].id );
		while( t_queue.empty() == false ){
			int t_enqueue = t_queue.front();
			t_queue.pop();
			if(edges[t_enqueue].is_visited == false){
				edges[t_enqueue].set_is_visited(true);
				for(int i=0 ; i<edges[t_enqueue].neighbor_vector.size() ; i++){
					if(is_in_vector(t_vector1,edges[t_enqueue].neighbor_vector[i]->id) == false ){
						t_queue.push( edges[t_enqueue].neighbor_vector[i]->id );
						t_vector1.push_back( edges[t_enqueue].neighbor_vector[i]->id );
					}
				}
			}
		}
		return t_vector1;
	}
	void print_graph(){
		for(int i=0 ; i<edges.size() ; i++){
			cout<<i<<" : ";
			for(int j=0 ; j<edges[i].neighbor_vector.size() ; j++){
				cout<<edges[i].neighbor_vector[j]->id<<",";
			}	
			cout<<endl;
		}
	}
	void set_all_visiteds(){
		for(int i=0 ; i<edges.size() ; i++){
			edges[i].set_is_visited(false);	
		}
	}
	void flooding(int starting_edge,int hop_count){
		set_all_visiteds();
		vector<int> t_vector1;
		queue<int> t_queue;

		t_queue.push( edges[starting_edge].id );
		t_vector1.push_back( edges[starting_edge].id );

		packet t_packet;
		t_packet.set_packet_id(starting_edge);
		t_packet.set_hop_count(hop_count);
		
		while( t_queue.empty() == false ){
			int t_enqueue = t_queue.front();
			t_queue.pop();
			if(edges[t_enqueue].is_visited == false){
				edges[t_enqueue].set_is_visited(true);
				t_packet.set_packet_id(t_enqueue);
				for(int i=0 ; i<edges[t_enqueue].neighbor_vector.size() ; i++){
					edges[t_enqueue].neighbor_vector[i]->packets.push_back(t_packet);;
					if(t_packet.hop_count == 0){ break; }
					if(is_in_vector(t_vector1,edges[t_enqueue].neighbor_vector[i]->id) == false ){
						t_queue.push( edges[t_enqueue].neighbor_vector[i]->id );
						t_vector1.push_back( edges[t_enqueue].neighbor_vector[i]->id );
					}
				}
				t_packet.decrease_hop_count();
			}
			if(t_packet.hop_count == 0){ break; }
		}
	}
	int total_received_packet(){
		int total_num = 0;
		for(int i=0 ; i<edges.size() ; i++){
			for(int j=0 ; j<edges[i].packets.size() ; j++){
				total_num++;
			}
		}
		return total_num;
	}
	bool is_in_packets(vector<packet> t_vector,int t_packet_id){
		for(int i=0 ; i<t_vector.size() ; i++){
			if(t_vector[i].packet_id == t_packet_id){ return true; }
		}
		return false;
	}
	int total_generated_packets(){
		vector<packet> t_vector;
		for(int i=0 ; i<edges.size() ; i++){
			for(int j=0 ; j<edges[i].packets.size() ; j++){
				if(is_in_packets(t_vector,edges[i].packets[j].packet_id) == false ){
					t_vector.push_back(edges[i].packets[j]);
				}
			}
		}
		return t_vector.size();
	}
};

int main(int argc, char* argv[]){
	srand (time(NULL));
	graph my_graph;
	int hop_count; 
	string argument1 = argv[1];
	string argument2 = argv[2];
	istringstream buffer1(argument1);
	buffer1 >> my_graph.num_edges;
	istringstream buffer2(argument2);
	buffer2 >> hop_count;
	int x, y;

	my_graph.create_graph();
	my_graph.random_assignment();
	my_graph.print_graph();

	vector<int> t_vector = my_graph.bfs_search(0);
	if(t_vector.size() == my_graph.edges.size()){ 
		cout<<"Graph is connected"<<endl;	
	}

	my_graph.flooding(0,hop_count);
	int t = my_graph.total_received_packet();
	cout<<"The total number of times that the nodes receive the same packet : "<<t<<endl;
	t = my_graph.total_generated_packets();
	cout<<"The total number of generated packets during the simulation : "<<t<<endl;
	
	system("PAUSE");
	return 0;
}

