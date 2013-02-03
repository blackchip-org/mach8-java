/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.blackchip.jv6502.inst;

import org.blackchip.jv6502.CPUSetup;
import org.blackchip.jv6502.cpu;
import org.blackchip.system.map;
import org.blackchip.jv6502.util.Assembler;
import org.blackchip.jv6502.util.Disassembler;
import org.blackchip.jv6502.op;
import org.blackchip.system.sys;
import org.blackchip.jv6502.sysop;
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
public class STYTest {

    public STYTest() {
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
        sys.println("\n***** STY *****");

        CPUSetup.debug();

        Assembler a = new Assembler();
        Disassembler d = new Disassembler();

        sys.println("\n===== STY Zero Page");
        a.pb(op.LDY_IMM).pb(0x44);
        a.pb(op.STY_ZP ).pb(0x73);
        a.pb(op.LDA_ZP ).pb(0x73);
        a.brk();

        d.print(a); cpu.resume();
        assertEquals(0x44, cpu.a);

        sys.println("\n===== STY Zero Page, X");
        a.pb(op.LDY_IMM).pb(0x55);
        a.pb(op.LDX_IMM).pb(0x04);
        a.pb(op.STY_ZPX).pb(0x12);
        a.pb(op.LDA_ABS).pw(0x0016);
        a.brk();

        d.print(a); cpu.resume(); 
        assertEquals(0x55, cpu.a);

        sys.println("\n===== STY Absolute");
        a.pb(op.LDY_IMM).pb(0x66);
        a.pb(op.STY_ABS).pw(0x1234);
        a.pb(op.LDA_ABS).pw(0x1234);
        a.brk();

        d.print(a); cpu.resume(); 
        assertEquals(0x66, cpu.a);

    }

}