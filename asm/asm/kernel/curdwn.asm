; $Id: curdwn.asm 17 2010-12-30 00:45:40Z mcgann $

.require "memory.asm"

_CURDWN:
            inc CURSOR_Y
            jsr CURPOS
            rts
