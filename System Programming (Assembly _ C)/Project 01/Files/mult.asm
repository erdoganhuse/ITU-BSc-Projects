segment .text
global mult

mult:
    push ebp          ;save the old base pointer value
    mov  ebp,esp      ;base pointer <- stack pointer
    push ebx
;-----------------------------------------------------------;
    mov eax,dword[ebp+20]	;size
	mov ecx,4
	mul ecx
	
	mov dword[ebp-24],eax	;size*4
	
	mov eax,dword[ebp+20]
	mul eax					;size*size
	mov dword[ebp-8],eax		
	
	mul ecx
	mov dword[ebp-28],eax	;size*size*4
   
    mov ebx,0
    mov edx,0
    
	mov dword[ebp-12],ebx
	mov dword[ebp-16],ebx
	mov dword[ebp-20],ebx
	mov dword[ebp-20],ebx
	
	mov ebx,dword[ebp-24]
	mov dword[ebp-32],ebx
    
loopmult:
	mov edi,dword[ebp-12]	;1. işaretci al
	
	mov ecx,0				
	mov dword[ebp-16],ecx	;2. işaretciyi sıfırla
	
loopmult1:
	mov edi,dword[ebp-12]	;1. işaretci
	mov esi,dword[ebp-16] 	;2. işaretci

loopmult2:
	mov ecx,dword[ebp+8]	;matris1 in başlangıc adresi
	mov ecx,[ecx+edi]	
	
	mov eax,dword[ebp+12]	;matris2 nin başlangıc adresi
	mov eax,[eax+esi]
	mul	ecx
	
	mov ecx,dword[ebp+16]	;Resulttaki
	mov ebx,dword[ebp-20]	;yere
	add [ecx+ebx],eax		;çarpımı
	mov eax,[ecx+ebx]		;ekliyor

	mov eax,dword[ebp+20]	;matrix2
	mov ebx,4				;nin 
	mul ebx					;işaretcisini	
	add esi,eax				;arttır	

	add edi,4				;matrix1 in işaretcisini arttır
	
	cmp edi,dword[ebp-32]	;edi ile size*4 ü kontrol et

	jl loopmult2			;Soldaki sagdakinden küçükse devam et

	mov ebx,dword[ebp-20]	;result
	add ebx,4				;işaretcisini
	mov dword[ebp-20],ebx	;arttır
	
	mov eax,dword[ebp-16]	;2. işaretciyi
	add eax,4				;4 arttır
	mov dword[ebp-16],eax	;geri yerine yaz
	
	cmp	eax,dword[ebp-24]	;2. işaretci size*4 den
	jl loopmult1			;farklıysa loopmult1 e dön
	
	mov ecx,dword[ebp-12]	;1. işaretciyi
	add ecx,dword[ebp-24]	;size*4 arttır
	mov dword[ebp-12],ecx	;geri yerine yaz

	mov eax,dword[ebp-32]	;1. matrix
	add eax,dword[ebp-24]	;2. satıra gecerken
	mov dword[ebp-32],eax	;kontrolü arttır
	
	cmp	ecx,dword[ebp-28] 	;1. işaretci size*4 den
	jl loopmult				;farklıysa loopmult1 e dön	
  
	pop ebx
    mov	esp, ebp
    pop ebp          ;restore base pointer
    ret               ;jump to return address
