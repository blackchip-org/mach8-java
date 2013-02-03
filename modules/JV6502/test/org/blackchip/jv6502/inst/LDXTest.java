/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.blackchip.jv6502.inst;

import org.blackchip.jv6502.inst.LDX;
import org.blackchip.jv6502.op;
import org.blackchip.jv6502.cpu;
import org.blackchip.jv6502.mmu;
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
public class LDXTest {

    public LDXTest() {
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
    public void testImmediate() {

        System.out.println("\n===== LDX Immediate\n");

        mmu.clear();

        Runnable inst = new LDX(op.LDX_IMM);
        cpu.pc(0x3fff);

        mmu.store(0x4000, 0x42);
        inst.run();
        cpu.print();
        assertEquals(0x42, cpu.x);
        assertFalse(cpu.p.z);
        assertFalse(cpu.p.n);

        mmu.store(0x4001, 0xff);
        inst.run();
        cpu.print();
        assertEquals(0xff, cpu.x);
        assertFalse(cpu.p.z);
        assertTrue(cpu.p.n);

        inst.run();
        cpu.print();
        assertEquals(0x00, cpu.x);
        assertTrue(cpu.p.z);
        assertFalse(cpu.p.n);

    }

    @Test
    public void testZeroPage() {

        System.out.println("\n===== LDX Zero Page\n");

        mmu.clear();

        Runnable inst = new LDX(op.LDX_ZP);
        cpu.pc(0x3fff);

        mmu.store(0x69, 0x42);
        mmu.store(0x4000, 0x69);
        mmu.store(0x4001, 0x11);
        inst.run();
        cpu.print();
        assertEquals(0x42, cpu.x);

        inst = new LDX(op.LDX_ZPY);
        cpu.fetch();
        mmu.store(0x6a, 0x43);
        mmu.store(0x4002, 0x69);
        cpu.y = 1;
        inst.run();
        cpu.print();
        assertEquals(0x43, cpu.x);

        mmu.store(0x01, 0x77);
        mmu.store(0x4003, 0xff);
        cpu.y = 2;
        inst.run();
        cpu.print();
        assertEquals(0x77, cpu.x);

        mmu.store(0xfe, 0x88);
        mmu.store(0x4004, 0x00);
        cpu.y = -2;
        inst.run();
        cpu.print();
        assertEquals(0x88, cpu.x);


    }

    @Test
    public void testAbsolute() {

        System.out.println("\n===== LDX Absolute");

        mmu.clear();

        Runnable inst = new LDX(op.LDX_ABS);
        cpu.pc(0xbfff);

        mmu.store(0x1234, 0x11);
        mmu.store(0xc000, 0x34);
        mmu.store(0xc001, 0x12);
        inst.run();
        cpu.print();
        assertEquals(0x11, cpu.x);

        mmu.store(0xffff, 0x22);
        mmu.store(0xc002, 0xff);
        mmu.store(0xc003, 0xff);
        inst.run();
        cpu.print();
        assertEquals(0x22, cpu.x);

        inst = new LDX(op.LDX_ABY);
        mmu.store(0x2345, 0x33);
        mmu.store(0xc004, 0x40);
        mmu.store(0xc005, 0x23);
        cpu.y = 5;
        inst.run();
        cpu.print();
        assertEquals(0x33, cpu.x);

        mmu.store(0x02, 0xee);
        mmu.store(0xc006, 0xfe);
        mmu.store(0xc007, 0xff);
        cpu.y = 4;
        inst.run();
        cpu.print();
        assertEquals(0xee, cpu.x);


    }

}