; $Id: currgt.asm 17 2010-12-30 00:45:40Z mcgann $

.require "memory.asm"

_CURLFT:
        inc CURSOR_X
        jsr CURPOS
        rts