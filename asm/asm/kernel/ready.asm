.require "memory.asm"

        lda #<RDYTXT
        sta REG1
        lda #>RDYTXT
        sta REG2
        jsr SPRINT

        rts


RDYTXT: .byte $d, "READY.", $d, 0