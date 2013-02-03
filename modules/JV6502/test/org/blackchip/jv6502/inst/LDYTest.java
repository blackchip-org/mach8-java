/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.blackchip.jv6502.inst;

import org.blackchip.jv6502.inst.LDY;
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
public class LDYTest {

    public LDYTest() {
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

        System.out.println("\n===== LDY Immediate\n");

        mmu.clear();

        Runnable inst = new LDY(op.LDY_IMM);
        cpu.pc(0x3fff);

        mmu.store(0x4000, 0x42);
        inst.run();
        cpu.print();
        assertEquals(0x42, cpu.y);
        assertFalse(cpu.p.z);
        assertFalse(cpu.p.n);

        mmu.store(0x4001, 0xff);
        inst.run();
        cpu.print();
        assertEquals(0xff, cpu.y);
        assertFalse(cpu.p.z);
        assertTrue(cpu.p.n);

        inst.run();
        cpu.print();
        assertEquals(0x00, cpu.y);
        assertTrue(cpu.p.z);
        assertFalse(cpu.p.n);

    }

    @Test
    public void testZeroPage() {

        System.out.println("\n===== LDY Zero Page\n");

        mmu.clear();

        Runnable inst = new LDY(op.LDY_ZP);
        cpu.pc(0x3fff);

        mmu.store(0x69, 0x42);
        mmu.store(0x4000, 0x69);
        mmu.store(0x4001, 0x11);
        inst.run();
        cpu.print();
        assertEquals(0x42, cpu.y);

        inst = new LDY(op.LDY_ZPX);
        cpu.fetch();
        mmu.store(0x6a, 0x43);
        mmu.store(0x4002, 0x69);
        cpu.x = 1;
        inst.run();
        cpu.print();
        assertEquals(0x43, cpu.y);

        mmu.store(0x01, 0x77);
        mmu.store(0x4003, 0xff);
        cpu.x = 2;
        inst.run();
        cpu.print();
        assertEquals(0x77, cpu.y);

        mmu.store(0xfe, 0x88);
        mmu.store(0x4004, 0x00);
        cpu.x = -2;
        inst.run();
        cpu.print();
        assertEquals(0x88, cpu.y);


    }

    @Test
    public void testAbsolute() {

        System.out.println("\n===== LDY Absolute");

        mmu.clear();

        Runnable inst = new LDY(op.LDY_ABS);
        cpu.pc(0xbfff);

        mmu.store(0x1234, 0x11);
        mmu.store(0xc000, 0x34);
        mmu.store(0xc001, 0x12);
        inst.run();
        cpu.print();
        assertEquals(0x11, cpu.y);

        mmu.store(0xffff, 0x22);
        mmu.store(0xc002, 0xff);
        mmu.store(0xc003, 0xff);
        inst.run();
        cpu.print();
        assertEquals(0x22, cpu.y);

        inst = new LDY(op.LDY_ABX);
        mmu.store(0x2345, 0x33);
        mmu.store(0xc004, 0x40);
        mmu.store(0xc005, 0x23);
        cpu.x = 5;
        inst.run();
        cpu.print();
        assertEquals(0x33, cpu.y);

        mmu.store(0x02, 0xee);
        mmu.store(0xc006, 0xfe);
        mmu.store(0xc007, 0xff);
        cpu.x = 4;
        inst.run();
        cpu.print();
        assertEquals(0xee, cpu.y);


    }

}