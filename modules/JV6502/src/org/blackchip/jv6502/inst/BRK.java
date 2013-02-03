/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.blackchip.jv6502.inst;

import org.blackchip.jv6502.Instruction;
import org.blackchip.jv6502.InstructionVisitor;
import org.blackchip.jv6502.TrapException;
import org.blackchip.jv6502.cpu;
import org.blackchip.jv6502.op;
import org.blackchip.jv6502.trap;

/**
 *
 * @author mcgann
 */
public class BRK extends Instruction
{

    public BRK()
    {
        super(op.BRK);
    }

    public int getOperandLength()
    {
        return 0;
    }

    public void run()
    {
        cpu.exception = new TrapException(trap.SOFTWARE_INTERRUPT, "");
        cpu.p.b = true;
        cpu.pc(cpu.pc() + 1);
    }

    @Override
    public void accept(int opaddr, InstructionVisitor v, int... operands)
    {
        v.visitSimple(this);
    }



}
