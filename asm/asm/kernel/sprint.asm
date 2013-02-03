; $Id: sprint.asm 17 2010-12-30 00:45:40Z mcgann $

.require "memory.asm"

; Print a string to the console at the current cursor position

_SPRINT:    phy
            ldy #0

LOOP:       lda (REG1),Y
            beq DONE

            jsr CPRINT
            iny
            bra LOOP

DONE:       pla
            rts