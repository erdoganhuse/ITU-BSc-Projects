#include<iostream>
#include<cstdio>
#include<cmath>
#include<fstream>
#include<sstream>
#include<string>
#include<vector>
#include<stack>

using namespace std;

template <typename T>
std::string to_string(T value){
		std::ostringstream os;
		os << value;
		return os.str();
}

//for storing rules of grammar
class rule{
public:
	int rule_id; //for storing a single grammar rule's id 
	string state; //for storing a single grammar rule's state
	vector<string> rule_vector; //for storing a single grammar rule
	
	//for placing a dot to beginnig of rule_vector
	void place_dot(){
		vector<string> temp_rule_vector;
		temp_rule_vector.push_back(".");
		for(int i=0 ; i<rule_vector.size() ; i++){
			temp_rule_vector.push_back(rule_vector[i]);
		} 
		rule_vector = temp_rule_vector;
	}
	//for controlling is dot at the end of rule_vector
	bool is_dot_at_end(){
		if(rule_vector[rule_vector.size()-1] == "."){
			return true;
		}
		return false;
	}
	//for sliding dot
	rule slide_dot(){
		rule t_rule;
		t_rule.rule_id = this->rule_id;
		t_rule.state = this->state;

		int index = -1;
		for(int i=0 ; i<rule_vector.size()-1 ; i++){
			if( rule_vector[i] == "."){ index = i; } 
		}
		for(int i=0  ; i<rule_vector.size() ; i++){
			t_rule.rule_vector.push_back(this->rule_vector[i]);
		}
		if(index != -1){
			t_rule.rule_vector[index] = rule_vector[index+1];
			t_rule.rule_vector[index+1] = ".";
			return t_rule;
		}
	}
	//for getting character after dot in rule_vector
	string get_string_after_dot(){
		for(int i=0 ; i<rule_vector.size()-1 ; i++){
			if( rule_vector[i] == "."){ 
				return rule_vector[i+1]; 
			} 
		}		
	}
	//for printing a single rule
	void print_rule(){
		cout<<state<<"->";
		for(int i=0 ; i<rule_vector.size() ; i++){
			cout<<rule_vector[i];
		}
	}
}; 
//for storing grammar 
class grammar{
public:
	vector<rule> grammer_vector; //for storing grammar
	vector<string> nonterminal_list; //for storing nonterminals in grammar
	vector<string> terminal_list; //for storing terminals in grammar

