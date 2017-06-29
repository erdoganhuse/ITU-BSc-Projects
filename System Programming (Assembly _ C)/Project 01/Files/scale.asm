segment .text
global scale

scale:
    push ebp          ;save the old base pointer value
    mov  ebp,esp      ;base pointer <- stack pointer
    push ebx
;-----------------------------------------------------------;
    mov eax,dword [ebp+20]	;size
    mul eax
	mov ebx,eax
	mov edx,0
	
loopscale:
    mov eax,dword [ebp+12]	;number
    mov	ecx, dword [ebp+8]	;*matrix1
    mov	ecx, [ecx + edx]
    push edx
    mul ecx

    mov ecx ,dword [ebp+16]	;*result
    pop edx
    mov [ecx+edx],eax
    
    add edx,4
    dec ebx
    cmp ebx,0
    jne	loopscale
;-----------------------------------------------------------;    
	pop ebx
    mov	esp, ebp
    pop ebp          ;restore base pointer
    ret               ;jump to return address
