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
public class CPY extends Instruction
{

    public CPY(int o)
    {
        super(o);

    }

    @Override
    public int getOperandLength()
    {
        return ( opcode == op.CPY_ABS ) ? 2 : 1;
    }

    public void run()
    {
        int operand = 0;

        switch ( opcode )
        {
            case op.CPY_IMM:
                operand = cpu.fetch(); break;
            case op.CPY_ZP :
                operand = mmu.loadZP(cpu.fetch()); break;
            case op.CPY_ABS:
                operand = mmu.load(cpu.fetch16()); break;
            default:
                throw new IllegalArgumentException("Invalid opcode for CPY: " +
                        sys.toHex(opcode));
        }

        int value = cpu.y - operand;
        cpu.p.z = value == 0;
        cpu.p.c = value >= 0;
        cpu.p.n = ( cpu.y & sys.BIT7 ) != 0;
    }

    @Override
    public void accept(int opaddr, InstructionVisitor v, int... operands)
    {
        v.visitCPY(this, operands);
    }



}
