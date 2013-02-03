.require "memory.asm"

.alias SRC      REG1
.alias DEST     REG3
.alias COUNT    REG5
 
_MEMCPY:    pha
            phx 
            ldx #0

LOOP:       lda COUNT
            bne NEXT 
            lda COUNT+1
            bne NEXT
            
DONE:       plx
            pla
            rts

NEXT:       lda (SRC,X)
            sta (DEST,X)
            
            clc
            lda #1
            adc SRC
            sta SRC
            lda #0
            adc SRC+1
            sta SRC+1

            clc
            lda #1
            adc DEST
            sta DEST
            lda #0
            adc DEST+1
            sta DEST+1

            clc
            lda #$ff
            adc COUNT
            sta COUNT
            lda #$ff
            adc COUNT+1
            sta COUNT+1

            bra LOOP

            