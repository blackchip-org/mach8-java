; $Id: idle.asm 17 2010-12-30 00:45:40Z mcgann $

.require "memory.asm"
.require "macro.asm"

_IDLE:      `JSC $06
            jmp _idle
