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
public class JSR extends Instruction
{

    public JSR()
    {
        super(op.JSR);
    }

    @Override
    public int getOperandLength()
    {
        return 2;
    }

    public void run()
    {
        int address = cpu.fetch16();
        int pushAddress = cpu.pc() -1;
        cpu.push((pushAddress & sys.HIGH_BYTE_MASK) >> 8);
        cpu.push(pushAddress & sys.BYTE_MASK);

        cpu.pc(address - 1);
    }

    @Override
    public void accept(int opaddr, InstructionVisitor v, int... operands)
    {
        v.visitJSR(this, operands);
    }



}
