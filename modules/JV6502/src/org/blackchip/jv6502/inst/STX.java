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
public class STX extends Instruction
{

    public STX(int o)
    {
        super(o);
    }

    public int getOperandLength()
    {
        switch ( opcode )
        {
            case op.STX_ABS:
                return 2;
        }
        return 1;
    }

    public void run()
    {
        switch ( opcode ) {
            case op.STX_ZP:
                mmu.storeZP(cpu.fetch(), cpu.x);
                break;
            case op.STX_ZPY:
                mmu.storeZP(cpu.fetch(), cpu.y, cpu.x);
                break;
            case op.STX_ABS:
                mmu.store(cpu.fetch16(), cpu.x);
                break;
            default:
                throw new IllegalArgumentException("Invalid opcode for STX: " +
                        opcode);
        }
    }

    public void accept(int opaddr, InstructionVisitor v, int... operands)
    {
        v.visitSTX(this, operands);
    }
}
