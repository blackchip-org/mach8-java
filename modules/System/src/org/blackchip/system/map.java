// $Id: map.java 18 2010-12-30 01:55:27Z mcgann $

package org.blackchip.system;

/*******************************************************************************
 *
 * Memory map.
 *
 * <table border='1' cellspacing='0' cellpadding='5'>
 * <tr><td>$100 - $1ff</td><td>Page 1: Stack</td></tr>
 * </table>
 *
 * @version {@code $Revision: 18 $ $Date: 2010-12-29 20:55:27 -0500 (Wed, 29 Dec 2010) $}
 * @see Stack: <a href='http://www.6502.org/tutorials/6502opcodes.html'>NMOS
 * 6502 Opcodes</a>
 * @see <code>VECRST:</code> <a href="http://multimedia.cx/eggs/re-nes/">Breaking
 * Eggs and Making Omelettes</a>
 *
 ******************************************************************************/
public interface map
{

    static final int BANK               = 0x01;
    static final int REG1               = 0x02;
    static final int REG2               = 0x03;
    static final int REG3               = 0x04;
    static final int REG4               = 0x05;
    static final int REG5               = 0x06;
    static final int REG6               = 0x07;

    static final int FP1_OUT            = 0x08;
    static final int FP1_OUT1           = 0x09;
    static final int FP1_OUT2           = 0x0a;
    static final int FP1_OUT3           = 0x0b;

    static final int FP1_OPA            = 0x0c;
    static final int FP1_OPA1           = 0x0d;
    static final int FP1_OPA2           = 0x0e;
    static final int FP1_OPA3           = 0x0f;

    static final int FP1_OPB            = 0x10;
    static final int FP1_OPB1           = 0x11;
    static final int FP1_OPB2           = 0x12;
    static final int FP1_OPB3           = 0x13;

    static final int FP2_OUT            = 0x14;
    static final int FP2_OUT1           = 0x15;
    static final int FP2_OUT2           = 0x16;
    static final int FP2_OUT3           = 0x17;

    static final int FP2_OPA            = 0x18;
    static final int FP2_OPA1           = 0x19;
    static final int FP2_OPA2           = 0x1a;
    static final int FP2_OPA3           = 0x1b;

    static final int FP2_OPB            = 0x1c;
    static final int FP2_OPB1           = 0x1d;
    static final int FP2_OPB2           = 0x1e;
    static final int FP2_OPB3           = 0x1f;

    static final int CURSOR_X           = 0x20;
    static final int CURSOR_Y           = 0x21;
    static final int BORDER_COLOR       = 0x22;
    static final int FG_COLOR           = 0x23;
    static final int BG_COLOR           = 0x24;
    static final int REVERSE_FLAG       = 0x25;
    /**
     * Increments every time the ISR is called
     */
    static final int JIFFY_COUNT        = 0x26;

    /**
     * State of cursor: 0 = unblink, 1 = blink
     */
    static final int CURSOR_STATE       = 0x27;

    static final int KEY_PRESS          = 0x28;
    static final int KEY_STROKE         = 0x29;

    static final int EASTER_JUMP        = 0x42;
    static final int EASTER_JUMP_2      = 0x43;
    static final int EASTER_JUMP_3      = 0x44;
    
    /**
     * $01ff: Top of the stack.
     */
    static final int STACK_TOP          = 0x1ff;

    /**
     * $0100: Bottom of the stack.
     */
    static final int STACK_BOTTOM       = 0x100;

    static final int TEXT_START         = 0x200;
    static final int TEXT_START_LOW     = sys.lowByte(TEXT_START);
    static final int TEXT_START_HIGH    = sys.highByte(TEXT_START);
    static final int TEXT_LINE2         = TEXT_START + 80;
    static final int TEXT_LINE40        = TEXT_START + ( 39 * 80 );
    static final int TEXT_END           = TEXT_START + vmap.TEXT_END;
    // END: 0xe7f

    static final int COLOR_START        = TEXT_START + vmap.COLOR_START;
    static final int COLOR_START_LOW    = sys.lowByte(COLOR_START);
    static final int COLOR_START_HIGH   = sys.highByte(COLOR_START);
    static final int COLOR_LINE2        = COLOR_START + 80;
    static final int COLOR_LINE40       = COLOR_START + ( 39 * 80 );
    // END: 0x1aff

    // Jump table
    static final int CODE_AREA_START    = 0x2000;

    static final int CPRINT             = 0x2000;
    static final int MULT_8x8           = 0x2004;
    static final int CURPOS             = 0x2008;
    static final int SPRINT             = 0x200c;
    static final int MEMCPY             = 0x2010;
    static final int SCROLL             = 0x2014;
    static final int BANNER             = 0x2018;
    static final int READY              = 0x201c;
    static final int IDLE               = 0x2020;
    static final int CURUP              = 0x2024;
    static final int CURDWN             = 0x2028;
    static final int CURLFT             = 0x202c;
    static final int CURRGT             = 0x2030;
    static final int CURHME             = 0x2034;
    static final int CURDEL             = 0x2038;
    static final int SYSRET             = 0x203c;
    static final int EASTER             = 0x2040;

    static final int KERNEL_START       = 0x2100;
    static final int IRQISR             = 0x2100;



    /**
     * $3000: Default entry point loaded into {@link #RSTVEC}
     */
    static final int ENTRY              = 0x3000;
    

    
    static final int CODE_AREA_END      = 0x4000;


    /**
     * $fffa: Non-Maskable interrupt (NMI) vector. On a non-maskable interrupt,
     * the program counter is loaded with the address found in this vector.
     */
    static final int NMIVEC = 0xfffa;

    /**
     * $fffc: Processor reset vector. On a start or reset of the CPU, the
     * program counter is loaded with the address found in this vector.   
     */
    static final int RSTVEC = 0xfffc;

    /**
     * $fffe: Interrupt request (IRQ) vector. On an interrupt, the program
     * counter is loaded with the address found in this vector. 
     */
    static final int IRQVEC = 0xfffe;

}
