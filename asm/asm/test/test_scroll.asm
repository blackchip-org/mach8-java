.require "memory.asm"

.org ENTRY

            lda #38
            sta CURSOR_Y

            lda #<LINE1
            sta REG1
            lda #>LINE1
            sta REG2
            jsr SPRINT

            lda #$0d
            jsr CPRINT

            lda #<LINE2
            sta REG1
            lda #>LINE2
            sta REG2
            jsr SPRINT

            lda #$0d
            jsr CPRINT

            lda #<LINE3
            sta REG1
            lda #>LINE3
            sta REG2
            jsr SPRINT

            lda #$0d
            jsr CPRINT

            lda #<LINE4
            sta REG1
            lda #>LINE4
            sta REG2
            jsr SPRINT

            brk
            nop


LINE1: .byte "1    Line 1", 0
LINE2: .byte "21   Line 2", 0
LINE3: .byte "321  Line 3", 0
LINE4: .byte "4321 Line 4", 0
