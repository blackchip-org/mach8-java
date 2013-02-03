// $Id: BCS.java 16 2010-12-30 00:45:02Z mcgann $

package org.blackchip.jv6502.inst;

import org.blackchip.jv6502.Instruction;
import org.blackchip.jv6502.InstructionVisitor;
import org.blackchip.jv6502.cpu;
import org.blackchip.jv6502.op;

/*******************************************************************************
 *
 * Branch on carry set.
 *
 * @see <a href="package-summary.html#branch">Branch Instructions</a>
 * @version {@code $Revision: 16 $ $Date: 2010-12-29 19:45:02 -0500 (Wed, 29 Dec 2010) $}
 * @since NMOS 6502
 *
 ******************************************************************************/
public class BCS extends Instruction
{

    /**
     * Constructor. 
     */
    public BCS()
    {
        super(op.BCS);
    }

    @Override
    public int getOperandLength()
    {
        return 1;
    }

    public void run()
    {
        int displacement = cpu.fetch();
        if ( cpu.p.c )
        {
            cpu.pc(cpu.pc() + (byte)displacement);
        }
    }

    @Override
    public void accept(int opaddr, InstructionVisitor v, int... operands)
    {
        v.visitBranch(opaddr, this, operands);
    }

}
