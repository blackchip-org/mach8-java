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
public class CMP extends Instruction
{

    public CMP(int o)
    {
        super(o);

    }

    @Override
    public int getOperandLength()
    {
        switch ( opcode )
        {
            case op.CMP_ABS:
            case op.CMP_ABX:
            case op.CMP_ABY:
                return 2;
        }
        return 1;
    }

    public void run()
    {
        int operand = 0;

        switch ( opcode )
        {
            case op.CMP_IMM:
                operand = cpu.fetch(); break;
            case op.CMP_ZP :
                operand = mmu.loadZP(cpu.fetch()); break;
            case op.CMP_ZPX:
                operand = mmu.loadZP(cpu.fetch(), cpu.x); break;
            case op.CMP_ABS:
                operand = mmu.load(cpu.fetch16()); break;
            case op.CMP_ABX:
                operand = mmu.load(cpu.fetch16(), cpu.x); break;
            case op.CMP_ABY:
                operand = mmu.load(cpu.fetch16(), cpu.y); break;
            case op.CMP_IZX:
                operand = mmu.loadIZX(cpu.fetch(), cpu.x); break;
            case op.CMP_IZY:
                operand = mmu.loadIZY(cpu.fetch(), cpu.y); break;
            default:
                throw new IllegalArgumentException("Invalid opcode for CMP: " +
                        sys.toHex(opcode));
        }

        int value = cpu.a - operand;
        cpu.p.z = value == 0;
        cpu.p.c = value >= 0;
        cpu.p.n = ( cpu.a & sys.BIT7 ) != 0;
    }

    @Override
    public void accept(int opaddr, InstructionVisitor v, int... operands)
    {
        v.visitCMP(this, operands);
    }



}
