/************************************************************
*	Ad Soyad			: Huseyin Erdogan					*
*   Numara				: 040100054							*
*	Dersin Adi			: Isletim Sistemleri				*
*   Dersin Kodu         : BLG312                            *
*   Odev No             : 02                                *
*************************************************************/

#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/shm.h>
#include <sys/wait.h>
#include <sys/sem.h>
#include <unistd.h>
#include <signal.h>

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <time.h>

#define GNU SOURCE
#define KEYSHM1 1000541
#define KEYSHM2 1000542
#define KEYSHM3 1000543
#define KEYSHM4 1000544
#define KEYSHM5 1000545

#define SEMKEY1 1000546
#define SEMKEY2 1000547
#define SEMKEY3 1000548

void sem_signal(int semid, int val){
	struct sembuf semafor;
	semafor.sem_num = 0;
	semafor.sem_op = val;
	semafor.sem_flg = 1;
	semop(semid, &semafor, 1);
	}

void sem_wait(int semid, int val){
	struct sembuf semafor;
	semafor.sem_num = 0;
	semafor.sem_op = (-1*val);
	semafor.sem_flg = 1;
	semop(semid, &semafor, 1);
	}

void mysignal(void){}

void mysigset(int num){
	struct sigaction mysigaction;
	mysigaction.sa_handler = (void*) mysignal;
	mysigaction.sa_flags = 0;
	sigaction(num, &mysigaction, NULL);
}

