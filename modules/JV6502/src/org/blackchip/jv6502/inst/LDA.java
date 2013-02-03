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
public class LDA extends Instruction
{

    public LDA(int o)
    {
        super(o);
    }

    public int getOperandLength()
    {
        switch ( opcode )
        {
            case op.LDA_ABS:
            case op.LDA_ABX:
            case op.LDA_ABY:
                return 2;
        }
        return 1;
    }

    public void run()
    {
        switch ( opcode ) {
            case op.LDA_IMM:
                cpu.a = cpu.fetch();
                break;
            case op.LDA_ZP:
                cpu.a = mmu.loadZP(cpu.fetch());
                break;
            case op.LDA_ZPX:
                cpu.a = mmu.loadZP(cpu.fetch(), cpu.x);
                break;
            case op.LDA_ABS:
                cpu.a = mmu.load(cpu.fetch16());
                break;
            case op.LDA_ABX:
                cpu.a = mmu.load(cpu.fetch16(), cpu.x);
                break;
            case op.LDA_ABY:
                cpu.a = mmu.load(cpu.fetch16(), cpu.y);
                break;
            case op.LDA_IZX:
                cpu.a = mmu.loadIZX(cpu.fetch(), cpu.x);
                break;
            case op.LDA_IZY:
                cpu.a = mmu.loadIZY(cpu.fetch(), cpu.y);
                break;
            default:
                throw new IllegalArgumentException("Invalid opcode for LDA: " +
                        opcode);
        }
        
        cpu.p.z = ( cpu.a == 0 );
        cpu.p.n = ( (byte)cpu.a < 0 );
    }

    public void accept(int opaddr, InstructionVisitor v, int... operands)
    {
        v.visitLDA(this, operands);
    }
}
