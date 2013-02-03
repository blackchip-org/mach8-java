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
public class ANDTest {

    public ANDTest() {
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
        sys.println("\n***** AND ******");

        CPUSetup.debug();

        Assembler a = new Assembler();
        Disassembler d = new Disassembler();

        sys.println("\n===== AND Immediate, Standard");

        a.pb(op.LDA_IMM).pb(sys.BIT1 | sys.BIT3 | sys.BIT5);
        a.pb(op.AND_IMM).pb(sys.BIT1 | sys.BIT2 | sys.BIT5);
        a.brk();

        d.print(a); cpu.resume();
        assertEquals(sys.BIT1 | sys.BIT5, cpu.a);

        sys.println("\n===== AND Immediate, Z Flag");

        a.pb(op.LDA_IMM).pb(sys.BIT1 | sys.BIT3 | sys.BIT5);
        a.pb(op.AND_IMM).pb(sys.BIT2 | sys.BIT4 | sys.BIT6);
        a.brk();

        d.print(a); cpu.resume();
        assertEquals(0, cpu.a);
        assertTrue(cpu.p.z);

        sys.println("\n===== AND Immediate, N Flag");

        a.pb(op.LDA_IMM).pb(sys.BIT7 | sys.BIT3 | sys.BIT5);
        a.pb(op.AND_IMM).pb(sys.BIT7);
        a.brk();

        d.print(a); cpu.resume();
        assertTrue(cpu.p.n);

        sys.println("\n===== AND, Zero Page");
        a.pb(op.LDY_IMM).pb(sys.BIT1 | sys.BIT3 | sys.BIT5);
        a.pb(op.STY_ZP ).pb(0x44);
        a.pb(op.LDA_IMM).pb(sys.BIT1 | sys.BIT2 | sys.BIT5);
        a.pb(op.AND_ZP ).pb(0x44);
        a.brk();

        d.print(a); cpu.resume();
        assertEquals(sys.BIT1 | sys.BIT5, cpu.a);

        sys.println("\n===== AND, Zero Page, X");
        a.pb(op.LDY_IMM).pb(sys.BIT1 | sys.BIT3 | sys.BIT5);
        a.pb(op.STY_ZP ).pb(0x88);
        a.pb(op.LDX_IMM).pb(0x08);
        a.pb(op.LDA_IMM).pb(sys.BIT1 | sys.BIT2 | sys.BIT5);
        a.pb(op.AND_ZPX).pb(0x80);
        a.brk();

        d.print(a); cpu.resume();
        assertEquals(sys.BIT1 | sys.BIT5, cpu.a);

        sys.println("\n===== AND, Absolute");
        a.pb(op.LDY_IMM).pb(sys.BIT1 | sys.BIT3 | sys.BIT5);
        a.pb(op.STY_ABS).pw(0x8000);
        a.pb(op.LDA_IMM).pb(sys.BIT1 | sys.BIT2 | sys.BIT5);
        a.pb(op.AND_ABS).pw(0x8000);
        a.brk();

        d.print(a); cpu.resume();
        assertEquals(sys.BIT1 | sys.BIT5, cpu.a);

        sys.println("\n===== AND, Absolute, X");
        a.pb(op.LDY_IMM).pb(sys.BIT1 | sys.BIT3 | sys.BIT5);
        a.pb(op.STY_ABS).pw(0x8044);
        a.pb(op.LDA_IMM).pb(sys.BIT1 | sys.BIT2 | sys.BIT5);
        a.pb(op.LDX_IMM).pb(0x44);
        a.pb(op.AND_ABX).pw(0x8000);
        a.brk();

        d.print(a); cpu.resume();
        assertEquals(sys.BIT1 | sys.BIT5, cpu.a);

        sys.println("\n===== AND, Absolute, Y");
        a.pb(op.LDX_IMM).pb(sys.BIT1 | sys.BIT3 | sys.BIT5);
        a.pb(op.STX_ABS).pw(0x8022);
        a.pb(op.LDA_IMM).pb(sys.BIT1 | sys.BIT2 | sys.BIT5);
        a.pb(op.LDY_IMM).pb(0x22);
        a.pb(op.AND_ABY).pw(0x8000);
        a.brk();

        d.print(a); cpu.resume();
        assertEquals(sys.BIT1 | sys.BIT5, cpu.a);

        sys.println("\n===== AND, Indirect X");
        a.pb(op.LDA_IMM).pb(sys.BIT1 | sys.BIT3 | sys.BIT5);
        a.pb(op.STA_ABS).pw(0xee12);
        a.pb(op.LDA_IMM).pb(0x12);
        a.pb(op.STA_ZP ).pb(0xba);
        a.pb(op.LDA_IMM).pb(0xee);
        a.pb(op.STA_ZP ).pb(0xbb);

        a.pb(op.LDX_IMM).pb(0x06);
        a.pb(op.LDA_IMM).pb(sys.BIT1 | sys.BIT2 | sys.BIT5);
        a.pb(op.AND_IZX).pb(0xb4);
        a.brk();

        d.print(a); cpu.resume();
        assertEquals(sys.BIT1 | sys.BIT5, cpu.a);

        sys.println("\n===== AND, Indirect Y");

        a.pb(op.LDA_IMM).pb(sys.BIT1 | sys.BIT3 | sys.BIT5);
        a.pb(op.STA_ABS).pw(0x12f4);
        a.pb(op.LDA_IMM).pb(0xee);
        a.pb(op.STA_ZP ).pb(0xb4);
        a.pb(op.LDA_IMM).pb(0x12);
        a.pb(op.STA_ZP ).pb(0xb5);

        a.pb(op.LDA_IMM).pb(sys.BIT1 | sys.BIT2 | sys.BIT5);
        a.pb(op.LDY_IMM).pb(0x06);
        a.pb(op.AND_IZY).pb(0xb4);
        a.brk();

        d.print(a); cpu.resume();
        assertEquals(sys.BIT1 | sys.BIT5, cpu.a);

    }


}