int main(int argc, char *argval[],char *env[] ){

    int f=0;
    int n,M,x=0,y=0;
    int indis;
    int shmid1, shmid2, shmid3, shmid4, shmid5;
    int *globaln, *globalM, *globalx, *globaly;
    int *globaldiziA, *globaldiziB, *globaldiziC;

    n = atoi(argval[1]);
    M = atoi(argval[2]);

    mysigset(12);

	srand (time(NULL));		//rastgele sayi uretmek icin

	//bellek alanlarý alýnýyor
	shmid1 = shmget(KEYSHM1, sizeof(int),0700|IPC_CREAT);
	shmid2 = shmget(KEYSHM2, sizeof(int),0700|IPC_CREAT);	
	shmid3 = shmget(KEYSHM3, sizeof(int),0700|IPC_CREAT);	
	shmid4 = shmget(KEYSHM4, sizeof(int),0700|IPC_CREAT);	
	shmid5 = shmget(KEYSHM5, sizeof(int)*(n),0700|IPC_CREAT);	

	//bellek alanlarýna eriþmek için 
	globaln = (int *)shmat(shmid1, 0, 0);			
   	globalM = (int *)shmat(shmid2, 0, 0);			
	globalx = (int *)shmat(shmid3, 0, 0);			
	globaly = (int *)shmat(shmid4, 0, 0);				
	globaldiziA = (int *)shmat(shmid5, 0, 0);			

	//gerekli bilgiler bellek alanlarýna yazýlýyor
	*globaln = n;	
	*globalM = M;	
    *globalx = x;
    *globaly = y;

	//rastgele sayýlar üretilip A aray ine atanýyor ve bellek alanýna yazýlýyor
    int i;
	for(i = 0; i< *globaln ; i++){
		globaldiziA[i] = rand()%100;	
		printf("globaldiziA[%d]: %d\n",i,globaldiziA[i]);
	}

	//burda tanýmladýgým semaforlarýn hepsi senkronizasyon semaforlarýdýr
    int semafor1 = 0;
    semafor1 = semget(SEMKEY1,1,0700|IPC_CREAT);	
    semctl(semafor1,0,SETVAL,0);					

    int semafor2 = 0;
    semafor2 = semget(SEMKEY2,1,0700|IPC_CREAT);	
    semctl(semafor2,0,SETVAL,0);

    int semafor3 = 0;
    semafor3 = semget(SEMKEY3,1,0700|IPC_CREAT);	
    semctl(semafor3,0,SETVAL,0);

	//anne prosesden iki cocuk proses oluþturuluyor ve indislerini kullanarak cocuk prosesleri tanýyabiliyorum
	for(i=1; i <= 2 ; i++){	
		f = fork();	
		indis = i;		
		if(f == -1){
			printf("Fork yaratirken hata olustu\n");
			exit(1);
		}
		if(f == 0){	
            printf("Cocuk process %d oluÅŸturuldu.\n",indis);
			break;
        }
	}


	if(f != 0){		//anne proses
		//cocuk proseslerin bitmesini bekliyor
        sem_wait(semafor2,1);
        sem_wait(semafor3,1);

        globaln = (int *)shmat(shmid1, 0, 0);		
        globalM = (int *)shmat(shmid2, 0, 0);				
        globalx = (int *)shmat(shmid3, 0, 0);				
        globaly = (int *)shmat(shmid4, 0, 0);				
        globaldiziA = (int *)shmat(shmid5, 0, 0);				

        printf("n: %d\n",*globaln);
        printf("M: %d\n",*globalM);
        printf("x: %d\n",*globalx);
        printf("y: %d\n",*globaly);

		//burada B ve C arraylerini yazdýrýyorum
        int shmid6 = shmget( (KEYSHM5 + *globaln), sizeof(int)*(*globalx),0700|IPC_CREAT);
        int j=0;
        for(j=0; j< *globalx ; j++){
            printf("globaldiziB[%d]: %d\n",j,globaldiziB[j]);
        }

        int shmid7 = shmget( (KEYSHM5 + *globaln + *globalx), sizeof(int)*(*globaly),0700|IPC_CREAT);
        globaldiziC = (int *)shmat(shmid7, 0, 0);
        for(j=0; j< *globaly ; j++){
            printf("globaldiziC[%d]: %d\n",j,globaldiziC[j]);
        }

		//kaynaklari iade ediyorum
		shmdt(globaln);
		shmdt(globalM);
		shmdt(globalx);
		shmdt(globaly);
        shmdt(globaldiziA);
        shmdt(globaldiziB);
        shmdt(globaldiziC);

		shmctl(shmid1, IPC_RMID, 0);
		shmctl(shmid2, IPC_RMID, 0);
		shmctl(shmid3, IPC_RMID, 0);
		shmctl(shmid4, IPC_RMID, 0);
        shmctl(shmid5, IPC_RMID, 0);
		shmctl(shmid6, IPC_RMID, 0);
		shmctl(shmid7, IPC_RMID, 0);
		exit(0);	//anne proses bitiyor..

	}
    else{ 
        if(indis == 1){  //cocuk proses 1
            globaln = (int *)shmat(shmid1, 0, 0);
            globalM = (int *)shmat(shmid2, 0, 0);
            globalx = (int *)shmat(shmid3, 0, 0);
            globaldiziA = (int*) shmat(shmid5,0,0);

			//B array inin boyutunu buluyorum ve sonucu x için ayýrdýgým yerde tutuyorum
            for(i=0; i<*globaln; i++){
                if( globaldiziA[i] <= *globalM ){
                    *globalx = *globalx + 1;
                }
            }
			
			// B array i için bellek ayýrýyorum
            int shmid6 = shmget( (KEYSHM5 + *globaln), sizeof(int)*(*globalx),0700|IPC_CREAT);	
            globaldiziB = (int *)shmat(shmid6, 0, 0);
			
			//B array ini ayýrdýgým bellege yazýyorum
            int j=0;
            for(i=0; i< *globaln ; i++){
                if( globaldiziA[i] <= *globalM){
                    globaldiziB[j] = globaldiziA[i];
                    j++;
                }
            }

            //kaynaklari iade ediyorum.
            shmdt(globaln);
            shmdt(globalM);
            shmdt(globalx);
            shmdt(globaldiziA);
            shmdt(globaldiziB);

			//cocuk 2 için senkronizasyonu saglýyor
            sem_signal(semafor1,1); 
            //anne için senkronizasyonu saglýyor
            sem_signal(semafor2,1);
        }
        else if(indis == 2){ //cocuk proses 2
        	//cocuk 1 için senkronizasyonu saglýyor
            sem_wait(semafor1,1);

            globaln = (int *)shmat(shmid1, 0, 0);
            globalM = (int *)shmat(shmid2, 0, 0);
            globalx = (int *)shmat(shmid3, 0, 0);
            globaly = (int *)shmat(shmid4, 0, 0);
            globaldiziA = (int*) shmat(shmid5,0,0);
			
			//C array inin boyutunu buluyorum ve sonucu y için ayýrdýgým yerde tutuyorum
            for(i=0; i<*globaln; i++){
                if(globaldiziA[i] > *globalM){
                    *globaly = *globaly + 1;
                }
            }

            int shmid7 = shmget( (KEYSHM5 + *globaln + *globalx), sizeof(int)*(*globaly),0700|IPC_CREAT);
            globaldiziC = (int *)shmat(shmid7, 0, 0);
			
			//C array ini ayýrdýgým bellege yazýyorum
            int j=0;
            for(i=0; i< *globaln ; i++){
                if( globaldiziA[i] > *globalM){
                    globaldiziC[j] = globaldiziA[i];
                    j++;
                }
            }

            //kaynaklari iade ediyorum.
            shmdt(globaln);
            shmdt(globalM);
            shmdt(globalx);
            shmdt(globaly);
            shmdt(globaldiziA);
            shmdt(globaldiziC);
            
            //anne için senkronizasyonu saglýyor
            sem_signal(semafor3,1);
        }
    }
	return 0;
}
