/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.blackchip.jv6502;

/**
 *
 * @author mcgann
 */
public interface op {

    static final int BRK        = 0x00;
    static final int JSC        = 0x22;
    static final int NOP        = 0xea;

    static final int LDA_IMM    = 0xa9;
    static final int LDA_ZP     = 0xa5;
    static final int LDA_ZPX    = 0xb5;
    static final int LDA_ABS    = 0xad;
    static final int LDA_ABX    = 0xbd;
    static final int LDA_ABY    = 0xb9;
    static final int LDA_IZX    = 0xa1;
    static final int LDA_IZY    = 0xb1;

    static final int LDX_IMM    = 0xa2;
    static final int LDX_ZP     = 0xa6;
    static final int LDX_ZPY    = 0xb6;
    static final int LDX_ABS    = 0xae;
    static final int LDX_ABY    = 0xbe;

    static final int LDY_IMM    = 0xa0;
    static final int LDY_ZP     = 0xa4;
    static final int LDY_ZPX    = 0xb4;
    static final int LDY_ABS    = 0xac;
    static final int LDY_ABX    = 0xbc;

    static final int STA_ZP     = 0x85;
    static final int STA_ZPX    = 0x95;
    static final int STA_ABS    = 0x8d;
    static final int STA_ABX    = 0x9d;
    static final int STA_ABY    = 0x99;
    static final int STA_IZX    = 0x81;
    static final int STA_IZY    = 0x91;

    static final int STX_ZP     = 0x86;
    static final int STX_ZPY    = 0x96;
    static final int STX_ABS    = 0x8e;

    static final int STY_ZP     = 0x84;
    static final int STY_ZPX    = 0x94;
    static final int STY_ABS    = 0x8c;

    static final int CLC        = 0x18;
    static final int SEC        = 0x38;
    static final int CLI        = 0x58;
    static final int SEI        = 0x78;
    static final int CLV        = 0xb8;
    static final int CLD        = 0xd8;
    static final int SED        = 0xf8;

    static final int TAX        = 0xaa;
    static final int TXA        = 0x8a;
    static final int DEX        = 0xca;
    static final int INX        = 0xe8;
    static final int TAY        = 0xa8;
    static final int TYA        = 0x98;
    static final int DEY        = 0x88;
    static final int INY        = 0xc8;

    static final int DEC_ZP     = 0xc6;
    static final int DEC_ZPX    = 0xd6;
    static final int DEC_ABS    = 0xce;
    static final int DEC_ABX    = 0xde;

    static final int INC_ZP     = 0xe6;
    static final int INC_ZPX    = 0xf6;
    static final int INC_ABS    = 0xee;
    static final int INC_ABX    = 0xfe;

    static final int TXS        = 0x9a;
    static final int TSX        = 0xba;
    static final int PHA        = 0x48;
    static final int PLA        = 0x68;
    static final int PHP        = 0x08;
    static final int PLP        = 0x28;

    static final int CMP_IMM    = 0xc9;
    static final int CMP_ZP     = 0xc5;
    static final int CMP_ZPX    = 0xd5;
    static final int CMP_ABS    = 0xcd;
    static final int CMP_ABX    = 0xdd;
    static final int CMP_ABY    = 0xd9;
    static final int CMP_IZX    = 0xc1;
    static final int CMP_IZY    = 0xd1;

    static final int CPX_IMM    = 0xe0;
    static final int CPX_ZP     = 0xe4;
    static final int CPX_ABS    = 0xec;

    static final int CPY_IMM    = 0xc0;
    static final int CPY_ZP     = 0xc4;
    static final int CPY_ABS    = 0xcc;

    static final int JMP_ABS    = 0x4c;
    static final int JMP_IND    = 0x6c;

    static final int BPL        = 0x10;
    static final int BMI        = 0x30;
    static final int BVC        = 0x50;
    static final int BVS        = 0x70;
    static final int BCC        = 0x90;
    static final int BCS        = 0xb0;
    static final int BNE        = 0xd0;
    static final int BEQ        = 0xf0;

    static final int ADC_IMM    = 0x69;
    static final int ADC_ZP     = 0x65;
    static final int ADC_ZPX    = 0x75;
    static final int ADC_ABS    = 0x6d;
    static final int ADC_ABX    = 0x7d;
    static final int ADC_ABY    = 0x79;
    static final int ADC_IZX    = 0x61;
    static final int ADC_IZY    = 0x71;

    static final int SBC_IMM    = 0xe9;
    static final int SBC_ZP     = 0xe5;
    static final int SBC_ZPX    = 0xf5;
    static final int SBC_ABS    = 0xed;
    static final int SBC_ABX    = 0xfd;
    static final int SBC_ABY    = 0xf9;
    static final int SBC_IZX    = 0xe1;
    static final int SBC_IZY    = 0xf1;

    static final int AND_IMM    = 0x29;
    static final int AND_ZP     = 0x25;
    static final int AND_ZPX    = 0x35;
    static final int AND_ABS    = 0x2d;
    static final int AND_ABX    = 0x3d;
    static final int AND_ABY    = 0x39;
    static final int AND_IZX    = 0x21;
    static final int AND_IZY    = 0x31;

    static final int EOR_IMM    = 0x49;
    static final int EOR_ZP     = 0x45;
    static final int EOR_ZPX    = 0x55;
    static final int EOR_ABS    = 0x4d;
    static final int EOR_ABX    = 0x5d;
    static final int EOR_ABY    = 0x59;
    static final int EOR_IZX    = 0x41;
    static final int EOR_IZY    = 0x51;

    static final int ORA_IMM    = 0x09;
    static final int ORA_ZP     = 0x05;
    static final int ORA_ZPX    = 0x15;
    static final int ORA_ABS    = 0x0d;
    static final int ORA_ABX    = 0x1d;
    static final int ORA_ABY    = 0x19;
    static final int ORA_IZX    = 0x01;
    static final int ORA_IZY    = 0x11;

    static final int BIT_ZP     = 0x24;
    static final int BIT_ABS    = 0x2c;

    static final int ASL_ACC    = 0x0a;
    static final int ASL_ZP     = 0x06;
    static final int ASL_ZPX    = 0x16;
    static final int ASL_ABS    = 0x0e;
    static final int ASL_ABX    = 0x1e;

    static final int LSR_ACC    = 0x4a;
    static final int LSR_ZP     = 0x46;
    static final int LSR_ZPX    = 0x56;
    static final int LSR_ABS    = 0x4e;
    static final int LSR_ABX    = 0x5e;

    static final int ROL_ACC    = 0x2a;
    static final int ROL_ZP     = 0x26;
    static final int ROL_ZPX    = 0x36;
    static final int ROL_ABS    = 0x2e;
    static final int ROL_ABX    = 0x3e;

    static final int ROR_ACC    = 0x6a;
    static final int ROR_ZP     = 0x66;
    static final int ROR_ZPX    = 0x76;
    static final int ROR_ABS    = 0x6e;
    static final int ROR_ABX    = 0x7e;

    static final int JSR        = 0x20;
    static final int RTS        = 0x60;
    static final int RTI        = 0x40;
    
    // 65C02
    static final int PHX        = 0xda;
    static final int PHY        = 0x5a;
    static final int PLX        = 0xfa;
    static final int PLY        = 0x7a;
    
    static final int BRA        = 0x80;
    

}
