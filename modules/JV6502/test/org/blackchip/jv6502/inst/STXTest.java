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
public class STXTest {

    public STXTest() {
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
        sys.println("\n***** STX *****");

        CPUSetup.debug();

        Assembler a = new Assembler();
        Disassembler d = new Disassembler();

        sys.println("\n===== STX Zero Page");
        a.pb(op.LDX_IMM).pb(0x44);
        a.pb(op.STX_ZP ).pb(0x73);
        a.pb(op.LDA_ZP ).pb(0x73);
        a.brk();

        d.print(a); cpu.resume();
        assertEquals(0x44, cpu.a);

        sys.println("\n===== STX Zero Page, Y");
        a.pb(op.LDX_IMM).pb(0x55);
        a.pb(op.LDY_IMM).pb(0x04);
        a.pb(op.STX_ZPY).pb(0x12);
        a.pb(op.LDA_ABS).pw(0x0016);
        a.brk();

        d.print(a); cpu.resume(); 
        assertEquals(0x55, cpu.a);

        sys.println("\n===== STX Absolute");
        a.pb(op.LDX_IMM).pb(0x66);
        a.pb(op.STX_ABS).pw(0x1234);
        a.pb(op.LDA_ABS).pw(0x1234);
        a.brk();

        d.print(a); cpu.resume(); 
        assertEquals(0x66, cpu.a);
        
    }

}