segment .text
global square
extern mult

square:
    push ebp          ;save the old base pointer value
    mov  ebp,esp      ;base pointer <- stack pointer
    push ebx
;-----------------------------------------------------------;
    mov eax,dword[ebp+16]
    push eax
    mov eax,dword[ebp+12]
    push eax
    mov eax,dword[ebp+8]
    push eax
    push eax
    call mult              
;-----------------------------------------------------------;    
	pop ebx
    mov	esp, ebp
    pop ebp          ;restore base pointer
    ret               ;jump to return address

