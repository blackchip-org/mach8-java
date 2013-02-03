/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.blackchip.jv6502.inst;

import org.blackchip.jv6502.Instruction;
import org.blackchip.jv6502.InstructionVisitor;
import org.blackchip.jv6502.trap;
import org.blackchip.jv6502.cpu;

/**
 *
 * @author mcgann
 */
public class ILL extends Instruction
{

    public ILL(int o)
    {
        super(o);
    }

    public int getOperandLength()
    {
        return 0;
    }

    public void run()
    {
        cpu.trap(trap.ILLEGAL_INSTRUCTION, "Illegal instruction: $%02X " + 
                "at $%04X", opcode, cpu.pc());
    }

    public void accept(int opaddr, InstructionVisitor v, int... operands)
    {
        v.visitILL(this);
    }

}
