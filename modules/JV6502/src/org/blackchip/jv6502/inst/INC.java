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
public class INC extends Instruction
{

    public INC(int o)
    {
        super(o);
    }

    @Override
    public int getOperandLength()
    {
        switch ( opcode )
        {
            case op.INC_ABS:
            case op.INC_ABX:
                return 2;
        }
        return 1;
    }

    public void run()
    {
        int value = 0;
        int addr  = 0;

        switch ( opcode )
        {
            case op.INC_ZP:
                addr = cpu.fetch();
                value = ( mmu.loadZP(addr) + 1 ) & sys.BYTE_MASK;
                mmu.storeZP(addr, value);
                break;
            case op.INC_ZPX:
                addr = cpu.fetch();
                value = ( mmu.loadZP(addr, cpu.x) + 1 ) & sys.BYTE_MASK;
                mmu.storeZP(addr, cpu.x, value);
                break;
            case op.INC_ABS:
                addr = cpu.fetch16();
                value = ( mmu.load(addr) + 1 ) & sys.BYTE_MASK;
                mmu.store(addr, value);
                break;
            case op.INC_ABX:
                addr = cpu.fetch16();
                value = ( mmu.load(addr, cpu.x) + 1 ) & sys.BYTE_MASK;
                mmu.store(addr, cpu.x, value);
                break;
            default:
                throw new IllegalArgumentException("Invalid opcode for INC: " +
                        sys.toHex(opcode));
        }
        
        cpu.p.z = value == 0;
        cpu.p.n = ( value & sys.BIT7 ) != 0;
    }

    @Override
    public void accept(int opaddr, InstructionVisitor v, int... operands)
    {
        v.visitINC(this, operands);
    }




}
