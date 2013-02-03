/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.blackchip.jv6502.inst;

import org.blackchip.jv6502.op;
import org.blackchip.jv6502.Instruction;
import org.blackchip.jv6502.InstructionVisitor;
import org.blackchip.jv6502.cpu;
import org.blackchip.jv6502.mmu;

/**
 *
 * @author mcgann
 */
public class LDX extends Instruction
{

    public LDX(int o)
    {
        super(o);
    }

    public int getOperandLength()
    {
        switch ( opcode )
        {
            case op.LDX_ABS:
            case op.LDX_ABY:
                return 2;
        }
        return 1;
    }

    public void run()
    {
        switch ( opcode ) {
            case op.LDX_IMM:
                cpu.x = cpu.fetch();
                break;
            case op.LDX_ZP:
                cpu.x = mmu.loadZP(cpu.fetch());
                break;
            case op.LDX_ZPY:
                cpu.x = mmu.loadZP(cpu.fetch(), cpu.y);
                break;
            case op.LDX_ABS:
                cpu.x = mmu.load(cpu.fetch16());
                break;
            case op.LDX_ABY:
                cpu.x = mmu.load(cpu.fetch16(), cpu.y);
                break;
            default:
                throw new IllegalArgumentException("Invalid opcode for LDX: " +
                        opcode);
        }

        cpu.p.z = ( cpu.x == 0 );
        cpu.p.n = ( (byte)cpu.x < 0 );
    }

    public void accept(int opaddr, InstructionVisitor v, int... operands)
    {
        v.visitLDX(this, operands);
    }

}
