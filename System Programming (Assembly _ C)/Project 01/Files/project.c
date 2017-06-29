#include <stdio.h>
#include <stdbool.h>
#include <stdlib.h>

void display();
void print_matrix(int *my_matrix,int size);
void add(int *matrix1,int *matrix2, int *result , int size);
int sum(int *matrix, int size);
void mult(int *matrix1,int *matrix2, int *result, int size);
void scale(int *matrix1, int number , int *result ,int size);
void square(int *matrix1, int *result ,int size);
void ITU(int *matrix , int size);

int main(int argc, char **argv){
	bool kontrol;
	int secim,size,k,i,scale_number,ITU_size;
	int *matrix1,*matrix2,*result1,*result2,*result3,*result4,*result5,*result6;
	int *ITU_matrix;
	FILE *dosyaoku;
	
	i=0;
	kontrol=true;
	
    //Reading matrix_1.txt
    dosyaoku=fopen("matrix_1.txt","r");
    if(dosyaoku==NULL){
		printf("hata");
	} 
	else{
		fscanf(dosyaoku,"%d",&size);
		matrix1=(int*)malloc((size*size)*sizeof(int));
		do{
			k=fscanf(dosyaoku,"%d",&matrix1[i]);
			i++;
		}while(k==1);
	}
	printf("Matrix 1:\n"); 
	print_matrix(matrix1,size);
	//Reading matrix_1.txt ends 
		
    //Reading matrix_2.txt
	i=0;
	dosyaoku=fopen("matrix_2.txt","r");
    if(dosyaoku==NULL){
		printf("hata");
	} 
	else{
		fscanf(dosyaoku,"%d",&size);
		matrix2=(int*)malloc((size*size)*sizeof(int));
		do{
			k=fscanf(dosyaoku,"%d",&matrix2[i]);
			i++;
		}while(k==1);
	}
	printf("Matrix 2:\n"); 
	print_matrix(matrix2,size);  	
	//Reading matrix_1.txt ends
	
	int my_result;
	while(kontrol){
		display();
		scanf("%d",&secim);
		switch(secim){
			case 1:
				result1=(int*)malloc((size*size)*sizeof(int));
				add( matrix1,matrix2,result1,size);
				printf("Result:\n");
				print_matrix(result1,size);
				free(result1);
			break;
			case 2:
				my_result = sum(matrix1,size);
				printf("Result for Matrix 1: %d \n",my_result);
				my_result = sum(matrix2,size);
				printf("Result for Matrix 2: %d \n",my_result);				
			break;
			case 3:
				result4=(int*)malloc((size*size)*sizeof(int));
				mult(matrix1,matrix2,result4,size);
				printf("Result:\n");
				print_matrix(result4,size);		
				free(result4);		
			break;
			case 4:
				result2=(int*)malloc((size*size)*sizeof(int));
				printf("Enter scale for matrix 1:");
				scanf("%d",&scale_number);
				scale(matrix1,scale_number,result2,size);
				printf("Result:\n");
				print_matrix(result2,size);
				free(result2);
				
				result3=(int*)malloc((size*size)*sizeof(int));
				printf("Enter scale for matrix 2:");
				scanf("%d",&scale_number);
				scale(matrix2,scale_number,result3,size);
				printf("Result:\n");				
				print_matrix(result3,size);
				free(result3);
			break;
			case 5:
				result5=(int*)malloc((size*size)*sizeof(int));
				printf("Square of Matrix1:\n");
				square(matrix1,result5,size);
				print_matrix(result5,size);
				free(result5);
				
				result6=(int*)malloc((size*size)*sizeof(int));
				printf("Square of Matrix2:\n");
				square(matrix2,result6,size);
				print_matrix(result6,size);
				free(result6);				
			break;
			case 6:
				printf("Enter size for ITU matrix :");
				scanf("%d",&ITU_size);
				ITU_matrix=(int*)malloc((size*size)*sizeof(int));
				ITU(ITU_matrix,ITU_size);
				print_matrix(ITU_matrix,ITU_size);	
				free(ITU_matrix);			
			break;
			case -1:
				kontrol = false;
			break;
		}
	}
	
	return 0;
}

void display(){
	    printf("------Functions------\n");
	    printf("1 for add\n");
	    printf("2 for sum\n");
	    printf("3 for mult\n");
	    printf("4 for scale\n");
	    printf("5 for square\n");
	    printf("6 for ITU\n");
	    printf("-1 for exit\n");
	    printf("Select a Function :");
}

void print_matrix(int *my_matrix,int size){
	int i;
	for(i=0 ; i < size*size ; i++){
		printf("%d ",my_matrix[i]);
		if( (i+1)%size  == 0){
			printf("\n");
		}
	}
}
