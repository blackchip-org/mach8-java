.require "memory.asm"

.data
.org $3000
.space DATA 3


.text
.org ENTRY
            lda #$11
            sta DATA
            lda #$22
            sta DATA+1
            lda #$33
            sta DATA+2

            lda #$01
            sta REG1
            lda #$30
            sta REG2
            
            lda #$00
            sta REG3
            lda #$30
            sta REG4

            lda #$02
            sta REG5
            lda #$00
            sta REG6

            jsr MEMCPY
            brk

