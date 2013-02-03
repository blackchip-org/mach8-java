/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.blackchip.jv6502.inst;

import org.blackchip.jv6502.Instruction;
import org.blackchip.jv6502.InstructionVisitor;
import org.blackchip.jv6502.op;
import org.blackchip.jv6502.cpu;
import org.blackchip.jv6502.mmu;

/**
 *
 * @author mcgann
 */
public class STA extends Instruction
{

    public STA(int o)
    {
        super(o);
    }

    public int getOperandLength()
    {
        switch ( opcode )
        {
            case op.STA_ABS:
            case op.STA_ABX:
            case op.STA_ABY:
                return 2;
        }
        return 1;
    }

    public void run()
    {
        switch ( opcode ) {
            case op.STA_ZP:
                mmu.storeZP(cpu.fetch(), cpu.a);
                break;
            case op.STA_ZPX:
                mmu.storeZP(cpu.fetch(), cpu.x, cpu.a);
                break;
            case op.STA_ABS:
                mmu.store(cpu.fetch16(), cpu.a);
                break;
            case op.STA_ABX:
                mmu.store(cpu.fetch16(), cpu.x, cpu.a);
                break;
            case op.STA_ABY:
                mmu.store(cpu.fetch16(), cpu.y, cpu.a);
                break;
            case op.STA_IZX:
                mmu.storeIZX(cpu.fetch(), cpu.x, cpu.a);
                break;
            case op.STA_IZY:
                mmu.storeIZY(cpu.fetch(), cpu.y, cpu.a);
                break;
            default:
                throw new IllegalArgumentException("Invalid opcode for STA: " +
                        opcode);
        }
    }

    public void accept(int opaddr, InstructionVisitor v, int... operands)
    {
        v.visitSTA(this, operands);
    }
}

