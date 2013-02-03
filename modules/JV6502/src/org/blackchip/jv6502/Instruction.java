// $Id: Instruction.java 16 2010-12-30 00:45:02Z mcgann $

package org.blackchip.jv6502;

/*******************************************************************************
 *
 * Base class for all 6502 instructions.
 *
 * <p>
 * When the CPU fetches and decodes an opcode, it selects the corresponding
 * Instruction class from the {@link cpu#inst} array and executes its
 * {@code run} method. For each instruction (e.g., LDA), there is an instance
 * for each addressing mode.
 * </p>
 *
 * @version {@code $Revision: 16 $ $Date: 2010-12-29 19:45:02 -0500 (Wed, 29 Dec 2010) $}
 *
 ******************************************************************************/
public abstract class Instruction implements Runnable
{
    /**
     * The opcode for this instruction instance.
     */
    protected int opcode;

    /**
     * Name of this instruction.
     */
    private String label;

    private Instruction()
    {
    }

    /**
     * Constructor.
     *
     * @param op opcode for this instruction instance.
     */
    protected Instruction(int op)
    {
        this.opcode = op;
        label = getClass().getSimpleName();
    }

    /**
     * Opcode for this instruction instance.
     *
     * @return the opcode.
     */
    public final int getOpcode()
    {
        return opcode;
    }

    /**
     * Symbolic name of this instruction (e.g., LDA).
     *
     * @return the name.
     */
    public final String getLabel()
    {
        return label;
    }

    /**
     * The length of the operands. Useful for the disassembler.
     *
     * @return the length of the operands in bytes.
     */
    public abstract int getOperandLength();

    /**
     * Visitor pattern for diassembly printing.
     *
     * @param opaddr the memory address where this opcode is found.
     * @param v instance that should be visited.
     * @param operands the bytes that are operands of this instruction. 
     */
    public abstract void accept(int opaddr, InstructionVisitor v,
            int... operands);

    /**
     * Executes this instruction. 
     */
    public abstract void run();

}
