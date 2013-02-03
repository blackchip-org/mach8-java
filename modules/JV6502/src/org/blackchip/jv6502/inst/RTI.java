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
public class RTI extends Instruction
{

    public RTI()
    {
        super(op.RTI);
    }

    @Override
    public int getOperandLength()
    {
        return 0;
    }

    public void run()
    {
        cpu.p.set(cpu.pop());
        int lowAddress = cpu.pop();
        int highAddress = cpu.pop();
        int address = sys.to16(lowAddress, highAddress);
        cpu.pc(address);
        cpu.p.i = false;
    }

    @Override
    public void accept(int opaddr, InstructionVisitor v, int... operands)
    {
        v.visitSimple(this);
    }

}
