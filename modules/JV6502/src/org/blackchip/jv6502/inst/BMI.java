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
public class BMI extends Instruction
{

    public BMI()
    {
        super(op.BMI);
    }

    @Override
    public int getOperandLength()
    {
        return 1;
    }

    public void run()
    {
        int displacement = cpu.fetch();
        if ( cpu.p.n )
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
