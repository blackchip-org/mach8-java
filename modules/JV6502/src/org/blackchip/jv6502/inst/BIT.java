// $Id: BIT.java 16 2010-12-30 00:45:02Z mcgann $

package org.blackchip.jv6502.inst;

import org.blackchip.jv6502.Instruction;
import org.blackchip.jv6502.InstructionVisitor;
import org.blackchip.jv6502.cpu;
import org.blackchip.jv6502.mmu;
import org.blackchip.jv6502.op;
import org.blackchip.system.sys;

/*******************************************************************************
 *
 * Test bits.
 *
 * <pre>
 * Affects Flags: N V Z
 *
 * MODE           SYNTAX       HEX LEN TIM
 * Zero Page     BIT $44       $24  2   3
 * Absolute      BIT $4400     $2C  3   4
 *
 * BIT sets the Z flag as though the value in the address tested were ANDed with
 * the accumulator. The S and V flags are set to match bits 7 and 6 respectively
 * in the value stored at the tested address.
 *
 * BIT is often used to skip one or two following bytes as in:
 *
 * CLOSE1 LDX #$10   If entered here, we
 *        .BYTE $2C  effectively perform
 * CLOSE2 LDX #$20   a BIT test on $20A2,
 *        .BYTE $2C  another one on $30A2,
 * CLOSE3 LDX #$30   and end up with the X
 * CLOSEX LDA #12    register still at $10
 *        STA ICCOM,X upon arrival here.
 * </pre>
 *
 * @version {@code $Revision: 16 $ $Date: 2010-12-29 19:45:02 -0500 (Wed, 29 Dec 2010) $}
 * @since NMOS 6502.
 *
 ******************************************************************************/
public class BIT extends Instruction
{
    /**
     * Constructor.
     *
     * @param o opcode for this instance.
     */
    public BIT(int o)
    {
        super(o);
    }

    @Override
    public int getOperandLength()
    {
        return opcode == op.BIT_ABS ? 2 : 1;
    }

    public void run()
    {
        int operand = 0;

        switch ( opcode )
        {
            case op.BIT_ZP:
                operand = mmu.loadZP(cpu.fetch()); break;
            case op.BIT_ABS:
                operand = mmu.load(cpu.fetch16()); break;
            default:
                throw new IllegalArgumentException("Invalid opcode for BIT: " +
                        sys.toHex(opcode));
        }

        int result = ( cpu.a & operand ) & sys.BYTE_MASK;
        cpu.p.z = result == 0;
        cpu.p.n = ( operand & sys.BIT7 ) != 0;
        cpu.p.v = ( operand & sys.BIT6 ) != 0;
    }

    @Override
    public void accept(int opaddr, InstructionVisitor v, int... operands)
    {
        v.visitBIT(this, operands);
    }



}
