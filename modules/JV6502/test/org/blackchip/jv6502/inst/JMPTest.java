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
public class JMPTest {

    public JMPTest() {
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
        sys.println("\n***** JMP *****");

        CPUSetup.debug();

        Assembler a = new Assembler();
        Disassembler d = new Disassembler();

        sys.println("\n===== JMP, Absolute");

        a.pb(op.JMP_ABS).abs("JUMP1");
        d.print(a);
        a.setAddress(0x8000);
        d.setAddress(0x8000);
        a.label("JUMP1");
        a.pb(op.LDA_IMM).pb(0x88);
        a.brk();
        a.resolve();

        d.print(a); cpu.resume();
        assertEquals(0x88, cpu.a);

        sys.println("\n===== JMP, Indirect");

        a.pb(op.LDA_IMM).pb(0x34);
        a.pb(op.STA_ABS).pw(0x6000);
        a.pb(op.LDA_IMM).pb(0x12);
        a.pb(op.STA_ABS).pw(0x6001);
        a.pb(op.JMP_IND).pw(0x6000);
        d.print(a);
        a.setAddress(0x1234);
        d.setAddress(0x1234);
        a.pb(op.LDA_IMM).pb(0x99);
        a.brk();

        d.print(a); cpu.resume();
        assertEquals(0x99, cpu.a);

    }


}