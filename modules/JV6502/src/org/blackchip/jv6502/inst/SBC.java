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
import org.blackchip.jv6502.trap;

/**
 *
 * @author mcgann
 */
public class SBC extends Instruction
{

    public SBC(int o)
    {
        super(o);
    }

    public int getOperandLength()
    {
        switch ( opcode )
        {
            case op.SBC_ABS:
            case op.SBC_ABX:
            case op.SBC_ABY:
                return 2;
        }
        return 1;
    }

    public void run()
    {
        int operand; 
        
        switch ( opcode )
        {
            case op.SBC_IMM:
                operand = cpu.fetch();
                break;
            case op.SBC_ZP:
                operand = mmu.loadZP(cpu.fetch());
                break;
            case op.SBC_ZPX:
                operand = mmu.loadZP(cpu.fetch(), cpu.x);
                break;
            case op.SBC_ABS:
                operand = mmu.load(cpu.fetch16());
                break;
            case op.SBC_ABX:
                operand = mmu.load(cpu.fetch16(), cpu.x);
                break;
            case op.SBC_ABY:
                operand = mmu.load(cpu.fetch16(), cpu.y);
                break;
            case op.SBC_IZX:
                operand = mmu.loadIZX(cpu.fetch(), cpu.x);
                break;
            case op.SBC_IZY:
                operand = mmu.loadIZY(cpu.fetch(), cpu.y);
                break;
            default:
                throw new IllegalArgumentException("Invalid opcode for SBC: " +
                        opcode);
        }

        int result = 0;

        // In BCD mode?
        if ( cpu.p.d )
        {
            // Yes: Convert operand and accumlator to BCD values
            int b1 = sys.toBCD[operand];
            int b2 = sys.toBCD[cpu.a];

            // Are they valid BCD values? Trap if not.
            if ( b2 == -1 )
            {
                cpu.trap(trap.INVALID_BCD_NUMBER, "Invalid BCD number: %02X",
                        cpu.a);
                return;
            }
            if ( b1 == -1 )
            {
                cpu.trap(trap.INVALID_BCD_NUMBER, "Invalid BCD number: %02X",
                        operand);
                return;
            }

            // Perform addition with carry
            int bcdResult = b2 - b1 - (!cpu.p.c ? 1 : 0);

            // Carry if over max BCD value
            cpu.p.c = ! (bcdResult < 0);

            // Convert result back from BCD, chop off last two digits if there
            // is overflow.
            if ( bcdResult < 0 )
            {
                bcdResult = 100 + bcdResult;
            }
            result = sys.fromBCD[bcdResult % 100];
        }
        else
        {
            // No: Normal addition with carry.
            result = (byte)cpu.a - (byte)operand - (!cpu.p.c ? 1 : 0);
            cpu.p.c = !(cpu.a - operand - (!cpu.p.c ? 1 : 0) < 0);
        }

        // Set other flags and accumulator with result
        cpu.p.z = ( result & sys.BYTE_MASK ) == 0;
        cpu.p.n = ( result & sys.BIT7 ) != 0;
        cpu.p.v = result < -128 || result > 127;
        cpu.a = result & sys.BYTE_MASK;
    }

    public void accept(int opaddr, InstructionVisitor v, int... operands)
    {
        v.visitSBC(this, operands);
    }

}
