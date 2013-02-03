.require "memory.asm"

.org ENTRY

            lda #<MESSAGE
            sta REG1
            lda #>MESSAGE
            sta REG2
            jsr SPRINT
            jmp SYSRET

MESSAGE:    .byte "Hello World!", $0d, 0
