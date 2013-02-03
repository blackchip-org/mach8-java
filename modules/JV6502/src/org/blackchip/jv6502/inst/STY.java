/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.blackchip.jv6502.inst;

import org.blackchip.jv6502.Instruction;
import org.blackchip.jv6502.InstructionVisitor;
import org.blackchip.jv6502.cpu;
import org.blackchip.jv6502.mmu;
import org.blackchip.jv6502.op;

/**
 *
 * @author mcgann
 */
public class STY extends Instruction
{

    public STY(int o)
    {
        super(o);
    }

    public int getOperandLength()
    {
        switch ( opcode )
        {
            case op.STY_ABS:
                return 2;
        }
        return 1;
    }

    public void run()
    {
        switch ( opcode ) {
            case op.STY_ZP:
                mmu.storeZP(cpu.fetch(), cpu.y);
                break;
            case op.STY_ZPX:
                mmu.storeZP(cpu.fetch(), cpu.x, cpu.y);
                break;
            case op.STY_ABS:
                mmu.store(cpu.fetch16(), cpu.y);
                break;
            default:
                throw new IllegalArgumentException("Invalid opcode for STY: " +
                        opcode);
        }
    }

    public void accept(int opaddr, InstructionVisitor v, int... operands)
    {
        v.visitSTY(this, operands);
    }
}
