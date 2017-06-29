#include <iostream>
#include <ctime>
#include "Graph.h"

using namespace std;

int main() {

	clock_t t1;
	t1 = clock();

	Graph my_graph(25);
	my_graph.readfromFile("Edges.txt");
	my_graph.DFS(2,11);	
	my_graph.BFS(2,11);
	
	float diff = (double)(clock() - t1)/CLOCKS_PER_SEC ;
	cout <<endl<< "The time taken for Breadth first search: "<< diff << endl;
	system("PAUSE");
	return 0;
}
