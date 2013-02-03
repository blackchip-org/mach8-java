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
public class INCTest {

    public INCTest() {
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
        sys.println("\n***** INC *****");
        
        CPUSetup.debug();

        Assembler a = new Assembler();
        Disassembler d = new Disassembler();

        sys.println("\n===== INC, Zero Page");
        a.pb(op.LDA_IMM).pb(0x88);
        a.pb(op.STA_ZP ).pb(0x12);
        a.pb(op.INC_ZP ).pb(0x12);
        a.pb(op.LDX_ZP ).pb(0x12);
        a.brk();
        
        d.print(a); cpu.resume(); 
        assertEquals(0x89, cpu.x);
        assertTrue( cpu.p.n && !cpu.p.z );

        sys.println("\n==== INC, Zero Page, X");
        a.pb(op.LDA_IMM).pb(0x22);
        a.pb(op.STA_ZP ).pb(0x55);
        a.pb(op.LDX_IMM).pb(0x05);
        a.pb(op.INC_ZPX).pb(0x50);
        a.pb(op.LDY_ZP ).pb(0x55);
        a.brk();

        d.print(a); cpu.resume(); 
        assertEquals(0x23, cpu.y);
        assertTrue(!cpu.p.n && !cpu.p.z);

        sys.println("\n===== INC, Absolute");
        a.pb(op.LDA_IMM).pb(0x33);
        a.pb(op.STA_ABS).pw(0x8000);
        a.pb(op.INC_ABS).pw(0x8000);
        a.pb(op.LDX_ABS).pw(0x8000);
        a.brk();

        d.print(a); cpu.resume();
        assertEquals(0x34, cpu.x);

        sys.println("\n===== INC, Absolute X");
        a.pb(op.LDX_IMM).pb(0x03);
        a.pb(op.INC_ABX).pw(0x7ffd);
        a.pb(op.LDY_ABS).pw(0x8000);
        a.brk();

        d.print(a); cpu.resume();
        assertEquals(0x35, cpu.y);

        sys.println("\n===== INC, Wrap around (up)");
        a.pb(op.LDA_IMM).pb(0xff);
        a.pb(op.STA_ABS).pw(0x8002);
        a.pb(op.INC_ABS).pw(0x8002);
        a.pb(op.LDX_ABS).pw(0x8002);
        a.brk();

        d.print(a); cpu.resume();
        assertEquals(0x00, cpu.x);

    }


}