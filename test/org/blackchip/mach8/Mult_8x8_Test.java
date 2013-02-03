/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.blackchip.mach8;

import java.io.IOException;
import org.blackchip.jv6502.CPUSetup;
import org.blackchip.jv6502.cpu;
import org.blackchip.jv6502.mmu;
import org.blackchip.jv6502.op;
import org.blackchip.jv6502.util.Assembler;
import org.blackchip.jv6502.util.Disassembler;
import org.blackchip.system.map;
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
public class Mult_8x8_Test {

    public Mult_8x8_Test() {
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
    public void test() throws IOException
    {
        sys.println("\n***** MULT_8x8 *****");

        CPUSetup.debug();
        cpu.param.printExecution = false;
        mach8.loadKernel();

        Assembler a = new Assembler();
        Disassembler d = new Disassembler();

        sys.println("\n===== 6 * 7");
        a.pb(op.LDA_IMM).pb(6);
        a.pb(op.STA_ZP ).pb(map.FP1_OPA);
        a.pb(op.LDA_IMM).pb(7);
        a.pb(op.STA_ZP ).pb(map.FP1_OPB);
        a.pb(op.LDX_IMM).pb(0);
        a.pb(op.JSR    ).pw(map.MULT_8x8);
        a.brk();

        d.print(a); cpu.resume();
        assertEquals(42, mmu.load16(map.FP1_OUT));

        sys.println("\n===== 213 * 77");
        a.pb(op.LDA_IMM).pb(213);
        a.pb(op.STA_ZP ).pb(map.FP1_OPA);
        a.pb(op.LDA_IMM).pb(77);
        a.pb(op.STA_ZP ).pb(map.FP1_OPB);
        a.pb(op.LDX_IMM).pb(0);
        a.pb(op.JSR    ).pw(map.MULT_8x8);
        a.brk();

        d.print(a); cpu.resume();
        assertEquals(16401, mmu.load16(map.FP1_OUT));

        sys.println("\n===== 0 * 0");
        a.pb(op.LDA_IMM).pb(0);
        a.pb(op.STA_ZP ).pb(map.FP1_OPA);
        a.pb(op.LDA_IMM).pb(0);
        a.pb(op.STA_ZP ).pb(map.FP1_OPB);
        a.pb(op.LDX_IMM).pb(0);
        a.pb(op.JSR    ).pw(map.MULT_8x8);
        a.brk();

        d.print(a); cpu.resume();
        assertEquals(0, mmu.load16(map.FP1_OUT));

        sys.println("\n===== 255 * 255");
        a.pb(op.LDA_IMM).pb(255);
        a.pb(op.STA_ZP ).pb(map.FP1_OPA);
        a.pb(op.LDA_IMM).pb(255);
        a.pb(op.STA_ZP ).pb(map.FP1_OPB);
        a.pb(op.LDX_IMM).pb(0);
        a.pb(op.JSR    ).pw(map.MULT_8x8);
        a.brk();

        d.print(a); cpu.resume();
        assertEquals(65025, mmu.load16(map.FP1_OUT));

        sys.println("\n===== 255 * 255 (ZP Registers 2)");
        a.pb(op.LDA_IMM).pb(255);
        a.pb(op.STA_ZP ).pb(map.FP2_OPA);
        a.pb(op.LDA_IMM).pb(255);
        a.pb(op.STA_ZP ).pb(map.FP2_OPB);
        a.pb(op.LDX_IMM).pb(1);
        a.pb(op.JSR    ).pw(map.MULT_8x8);
        a.brk();

        d.print(a); cpu.resume();
        assertEquals(65025, mmu.load16(map.FP2_OUT));


    }

}