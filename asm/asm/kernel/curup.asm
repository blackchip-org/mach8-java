; $Id: curup.asm 17 2010-12-30 00:45:40Z mcgann $

.require "memory.asm"

_CURUP:
            pha
            lda CURSOR_Y
            beq DONE
            dec CURSOR_Y
DONE:       pla
            rts
