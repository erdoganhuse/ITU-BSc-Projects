/************************************************************
*	Ad Soyad			: Huseyin Erdogan					*
*   Numara				: 040100054							*
*	Dersin Adi			: Isletim Sistemleri				*
*   Dersin Kodu         : BLG312                            *
*   Odev No             : 03                                *
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
#define SEMKEY1 1000546
#define SEMKEY2 1000547
#define SEMKEY3 1000548
#define SEMKEY4 1000549
#define SEMKEY5 1000545
#define SEMKEY6 1000544

int sayma_semaforu_A,sayma_semaforu_B;
int dislama_semaforu_A,dislama_semaforu_B,dislama_semaforu_F;
int shmid1,shmid2;

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
void mysignal(void){
}
void mysigset(int num){
	struct sigaction mysigaction;
	mysigaction.sa_handler = (void*) mysignal;
	mysigaction.sa_flags = 0;
	sigaction(num, &mysigaction, NULL);
}
char yazici_al(char yazici_tipi){
    if(yazici_tipi == 'A'){
        sem_wait(sayma_semaforu_A,1);
        sem_wait(dislama_semaforu_A,1);
        int *sayac_A = (int *)shmat(shmid1, 0, 0);
        *sayac_A--;
        sem_signal(dislama_semaforu_A,1);
        return 'A';
    }
    else if(yazici_tipi == 'B'){
        sem_wait(sayma_semaforu_B,1);
        sem_wait(dislama_semaforu_B,1);
        int *sayac_B = (int *)shmat(shmid2, 0, 0);
        *sayac_B--;
        sem_signal(dislama_semaforu_B,1);
        return 'B';
    }
    else if(yazici_tipi == 'F'){
        sem_wait(dislama_semaforu_F,1);
        int sem_id_A=semget(SEMKEY1,1,IPC_CREAT);
        int sem_id_B=semget(SEMKEY2,1,IPC_CREAT);

        if(sem_id_A >= sem_id_B){
            sem_wait(sayma_semaforu_A,1);
            sem_wait(dislama_semaforu_A,1);
            sem_signal(dislama_semaforu_A,1);
            sem_signal(dislama_semaforu_F,1);
            return 'A';
        }
        else if(sem_id_A < sem_id_B){
            sem_wait(sayma_semaforu_B,1);
            sem_wait(dislama_semaforu_B,1);
            sem_signal(dislama_semaforu_B,1);
            sem_signal(dislama_semaforu_F,1);
            return 'B';
        }

    }
}
void yazici_ver(char yazici_tipi){
    if(yazici_tipi == 'A'){
        sem_signal(sayma_semaforu_A,1);
        sem_wait(dislama_semaforu_A,1);
        int *sayac_A = (int *)shmat(shmid1, 0, 0);
        *sayac_A++;
        sem_signal(dislama_semaforu_A,1);
    }
    else if(yazici_tipi == 'B'){
        sem_signal(sayma_semaforu_B,1);
        sem_wait(dislama_semaforu_B,1);
        int *sayac_B = (int *)shmat(shmid2, 0, 0);
        *sayac_B++;
        sem_signal(dislama_semaforu_B,1);
    }
}
char harf_olustur(){
    int t = 1 + rand()%3;
    if(t==1){
        return 'X';
    }
	else if(t==2){
		return 'Y';
	}
    return 'Z';
}
char harf_donustur(char harf){
    if(harf=='X'){
        return 'A';
    }
	else if(harf=='Y'){
		return 'B';
	}
	return 'F';
}
int main(int argc, char *argval[],char *env[] ){

    srand (time(NULL));
    mysigset(12);
    int f=0;
    int yazici_sayisi,kullanici_sayisi;
    char yazici_dizisi[25];
    char katar_dizisi[25];
    int *global_sayac_A,*global_sayac_B;

    //num_of_printer = atoi(argval[1]);cmd
    //num_of_user = atoi(argval[2]);
	printf("Yazici Sayisi : ");
	scanf("%d",&yazici_sayisi);
	printf("Kullanici Sayisi : ");
	scanf("%d",&kullanici_sayisi);

    shmid1 = shmget(KEYSHM1, sizeof(int),0700|IPC_CREAT);
    global_sayac_A = (int *)shmat(shmid1, 0, 0);
    *global_sayac_A = yazici_sayisi;

    shmid2 = shmget(KEYSHM1, sizeof(int),0700|IPC_CREAT);
    global_sayac_B = (int *)shmat(shmid2, 0, 0);
    *global_sayac_B = yazici_sayisi;

    sayma_semaforu_A = semget(SEMKEY1,1,0700|IPC_CREAT);
    semctl(sayma_semaforu_A,0,SETVAL,yazici_sayisi);

    sayma_semaforu_B = semget(SEMKEY2,1,0700|IPC_CREAT);
    semctl(sayma_semaforu_B,0,SETVAL,yazici_sayisi);

    dislama_semaforu_A = semget(SEMKEY3,1,0700|IPC_CREAT);
    semctl(dislama_semaforu_A,0,SETVAL,1);

    dislama_semaforu_B = semget(SEMKEY4,1,0700|IPC_CREAT);
    semctl(dislama_semaforu_B,0,SETVAL,1);

    dislama_semaforu_F = semget(SEMKEY5,1,0700|IPC_CREAT);
    semctl(dislama_semaforu_F,0,SETVAL,1);

    int dislama_semaforu = 0;
    dislama_semaforu = semget(SEMKEY6,1,0700|IPC_CREAT);
    semctl(dislama_semaforu,0,SETVAL,1);

    int i;
	for( i=1; i<kullanici_sayisi; i++ ){
		if(f == 0){
            f = fork();
        }
	}

    srand(getpid());
	for( i=0; i<25; i++ ){
        katar_dizisi[i]=harf_olustur();
        char yazici = harf_donustur(katar_dizisi[i]);
        char gelen_yazici = yazici_al(yazici);
        yazici_dizisi[i] = gelen_yazici;
        yazici_ver(yazici_dizisi[i]);
	}

    sem_wait(dislama_semaforu,1);
    printf("Kullanici Proses id : %d \n",getpid());
	for( i=0; i<25; i++ ){
        printf("%c",katar_dizisi[i]);
	}
	printf("\n");
	for( i=0; i<25; i++ ){
        printf("%c",yazici_dizisi[i]);
	}
	printf("\n");
    sem_signal(dislama_semaforu,1);

	return 0;
}


