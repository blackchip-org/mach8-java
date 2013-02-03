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
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mcgann
 */
public class LSRTest {

    public LSRTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void test()
    {
        sys.println("\n***** LSR *****");

        CPUSetup.debug();

        Assembler a = new Assembler();
        Disassembler d = new Disassembler();

        sys.println("\n===== LSR, Accumulator");
        a.pb(op.LDA_IMM).pb(2);
        a.pb(op.LSR_ACC);
        a.brk();

        d.print(a); cpu.resume();
        assertEquals(1, cpu.a);
        assertTrue(!cpu.p.z && !cpu.p.n && !cpu.p.c);

        sys.println("\n===== LSR, Shift out");
        a.pb(op.LSR_ACC);
        a.brk();

        d.print(a); cpu.resume();
        assertEquals(0, cpu.a);
        assertTrue(cpu.p.z && !cpu.p.n && cpu.p.c);

        sys.println("\n===== LSR, Zero Page");
        a.pb(op.CLC);
        a.pb(op.LDA_IMM).pb(8);
        a.pb(op.STA_ZP ).pb(0x44);
        a.pb(op.LSR_ZP ).pb(0x44);
        a.pb(op.LDA_ZP ).pb(0x44);
        a.brk();

        d.print(a); cpu.resume();
        assertEquals(4, cpu.a);

        sys.println("\n===== LSR, Zero Page, X");
        a.pb(op.LDA_IMM).pb(16);
        a.pb(op.STA_ZP ).pb(0x88);
        a.pb(op.LDX_IMM).pb(0x08);
        a.pb(op.LSR_ZPX).pb(0x80);
        a.pb(op.LDA_ZP ).pb(0x88);
        a.brk();

        d.print(a); cpu.resume();
        assertEquals(8, cpu.a);

        sys.println("\n===== LSR, Absolute");
        a.pb(op.LDA_IMM).pb(4);
        a.pb(op.STA_ABS).pw(0x6000);
        a.pb(op.LSR_ABS).pw(0x6000);
        a.pb(op.LDA_ABS).pw(0x6000);
        a.brk();

        d.print(a); cpu.resume();
        assertEquals(2, cpu.a);

        sys.println("\n===== LSR, Absolute, X");
        a.pb(op.LDA_IMM).pb(2);
        a.pb(op.STA_ABS).pw(0x6040);
        a.pb(op.LDX_IMM).pb(0x40);
        a.pb(op.LSR_ABX).pw(0x6000);
        a.pb(op.LDA_ABS).pw(0x6040);
        a.brk();

        d.print(a); cpu.resume();
        assertEquals(1, cpu.a);

        assertTrue(true);
    }

}