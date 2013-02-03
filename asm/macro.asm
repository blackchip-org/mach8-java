
.macro PUSHZPR
                lda REG1
                pha 
                lda REG2
                pha
                lda REG3
                pha
                lda REG4
                pha
                lda REG5
                pha
                lda REG6
                pha
.macend

.macro PULLZPR
                pla 
                sta REG6
                pla 
                sta REG5
                pla 
                sta REG4
                pla 
                sta REG3
                pla 
                sta REG2
                pla 
                sta REG1
.macend

; a = value, 1 = address, 2 = count

.macro QMEMSET
                phx
                ldx #_2
                dex
_loop:          sta _1,X
                dex
                bne _loop
                sta _1
                plx
.macend

; JSC psuedo-op = 0x22
.macro JSC
                .byte $022, _1
.macend