	//for reading grammar from 'Grammar.txt' file
	void read_from_file(char *file_name){
		string line,temp;
		int position;
		fstream my_file;
		my_file.open(file_name);
		if(my_file.is_open()){
			while (getline(my_file, line)){
				rule t_rule;
				position = line.find(" ");
				temp = line.substr(0,position).c_str();
				
				istringstream buffer(temp);
				buffer >> t_rule.rule_id;					
				
				line = line.substr(position+1);
				position = line.find(" ");
				temp = line.substr(0,position).c_str();
				t_rule.state = temp;
				line = line.substr(position+1);
				position = line.find(" ");
				line = line.substr(position+1);
				while(position <= line.length()){
					position = line.find(" ");
					temp = line.substr(0,position).c_str();
					t_rule.rule_vector.push_back(temp);
					line = line.substr(position+1);
				}
				grammer_vector.push_back(t_rule);
			}
			my_file.close();
		}
		else{ cout << "File could not be opened." << endl; }
	}
	//for printing whole grammar
	void print_grammar(){
		cout<<"-----Grammar Rules-----"<<endl;
		for(int i=0 ; i<grammer_vector.size() ; i++){
			cout<<grammer_vector[i].rule_id<<" ";
			grammer_vector[i].print_rule();
			cout<<endl;
		}	
	}
	//for finding nonterminals and assigning them to nonterminal_list
	void create_nonterminal_list(){
		for(int i=0 ; i<grammer_vector.size() ; i++){
			int control = 0;
			for(int j=0 ; j<nonterminal_list.size() ; j++){
				if(nonterminal_list[j] != grammer_vector[i].state){ control++; }
			}
			if(control == nonterminal_list.size()){
				nonterminal_list.push_back(grammer_vector[i].state);
			}
		}
	}
	//for finding terminals and assigning them to terminal_list
	void create_terminal_list(){
		for(int i=0 ; i<grammer_vector.size() ; i++){
			int control = 0;
			for(int j=0 ; j<grammer_vector[i].rule_vector.size() ; j++){
				string t_string = grammer_vector[i].rule_vector[j];
				if(is_nonterminal(t_string) == false){ 
					int control=0;
					for(int k=0 ; k<terminal_list.size() ; k++){
						if(t_string !=terminal_list[k]){ control++; }
					}
					if(control == terminal_list.size() ){ terminal_list.push_back(t_string); }
				}
			}
		}
	}
	//for controling if t_string is nonterminal
	bool is_nonterminal(string t_string){
		for(int i=0 ; i<nonterminal_list.size() ; i++){
			if(nonterminal_list[i] == t_string){ return true; }	
		}
		return false;
	}
	//for finding rules that are starting with t_string
	vector<rule> find_rules(string t_string){
		vector<rule> t_rule_vector;
		for(int i=0 ; i<grammer_vector.size() ; i++){
			rule t_rule = grammer_vector[i];
			if( t_rule.state == t_string){
				t_rule.place_dot();
				t_rule_vector.push_back(t_rule);
			}
		}
		return t_rule_vector;
	}
	//for returning rule_id of gramar rule before dot
	int rule_id_before_dot(rule t_rule){
		for(int i=0 ; i<grammer_vector.size() ; i++){
			int control = 0; 
			if(grammer_vector[i].state == t_rule.state && (t_rule.rule_vector.size()-1)== grammer_vector[i].rule_vector.size()){
				for(int j=0;j<grammer_vector[i].rule_vector.size() ; j++){
					if(grammer_vector[i].rule_vector[j] == t_rule.rule_vector[j]){ control++; }
				}
			}
			if( control == grammer_vector[i].rule_vector.size()){ return grammer_vector[i].rule_id; }
		}
	}
};
//for stroring every cell in LR(0) table
class lr_table_node{
public:
	string node; // symbol for current cell
	int terminal_or_nonterminal; //terminal=1 , nonterminal=2 
	vector<string> t_box; //for storing content of cell
};
//for storing DFA states
class DFA_state{
public:
	int state_id; //for storing state id 
	bool is_explored; //for storing if state is explored 
	grammar dfa_grammer; //for assigning grammar and using it
	vector<rule> rule_list; //for storing rules in current state
	vector<int> transition_states; //for storing state_id's that current state is going
	vector<string> transition_strings; //for storing caharacters number that current state is going
	vector<lr_table_node> lr_table_vector; //for storing transitions in current state row 

	//for assigning false to is_explored 
	DFA_state(){
		is_explored = false;
	}
	//for creating lr_table_vector
	void create_lr_table_vector(){
		for(int i=0 ; i<dfa_grammer.terminal_list.size() ; i++){
			lr_table_node t;
			t.node = dfa_grammer.terminal_list[i];
			t.terminal_or_nonterminal = 1;
			lr_table_vector.push_back(t);
		}
		for(int i=0 ; i<dfa_grammer.nonterminal_list.size() ; i++){
			lr_table_node t;
			t.node = dfa_grammer.nonterminal_list[i];
			t.terminal_or_nonterminal = 2;
			lr_table_vector.push_back(t);
		}
	}
	//for setting a value to is_explored
	void set_is_explored(bool t_bool){
		is_explored = t_bool;
	}
	//for asigning values to transition_states vector
	//NOTE : '/' means current state is not going any state 
	void create_transition_states(){
		for(int i=0 ; i<rule_list.size() ; i++){
			if(rule_list[i].is_dot_at_end() == true){
				transition_strings.push_back("/");
			}
			else{
				string t_string = rule_list[i].get_string_after_dot();
				if( transition_strings.empty()==true ){ transition_strings.push_back(t_string); }
				else{	
					int control = 0;
					for(int j=0 ; j<transition_strings.size() ; j++){
						if( t_string != transition_strings[j] ){ control++; }
					}
					if( control == transition_strings.size() ){ transition_strings.push_back(t_string);  }
				}
			}
		}
	}
	//for controlling if t_rule1 and t_rule2 is same
	bool is_rules_same(rule t_rule1,rule t_rule2){
		int control=0;
		if(t_rule1.state == t_rule2.state){ control++; }
	
		if( t_rule1.rule_vector.size() == t_rule2.rule_vector.size() ){
			for(int j=0 ; j<t_rule1.rule_vector.size() ; j++){
				if( t_rule1.rule_vector[j] == t_rule2.rule_vector[j]){ control++; }
			}
		}
		if( control == 1+t_rule1.rule_vector.size() ){ return true; }
		else{ return false; }
	}
	//for controlling if t_rule is in current state's rule_list
	bool is_in_rule_list(rule t_rule){
		for(int i=0 ; i<rule_list.size() ; i++){
			if( is_rules_same(t_rule,rule_list[i])==true ){ return true; }
		}
		return false;
	}
	//for controlling if t_string is in t_vector
	bool is_in_vector(vector<string> t_vector,string t_string){
		bool control = false;
		for(int i=0 ; i<t_vector.size() ; i++ ){
			if( t_string == t_vector[i]){ 
				control = true; 
			}
		}
		return control;
	}
	//kýlýf function for current state
	void kilif(){
		vector<string> t_vector;
		for(int i=0 ; i<rule_list.size() ; i++){
			rule t_rule = rule_list[i];
			if(t_rule.is_dot_at_end() == false){
				string t_string = t_rule.get_string_after_dot();
				if( dfa_grammer.is_nonterminal(t_string) == true ){
					t_vector.push_back(t_string);
					while( t_vector.empty() == false ){
						string t_string2 = t_vector[t_vector.size()-1];
						t_vector.pop_back(); 
						vector<rule> t_rule_vector = dfa_grammer.find_rules(t_string2);
						for(int j=0 ; j<t_rule_vector.size() ; j++){
							if( is_in_rule_list(t_rule_vector[j]) == false ){
								rule_list.push_back(t_rule_vector[j]);
								string after_dot = t_rule_vector[j].get_string_after_dot();
								if( dfa_grammer.is_nonterminal(after_dot)==true && is_in_vector(t_vector,after_dot)==false ){
										t_vector.push_back(after_dot);
								}
							}
						}
					}
				}
			}
		}	
	}
	//gecis function 
	//NOTE : returns rule vector if t_string comes after dot in current state's rule_list
	vector<rule> gecis(string t_string){
		vector<rule> t_vector;
		for(int i=0 ; i<rule_list.size() ; i++ ){
			if(rule_list[i].is_dot_at_end() == false){
				if(t_string == rule_list[i].get_string_after_dot()){
					rule t_rule = rule_list[i];
					t_rule = t_rule.slide_dot();
					t_vector.push_back(t_rule);
				}
			}
		}
		return t_vector;
	}
};

