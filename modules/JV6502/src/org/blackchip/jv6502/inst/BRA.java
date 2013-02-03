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
public class BRA extends Instruction
{

    public BRA()
    {
        super(op.BRA);
    }

    @Override
    public int getOperandLength()
    {
        return 1;
    }

    public void run()
    {
        int displacement = cpu.fetch();
        cpu.pc(cpu.pc() + (byte)displacement);
    }

    @Override
    public void accept(int opaddr, InstructionVisitor v, int... operands)
    {
        v.visitBranch(opaddr, this, operands);
    }

}
