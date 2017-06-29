segment .text
global sum

sum:
    push ebp          ;save the old base pointer value
    mov  ebp,esp      ;base pointer <- stack pointer
    push ebx
;-----------------------------------------------------------;
    mov eax,dword [ebp+12]	;size
    mul eax
    mov ebx,0
    mov edx,0
      
loopsum:
    mov	ecx, dword [ebp+8]	;*matrix1
    mov	ecx, [ecx + ebx]
    add edx,ecx
    
    add ebx,4
    dec eax
    
    cmp eax, 0
    jne	loopsum
	
	mov eax,edx
;-----------------------------------------------------------; 
	pop ebx
    mov	esp, ebp
    pop ebp          ;restore base pointer
    ret               ;jump to return address
