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
public class TXA extends Instruction
{

    public TXA()
    {
        super(op.TXA);
    }

    @Override
    public int getOperandLength()
    {
        return 0;
    }

    public void run()
    {
        cpu.a = cpu.x;

        cpu.p.z = cpu.a == 0;
        cpu.p.n = ( cpu.a & sys.BIT7 ) != 0;
    }


    @Override
    public void accept(int opaddr, InstructionVisitor v, int... operands)
    {
        v.visitSimple(this);
    }



}
