/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.blackchip.jv6502.inst;

import org.blackchip.jv6502.Instruction;
import org.blackchip.jv6502.InstructionVisitor;
import org.blackchip.jv6502.cpu;
import org.blackchip.jv6502.op;

/**
 *
 * @author mcgann
 */
public class BNE extends Instruction
{

    public BNE()
    {
        super(op.BNE);
    }

    @Override
    public int getOperandLength()
    {
        return 1;
    }

    public void run()
    {
        int displacement = cpu.fetch();
        if ( !cpu.p.z )
        {
            cpu.pc(cpu.pc() + (byte)displacement);
        }
    }

    @Override
    public void accept(int opaddr, InstructionVisitor v, int... operands)
    {
        v.visitBranch(opaddr, this, operands);
    }

}
