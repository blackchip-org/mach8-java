/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.blackchip.jv6502.inst;

import org.blackchip.jv6502.Instruction;
import org.blackchip.jv6502.InstructionVisitor;
import org.blackchip.jv6502.trap;
import org.blackchip.jv6502.cpu;
import org.blackchip.jv6502.op;
import org.blackchip.system.sys;

/**
 *
 * @author mcgann
 */
public class JSC extends Instruction
{
    public static Runnable[] call = new Runnable[0x100];

    public JSC()
    {
        super(op.JSC);
    }

    public void run()
    {
        int operand = cpu.fetch();
        Runnable function = call[operand];
        if ( function == null )
        {
            cpu.trap(trap.INVALID_SYS_CALL, "Invalid Java System Call: " +
                    operand);
        }
        else
        {
            function.run();
        }
    }

    @Override
    public int getOperandLength()
    {
        return 1;
    }

    public void accept(int opaddr, InstructionVisitor v, int... operands)
    {
        v.visitJSC(operands);
    }
}

