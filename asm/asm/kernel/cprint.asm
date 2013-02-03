; $Id: cprint.asm 17 2010-12-30 00:45:40Z mcgann $

.require "memory.asm"
.require "petscii.asm"

; Print a character to the console at the current cursor position 
;
; A = Character to print

_CPRINT:    
            phx
            phy
            pha

            ldx #$ff
            stx JIFFY_COUNT     ; Turn cursor off

            cmp #$0d            ; Is it a carriage return?
            beq CR

            cmp #KEY_UP
            bne +
            jsr CURUP
            bra DONE

*           cmp #KEY_DOWN
            bne +
            jsr CURDWN
            bra DONE

*           cmp #KEY_LEFT
            bne +
            jsr CURLFT
            bra DONE

*           cmp #KEY_RIGHT
            bne +
            jsr CURRGT
            bra DONE

*           cmp #KEY_HOME
            bne +
            jsr CURHME
            bra DONE

*           cmp #KEY_DEL
            bne +
            jsr CURDEL
            bra DONE

*           cmp #$01            ; Is it reverse mode toggle?
            beq RVS

            pha                 ; Is it a color select?
            and #$f0
            cmp #$80
            beq CLRSEL
            
            pla

            ldx #0              ; None of the above, is a printable character
            jsr CURPOS          ; Compute text memory address of cursor location
            sta (FP1_OUT,X)     ; Store character there

            ldx #1              ; Compute color memory address
            jsr CURPOS

            lda REVERSE_FLAG    ; Are we in reverse mode?
            beq NO_RVS
            lda #$10            ; Yes, OR in bit 4 to indicate reverse char
            bra STO_RVS

NO_RVS:     lda #0              ; No, OR in a cleared bit 4

STO_RVS:    ora FG_COLOR
            ldx #0              ; Store color value
            sta (FP1_OUT,X)

            inc CURSOR_X        ; Increment the cursor position
            bra DONE

CLRSEL:     pla                 ; Color select, store low nibble in current
            and #$0f            ;   foreground color
            sta FG_COLOR
            bra DONE
            
RVS:        lda #1              ; Reverse mode, toggle reverse flag
            eor REVERSE_FLAG
            sta REVERSE_FLAG
            bra DONE

CR:         lda #$00            ; Carriage return, increment the cursor X
            sta CURSOR_X
            inc CURSOR_Y
;           jsr CURPOS          ; Compute which will scroll is needed


DONE:       jsr CURPOS          ; Ugly -- force recompute in case wrapping is
                                ; needed.

            lda #$00
            sta JIFFY_COUNT     ; Turn cursor on

            pla
            ply
            plx
            rts


