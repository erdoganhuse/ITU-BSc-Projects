*-----------------------------------------------------------
* Title      : BLG322 Homework 3
* Written by : Hüseyin Erdoðan 040100054
* Date       : 16.04.2014
*-----------------------------------------------------------
    ORG $2000
ARR1 DC.B    $12, $33, $14, $23, $11, $02, $02, $07, $00

    ORG $1000
START:                  ; first instruction of program
        LEA STACK, A7
        ADDA.L  #40, A7
        MOVE.L  #SERVICE, ($2C)
        DC.W    $F000, 0, 0, $2000

        ORG     $500
STACK   DS.B    40

SERVICE MOVEM.L D0-D2/A0-A1, -(A7)
        MOVEA.L 22(A7), A0   
        MOVE.W  (A0)+,D0     ; First 16 bits of the instr OpCode->D0 
        MOVEA.L (A0)+, A1   ; Address1 -> a1
        TST.B   D0
        BNE      DESC
        
;----------------Descending Loop--------------------------------;        
DESC    MOVE    #0, D2 
        MOVE.L  A1,A2     
DLOOP1  MOVE.B  (A2),D3
        SUB.B   D2,D3
        BEQ     LOOP1    
        ADD.L   #1,A2
        MOVE.L  A1,A3
DLOOP2  MOVE.B  (A3),D4 
        MOVE.L  A3,A4 
        ADD.L   #1,A4
        MOVE.B  (A4),D6
        SUB.B   D2,D6
        BEQ     DLOOP1         
        MOVE.B  (A3),D7
        CMP.B   D6,D7 
        BHI     DSWAP   
        MOVE.B  D7,(A4) 
        MOVE.B  D6,(A3)             
DSWAP   ADD.L   #1,A3 
        SUB.B   D2,D4
        BNE     DLOOP2
;---------------Descending Loop End-----------------------------;

LOOP1   MOVE.L  A1,A2
        ADD.L   #$100,A1 
        MOVE.L  A1,A3  
LOOP2   MOVE.B  (A2),D3
        MOVE.B  D3,(A3)
        SUB.B   D2,D3
        BEQ     ASC      
        ADD.L   #1,A2
        ADD.L   #1,A3
        BRA     LOOP2   
      
;----------------Ascending Loop--------------------------------;            
ASC     MOVE    #0, D2 
        MOVE.L  A1,A2         
ALOOP1  MOVE.B  (A2),D3
        SUB.B   D2,D3
        BEQ     RET    
        ADD.L   #1,A2
        MOVE.L  A1,A3
ALOOP2  MOVE.B  (A3),D4 
        MOVE.L  A3,A4 
        ADD.L   #1,A4
        MOVE.B  (A4),D6
        SUB.B   D2,D6
        BEQ     ALOOP1  
        MOVE.B  (A3),D7
        CMP.B   D6,D7 
        BLS     ASWAP   
        MOVE.B  D7,(A4) 
        MOVE.B  D6,(A3)             
ASWAP   ADD.L   #1,A3 
        SUB.B   D2,D4
        BNE     ALOOP2  
;----------------Ascending Loop End----------------------------;

RET     MOVE.L  A0, 22(A7)
        MOVEM.L (A7)+, D0/A0-A1
        RTE
        

    SIMHALT             ; halt simulator

ASIZE   DC.B    $05
    END    START        ; last line of source






*~Font name~Courier New~
*~Font size~10~
*~Tab type~1~
*~Tab size~4~
