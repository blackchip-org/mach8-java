/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.blackchip.jv6502.monitor;

import org.blackchip.jv6502.op;
import org.blackchip.jv6502.CPUSetup;
import org.blackchip.jv6502.mmu;
import org.blackchip.system.sys;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author mcgann
 */
public class AssemblerTest {

    public AssemblerTest() {
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
//        Setup.standard();
//
//        Assembler a = new Assembler(0x4000);
//        a.pb(op.LDA_IMM).pb(0x12);
//        a.pb(op.LDA_ZP ).pb(0x13);
//        a.pb(op.LDA_ZPX).pb(0x14);
//        a.pb(op.LDA_ABS).pw(0x2345);
//        a.pb(op.LDA_ABX).pw(0x2244);
//        a.pb(op.LDA_ABY).pw(0xffff);
//        a.pb(op.LDA_IZX).pb(0x88);
//        a.pb(op.LDA_IZY).pb(0xaa);
//
//        mmu.print(0x4000, a.getAddress());
//
//        a.pb(op.LDX_IMM).pb(0xbb);
//        a.pb(op.LDX_ZP ).pb(0x33);
//        a.pb(op.LDX_ZPY).pb(0x55);
//        a.pb(op.LDX_ABS).pw(0x5432);
//        a.pb(op.LDX_ABY).pw(0x2345);
//
//        a.pb(op.LDY_IMM).pb(0xbb);
//        a.pb(op.LDY_ZP ).pb(0x33);
//        a.pb(op.LDY_ZPX).pb(0x55);
//        a.pb(op.LDY_ABS).pw(0x5432);
//        a.pb(op.LDY_ABX).pw(0x2345);
//
//        Disassembler d = new Disassembler(0x4000);
//        d.print(a);

    }

}