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
public class LDY extends Instruction {

    public LDY(int o)
    {
        super(o);
    }

    public int getOperandLength()
    {
        switch ( opcode )
        {
            case op.LDY_ABS:
            case op.LDY_ABX:
                return 2;
        }
        return 1;
    }

    public void run()
    {
        switch ( opcode ) {
            case op.LDY_IMM:
                cpu.y = cpu.fetch();
                break;
            case op.LDY_ZP:
                cpu.y = mmu.loadZP(cpu.fetch());
                break;
            case op.LDY_ZPX:
                cpu.y = mmu.loadZP(cpu.fetch(), cpu.x);
                break;
            case op.LDY_ABS:
                cpu.y = mmu.load(cpu.fetch16());
                break;
            case op.LDY_ABX:
                cpu.y = mmu.load(cpu.fetch16(), cpu.x);
                break;
            default:
                throw new IllegalArgumentException("Invalid opcode for LDY: " +
                        opcode);
        }

        cpu.p.z = ( cpu.y == 0 );
        cpu.p.n = ( (byte)cpu.y < 0 );
    }

    public void accept(int opaddr, InstructionVisitor v, int... operands)
    {
        v.visitLDY(this, operands);
    }
}
