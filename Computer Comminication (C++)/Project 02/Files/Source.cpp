#include <iostream>
#include <cstdio>
#include <cmath>
#include <cstdlib>
#include <ctime>
#include <vector>
#include <string>


using namespace std;

class my_frame{
public:
	int frame_id;
	bool has_transmission_error;
	bool has_ack_error;

	my_frame(){
		has_transmission_error = false;
		has_ack_error = false;
	}
	void set_transmission_error(bool t){
		has_transmission_error=t;
	}
	void set_ack_error(bool t){
		has_ack_error=t;
	}
};

class my_event{
public:
	char station; //'A' or 'B'
	my_frame t_frame;
	int event_time;
	int is_transmitted;
	int is_received;

	my_event(){
		is_transmitted = 0;
		is_received = 0;
	}
	void set_is_transmitted(int t){
		is_transmitted = t;
	}
	void set_is_received(int t){
		is_received = t;
	}
	void set_event_time(int t){
		event_time = t;
	}
	void set_station(char t){
		station = t;
	}
};

class stop_and_wait{
public:
	vector<my_frame> frame_array;
	vector<my_event> my_event_list; 
	int starting_time;
	int ending_time;
	int number_of_frame;

	stop_and_wait(int t_size,int t_starting_time){
		number_of_frame = t_size;
		starting_time=t_starting_time;
	
		for(int i=0;i<number_of_frame;i++){
			my_frame t_frame;
			t_frame.frame_id = i;
			frame_array.push_back(t_frame);
		}
	}
	my_event last_event(){
		return my_event_list[my_event_list.size() - 1];
	}
	void station_A(){
		my_event t_event;
		t_event.set_station('A');

		//ilk elemaný gönder
		if(my_event_list.empty() == true){
			t_event.set_event_time(starting_time);
			t_event.t_frame = frame_array[0];
			if(t_event.t_frame.has_transmission_error == 0){
				t_event.set_is_transmitted(1);
			}
			else{
				t_event.set_is_transmitted(0);
			}
		}
		//ilk eleman degilse
		else{
			//sonrakini gönder
			if( last_event().is_received == 1){
				t_event.t_frame = frame_array[ last_event().t_frame.frame_id + 1 ];
				t_event.set_event_time( last_event().event_time + 270 );
				if(t_event.t_frame.has_transmission_error == 0){
					t_event.set_is_transmitted(1);
				}
				else{
					t_event.set_is_transmitted(0);
				}
			}
			//tekrar gönder
			else{
				t_event.t_frame = frame_array[ last_event().t_frame.frame_id];
				t_event.set_event_time( last_event().event_time + 280 );		
				t_event.set_is_transmitted(1);			
			}
		}
		my_event_list.push_back(t_event);
	}
	void station_B(){
		my_event t_event;
		t_event.set_station('B');
		t_event.t_frame = last_event().t_frame;
		t_event.set_event_time( last_event().event_time + 270 );
		if( last_event().is_transmitted == 1  &&
			t_event.t_frame.has_ack_error == 0)
		{
			t_event.set_is_received(1);
		}
		else{
			t_event.set_is_received(0);
		}	
		my_event_list.push_back(t_event);
	}
	bool is_finished(){
		if( last_event().t_frame.frame_id == number_of_frame - 1 &&
			last_event().is_received == 1)
		{
			ending_time = last_event().event_time;
			return true;
		}
		else{
			return false;
		}
	}
	void print_event_list(){
		cout<<endl<<"-----Event list for Stop-And-Wait Protocol-----"<<endl;
		for(int i=0;i<my_event_list.size();i++){
			if(my_event_list[i].station =='A'){
				cout<<"Transmitted Frame : "<<my_event_list[i].t_frame.frame_id +1;
				cout<<" Time : "<<my_event_list[i].event_time<<endl;
			}
		}
		cout<<"Transmission End Time : "<<ending_time<<endl;
	}
	void rand_function(	int error_rate){
		int i=0;
		while(i < error_rate*(frame_array.size()/100)){
			int t=rand()%frame_array.size();	
			if(frame_array[t].has_transmission_error == false){
				frame_array[t].set_transmission_error(true);
				i++;
			}		
		}
	}
};

int main(){


	cout<<endl<<"----------------------------------"<<endl;
	cout<<"-----SLIDING WINDOW PROTOCOLS-----"<<endl;
	cout<<"----------------------------------"<<endl<<endl;
	int option=0;
	while(option != -1){
		cout<<endl<<"Option 1 : Test program with three given series"<<endl;
		cout<<"Option 2 : 1000 frames sent by station A"<<endl;
		cout<<"Exit : -1"<<endl<<endl;
		cout<<"Select an option : ";
		cin>>option;
		
		if(option == 1){
			/*-----Test 1-----*/
			stop_and_wait my_sat_protocol_test1(10,0);
			my_sat_protocol_test1.frame_array[2].has_transmission_error = true;
			if(option == 1){
				do{
					my_sat_protocol_test1.station_A();
					my_sat_protocol_test1.station_B();	
				}while(my_sat_protocol_test1.is_finished() == false);
			}
			my_sat_protocol_test1.print_event_list();
			/*-----Test 2-----*/

			/*-----Test 3-----*/
	
		}
		else if(option == 2){
			cout<<"Enter error rate : ";
			int error_rate; 
			cin>>error_rate;
			/*-----Test 1-----*/
			stop_and_wait my_sat_protocol_test1000(1000,0);
			my_sat_protocol_test1000.rand_function(error_rate);
			do{
				my_sat_protocol_test1000.station_A();	
				my_sat_protocol_test1000.station_B();
			}while(my_sat_protocol_test1000.is_finished() == false);
			my_sat_protocol_test1000.print_event_list();
			/*-----Test 2-----*/

			/*-----Test 3-----*/

		}
	}
	return 0;
}

