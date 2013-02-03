.require "memory.asm"

.org ENTRY

            lda #<TEXT
            sta REG1
            lda #>TEXT
            sta REG2
            jsr SPRINT

            lda #<RVST
            sta REG1
            lda #>RVST
            sta REG2
            jsr SPRINT
            jmp SYSRET


TEXT:       .byte $80, "Black",         $0d
            .byte $81, "White",         $0d
            .byte $82, "Red",           $0d
            .byte $83, "Cyan",          $0d
            .byte $84, "Purple",        $0d
            .byte $85, "Green",         $0d
            .byte $86, "Blue",          $0d
            .byte $87, "Yellow",        $0d
            .byte $88, "Orange",        $0d
            .byte $89, "Brown",         $0d
            .byte $8a, "Light Red",     $0d
            .byte $8b, "Dark Gray",     $0d
            .byte $8c, "Gray",          $0d
            .byte $8d, "Light Green",   $0d
            .byte $8e, "Light Blue",    $0d
            .byte $8f, "Light Gray",    $0d
            
            .byte $0d, $0

RVST:       .byte $80, $01, "Black",         $0d, $01 
            .byte $81, $01, "White",         $0d, $01
            .byte $82, $01, "Red",           $0d, $01
            .byte $83, $01, "Cyan",          $0d, $01
            .byte $84, $01, "Purple",        $0d, $01
            .byte $85, $01, "Green",         $0d, $01
            .byte $86, $01, "Blue",          $0d, $01
            .byte $87, $01, "Yellow",        $0d, $01
            .byte $88, $01, "Orange",        $0d, $01
            .byte $89, $01, "Brown",         $0d, $01
            .byte $8a, $01, "Light Red",     $0d, $01
            .byte $8b, $01, "Dark Gray",     $0d, $01
            .byte $8c, $01, "Gray",          $0d, $01
            .byte $8d, $01, "Light Green",   $0d, $01
            .byte $8e, $01, "Light Blue",    $0d, $01
            .byte $8f, $01, "Light Gray",    $0d, $01
            
            .byte $8d, $0
