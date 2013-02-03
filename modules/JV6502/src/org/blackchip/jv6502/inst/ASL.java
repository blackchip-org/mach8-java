// $Id: ASL.java 16 2010-12-30 00:45:02Z mcgann $

package org.blackchip.jv6502.inst;

import org.blackchip.jv6502.Instruction;
import org.blackchip.jv6502.InstructionVisitor;
import org.blackchip.jv6502.cpu;
import org.blackchip.jv6502.mmu;
import org.blackchip.jv6502.op;
import org.blackchip.system.sys;

/*******************************************************************************
 *
 * Arithmetic shift left.
 *
 * <pre>
 * Affects Flags: S Z C
 *
 * MODE           SYNTAX       HEX LEN TIM
 * Accumulator   ASL A         $0A  1   2
 * Zero Page     ASL $44       $06  2   5
 * Zero Page,X   ASL $44,X     $16  2   6
 * Absolute      ASL $4400     $0E  3   6
 * Absolute,X    ASL $4400,X   $1E  3   7
 *
 * ASL shifts all bits left one position. 0 is shifted into bit 0 and the
 * original bit 7 is shifted into the Carry.
 * </pre>
 * 
 * @version {@code $Revision: 16 $ $Date: 2010-12-29 19:45:02 -0500 (Wed, 29 Dec 2010) $}
 * @since NMOS 6502
 *
 ******************************************************************************/

public class ASL extends Instruction
{

    /**
     * Constructor.
     *
     * @param o opcode for this instance. 
     */
    public ASL(int o)
    {
        super(o);
    }

    @Override
    public int getOperandLength()
    {
        switch ( opcode )
        {
            case op.ASL_ACC:
                return 0;
            case op.ASL_ABS:
            case op.ASL_ABX:
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
            case op.ASL_ACC:
                cpu.a = shift(cpu.a);
                break;
            case op.ASL_ZP:
                address = cpu.fetch();
                operand = mmu.loadZP(address);
                mmu.storeZP(address, shift(operand));
                break;
            case op.ASL_ZPX:
                address = cpu.fetch();
                operand = mmu.loadZP(address, cpu.x);
                mmu.storeZP(address, cpu.x, shift(operand));
                break;
            case op.ASL_ABS:
                address = cpu.fetch16();
                operand = mmu.load(address);
                mmu.store(address, shift(operand));
                break;
            case op.ASL_ABX:
                address = cpu.fetch16();
                operand = mmu.load(address, cpu.x);
                mmu.store(address, cpu.x, shift(operand));
                break;
        }
    }

    public int shift(int v)
    {
        cpu.p.c = ( v & sys.BIT7 ) != 0;
        int result = ( v << 1 ) & sys.BYTE_MASK;
        cpu.p.z = result == 0;
        cpu.p.n = (byte)result < 0;
        return result;
    }

    @Override
    public void accept(int opaddr, InstructionVisitor v, int... operands)
    {
        v.visitASL(this, operands);
    }



}
