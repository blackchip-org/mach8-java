; $Id: curlft.asm 17 2010-12-30 00:45:40Z mcgann $

.require "memory.asm"

_CURLFT:
        pha

        lda CURSOR_X
        bne DONE

        dec CURSOR_Y
        lda #79
        sta CURSOR_X

DONE:
        dec CURSOR_X
        jsr CURPOS
        pla
        rts
