.require "memory.asm"

; Easter Egg

_EASTER:
        lda #<EGGTXT
        sta REG1
        lda #>EGGTXT
        sta REG2
        jsr SPRINT
        jmp SYSRET


EGGTXT:
    .byte $d
    .byte "Inspired by the Vintage Computer Club", $d, $d
    .byte " * Eric Chomko", $d
    .byte " * David Gent", $d
    .byte " * Mike McGann", $d
    .byte $0

