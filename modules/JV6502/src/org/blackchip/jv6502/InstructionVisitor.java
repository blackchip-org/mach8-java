/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.blackchip.jv6502;

/**
 *
 * @author mcgann
 */
public interface InstructionVisitor
{
    void visitLDA(Instruction i, int... operands);
    void visitLDX(Instruction i, int... operands);
    void visitLDY(Instruction i, int... operands);
    void visitSTA(Instruction i, int... operands);
    void visitSTX(Instruction i, int... operands);
    void visitSTY(Instruction i, int... operands);
    void visitADC(Instruction i, int... operands);
    void visitSBC(Instruction i, int... operands);
    void visitDEC(Instruction i, int... operands);
    void visitINC(Instruction i, int... operands);
    void visitCMP(Instruction i, int... operands);
    void visitCPX(Instruction i, int... operands);
    void visitCPY(Instruction i, int... operands);
    void visitJMP(Instruction i, int... operands);
    void visitAND(Instruction i, int... operands);
    void visitEOR(Instruction i, int... operands);
    void visitORA(Instruction i, int... operands);
    void visitBIT(Instruction i, int... operands);
    void visitASL(Instruction i, int... operands);
    void visitLSR(Instruction i, int... operands);
    void visitROR(Instruction i, int... operands);
    void visitROL(Instruction i, int... operands);
    void visitJSR(Instruction i, int... opernads);

    void visitSimple(Instruction i);
    void visitBranch(int opaddr, Instruction i, int... operands);

    void visitILL(Instruction i);
    void visitJSC(int... operands);
}
