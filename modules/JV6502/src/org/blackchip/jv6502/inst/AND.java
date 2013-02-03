// $Id: AND.java 16 2010-12-30 00:45:02Z mcgann $

package org.blackchip.jv6502.inst;

import org.blackchip.jv6502.Instruction;
import org.blackchip.jv6502.InstructionVisitor;
import org.blackchip.jv6502.cpu;
import org.blackchip.jv6502.mmu;
import org.blackchip.jv6502.op;
import org.blackchip.system.sys;

/*******************************************************************************
 *
 * Bitwise AND with accumulator.
 *
 * <pre>
 * Affects Flags: S Z
 *
 * MODE           SYNTAX       HEX LEN TIM
 * Immediate     AND #$44      $29  2   2
 * Zero Page     AND $44       $25  2   2
 * Zero Page,X   AND $44,X     $35  2   3
 * Absolute      AND $4400     $2D  3   4
 * Absolute,X    AND $4400,X   $3D  3   4+
 * Absolute,Y    AND $4400,Y   $39  3   4+
 * Indirect,X    AND ($44,X)   $21  2   6
 * Indirect,Y    AND ($44),Y   $31  2   5+
 *
 * + add 1 cycle if page boundary crossed
 * </pre>
 *
 * @version {@code $Revision: 16 $ $Date: 2010-12-29 19:45:02 -0500 (Wed, 29 Dec 2010) $}
 * @since NMOS 6502
 *
 ******************************************************************************/

public class AND extends Instruction
{

    /**
     * Constructor.
     *
     * @param o opcode for this instance.
     */
    public AND(int o)
    {
        super(o);
    }

    @Override
    public int getOperandLength()
    {
        switch ( opcode )
        {
            case op.AND_ABS:
            case op.AND_ABX:
            case op.AND_ABY:
                return 2;
        }
        return 1;
    }

    public void run()
    {
        int operand;

        switch ( opcode )
        {
            case op.AND_IMM:
                operand = cpu.fetch(); break;
            case op.AND_ZP :
                operand = mmu.loadZP(cpu.fetch()); break;
            case op.AND_ZPX:
                operand = mmu.loadZP(cpu.fetch(), cpu.x); break;
            case op.AND_ABS:
                operand = mmu.load(cpu.fetch16()); break;
            case op.AND_ABX:
                operand = mmu.load(cpu.fetch16(), cpu.x); break;
            case op.AND_ABY:
                operand = mmu.load(cpu.fetch16(), cpu.y); break;
            case op.AND_IZX:
                operand = mmu.loadIZX(cpu.fetch(), cpu.x); break;
            case op.AND_IZY:
                operand = mmu.loadIZY(cpu.fetch(), cpu.y); break;
            default:
                throw new IllegalArgumentException("Invalid opcode for AND: " +
                        sys.toHex(opcode));
        }

        cpu.a = ( cpu.a & operand ) & sys.BYTE_MASK;
        cpu.p.z = cpu.a == 0;
        cpu.p.n = ( cpu.a & sys.BIT7 ) != 0;

    }

    @Override
    public void accept(int opaddr, InstructionVisitor v, int... operands)
    {
        v.visitAND(this, operands);
    }




}
