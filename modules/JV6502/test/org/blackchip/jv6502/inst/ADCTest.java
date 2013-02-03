/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.blackchip.jv6502.inst;

import org.blackchip.jv6502.CPUSetup;
import org.blackchip.jv6502.cpu;
import org.blackchip.system.map;
import org.blackchip.jv6502.mmu;
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
public class ADCTest {

    public ADCTest() {
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
        sys.println("\n***** ADC *****");

        CPUSetup.debug();
        
        Assembler a = new Assembler();
        Disassembler d = new Disassembler();

        sys.println("\n===== ADC standard");
        a.pb(op.LDA_IMM).pb(0x40);
        a.pb(op.CLC    );
        a.pb(op.ADC_IMM).pb(0x05);
        a.brk();

        d.print(a); cpu.resume(); 
        assertEquals(0x45, cpu.a);
        assertTrue( !cpu.p.n && !cpu.p.v && !cpu.p.z && !cpu.p.c );

        sys.println("\n===== ADC, Overflow set?");
        a.pb(op.CLC    );
        a.pb(op.LDA_IMM).pb(0x7f);
        a.pb(op.ADC_IMM).pb(0x01);
        a.brk();

        d.print(a); cpu.resume();
        assertTrue(cpu.p.v);

        sys.println("\n===== ADC, Overflow clear?");
        a.pb(op.CLC    );
        a.pb(op.LDA_IMM).pb(0x81);
        a.pb(op.ADC_IMM).pb(0xff);
        a.brk();
        
        d.print(a); cpu.resume();
        assertTrue(!cpu.p.v);
        
        sys.println("\n===== ADC, check N, V flags");
        a.pb(op.CLC    );
        a.pb(op.LDA_IMM).pb(0x45);
        a.pb(op.ADC_IMM).pb(0x50);
        a.brk();

        d.print(a); cpu.resume();
        assertEquals(0x95, cpu.a);
        assertTrue(  cpu.p.n &&  cpu.p.v && !cpu.p.z && !cpu.p.c );

        sys.println("\n==== ADC, check clear of V flag");
        a.pb(op.ADC_IMM).pb(0x0b);
        a.brk();

        d.print(a); cpu.resume(); 
        assertEquals(0xa0, cpu.a);
        assertTrue(  cpu.p.n && !cpu.p.v && !cpu.p.z && !cpu.p.c );

        sys.println("\n===== ADC, check wrap around and Z, V, C flags");
        a.pb(op.ADC_IMM).pb(0x60);
        a.brk();

        d.print(a); cpu.resume(); 
        assertEquals(0, cpu.a);
        assertTrue( !cpu.p.n &&  cpu.p.v &&  cpu.p.z &&  cpu.p.c );

        sys.println("\n===== ADC, check carry on");
        a.pb(op.ADC_IMM).pb(0x01);
        a.brk();
        
        d.print(a); cpu.resume(); 
        assertEquals(2, cpu.a); // Carry set
        assertTrue( !cpu.p.n && !cpu.p.v && !cpu.p.z && !cpu.p.c );

        sys.println("\n===== ADC, check carry off");
        a.pb(op.ADC_IMM).pb(0x01);
        a.brk();

        d.print(a); cpu.resume(); 
        assertEquals(3, cpu.a); // Carry not set

        sys.println("\n===== ADC, check 16-bit addition");
        a.pb(op.LDA_IMM).pb(0x12);
        a.pb(op.STA_ZP ).pb(0xa0);
        a.pb(op.LDA_IMM).pb(0x23);
        a.pb(op.STA_ZP ).pb(0xa1);
        a.pb(op.LDA_IMM).pb(0x22);
        a.pb(op.STA_ZP ).pb(0xa2);
        a.pb(op.LDA_IMM).pb(0x33);
        a.pb(op.STA_ZP ).pb(0xa3);

        a.pb(op.CLC);
        a.pb(op.LDA_ZP ).pb(0xa0);
        a.pb(op.ADC_ZP ).pb(0xa2);
        a.pb(op.STA_ZP ).pb(0xa4);
        a.pb(op.LDA_ZP ).pb(0xa1);
        a.pb(op.ADC_ZP ).pb(0xa3);
        a.pb(op.STA_ZP ).pb(0xa5);

        a.pb(op.LDX_ZP ).pb(0xa4);
        a.pb(op.LDY_ZP ).pb(0xa5);

        a.brk();

        d.print(a); cpu.resume();
        sys.nl(); mmu.print(0xa0, 0xa5);
        assertEquals(0x34, cpu.x);
        assertEquals(0x56, cpu.y);

        sys.println("\n===== ADC, check BCD");
        a.pb(op.SED);
        a.pb(op.LDA_IMM).pb(0x32);
        a.pb(op.STA_ABS).pw(0x8000);
        a.pb(op.CLC);
        a.pb(op.LDA_IMM).pb(0x58);
        a.pb(op.ADC_ABS).pw(0x8000);
        a.brk();

        d.print(a); cpu.resume(); 
        assertEquals(0x90, cpu.a);

        sys.println("\n===== ADC, check BCD overflow");
        a.pb(op.LDX_IMM).pb(0x15);
        a.pb(op.STX_ABS).pw(0x8000);
        a.pb(op.ADC_ABS).pw(0x8000);
        a.brk();

        d.print(a); cpu.resume(); 
        assertEquals(0x05, cpu.a);
        assertTrue(cpu.p.v);
        
        // Clear BCD Mode
        a.pb(op.CLD);

        sys.println("\n===== ADC, Zero Page, X");
        a.pb(op.LDA_IMM).pb(0);
        a.pb(op.LDX_IMM).pb(4);
        a.pb(op.CLC);
        a.pb(op.ADC_ZPX).pb(0xa0);
        a.brk();

        d.print(a); cpu.resume(); 
        assertEquals(0x34, cpu.a);

        sys.println("\n===== ADC, Absolute");
        a.pb(op.LDA_IMM).pb(0);
        a.pb(op.CLC);
        a.pb(op.ADC_ABS).pw(0x00a0);
        a.brk();

        d.print(a); cpu.resume(); 
        assertEquals(0x12, cpu.a);

        sys.println("\n===== ADC, Absolute X");
        a.pb(op.LDA_IMM).pb(0);
        a.pb(op.LDX_IMM).pb(4);
        a.pb(op.CLC);
        a.pb(op.ADC_ABX).pw(0x00a0);
        a.brk();

        d.print(a); cpu.resume(); 
        assertEquals(0x34, cpu.a);

        sys.println("\n===== ADC, Absolute Y");
        a.pb(op.LDA_IMM).pb(0);
        a.pb(op.LDY_IMM).pb(5);
        a.pb(op.CLC);
        a.pb(op.ADC_ABY).pw(0x00a0);
        a.brk();
        
        d.print(a); cpu.resume(); 
        assertEquals(0x56, cpu.a);

        sys.println("\n===== ADC, Indirect X");
        a.pb(op.LDA_IMM).pb(0x55);
        a.pb(op.STA_ABS).pw(0xee12);
        a.pb(op.LDA_IMM).pb(0x12);
        a.pb(op.STA_ZP ).pb(0xba);
        a.pb(op.LDA_IMM).pb(0xee);
        a.pb(op.STA_ZP ).pb(0xbb);

        a.pb(op.LDX_IMM).pb(0x06);
        a.pb(op.LDA_IMM).pb(0x03);
        a.pb(op.CLC);
        a.pb(op.ADC_IZX).pb(0xb4);
        a.brk();

        d.print(a); cpu.resume(); 
        assertEquals(0x58, cpu.a);

        sys.println("\n===== ADC, Indirect Y");
        
        a.pb(op.LDA_IMM).pb(0xee);
        a.pb(op.STA_ZP ).pb(0xb4);
        a.pb(op.LDA_IMM).pb(0x12);
        a.pb(op.STA_ZP ).pb(0xb5);
        a.pb(op.LDA_IMM).pb(0x66);
        a.pb(op.STA_ABS).pw(0x12f4);
        
        a.pb(op.LDA_IMM).pb(0x03);
        a.pb(op.LDY_IMM).pb(0x06);
        a.pb(op.CLC);
        a.pb(op.ADC_IZY).pb(0xb4);
        a.brk();
        
        d.print(a); cpu.resume(); 
        assertEquals(0x69, cpu.a);
     
    }


}