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
public class LSR extends Instruction
{

    public LSR(int o)
    {
        super(o);
    }

    @Override
    public int getOperandLength()
    {
        switch ( opcode )
        {
            case op.LSR_ACC:
                return 0;
            case op.LSR_ABS:
            case op.LSR_ABX:
                return 2;
        }
        return 1;
    }

    public void run()
    {
        int operand;
        int address;

        switch ( opcode )
        {
            case op.LSR_ACC:
                cpu.a = shift(cpu.a);
                break;
            case op.LSR_ZP:
                address = cpu.fetch();
                operand = mmu.loadZP(address);
                mmu.storeZP(address, shift(operand));
                break;
            case op.LSR_ZPX:
                address = cpu.fetch();
                operand = mmu.loadZP(address, cpu.x);
                mmu.storeZP(address, cpu.x, shift(operand));
                break;
            case op.LSR_ABS:
                address = cpu.fetch16();
                operand = mmu.load(address);
                mmu.store(address, shift(operand));
                break;
            case op.LSR_ABX:
                address = cpu.fetch16();
                operand = mmu.load(address, cpu.x);
                mmu.store(address, cpu.x, shift(operand));
                break;
        }
    }

    public int shift(int v)
    {
        cpu.p.c = ( v & sys.BIT0 ) != 0;
        int result = ( v >> 1 ) & sys.BYTE_MASK;
        cpu.p.z = result == 0;
        cpu.p.n = (byte)result < 0;
        return result;
    }

    @Override
    public void accept(int opaddr, InstructionVisitor v, int... operands)
    {
        v.visitLSR(this, operands);
    }



}
