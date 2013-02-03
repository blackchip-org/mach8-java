// $Id: ADC.java 16 2010-12-30 00:45:02Z mcgann $

package org.blackchip.jv6502.inst;

import org.blackchip.jv6502.Instruction;
import org.blackchip.jv6502.InstructionVisitor;
import org.blackchip.jv6502.cpu;
import org.blackchip.jv6502.mmu;
import org.blackchip.jv6502.op;
import org.blackchip.system.sys;
import org.blackchip.jv6502.trap;

/*******************************************************************************
 *
 * Add with carry.
 *
 * <pre>
 * Affects Flags: S V Z C
 *
 * MODE           SYNTAX       HEX LEN TIM
 * Immediate     ADC #$44      $69  2   2
 * Zero Page     ADC $44       $65  2   3
 * Zero Page,X   ADC $44,X     $75  2   4
 * Absolute      ADC $4400     $6D  3   4
 * Absolute,X    ADC $4400,X   $7D  3   4+
 * Absolute,Y    ADC $4400,Y   $79  3   4+
 * Indirect,X    ADC ($44,X)   $61  2   6
 * Indirect,Y    ADC ($44),Y   $71  2   5+
 *
 * + add 1 cycle if page boundary crossed
 *
 * ADC results are dependant on the setting of the decimal flag. In decimal
 * mode, addition is carried out on the assumption that the values involved are
 * packed BCD (Binary Coded Decimal).
 *
 * There is no way to add without carry.
 * </pre>
 *
 * @version {@code $Revision: 16 $ $Date: 2010-12-29 19:45:02 -0500 (Wed, 29 Dec 2010) $}
 * @since NMOS 6502
 *
 ******************************************************************************/

public class ADC extends Instruction
{
    /**
     * Constructor.
     *
     * @param o opcode for this instance.
     */
    public ADC(int o)
    {
        super(o);
    }

    public int getOperandLength()
    {
        switch ( opcode )
        {
            case op.ADC_ABS:
            case op.ADC_ABX:
            case op.ADC_ABY:
                return 2;
        }
        return 1;
    }

    public void run()
    {
        int operand; 
        
        switch ( opcode )
        {
            case op.ADC_IMM:
                operand = cpu.fetch();
                break;
            case op.ADC_ZP:
                operand = mmu.loadZP(cpu.fetch());
                break;
            case op.ADC_ZPX:
                operand = mmu.loadZP(cpu.fetch(), cpu.x);
                break;
            case op.ADC_ABS:
                operand = mmu.load(cpu.fetch16());
                break;
            case op.ADC_ABX:
                operand = mmu.load(cpu.fetch16(), cpu.x);
                break;
            case op.ADC_ABY:
                operand = mmu.load(cpu.fetch16(), cpu.y);
                break;
            case op.ADC_IZX:
                operand = mmu.loadIZX(cpu.fetch(), cpu.x);
                break;
            case op.ADC_IZY:
                operand = mmu.loadIZY(cpu.fetch(), cpu.y);
                break;
            default:
                throw new IllegalArgumentException("Invalid opcode for ADC: " +
                        opcode);
        }

        int result = 0;

        // In BCD mode?
        if ( cpu.p.d )
        {
            // Yes: Convert operand and accumlator to BCD values
            int b1 = sys.toBCD[operand];
            int b2 = sys.toBCD[cpu.a];

            // Are they valid BCD values? Trap if not.
            if ( b2 == -1 )
            {
                cpu.trap(trap.INVALID_BCD_NUMBER, "Invalid BCD number: %02X",
                        cpu.a);
                return;
            }
            if ( b1 == -1 )
            {
                cpu.trap(trap.INVALID_BCD_NUMBER, "Invalid BCD number: %02X",
                        operand);
                return;
            }

            // Perform addition with carry
            int bcdResult = b2 + b1 + (cpu.p.c ? 1 : 0);

            // Carry if over max BCD value
            cpu.p.c = bcdResult > 99;

            // Convert result back from BCD, chop off last two digits if there
            // is overflow.
            result = sys.fromBCD[bcdResult % 100];
        }
        else
        {
            // No: Normal addition with carry.
            result = cpu.a + operand + (cpu.p.c ? 1 : 0);
            cpu.p.c = result > 255;
        }

        // Set other flags and accumulator with result
        cpu.p.z = ( result & sys.BYTE_MASK ) == 0;
        cpu.p.n = ( result & sys.BIT7 ) != 0;
        cpu.p.v = ( result & sys.BIT7 ) != ( cpu.a & sys.BIT7 );
        cpu.a = result & sys.BYTE_MASK;
    }

    public void accept(int opaddr, InstructionVisitor v, int... operands)
    {
        v.visitADC(this, operands);
    }

}
