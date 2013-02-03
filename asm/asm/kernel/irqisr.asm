; $Id: irqisr.asm 17 2010-12-30 00:45:40Z mcgann $

.require "memory.asm"
.require "macro.asm"

.org $2100

; System IRQ service routine

_IRQISR:
            pha

            inc JIFFY_COUNT        ; Increment everytime ISR called

            lda KEY_PRESS          ; Has a key been pressed on the keyboard?
            beq NO_PRESS

            lda KEY_STROKE         ; Yes, print it out
            jsr CPRINT

            lda #$00               ; Clear out key press flag
            sta KEY_PRESS

NO_PRESS:
            pla
            cli
            rti

