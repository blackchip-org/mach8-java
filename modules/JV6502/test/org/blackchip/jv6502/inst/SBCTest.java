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
public class SBCTest {

    public SBCTest() {
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
        sys.println("\n***** SBC *****");

        CPUSetup.debug();
        
        Assembler a = new Assembler();
        Disassembler d = new Disassembler();

        sys.println("\n===== SBC standard");
        a.pb(op.LDA_IMM).pb(0x45);
        a.pb(op.SEC   );
        a.pb(op.SBC_IMM).pb(0x05);
        a.brk();

        d.print(a); cpu.resume(); 
        assertEquals(0x40, cpu.a);
        assertTrue( !cpu.p.n && !cpu.p.v && !cpu.p.z && cpu.p.c );

        sys.println("\n===== SBC, Overflow set?");
        a.pb(op.SEC    );
        a.pb(op.LDA_IMM).pb(0x80);
        a.pb(op.SBC_IMM).pb(0x01);
        a.brk();

        d.print(a); cpu.resume();
        assertTrue(cpu.p.v);

        sys.println("\n===== SBC, Overflow clear?");
        a.pb(op.SEC    );
        a.pb(op.LDA_IMM).pb(0x81);
        a.pb(op.SBC_IMM).pb(0xff);
        a.brk();
        
        d.print(a); cpu.resume();
        assertTrue(!cpu.p.v);
        
        sys.println("\n===== SBC, check N, V flags");
        a.pb(op.SEC    );
        a.pb(op.LDA_IMM).pb(0x75);
        a.pb(op.SBC_IMM).pb(0x85);
        a.brk();

        d.print(a); cpu.resume();
        assertEquals(0xf0, cpu.a);
        assertTrue(cpu.p.n &&  cpu.p.v && !cpu.p.z && !cpu.p.c );

        sys.println("\n==== SBC, check clear of V flag");
        a.pb(op.SEC    );
        a.pb(op.LDA_IMM).pb(0xf0);
        a.pb(op.SBC_IMM).pb(0x10);
        a.brk();

        d.print(a); cpu.resume(); 
        assertEquals(0xe0, cpu.a);
        assertTrue( cpu.p.n && !cpu.p.v && !cpu.p.z && cpu.p.c );

        sys.println("\n===== SBC, check carry off");
        a.pb(op.SEC    );
        a.pb(op.LDA_IMM).pb(0x00);
        a.pb(op.SBC_IMM).pb(0x01);
        a.brk();
        
        d.print(a); cpu.resume(); 
        assertEquals(0xff, cpu.a); // Carry clear
        assertTrue(cpu.p.n && !cpu.p.v && !cpu.p.z && !cpu.p.c );

        sys.println("\n===== SBC, check carry on");
        a.pb(op.SBC_IMM).pb(0x01);
        a.brk();

        d.print(a); cpu.resume(); 
        assertEquals(0xfd, cpu.a); // Carry set

        sys.println("\n===== SBC, check 16-bit subtraction");
        a.pb(op.LDA_IMM).pb(0x22);
        a.pb(op.STA_ZP ).pb(0xa0);
        a.pb(op.LDA_IMM).pb(0x33);
        a.pb(op.STA_ZP ).pb(0xa1);
        a.pb(op.LDA_IMM).pb(0x12);
        a.pb(op.STA_ZP ).pb(0xa2);
        a.pb(op.LDA_IMM).pb(0x10);
        a.pb(op.STA_ZP ).pb(0xa3);

        a.pb(op.SEC    );
        a.pb(op.LDA_ZP ).pb(0xa0);
        a.pb(op.SBC_ZP ).pb(0xa2);
        a.pb(op.STA_ZP ).pb(0xa4);
        a.pb(op.LDA_ZP ).pb(0xa1);
        a.pb(op.SBC_ZP ).pb(0xa3);
        a.pb(op.STA_ZP ).pb(0xa5);

        a.pb(op.LDX_ZP ).pb(0xa4);
        a.pb(op.LDY_ZP ).pb(0xa5);

        a.brk();

        d.print(a); cpu.resume();
        sys.nl(); mmu.print(0xa0, 0xa5);
        assertEquals(0x10, cpu.x);
        assertEquals(0x23, cpu.y);

        sys.println("\n===== SBC, check BCD");
        a.pb(op.SED);
        a.pb(op.SEC);
        a.pb(op.LDA_IMM).pb(0x90);
        a.pb(op.SBC_IMM).pw(0x01);
        a.brk();

        d.print(a); cpu.resume(); 
        assertEquals(0x89, cpu.a);

        sys.println("\n===== SBC, check BCD overflow");
        a.pb(op.SED);
        a.pb(op.LDA_IMM).pb(0x01);
        a.pb(op.SBC_IMM).pb(0x02);
        a.brk();

        d.print(a); cpu.resume(); 
        assertEquals(0x99, cpu.a);
        assertTrue(cpu.p.v);
        
        // Clear BCD Mode
        a.pb(op.CLD);

        sys.println("\n===== SBC, Zero Page, X");
        a.pb(op.LDA_IMM).pb(0x04);
        a.pb(op.STA_ZP ).pb(0x15);

        a.pb(op.LDA_IMM).pb(0x38);
        a.pb(op.LDX_IMM).pb(0x05);
        a.pb(op.SEC);
        a.pb(op.SBC_ZPX).pb(0x10);
        a.brk();

        d.print(a); cpu.resume(); 
        assertEquals(0x34, cpu.a);

        sys.println("\n===== SBC, Absolute");
        a.pb(op.LDA_IMM).pb(0x06);
        a.pb(op.STA_ABS).pw(0x8000);
        a.pb(op.SEC);
        a.pb(op.LDA_IMM).pb(0x77);
        a.pb(op.SBC_ABS).pw(0x8000);
        a.brk();

        d.print(a); cpu.resume(); 
        assertEquals(0x71, cpu.a);

        sys.println("\n===== SBC, Absolute X");
        a.pb(op.LDA_IMM).pb(0x02);
        a.pb(op.STA_ABS).pw(0x8033);
        a.pb(op.LDA_IMM).pb(0x80);
        a.pb(op.LDX_IMM).pb(0x33);
        a.pb(op.SEC);
        a.pb(op.SBC_ABX).pw(0x8000);
        a.brk();

        d.print(a); cpu.resume(); 
        assertEquals(0x7e, cpu.a);

        sys.println("\n===== SBC, Absolute Y");
        a.pb(op.LDA_IMM).pb(0x03);
        a.pb(op.STA_ABS).pw(0x8044);
        a.pb(op.LDA_IMM).pb(0x80);
        a.pb(op.LDY_IMM).pb(0x44);
        a.pb(op.SEC);
        a.pb(op.SBC_ABY).pw(0x8000);
        a.brk();

        d.print(a); cpu.resume();
        assertEquals(0x7d, cpu.a);

        sys.println("\n===== SBC, Indirect X");
        a.pb(op.SEC    );
        a.pb(op.LDA_IMM).pb(0x03);
        a.pb(op.STA_ABS).pw(0xee12);
        a.pb(op.LDA_IMM).pb(0x12);
        a.pb(op.STA_ZP ).pb(0xba);
        a.pb(op.LDA_IMM).pb(0xee);
        a.pb(op.STA_ZP ).pb(0xbb);

        a.pb(op.LDX_IMM).pb(0x06);
        a.pb(op.LDA_IMM).pb(0x55);
        a.pb(op.SBC_IZX).pb(0xb4);
        a.brk();

        d.print(a); cpu.resume(); 
        assertEquals(0x52, cpu.a);

        sys.println("\n===== SBC, Indirect Y");
        
        a.pb(op.LDA_IMM).pb(0xee);
        a.pb(op.STA_ZP ).pb(0xb4);
        a.pb(op.LDA_IMM).pb(0x12);
        a.pb(op.STA_ZP ).pb(0xb5);
        a.pb(op.LDA_IMM).pb(0x03);
        a.pb(op.STA_ABS).pw(0x12f4);
        
        a.pb(op.LDA_IMM).pb(0x66);
        a.pb(op.LDY_IMM).pb(0x06);
        a.pb(op.SEC);
        a.pb(op.SBC_IZY).pb(0xb4);
        a.brk();
        
        d.print(a); cpu.resume(); 
        assertEquals(0x63, cpu.a);
     
    }


}