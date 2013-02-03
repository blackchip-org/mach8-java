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
public class ORA extends Instruction
{

    public ORA(int o)
    {
        super(o);
    }

    @Override
    public int getOperandLength()
    {
        switch ( opcode )
        {
            case op.ORA_ABS:
            case op.ORA_ABX:
            case op.ORA_ABY:
                return 2;
        }
        return 1;
    }

    public void run()
    {
        int operand;

        switch ( opcode )
        {
            case op.ORA_IMM:
                operand = cpu.fetch(); break;
            case op.ORA_ZP :
                operand = mmu.loadZP(cpu.fetch()); break;
            case op.ORA_ZPX:
                operand = mmu.loadZP(cpu.fetch(), cpu.x); break;
            case op.ORA_ABS:
                operand = mmu.load(cpu.fetch16()); break;
            case op.ORA_ABX:
                operand = mmu.load(cpu.fetch16(), cpu.x); break;
            case op.ORA_ABY:
                operand = mmu.load(cpu.fetch16(), cpu.y); break;
            case op.ORA_IZX:
                operand = mmu.loadIZX(cpu.fetch(), cpu.x); break;
            case op.ORA_IZY:
                operand = mmu.loadIZY(cpu.fetch(), cpu.y); break;
            default:
                throw new IllegalArgumentException("Invalid opcode for ORA: " +
                        sys.toHex(opcode));
        }

        cpu.a = ( cpu.a | operand ) & sys.BYTE_MASK;
        cpu.p.z = cpu.a == 0;
        cpu.p.n = ( cpu.a & sys.BIT7 ) != 0;

    }

    @Override
    public void accept(int opaddr, InstructionVisitor v, int... operands)
    {
        v.visitORA(this, operands);
    }




}
