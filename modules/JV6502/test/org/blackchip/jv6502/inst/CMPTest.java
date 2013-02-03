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
public class CMPTest {

    public CMPTest() {
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
        sys.println("\n***** CMP *****");

        CPUSetup.debug();

        Assembler a = new Assembler();
        Disassembler d = new Disassembler();

        sys.println("\n===== CMP, Immediate");
        a.pb(op.LDA_IMM).pb(0x22);
        a.pb(op.CMP_IMM).pb(0x22);
        a.brk();

        d.print(a); cpu.resume();
        assertTrue( cpu.p.z &&  cpu.p.c && !cpu.p.n);

        sys.nl();
        a.pb(op.LDA_IMM).pb(0x23);
        a.pb(op.CMP_IMM).pb(0x22);
        a.brk();

        d.print(a); cpu.resume();
        assertTrue(!cpu.p.z &&  cpu.p.c && !cpu.p.n);

        sys.nl();
        a.pb(op.LDA_IMM).pb(0xaa);
        a.pb(op.CMP_IMM).pb(0xbb);
        a.brk();

        d.print(a); cpu.resume();
        assertTrue(!cpu.p.z && !cpu.p.c && cpu.p.n);

        sys.println("\n===== CMP, Zero Page");
        a.pb(op.LDA_IMM).pb(0x22);
        a.pb(op.STA_ZP ).pb(0xdd);
        a.pb(op.CMP_ZP ).pb(0xdd);
        a.brk();

        d.print(a); cpu.resume();
        assertTrue( cpu.p.z &&  cpu.p.c && !cpu.p.n);

        sys.println("\n===== CMP, Zero Page X");
        a.pb(op.LDA_IMM).pb(0x33);
        a.pb(op.STA_ZP ).pb(0xc5);
        a.pb(op.LDX_IMM).pb(0x05);
        a.pb(op.CMP_ZPX).pb(0xc0);
        a.brk();

        d.print(a); cpu.resume();
        assertTrue( cpu.p.z &&  cpu.p.c && !cpu.p.n);

        sys.println("\n===== CMP, Absolute");
        a.pb(op.LDA_IMM).pb(0x33);
        a.pb(op.STA_ABS).pw(0x8111);
        a.pb(op.CMP_ABS).pw(0x8111);
        a.brk();

        d.print(a); cpu.resume();
        assertTrue( cpu.p.z &&  cpu.p.c && !cpu.p.n);

        sys.println("\n===== CMP, Absolute X");
        a.pb(op.LDA_IMM).pb(0x44);
        a.pb(op.STA_ABS).pw(0x8005);
        a.pb(op.LDX_IMM).pb(0x05);
        a.pb(op.CMP_ABX).pw(0x8000);
        a.brk();

        d.print(a); cpu.resume();
        assertTrue( cpu.p.z &&  cpu.p.c && !cpu.p.n);

        sys.println("\n===== CMP, Absolute Y");
        a.pb(op.LDA_IMM).pb(0x55);
        a.pb(op.STA_ABS).pw(0x9005);
        a.pb(op.LDY_IMM).pb(0x05);
        a.pb(op.CMP_ABX).pw(0x9000);
        a.brk();

        d.print(a); cpu.resume();
        assertTrue( cpu.p.z &&  cpu.p.c && !cpu.p.n);

        sys.println("\n===== CMP, Indirect X");
        a.pb(op.LDA_IMM).pb(0x55);
        a.pb(op.STA_ABS).pw(0xee12);
        a.pb(op.LDA_IMM).pb(0x12);
        a.pb(op.STA_ZP ).pb(0xba);
        a.pb(op.LDA_IMM).pb(0xee);
        a.pb(op.STA_ZP ).pb(0xbb);

        a.pb(op.LDX_IMM).pb(0x06);
        a.pb(op.LDA_IMM).pb(0x55);
        a.pb(op.CMP_IZX).pb(0xb4);
        a.brk();

        d.print(a); cpu.resume(); 
        assertTrue( cpu.p.z &&  cpu.p.c && !cpu.p.n);


        sys.println("\n===== CMP, Indirect Y");
        
        a.pb(op.LDA_IMM).pb(0xee);
        a.pb(op.STA_ZP ).pb(0xb4);
        a.pb(op.LDA_IMM).pb(0x12);
        a.pb(op.STA_ZP ).pb(0xb5);
        a.pb(op.LDA_IMM).pb(0x66);
        a.pb(op.STA_ABS).pw(0x12f4);
        
        a.pb(op.LDA_IMM).pb(0x66);
        a.pb(op.LDY_IMM).pb(0x06);
        a.pb(op.CMP_IZY).pb(0xb4);
        a.brk();
        
        d.print(a); cpu.resume(); 
        assertEquals(0x66, cpu.a);
        
    }

}