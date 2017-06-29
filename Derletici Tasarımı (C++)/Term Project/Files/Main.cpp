#include<iostream>
#include<cstdio>
#include<cstdlib>
#include<cmath>
#include<fstream>
#include<string>
#include<vector>
#include"Header.h"

using namespace std;


int main(){
	
	grammar my_grammar;
	my_grammar.read_from_file("Grammar.txt");
	my_grammar.print_grammar();
	my_grammar.create_nonterminal_list();
	my_grammar.create_terminal_list();

	DFA_state dfa_start;
	dfa_start.dfa_grammer = my_grammar;
	dfa_start.state_id = 0;
	dfa_start.rule_list.push_back(my_grammar.grammer_vector[0]);
	state_transition_table my_table(dfa_start);
	my_table.create_transition_table();
	my_table.create_lr_table(); 
	char choice = '0';
	while(choice != '4'){
		cout<<endl<<"-----PART 1-----"<<endl;
		cout<<"Enter '1' to see DFA States "<<endl;
		cout<<"Enter '2' to see DFA Transitions"<<endl;
		cout<<"Enter '3' to see LR(0) Table "<<endl;
		cout<<"Enter '4' to pass PART 2 "<<endl;
		cout<<"Choice : ";
		cin>>choice;
		if(choice == '1'){ my_table.print_states(); }

		else if(choice == '2'){ my_table.print_transitions(); }
		else if(choice == '3'){ 
			my_table.print_lr_table();
		}
	}

	string t_string = "";
	cout<<endl<<"-----PART 2-----"<<endl;
	vector<string> grammar_string;
	while(t_string != "-1"){
		cout<<"Enter string one by one (-1 for exit) : ";
		cin>>t_string;
		if(t_string != "-1"){
			grammar_string.push_back(t_string);
		}
		else{
			grammar_string.push_back("$");
		}
	}
	bool t_bool = my_table.control_grammar_string(grammar_string);
	return 0;
}
