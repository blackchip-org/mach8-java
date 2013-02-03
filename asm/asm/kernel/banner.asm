.require "memory.asm"

; Prints the hello banner

        lda #<BANTXT
        sta REG1
        lda #>BANTXT
        sta REG2
        jsr SPRINT

        rts

        
BANTXT: .byte $d, "Welcome to the Mach-8", $d, 0

