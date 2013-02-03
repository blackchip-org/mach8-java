; $Id: curpos.asm 17 2010-12-30 00:45:40Z mcgann $

.require "memory.asm"

; Given the cursor X and Y ZP values, compute the video memory address for
; that location. 
;
; X == 0: Text segment
; X != 0: Color segment

_CURPOS:    pha                 ; Save registers
            phx

            lda CURSOR_X        ; Load X cursor location and check value
            cmp #80
            bcc X_OK
            
X_OVERFLOW: lda #0              ; If overflow, set to zero and inc y
            sta CURSOR_X
            inc CURSOR_Y

X_OK:       lda CURSOR_Y        ; Load Y cursor location and check value
            cmp #40
            bcc Y_OK

Y_OVERFLOW:
            ;lda #0              ; If overflow, set X to zero
            ;sta CURSOR_X
            lda #39             ; Set to last line and then scroll the screen
            sta CURSOR_Y
            jsr SCROLL

Y_OK:       sta FP1_OPA         ; Multiply Y by screen width
            lda #80
            sta FP1_OPB
            ldx #0
            jsr MULT_8x8

            lda CURSOR_X        ; Load X cursor location and check value
            clc                 ; Add X to result of Y * screen width
            adc FP1_OUT
            sta FP1_OUT
            lda #0
            adc FP1_OUT1
            sta FP1_OUT1
            
            clc                 ; Pull X and check as parameter
            plx
            bne COLOR_SEG       ; Text or color segment?

TEXT_SEG:   lda #TEXT_START_LOW
            adc FP1_OUT
            sta FP1_OUT
            lda #TEXT_START_HIGH
            adc FP1_OUT1
            sta FP1_OUT1
            bra DONE

COLOR_SEG:  lda #COLOR_START_LOW
            adc FP1_OUT
            sta FP1_OUT
            lda #COLOR_START_HIGH
            adc FP1_OUT1
            sta FP1_OUT1

DONE:       pla
            rts
