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
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mcgann
 */
public class CPYTest {

    public CPYTest() {
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
        sys.println("\n***** CPY *****");

        CPUSetup.debug();

        Assembler a = new Assembler();
        Disassembler d = new Disassembler();

        sys.println("\n===== CPY, Immediate");
        a.pb(op.LDY_IMM).pb(0x22);
        a.pb(op.CPY_IMM).pb(0x22);
        a.brk();

        d.print(a); cpu.resume();
        assertTrue( cpu.p.z &&  cpu.p.c && !cpu.p.n);

        sys.nl();
        a.pb(op.LDY_IMM).pb(0x23);
        a.pb(op.CPY_IMM).pb(0x22);
        a.brk();

        d.print(a); cpu.resume();
        assertTrue(!cpu.p.z &&  cpu.p.c && !cpu.p.n);

        sys.nl();
        a.pb(op.LDY_IMM).pb(0xaa);
        a.pb(op.CPY_IMM).pb(0xbb);
        a.brk();

        d.print(a); cpu.resume();
        assertTrue(!cpu.p.z && !cpu.p.c && cpu.p.n);

        sys.println("\n===== CPY, Zero Page");
        a.pb(op.LDY_IMM).pb(0x22);
        a.pb(op.STY_ZP ).pb(0xdd);
        a.pb(op.CPY_ZP ).pb(0xdd);
        a.brk();

        d.print(a); cpu.resume();
        assertTrue( cpu.p.z &&  cpu.p.c && !cpu.p.n);

        sys.println("\n===== CPX, Absolute");
        a.pb(op.LDY_IMM).pb(0x33);
        a.pb(op.STY_ABS).pw(0x8111);
        a.pb(op.CPY_ABS).pw(0x8111);
        a.brk();

        d.print(a); cpu.resume();
        assertTrue( cpu.p.z &&  cpu.p.c && !cpu.p.n);
    }
}