class state_transition_table : public DFA_state{
public:
	DFA_state start_state; //for storing starting state
	vector<DFA_state> transition_table;//for storing all states
	vector<string> yigin_for_grammar_string;
	vector<string> turetim_for_grammar_string;

	//for taking starting grammar rule and adding it to transition_table
	state_transition_table(DFA_state t_state){
		start_state = t_state;
		start_state.rule_list[0].place_dot();
		start_state.kilif();
		transition_table.push_back(start_state);
	}
	//for controlling if t_state1 and t_state2 is same
	bool is_states_same(DFA_state t_state1,DFA_state t_state2){
		int control = 0; 
		if(t_state1.rule_list.size() == t_state2.rule_list.size() ){
			for(int i=0 ; i<t_state1.rule_list.size() ; i++){
				for(int j=0 ; j<t_state2.rule_list.size() ; j++ ){
					if( is_rules_same(t_state1.rule_list[i],t_state2.rule_list[j]) ==true ){
						control++;
					}
				}
			}	
		}
		if( control == t_state1.rule_list.size() ){ return true; }
		else{ return false; }
	}
	//for controlling if t_state is in our transition_table
	//NOTE1 : If t_state is not in transition_table, function returns -1
	//NOTE2 : If t_state is in transition_table, function returns state_id of t_state 
	int is_in_table(DFA_state t_state){
		for(int i=0 ; i<transition_table.size() ; i++){
			if( is_states_same(t_state,transition_table[i]) ){ 
				return transition_table[i].state_id;
				//return i; 
			}
		}
		return -1;
	}
	//for returning index of next state that is going to explored
	int next_explored_state_index(){
		for(int i=0 ; i<transition_table.size() ; i++){
			if(transition_table[i].is_explored == false){ return i; }
		}
	}
	//for returning last state of transition_table
	DFA_state last_state(){
		return transition_table[transition_table.size()-1];
	}
	//for creating transition_table
	void create_transition_table(){
		vector<DFA_state> t_vector;
		while(last_state().is_explored == false){
			int index = next_explored_state_index();
			transition_table[index].create_transition_states();
			for(int i=0 ; i<transition_table[index].transition_strings.size() ; i++){
				DFA_state t_state; 
				string t_string = transition_table[index].transition_strings[i];
				if(t_string != "/"){
					//if(dfa_grammer.is_nonterminal(t_string) == true){
					t_state.rule_list = transition_table[index].gecis(t_string);
					t_state.dfa_grammer = start_state.dfa_grammer;
					t_state.kilif();
					int control = is_in_table(t_state) ;
					if( control == -1 ){
						t_state.state_id = last_state().state_id + 1;
						transition_table[index].transition_states.push_back(t_state.state_id);
						transition_table.push_back(t_state);
					}
					else{
						transition_table[index].transition_states.push_back(transition_table[control].state_id);
					}
				}
				else{ transition_table[index].transition_states.push_back(-1); }
			}
			transition_table[index].set_is_explored(true);
		}
	}
	//for printing DFA states
	void print_states(){
		cout<<"-----DFA States-----"<<endl;
		for(int i=0 ; i<transition_table.size() ; i++){
			cout<<"I"<<transition_table[i].state_id<<":= {";
			for(int j=0 ; j<transition_table[i].rule_list.size() ; j++){
				transition_table[i].rule_list[j].print_rule();
				if(j != transition_table[i].rule_list.size()-1){ cout<<", "; }
			}
			cout<<"}"<<endl;
		}
	}
	//for printing DFA transitions
	void print_transitions(){
		cout<<"-----DFA Transitions-----"<<endl;
		for(int i=0 ; i<transition_table.size() ; i++){
			for(int j=0 ; j<transition_table[i].transition_strings.size() ; j++){
				if(transition_table[i].transition_strings[j] != "/"){
					cout<<"I"<<transition_table[i].state_id<<"->[";
					cout<<transition_table[i].transition_strings[j]<<"] ";
					cout<<"I"<<transition_table[i].transition_states[j]<<endl;
				}
			}
		}	
	
	}
	//for creating LR(0) table
	void create_lr_table(){
		for(int i=0 ; i<transition_table.size() ; i++){
			transition_table[i].create_lr_table_vector();
			for(int j=0 ; j<transition_table[i].transition_strings.size() ; j++){
				string t = transition_table[i].transition_strings[j];
				if(t == "/"){
					int rule_id = transition_table[i].dfa_grammer.rule_id_before_dot(transition_table[i].rule_list[j]);
					string t_string = to_string(rule_id);
					for(int k=0 ; k < transition_table[i].lr_table_vector.size() ; k++){
						if( transition_table[i].lr_table_vector[k].terminal_or_nonterminal == 1){
							string t_string2 ="R";
							t_string2.append(t_string);
							transition_table[i].lr_table_vector[k].t_box.push_back(t_string2);
						}	
					}					
				}
				else if( transition_table[i].dfa_grammer.is_nonterminal(t) == false ){
					int t_int = transition_table[i].transition_states[j];
					string t_string = to_string(t_int);
					for(int k=0 ; k < transition_table[i].lr_table_vector.size() ; k++){
						if( transition_table[i].transition_strings[j] == transition_table[i].lr_table_vector[k].node){
							string t_string2 ="S I";
							t_string2.append(t_string);
							transition_table[i].lr_table_vector[k].t_box.push_back(t_string2);
						}	
					}					
				}
				else{
					int t_int = transition_table[i].transition_states[j];
					string t_string = to_string(t_int);
					for(int k=0 ; k < transition_table[i].lr_table_vector.size() ; k++){
						if( transition_table[i].transition_strings[j] == transition_table[i].lr_table_vector[k].node){
							string t_string2 ="I";
							t_string2.append(t_string);
							transition_table[i].lr_table_vector[k].t_box.push_back(t_string2);
						}	
					}					
				}
			}
		}
	}
	//for printing LR(0) table
	void print_lr_table(){
		cout<<"-----LR(0) Table-----"<<endl;
		cout<<"Order of symbols in LR(0) table: "<<endl;
		cout<<"         /$/;/id/:=/+/S'/S/A/E/"<<endl;
		for(int i=0 ; i<transition_table.size() ; i++){
			cout <<"State "<<i<<" : /";
			for(int j=0 ; j<transition_table[i].lr_table_vector.size() ; j++){
				if( transition_table[i].lr_table_vector[j].t_box.empty() ){
					cout<<"-"; 
				}
				else{
					for(int k=0 ; k<transition_table[i].lr_table_vector[j].t_box.size() ; k++){
						if(k == transition_table[i].lr_table_vector[j].t_box.size()-1 ){
							cout<<transition_table[i].lr_table_vector[j].t_box[k];
						}
						else{
							cout<<transition_table[i].lr_table_vector[j].t_box[k]<<" & ";
						}
					}	
				}
				cout<<"/";
			}
			cout<<endl;
		}
	}
	//ötele function for LR(0) table
	void otele(string t_state, string t_input){
		yigin_for_grammar_string.push_back(t_input);
		yigin_for_grammar_string.push_back(t_state);
	}
	//indirge function for LR(0) table
	void indirge(string t_string){
		int rule_id ;
		istringstream buffer(t_string);
		buffer >> rule_id;					
		
		rule t_rule = start_state.dfa_grammer.grammer_vector[rule_id - 1];
		string t_string1 = yigin_for_grammar_string[yigin_for_grammar_string.size() - 1];
		yigin_for_grammar_string.pop_back();
		while(t_rule.rule_vector[0] != t_string1){		
			t_string1 = yigin_for_grammar_string[yigin_for_grammar_string.size() - 1];
			yigin_for_grammar_string.pop_back();
		}
		int t1;
		istringstream buffer1(yigin_for_grammar_string[yigin_for_grammar_string.size()-1]);
		buffer1 >> t1;			
		
		int t2 = find_state_id(t_rule.state);
		string t_string3 = transition_table[t1].lr_table_vector[t2].t_box[0];
		if(t_string3[0] == 'S' ){ t_string3.erase(0,3); }
		else if(t_string3[0] == 'I'){ t_string3.erase(0,1); }
		yigin_for_grammar_string.push_back(t_rule.state);
		yigin_for_grammar_string.push_back(t_string3);
	}
	//for popping first element of vector<string>
	vector<string> pop_first_element(vector<string> t_vector){
		vector<string> t_vector2;
		for(int i=1 ; i<t_vector.size() ; i++){
			t_vector2.push_back(t_vector[i]);
		}
		return t_vector2;
	}
	//for printing vector<string>
	void print_string_vector(vector<string> t_vector){
		for(int i=0 ; i<t_vector.size() ; i++){
			cout<<t_vector[i]<<" ";
		}
	}
	//for finding state id in LR(0) table
	int find_state_id(string t_node){
		if(t_node == "$"){ return 0; }
		else if(t_node == ";"){ return 1; }
		else if(t_node == "id"){ return 2; }
		else if(t_node == ":="){ return 3; }
		else if(t_node == "+"){ return 4; }
		else if(t_node == "S'"){ return 5; }
		else if(t_node == "S"){ return 6; }
		else if(t_node == "A"){ return 7; }
		else if(t_node == "E"){ return 8; }
	}
	//for controlling string if it is suitable for our grammar
	bool control_grammar_string(vector<string> grammar_string){
		yigin_for_grammar_string.push_back("0");
		int counter = 0;
		while(grammar_string.empty() == false){
			string t_string1 =  grammar_string[0];
			int t_int1 = find_state_id(t_string1);

			string t_string2 = yigin_for_grammar_string[yigin_for_grammar_string.size() - 1];
			int t_int2 ;
			istringstream buffer(t_string2);
			buffer >> t_int2;		
			if( transition_table[t_int2].lr_table_vector[t_int1].t_box.empty() == false ){
				string t_string3 = transition_table[t_int2].lr_table_vector[t_int1].t_box[0];
				cout<<"-------------"<<counter<<"-------------"<<endl;
				cout<<"grammar string => ";
				print_string_vector(grammar_string);
				cout<<endl;
				cout<<"yigin for grammar string => ";
				print_string_vector(yigin_for_grammar_string);
				cout<<endl;
				//char c = *t_string3.rend();
				if(t_string3[0] == 'S'){
					t_string3.erase(0,3);
					cout<<"action => otele "<<t_string3<<endl;
					otele(t_string3,t_string1);
					grammar_string = pop_first_element(grammar_string);
				}
				else if(t_string3[0] == 'R'){
					t_string3.erase(0,1);
					cout<<"action => indirge ";
					int t_int3 ;
					istringstream buffer1(t_string3);
					buffer1 >> t_int3;					
					start_state.dfa_grammer.grammer_vector[t_int3 - 1].print_rule();
					cout<<endl;
					indirge(t_string3);
				}
				else if(t_string3[0] == 'I'){
					t_string3.erase(0,1);
					cout<<"action => goto "<<t_string3<<endl;
					indirge(t_string3);
					otele(t_string3,t_string1);
					grammar_string = pop_first_element(grammar_string);
				}
			}
			else if( grammar_string.size() != 0 ){
				cout<<endl<<"*** ERROR String is NOT suitable for grammar ***"<<endl;
				return false;
			}
			counter++;
		}
		cout<<endl<<"*** String is suitable for grammar ***"<<endl;
		return true;
	}
};
