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
public class RTS extends Instruction
{

    public RTS()
    {
        super(op.RTS);
    }

    @Override
    public int getOperandLength()
    {
        return 0;
    }

    public void run()
    {
        int addressLow = cpu.pop();
        int addressHigh = cpu.pop();

        cpu.pc((addressHigh << 8) + addressLow + 1);
    }

    @Override
    public void accept(int opaddr, InstructionVisitor v, int... operands)
    {
        v.visitSimple(this);
    }

}
