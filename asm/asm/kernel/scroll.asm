.require "memory.asm"
.require "macro.asm"

_SCROLL:        
                pha
                `PUSHZPR
         
                lda #<TEXT_LINE2
                sta REG1
                lda #>TEXT_LINE2
                sta REG2

                lda #<TEXT_START
                sta REG3
                lda #>TEXT_START
                sta REG4

                ; Size 80 * 40 - 80 = 0xc30
                lda #$30
                sta REG5
                lda #$0c
                sta REG6

                jsr MEMCPY

                lda #<COLOR_LINE2
                sta REG1
                lda #>COLOR_LINE2
                sta REG2

                lda #<COLOR_START
                sta REG3
                lda #>COLOR_START
                sta REG4

                ; Size 80 * 40 - 80 = 0xc30
                lda #$30
                sta REG5
                lda #$0c
                sta REG6

                jsr MEMCPY

                lda #32
                `QMEMSET TEXT_LINE40, 80
                lda FG_COLOR
                `QMEMSET COLOR_LINE40, 80

                `PULLZPR
                pla
                rts
