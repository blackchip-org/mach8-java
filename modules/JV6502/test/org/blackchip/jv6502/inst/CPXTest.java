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
public class CPXTest {

    public CPXTest() {
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
        sys.println("\n***** CPX *****");

        CPUSetup.debug();

        Assembler a = new Assembler();
        Disassembler d = new Disassembler();

        sys.println("\n===== CPX, Immediate");
        a.pb(op.LDX_IMM).pb(0x22);
        a.pb(op.CPX_IMM).pb(0x22);
        a.brk();

        d.print(a); cpu.resume();
        assertTrue( cpu.p.z &&  cpu.p.c && !cpu.p.n);

        sys.nl();
        a.pb(op.LDX_IMM).pb(0x23);
        a.pb(op.CPX_IMM).pb(0x22);
        a.brk();

        d.print(a); cpu.resume();
        assertTrue(!cpu.p.z &&  cpu.p.c && !cpu.p.n);

        sys.nl();
        a.pb(op.LDX_IMM).pb(0xaa);
        a.pb(op.CPX_IMM).pb(0xbb);
        a.brk();

        d.print(a); cpu.resume();
        assertTrue(!cpu.p.z && !cpu.p.c && cpu.p.n);

        sys.println("\n===== CPX, Zero Page");
        a.pb(op.LDX_IMM).pb(0x22);
        a.pb(op.STX_ZP ).pb(0xdd);
        a.pb(op.CPX_ZP ).pb(0xdd);
        a.brk();

        d.print(a); cpu.resume();
        assertTrue( cpu.p.z &&  cpu.p.c && !cpu.p.n);

        sys.println("\n===== CPX, Absolute");
        a.pb(op.LDX_IMM).pb(0x33);
        a.pb(op.STX_ABS).pw(0x8111);
        a.pb(op.CPX_ABS).pw(0x8111);
        a.brk();

        d.print(a); cpu.resume();
        assertTrue( cpu.p.z &&  cpu.p.c && !cpu.p.n);
    }
}