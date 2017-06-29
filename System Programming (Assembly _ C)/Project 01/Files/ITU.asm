segment .data
msg db "Error" ,10
len equ 5
segment .text
global ITU

ITU:
    push ebp          ;save the old base pointer value
    mov  ebp,esp      ;base pointer <- stack pointer
    push ebx

    mov eax,dword[ebp+12]	;size
    mov ebx,11
    cmp eax,ebx
    jl error
    mov ebx,80
    cmp eax,ebx
    jg error
    
;-----------------------------------------------------------;    

    mov edx,0

    mov eax,dword[ebp+12]	;size
    mov ecx,4
    mul ecx
    mov dword[ebp-8],eax	;size*4
    
    mul dword[ebp+12]
    mov dword[ebp-12],eax	;size*size*4
    
    mov eax,dword[ebp+12]	;size
    mov ecx,11
    div	ecx					;eax = size/11
    
    mov dword[ebp-16],eax	;size/11
     
    mov ecx,3
    mul ecx
    mov dword[ebp-20],eax	;size*(3/11)
    
    mov eax,dword[ebp-16]	;size/11
    mov ecx,4
    mul ecx
    mov dword[ebp-24],eax	;size*(4/11)

loopITU:
	mov esi,1
	mov edi,dword[ebp+8]	;*ITU_matrix

	mov eax,dword[ebp-8]	;size*4
	mov ecx,dword[ebp-24]
	mul dword[ebp-24]		;size*4*size*(4/11)


	mov ecx,0;
loopa:
	
	add eax,dword[ebp-24]
	mov ebx,eax
	add ebx,dword[ebp-24]
loopa1:
	mov [edi+eax],esi
	add	eax,4
	cmp eax,ebx		
	jne loopa1

	add eax,dword[ebp-24]
	mov ebx,eax
	add ebx,dword[ebp-24]
	add ebx,dword[ebp-24]
	add ebx,dword[ebp-24]
loopa2:
	mov [edi+eax],esi
	add	eax,4
	cmp eax,ebx		
	jne loopa2	

	add eax,dword[ebp-24]
	mov ebx,eax
	add ebx,dword[ebp-24]
loopa3:
	mov [edi+eax],esi
	add	eax,4
	cmp eax,ebx		
	jne loopa3
	
	add eax,dword[ebp-24]
	mov ebx,eax
	add ebx,dword[ebp-24]	
loopa4:
	mov [edi+eax],esi
	add	eax,4
	cmp eax,ebx		
	jne loopa4
	
	add	eax,[ebp-24]
	
	add ecx,4
	cmp ecx,dword[ebp-24]
	jne loopa

;---------------------------------------------------

	mov ecx,0;
loopb:
	
	add eax,dword[ebp-24]
	mov ebx,eax
	add ebx,dword[ebp-24]
loopb1:
	mov [edi+eax],esi
	add	eax,4
	cmp eax,ebx		
	jne loopb1

	add eax,dword[ebp-24]
	add eax,dword[ebp-24]
	mov ebx,eax
	add ebx,dword[ebp-24]
loopb2:
	mov [edi+eax],esi
	add	eax,4
	cmp eax,ebx		
	jne loopb2	

	add eax,dword[ebp-24]
	add eax,dword[ebp-24]
	mov ebx,eax
	add ebx,dword[ebp-24]
loopb3:
	mov [edi+eax],esi
	add	eax,4
	cmp eax,ebx		
	jne loopb3

	add eax,dword[ebp-24]
	mov ebx,eax
	add ebx,dword[ebp-24]
loopb4:
	mov [edi+eax],esi
	add	eax,4
	cmp eax,ebx		
	jne loopb4
	
	add	eax,[ebp-24]
	
	add ecx,4
	cmp ecx,dword[ebp-24]
	jne loopb	

;--------------------------------------------------
	mov ecx,0;
loopc:
	
	add eax,dword[ebp-24]
	mov ebx,eax
	add ebx,dword[ebp-24]
loopc1:
	mov [edi+eax],esi
	add	eax,4
	cmp eax,ebx		
	jne loopc1

	add eax,dword[ebp-24]
	add eax,dword[ebp-24]
	mov ebx,eax
	add ebx,dword[ebp-24]
loopc2:
	mov [edi+eax],esi
	add	eax,4
	cmp eax,ebx		
	jne loopc2	

	add eax,dword[ebp-24]
	add eax,dword[ebp-24]
	mov ebx,eax
	add ebx,dword[ebp-24]
	add ebx,dword[ebp-24]
	add ebx,dword[ebp-24]
loopc3:
	mov [edi+eax],esi
	add	eax,4
	cmp eax,ebx		
	jne loopc3
	
	add	eax,[ebp-24]
	
	add ecx,4
	cmp ecx,dword[ebp-24]
	jne loopc

;-----------------------------------------------------------;    
	pop ebx
    mov	esp, ebp
    pop ebp          ;restore base pointer
    ret               ;jump to return address

error:
	mov eax,4
	mov ebx,1
	mov ecx,msg
	mov edx,len
	int 80h

	mov eax ,1
	mov ebx,0
	int 80h
   
    pop ebx
    mov	esp, ebp
    pop ebp          ;restore base pointer
    ret               ;jump to return address
