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
public class BITTest {

    public BITTest() {
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
        sys.println("\n***** BIT *****");

        CPUSetup.debug();

        Assembler a = new Assembler();
        Disassembler d = new Disassembler();

        sys.println("\n===== BIT, Zero Page, match");
        a.pb(op.LDA_IMM).pb(sys.BIT2 | sys.BIT3);
        a.pb(op.STA_ZP ).pb(0x33);
        a.pb(op.LDA_IMM).pb(sys.BIT2);
        a.pb(op.BIT_ZP ).pb(0x33);
        a.brk();

        d.print(a); cpu.resume();
        assertTrue(!cpu.p.z);

        sys.println("\n===== BIT, Zero Page, no match");
        a.pb(op.LDA_IMM).pb(sys.BIT1);
        a.pb(op.BIT_ZP ).pb(0x33);
        a.brk();

        d.print(a); cpu.resume();
        assertTrue(cpu.p.z);

        sys.println("\n===== BIT, Zero Page, N Flag");
        a.pb(op.LDA_IMM).pb(sys.BIT7);
        a.pb(op.STA_ZP ).pb(0x33);
        a.pb(op.LDA_IMM).pb(sys.BIT1);
        a.pb(op.BIT_ZP ).pb(0x33);
        a.brk();

        d.print(a); cpu.resume();
        assertTrue(cpu.p.z && cpu.p.n);

        sys.println("\n===== BIT, Zero Page, V Flag");
        a.pb(op.LDA_IMM).pb(sys.BIT6);
        a.pb(op.STA_ZP ).pb(0x33);
        a.pb(op.LDA_IMM).pb(0);
        a.pb(op.BIT_ZP ).pb(0x33);
        a.brk();

        d.print(a); cpu.resume();
        assertTrue(cpu.p.z && cpu.p.v);

        sys.println("\n===== BIT, Absolute");
        a.pb(op.LDA_IMM).pb(sys.BIT2 | sys.BIT3);
        a.pb(op.STA_ABS).pw(0x3344);
        a.pb(op.LDA_IMM).pb(sys.BIT2);
        a.pb(op.BIT_ABS).pw(0x3344);
        a.brk();

        d.print(a); cpu.resume();
        assertTrue(!cpu.p.z);

        assertTrue(true);
    }


}