/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.blackchip.jv6502.inst;

import org.blackchip.jv6502.CPUSetup;
import org.blackchip.jv6502.cpu;
import org.blackchip.jv6502.util.Assembler;
import org.blackchip.jv6502.util.Disassembler;
import org.blackchip.jv6502.op;
import org.blackchip.system.sys;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mcgann
 */
public class MathTest {

    @Test
    public void test()
    {
        sys.println("***** Math test ****");

        CPUSetup.debug();

        Assembler a = new Assembler();
        Disassembler d = new Disassembler();

        sys.println("\n===== 1 + 1 = 2");
        a.pb(op.CLC);
        a.pb(op.LDA_IMM).pb(0x01);
        a.pb(op.ADC_IMM).pb(0x01);
        a.brk();

        d.print(a); cpu.resume();
        assertEquals(0x2, cpu.a);
        assertTrue(!cpu.p.c);
        assertTrue(!cpu.p.v);
        
        sys.println("\n===== 1 + -1 = 0");
        a.pb(op.CLC);
        a.pb(op.LDA_IMM).pb(0x01);
        a.pb(op.ADC_IMM).pb(0xff);
        a.brk();

        d.print(a); cpu.resume();
        assertEquals(0x00, cpu.a);
        assertTrue(cpu.p.c);
        assertTrue(!cpu.p.v);

        sys.println("\n===== 127 + 1 = 128");
        a.pb(op.CLC);
        a.pb(op.LDA_IMM).pb(0x7f);
        a.pb(op.ADC_IMM).pb(0x01);
        a.brk();

        d.print(a); cpu.resume();
        assertEquals(0x80, cpu.a);
        assertTrue(!cpu.p.c);
        assertTrue(cpu.p.v);

        sys.println("\n===== -128 + -1 = 129");
        a.pb(op.CLC);
        a.pb(op.LDA_IMM).pb(0x80);
        a.pb(op.ADC_IMM).pb(0xff);
        a.brk();

        d.print(a); cpu.resume();
        assertEquals(0x7f, cpu.a);
        assertTrue(cpu.p.c);
        assertTrue(cpu.p.v);

        sys.println("\n===== 0 - 1 = -1");
        a.pb(op.SEC);
        a.pb(op.LDA_IMM).pb(0x00);
        a.pb(op.SBC_IMM).pb(0x01);
        a.brk();

        d.print(a); cpu.resume();
        assertEquals(0xff, cpu.a);
        assertTrue(!cpu.p.v);

        sys.println("\n===== -128 - 1 = -129");
        a.pb(op.SEC);
        a.pb(op.LDA_IMM).pb(0x80);
        a.pb(op.SBC_IMM).pb(0x01);
        a.brk();

        d.print(a); cpu.resume();
        assertEquals(0x7f, cpu.a);
        assertTrue(cpu.p.v);

        sys.println("\n===== 127 - -1 = 128");
        a.pb(op.SEC);
        a.pb(op.LDA_IMM).pb(0x7f);
        a.pb(op.SBC_IMM).pb(0xff);
        a.brk();

        d.print(a); cpu.resume();
        assertEquals(0x80, cpu.a);
        assertTrue(cpu.p.v);

        sys.println("\n===== 0x80 - 0x80");
        a.pb(op.SEC);
        a.pb(op.LDA_IMM).pb(0x80);
        a.pb(op.SBC_IMM).pb(0x80);
        a.brk();

        d.print(a); cpu.resume();


        assertTrue(true);
    }

}
