segment .text
global add

add:
    push ebp          ;save the old base pointer value
    mov  ebp,esp      ;base pointer <- stack pointer
    push ebx
;-----------------------------------------------------------;
    mov eax,dword[ebp+20]	;size
    mul eax
    mov ebx,0
      
loopadd:
    mov	ecx, dword [ebp+8]	;*matrix1
    mov	ecx, [ecx + ebx]
    push ecx
    
    mov ecx ,dword [ebp+12]	;*matrix2 
    mov ecx ,[ecx+ebx]
    push ecx
    
    mov ecx ,dword [ebp+16]	;*result
    pop edx
    mov [ecx+ebx],edx
    pop edx
    add [ecx+ebx],edx
    
    add ebx,4
    dec eax
    
    cmp eax, 0
    jne	loopadd
;-----------------------------------------------------------;    
	pop ebx
    mov	esp, ebp
    pop ebp          ;restore base pointer
    ret               ;jump to return address
