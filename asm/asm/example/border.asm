.require "memory.asm"

.org ENTRY

        ldx #$00
LOOP:   stx BORDER_COLOR
        inx
        bra LOOP
