/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.blackchip.jv6502.inst;

import org.blackchip.jv6502.Instruction;
import org.blackchip.jv6502.InstructionVisitor;
import org.blackchip.jv6502.cpu;
import org.blackchip.jv6502.mmu;
import org.blackchip.jv6502.op;
import org.blackchip.system.sys;

/**
 *
 * @author mcgann
 */
public class JMP extends Instruction
{

    public JMP(int o)
    {
        super(o);
    }

    @Override
    public int getOperandLength()
    {
        return 2;
    }

    public void run()
    {
        int address = 0;

        switch ( opcode )
        {
            case op.JMP_ABS:
                address = cpu.fetch16(); break;
            case op.JMP_IND:
                address = mmu.load16(cpu.fetch16()); break;
            default:
                throw new IllegalArgumentException("Invalid opcode for JMP: " +
                        sys.toHex(opcode));
        }
        cpu.pc(address - 1);
    }

    @Override
    public void accept(int opaddr, InstructionVisitor v, int... operands)
    {
        v.visitJMP(this, operands);
    }



}
