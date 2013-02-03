/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.blackchip.jv6502.inst;

import org.blackchip.system.map;
import org.blackchip.jv6502.op;
import org.blackchip.jv6502.CPUSetup;
import org.blackchip.jv6502.sysop;
import org.blackchip.jv6502.cpu;
import org.blackchip.jv6502.util.Assembler;
import org.blackchip.jv6502.util.Disassembler;
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
public class STATest {

    public STATest() {
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
        sys.println("\n***** STA *****");

        CPUSetup.debug();

        Assembler a = new Assembler();
        Disassembler d = new Disassembler();

        sys.println("\n===== STA Zero Page");
        a.pb(op.LDA_IMM).pb(0x44);
        a.pb(op.STA_ZP ).pb(0x73);
        a.pb(op.LDX_ZP ).pb(0x73);
        a.brk();

        d.print(a); cpu.resume();
        assertEquals(0x44, cpu.x);

        sys.println("\n===== STA Zero Page, X");
        a.pb(op.LDA_IMM).pb(0x55);
        a.pb(op.LDX_IMM).pb(0x04);
        a.pb(op.STA_ZPX).pb(0x12);
        a.pb(op.LDY_ABS).pw(0x0016);
        a.brk();

        d.print(a); cpu.resume(); 
        assertEquals(0x55, cpu.y);

        sys.println("\n===== STA Absolute");
        a.pb(op.LDA_IMM).pb(0x66);
        a.pb(op.STA_ABS).pw(0x1234);
        a.pb(op.LDX_ABS).pw(0x1234);
        a.brk();

        d.print(a); cpu.resume(); 
        assertEquals(0x66, cpu.x);

        sys.println("\n===== STA Absolute, X");
        a.pb(op.LDA_IMM).pb(0xAA);
        a.pb(op.LDX_IMM).pb(0x40);
        a.pb(op.STA_ABX).pw(0x2000);
        a.pb(op.LDY_ABS).pw(0x2040);
        a.brk();

        d.print(a); cpu.resume(); 
        assertEquals(0xaa, cpu.y);

        sys.println("\n===== STA Absolute, Y");
        a.pb(op.LDA_IMM).pb(0xBB);
        a.pb(op.LDY_IMM).pb(0x30);
        a.pb(op.STA_ABY).pw(0x2000);
        a.pb(op.LDX_ABS).pw(0x2030);
        a.brk();

        d.print(a); cpu.resume(); 
        assertEquals(0xbb, cpu.x);

        sys.println("\n===== STA Indirect X");
        a.pb(op.LDA_IMM).pb(0x12);
        a.pb(op.STA_ZP ).pb(0xBA);
        a.pb(op.LDA_IMM).pb(0xEE);
        a.pb(op.STA_ZP ).pb(0xBB);
        a.pb(op.LDA_IMM).pb(0x88);
        a.pb(op.LDX_IMM).pb(0x06);
        a.pb(op.STA_IZX).pb(0xB4);
        a.pb(op.LDY_ABS).pw(0xEE12);
        a.brk();

        d.print(a); cpu.resume(); 
        assertEquals(0x88, cpu.y);

        sys.println("\n===== STA Indirect Y");
        a.pb(op.LDA_IMM).pb(0xEE);
        a.pb(op.STA_ZP ).pb(0xB4);
        a.pb(op.LDA_IMM).pb(0x12);
        a.pb(op.STA_ZP ).pb(0xB5);
        a.pb(op.LDY_IMM).pb(0x06);
        a.pb(op.LDA_IMM).pb(0xCC);
        a.pb(op.STA_IZY).pb(0xB4);
        a.pb(op.LDX_ABS).pw(0x12F4);
        a.brk();

        d.print(a); cpu.resume(); 
        assertEquals(0xCC, cpu.x);
    }

}