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
public class StackTest {

    public StackTest() {
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
        sys.println("\n***** Stack operations *****");

        CPUSetup.debug();
        cpu.p.x = false;
        Assembler a = new Assembler();
        Disassembler d = new Disassembler();
        
        sys.println("\n==== PLA, Zero");

        a.pb(op.LDA_IMM).pb(0x00);
        a.pb(op.PHA);
        a.pb(op.LDA_IMM).pb(0x22);
        a.pb(op.PLA);
        a.brk();
        
        d.print(a); cpu.resume();
        sys.nl(); mmu.print(map.STACK_TOP - 7, map.STACK_TOP);
        assertEquals(0, cpu.a);
        assertTrue(cpu.p.z && !cpu.p.n);
        
        sys.println("\n==== PLA, Negative");

        a.pb(op.LDA_IMM).pb(0x81);
        a.pb(op.PHA);
        a.pb(op.LDA_IMM).pb(0x00);
        a.pb(op.PLA);
        a.brk();

        d.print(a); cpu.resume();
        sys.nl(); mmu.print(map.STACK_TOP - 7, map.STACK_TOP);
        assertEquals(0x81, cpu.a);
        assertTrue(!cpu.p.z && cpu.p.n);

        sys.println("\n==== PLX, Zero");

        a.pb(op.LDX_IMM).pb(0x00);
        a.pb(op.PHX);
        a.pb(op.LDX_IMM).pb(0x22);
        a.pb(op.PLX);
        a.brk();

        d.print(a); cpu.resume();
        sys.nl(); mmu.print(map.STACK_TOP - 7, map.STACK_TOP);
        assertEquals(0, cpu.x);
        assertTrue(cpu.p.z && !cpu.p.n);

        sys.println("\n==== PLX, Negative");

        a.pb(op.LDX_IMM).pb(0x81);
        a.pb(op.PHX);
        a.pb(op.LDX_IMM).pb(0x00);
        a.pb(op.PLX);
        a.brk();

        d.print(a); cpu.resume();
        sys.nl(); mmu.print(map.STACK_TOP - 7, map.STACK_TOP);
        assertEquals(0x81, cpu.x);
        assertTrue(!cpu.p.z && cpu.p.n);

        sys.println("\n==== PLY, Zero");

        a.pb(op.LDY_IMM).pb(0x00);
        a.pb(op.PHY);
        a.pb(op.LDY_IMM).pb(0x22);
        a.pb(op.PLY);
        a.brk();

        d.print(a); cpu.resume();
        sys.nl(); mmu.print(map.STACK_TOP - 7, map.STACK_TOP);
        assertEquals(0, cpu.y);
        assertTrue(cpu.p.z && !cpu.p.n);

        sys.println("\n==== PLY, Negative");

        a.pb(op.LDY_IMM).pb(0x81);
        a.pb(op.PHY);
        a.pb(op.LDY_IMM).pb(0x00);
        a.pb(op.PLY);
        a.brk();

        d.print(a); cpu.resume();
        sys.nl(); mmu.print(map.STACK_TOP - 7, map.STACK_TOP);
        assertEquals(0x81, cpu.y);
        assertTrue(!cpu.p.z && cpu.p.n);

        sys.println("\n==== PHA");

        a.pb(op.LDA_IMM).pb(0x44);
        a.pb(op.PHA    );
        a.pb(op.LDX_ABS).pw(map.STACK_TOP);
        a.brk();
        
        d.print(a); cpu.resume();
        sys.nl(); mmu.print(map.STACK_TOP - 7, map.STACK_TOP);
        assertEquals(0x44, cpu.x);
        
        sys.println("\n==== PHX");

        a.pb(op.LDX_IMM).pb(0x55);
        a.pb(op.PHX    );
        a.pb(op.LDA_ABS).pw(map.STACK_TOP - 1);
        a.brk();
        
        d.print(a); cpu.resume();
        sys.nl(); mmu.print(map.STACK_TOP - 7, map.STACK_TOP);
        assertEquals(0x55, cpu.a);
        
        sys.println("\n==== PHY");

        a.pb(op.LDY_IMM).pb(0x66);
        a.pb(op.PHY    );
        a.pb(op.LDA_ABS).pw(map.STACK_TOP - 2);
        a.brk();
        
        d.print(a); cpu.resume();
        sys.nl(); mmu.print(map.STACK_TOP - 7, map.STACK_TOP);
        assertEquals(0x66, cpu.a);
 
        sys.println("\n==== PLY");

        a.pb(op.PLY);
        a.brk();

        d.print(a); cpu.resume();
        assertEquals(0x66, cpu.y);
        
        sys.println("\n==== PLX");

        a.pb(op.PLX);
        a.brk();

        d.print(a); cpu.resume();
        assertEquals(0x55, cpu.x);
        
        sys.println("\n==== PLA");

        a.pb(op.PLA);
        a.brk();

        d.print(a); cpu.resume();
        assertEquals(0x44, cpu.a);

        sys.println("\n==== PHP");

        a.pb(op.LDA_IMM).pb(64);
        a.pb(op.JSC    ).pb(sysop.STPROC);
        a.pb(op.PHP    );
        a.pb(op.CMP_ABS).pw(map.STACK_TOP);
        a.brk();

        d.print(a); cpu.resume();
        assertTrue(cpu.p.z);

        sys.println("\n===== PLP");

        a.pb(op.PLP);
        a.pb(op.JSC).pb(sysop.LDPROC);
        a.pb(op.CMP_ABS).pw(map.STACK_TOP);
        a.brk();

        d.print(a); cpu.resume();
        assertTrue(cpu.p.z);

        sys.println("\n===== TXS");

        a.pb(op.LDA_IMM).pb(0x44);
        a.pb(op.STA_ABS).pw(0x1aa);
        a.pb(op.LDX_IMM).pb(0xa9);
        a.pb(op.TXS    );
        a.pb(op.PLA    );
        a.brk();

        d.print(a); cpu.resume();
        assertEquals(0x44, cpu.a);

        sys.println("\n===== TSX");

        a.pb(op.TSX);
        a.brk();

        d.print(a); cpu.resume();
        assertEquals(0xaa, cpu.x);
    }

}