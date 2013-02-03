/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.blackchip.jv6502.inst;

import org.blackchip.jv6502.Instruction;
import org.blackchip.jv6502.InstructionVisitor;
import org.blackchip.jv6502.cpu;
import org.blackchip.jv6502.op;
import org.blackchip.system.sys;

/**
 *
 * @author mcgann
 */
public class INY extends Instruction
{
    public INY()
    {
        super(op.INY);
    }

    @Override
    public int getOperandLength()
    {
        return 0;
    }

    public void run()
    {
        cpu.y = ( cpu.y + 1 ) & sys.BYTE_MASK;

        cpu.p.z = cpu.y == 0;
        cpu.p.n = (byte)cpu.y < 0;
    }


    @Override
    public void accept(int opaddr, InstructionVisitor v, int... operands)
    {
        v.visitSimple(this);
    }

}
