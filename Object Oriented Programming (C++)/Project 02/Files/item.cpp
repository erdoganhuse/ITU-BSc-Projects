#include<iostream>
#include"item.h"

using namespace std;

/*---------------------------Item class----------------------------------------------*/
void Item::print() const{
	cout<<" - "<<priceofitem<<" TL";
}
/*---------------------------Flower class(Derived class of Item)----------------------------------------------*/
Flower::Flower(bool is_art):is_artificial(is_art){}
void Flower::calculate_price(){
	if(is_artificial==1){
		priceofitem=priceofitem*1.5;
	}
}
void Flower::print() const{
	if(is_artificial==1){
		cout<<" <artificial>";
	}
	Item::print();
	cout<<endl;
}
/*---------------------------Rose class(Derived class of Flower)----------------------------------------------*/
Rose::Rose(bool is_art,int t_number):number(t_number),Flower(is_art){
	if(number<10){
		priceofitem=10;
	}
	else{
		priceofitem=8;
	}
}
void Rose::calculate_price(){
	Flower::calculate_price();
	priceofitem = priceofitem * number;
}
void Rose::print() const{
	cout<<number<<" roses";
	Flower::print();
}
/*--------------------------Daisy class(Derived class of Flower)-----------------------------------------------*/
Daisy::Daisy(bool is_art):Flower(is_art){
	priceofitem=20;
}
void Daisy::calculate_price(){
	Flower::calculate_price();
}
void Daisy::print() const{
	cout<<"A bunch of daisies";
	Flower::print();
}
/*-------------------------Gourmet class(Derived class of Item)------------------------------------------------*/
Gourmet::Gourmet(int t_size):basketSize(t_size){}
void Gourmet::print() const{
	if(basketSize==0){
		cout<<"<small>";
	}
	else if(basketSize==1){
		cout<<"<medium>";
	}
	else if(basketSize==2){
		cout<<"<large>";
	}
	Item::print();
	if(promotion==1){
		cout<<endl<<"Promotion: a bunch of daisies for free";
	}
	cout<<endl;
}
/*-------------------------FruitBasket class(Derived class of Gourmet)------------------------------------------------*/
FruitBasket::FruitBasket(int t_size,int t_ftype,bool t_sauce):fruitType(t_ftype),with_chocolate_sauce(t_sauce),Gourmet(t_size){}
void FruitBasket::calculate_price(){
	priceofitem = 25*(basketSize+1);
	if( fruitType == 1 ){ 
		priceofitem = priceofitem*8/5; 
	}		
	else if( fruitType == 2 ){ 
		priceofitem = priceofitem*2; 
	}
	if( with_chocolate_sauce ){ 
		priceofitem=priceofitem+20; 
	}
	if(priceofitem >= 80){ 
		promotion = 1; 
	}
}
void FruitBasket::print() const{
	if(fruitType==0){ 
		cout<<"Standart fruit basket "; 
	}
	else if(fruitType==1){ 
		cout<<"Citrus fruit basket "; 
	}
	else if(fruitType==2){ 
		cout<<"Tropical fruit basket "; 
	}
	if(with_chocolate_sauce==1){
		cout<<"with chocalate sauce ";
	}
	Gourmet::print();
}
/*-------------------------CookieBasket(Derived class of Gourmet)------------------------------------------------*/
CookieBasket::CookieBasket(int t_size):Gourmet(t_size){
	if(basketSize == 2 ){
		promotion=1;
	}
}
void CookieBasket::calculate_price(){
	priceofitem = 30 + basketSize*20 ;
}
void CookieBasket::print() const{
	cout<<"A basket of cookies ";
	Gourmet::print();
}
