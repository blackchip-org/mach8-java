; $Id: mult8x8.asm 17 2010-12-30 00:45:40Z mcgann $

.require "memory.asm"

; Multiply two 8-bit numbers for a 16-bit result
;
; Source: http://www.6502.org/source/integers/32muldiv.htm
;
; X: 0 = FP ZP registers 1
;    1 = FP ZP registers 2

_MULT8x8:   pha
            phy
            cpx #$00        ; Which ZP register set?
            bne SET2

SET1:       ldx #$00        ; First ZP register set, no offset
            beq START       ; Branch always

SET2:       ldx #12         ; Second ZP register set, 4 * 3 offset

START:      lda #$00
            sta FP1_OUT,X
            sta FP1_OUT1,X  
            ldy #8          ; Byte count

SHIFT_R:    lsr FP1_OPA,X   ; Shift multiplier right
            bcc ROTATE_R    ; Go rotate right if c = 0
            lda FP1_OUT1,X  ; Get upper half of product and add
            clc             ;    multiplicand to it
            adc FP1_OPB,X
            sta FP1_OUT1,X

ROTATE_R:   ror             ; Rotate partial product right
            sta FP1_OUT1,X
            ror FP1_OUT,X
            dey             ; Decrement bit count until zero
            bne SHIFT_R

            ply
            pla
            rts
