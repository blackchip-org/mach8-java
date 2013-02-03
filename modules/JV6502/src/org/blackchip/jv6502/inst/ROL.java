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
public class ROL extends Instruction
{

    public ROL(int o)
    {
        super(o);
    }

    @Override
    public int getOperandLength()
    {
        switch ( opcode )
        {
            case op.ROL_ACC:
                return 0;
            case op.ROL_ABS:
            case op.ROL_ABX:
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
            case op.ROL_ACC:
                cpu.a = shift(cpu.a);
                break;
            case op.ROL_ZP:
                address = cpu.fetch();
                operand = mmu.loadZP(address);
                mmu.storeZP(address, shift(operand));
                break;
            case op.ROL_ZPX:
                address = cpu.fetch();
                operand = mmu.loadZP(address, cpu.x);
                mmu.storeZP(address, cpu.x, shift(operand));
                break;
            case op.ROL_ABS:
                address = cpu.fetch16();
                operand = mmu.load(address);
                mmu.store(address, shift(operand));
                break;
            case op.ROL_ABX:
                address = cpu.fetch16();
                operand = mmu.load(address, cpu.x);
                mmu.store(address, cpu.x, shift(operand));
                break;
        }
    }

    public int shift(int v)
    {
        boolean newCarry = ( v & sys.BIT7 ) != 0;
        int oldCarry = cpu.p.c ? 1 : 0;
        int result = ((v << 1) + oldCarry) & sys.BYTE_MASK;
        cpu.p.c = newCarry;
        cpu.p.z = result == 0;
        cpu.p.n = (byte)result < 0;
        return result;
    }

    @Override
    public void accept(int opaddr, InstructionVisitor v, int... operands)
    {
        v.visitROL(this, operands);
    }



}
