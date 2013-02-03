// $Id: package-info.java 16 2010-12-30 00:45:02Z mcgann $

/*******************************************************************************
 *
 * 6502 instruction set implementation.
 *
 * <p>
 * Documentation for this package has been copied from the following sources:
 * </p>
 *
 * <ul>
 * <li>
 * <a href='http://www.6502.org/tutorials/6502opcodes.html'>NMOS 6502 Opcodes</a>
 * by John Pickens, Updated by Bruce Clark.
 * </li>
 * <li>
 * <a href='http://www.6502.org/tutorials/65c02opcodes.html'>CMOS 65C02 Opcodes</a>
 * by Bruce Clark.
 * </li>
 * </ul>
 *
 * <p>
 * Common documentation items follow below:
 * </p>
 *
 * <a name="branch">
 * <h2>Branch Instructions</h2>
 * </a>
 * <p>
 * Affect Flags: none
 * </p>
 * <p>
 * All branches are relative mode and have a length of two bytes. Syntax is
 * "Bxx Displacement" or (better) "Bxx Label". See the notes on the Program
 * Counter for more on displacements.
 * </p>
 * <p>
 * Branches are dependant on the status of the flag bits when the op code is
 * encountered. A branch not taken requires two machine cycles. Add one if the
 * branch is taken and add one more if the branch crosses a page boundary.
 * </p>
 * <pre>
 * MNEMONIC                       HEX
 * BPL (Branch on PLus)           $10
 * BMI (Branch on MInus)          $30
 * BVC (Branch on oVerflow Clear) $50
 * BVS (Branch on oVerflow Set)   $70
 * BCC (Branch on Carry Clear)    $90
 * BCS (Branch on Carry Set)      $B0
 * BNE (Branch on Not Equal)      $D0
 * BEQ (Branch on EQual)          $F0
 * </pre>
 * <p>
 * There is no BRA (BRanch Always) instruction but it can be easily emulated by
 * branching on the basis of a known condition. One of the best flags to use for
 * this purpose is the oVerflow which is unchanged by all but addition and
 * subtraction operations.
 * </p>
 * <p>
 * A page boundary crossing occurs when the branch destination is on a different
 * page than the instruction AFTER the branch instruction. For example:
 * </p>
 * <pre>
 * SEC
 * BCS LABEL
 * NOP
 * </pre>
 * <p>
 * A page boundary crossing occurs (i.e. the BCS takes 4 cycles) when (the
 * address of) LABEL and the NOP are on different pages. This means that
 * </p>
 * <pre>
 *        CLV
 *        BVC LABEL
 *  LABEL NOP
 * </pre>
 * <p>
 * the BVC instruction will take 3 cycles no matter what address it is located
 * at.
 * </p>
 */
package org.blackchip.jv6502.inst;

