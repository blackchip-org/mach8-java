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
public class CPX extends Instruction
{

    public CPX(int o)
    {
        super(o);

    }

    @Override
    public int getOperandLength()
    {
        return ( opcode == op.CPX_ABS ) ? 2 : 1;
    }

    public void run()
    {
        int operand = 0;

        switch ( opcode )
        {
            case op.CPX_IMM:
                operand = cpu.fetch(); break;
            case op.CPX_ZP :
                operand = mmu.loadZP(cpu.fetch()); break;
            case op.CPX_ABS:
                operand = mmu.load(cpu.fetch16()); break;
            default:
                throw new IllegalArgumentException("Invalid opcode for CPX: " +
                        sys.toHex(opcode));
        }

        int value = cpu.x - operand;
        cpu.p.z = value == 0;
        cpu.p.c = value >= 0;
        cpu.p.n = ( cpu.x & sys.BIT7 ) != 0;
    }

    @Override
    public void accept(int opaddr, InstructionVisitor v, int... operands)
    {
        v.visitCPX(this, operands);
    }



}
