.require "memory.asm"

.org $4000

RESTART:    lda #$20
            jsr CPRINT
            lda #$20
            ldx #$20
LOOP:       jsr CPRINT
            inx
            txa
            cmp #$80
            bcc LOOP    
            bra RESTART

