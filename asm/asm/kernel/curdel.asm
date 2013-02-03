; $Id: curdel.asm 17 2010-12-30 00:45:40Z mcgann $

.require "memory.asm"

_CURDEL:
            pha

;            brk
;            nop

            jsr CURLFT
            lda #$20
            jsr CPRINT
            jsr CURLFT

            pla
            rts