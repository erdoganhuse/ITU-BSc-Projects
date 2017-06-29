/*
* Student Name: Hüseyin Erdoðan
* Student ID : 040100054
* Date:19.05.2013
*/

#include <iostream>
#include<string>
#include<vector>
using namespace std;

template <class Type>
class ArithmeticVector:public vector{
private:
	Type *Array;
	int size;
public:
	ArithmeticVector(int); //constructur with parameter 	
	ArithmeticVector(const ArithmeticVector&);//copy constructor
	~ArithmeticVector(); // destructor
	const void print(); 
	bool contains(Type ); 
	bool operator == (const ArithmeticVector &) const; 
	void operator ++(); 
	void operator --(); 
	Type & operator[](int ); 
	ArithmeticVector operator / (const ArithmeticVector &) const;  
	ArithmeticVector operator + (const ArithmeticVector &) const;  
	void operator = (const ArithmeticVector &) const; 	
};

/*---------------------------------constructur with parameter ----------------------------------------------------*/
template<class Type>
ArithmeticVector<Type>::ArithmeticVector(int t_size):size(t_size){
	Array=new Type[size];
	for(int i=0;i<size;i++){
		Array[i]=1 + rand()%9;
	}
};
/*---------------------------------copy constructor----------------------------------------------------*/
template<class Type>
ArithmeticVector<Type>::ArithmeticVector(const ArithmeticVector &t_obj){
	size=t_obj.size;
	Array=new Type[size];
	for(int i=0;i<size;i++){ 
		Array[i]=t_obj.Array[i]; 
	}
};
/*--------------------------------- destructor----------------------------------------------------*/
template<class Type>
ArithmeticVector<Type>::~ArithmeticVector(){
	delete[] Array;
};
/*----------------------------------print function---------------------------------------------------*/
template<class Type>
const void ArithmeticVector<Type>::print(){
	for(int i=0;i<size;i++){
		cout<<Array[i]<<" ";
	}
	cout<<endl;
};
/*-----------------------------------contains function---------------------------------------------------*/
template<class Type>
bool ArithmeticVector<Type>::contains(Type num){
	for(int i=0;i<size;i++){
		if(num==Array[i])
			return true;
	}
	return false;
};
/*-----------------------------------== operator--------------------------------------------------*/
template<class Type>
bool ArithmeticVector<Type>::operator == (const ArithmeticVector &t_obj)const{
	if( size != t_obj.size  ){ return false; }
	for(int i=0;i<size;i++){
		if(Array[i] != t_obj.Array[i]){
				return false;
		}
	}	
	return true;
};
/*-----------------------------------++ operator-------------------------------------------------*/
template<class Type>
void ArithmeticVector<Type>::operator++(){
	for(int i=0;i<size;i++){
		Array[i]=Array[i] + 1;		
	}
};
/*---------------------------------- -- operator------------------------------------------------*/
template<class Type>
void ArithmeticVector<Type>::operator--(){
	for(int i=0;i<size;i++){
		Array[i]=Array[i] - 1;		
	}
};
/*-----------------------------------[] operator------------------------------------------------*/
template<class Type>
Type & ArithmeticVector<Type>::operator[](int location){
	if(size < location || location < 0 ){
		throw string("Index out of bounds") ;		
	}
	else
		return Array[location];
};
/*------------------------------------/ operator-------------------------------------------------*/
template<class Type>
ArithmeticVector<Type> ArithmeticVector<Type>::operator/(const ArithmeticVector &t_obj)const {
	ArithmeticVector temp(size);
	if( size == t_obj.size ){
		for(int i=0;i<size;i++){
			temp.Array[i]=Array[i]/t_obj.Array[i];		
		}
		return temp;
	}
	else throw string("Arrays does not have same sizes to divide them!");
};
/*-----------------------------------+ operator--------------------------------------------------*/
template<class Type>
ArithmeticVector<Type> ArithmeticVector<Type>::operator+(const ArithmeticVector &t_obj)const {
	ArithmeticVector temp(size);
	if( size == t_obj.size ){
		for(int i=0;i<size;i++){
			temp.Array[i]=Array[i]+t_obj.Array[i];		
		}
		return temp;
	}
	else  throw string("Arrays does not have same sizes to sum them!");
};
/*----------------------------------= operator--------------------------------------------------*/
template<class Type>
void ArithmeticVector<Type>::operator=(const ArithmeticVector &t_obj)const {
	if( size==t_obj.size){
		for(int i=0;i<size;i++){
			Array[i]=t_obj.Array[i];		
		}
	}
	else{ 
		throw string("Arrays does not have same size");
	}
};
