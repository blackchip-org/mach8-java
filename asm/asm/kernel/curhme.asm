; $Id: curhme.asm 17 2010-12-30 00:45:40Z mcgann $

.require "memory.asm"

_CURHME:
            pha

            lda #$0
            sta CURSOR_X
            jsr CURPOS

            pla
            rts