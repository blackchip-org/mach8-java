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
public class CLV extends Instruction
{

    public CLV()
    {
        super(op.CLV);
    }

    @Override
    public int getOperandLength()
    {
        return 0;
    }

    public void run()
    {
        cpu.p.v = false;
    }

    @Override
    public void accept(int opaddr, InstructionVisitor v, int... operands)
    {
        v.visitSimple(this);
    }

}

