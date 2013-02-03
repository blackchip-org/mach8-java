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
public class TXATest {

    public TXATest() {
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
        sys.println("\n***** TXA *****");

        CPUSetup.debug();

        Assembler a = new Assembler();
        Disassembler d = new Disassembler();

        sys.println("\n===== Standard transfer");
        a.pb(op.LDX_IMM).pb(0x44);
        a.pb(op.TXA    );
        a.brk();

        d.print(a); cpu.resume();
        assertEquals(0x44, cpu.a);
        assertTrue(!cpu.p.n && !cpu.p.z);

        sys.println("\n===== Zero transfer");
        a.pb(op.LDX_IMM).pb(0x00);
        a.pb(op.TXA    );
        a.brk();

        d.print(a); cpu.resume();
        assertEquals(0x00, cpu.a);
        assertTrue(!cpu.p.n &&  cpu.p.z);
        
        sys.println("\n===== Signed transfer");
        a.pb(op.LDX_IMM).pb(0xaa);
        a.pb(op.TXA    );
        a.brk();

        d.print(a); cpu.resume();
        assertEquals(0xaa, cpu.a);
        assertTrue( cpu.p.n && !cpu.p.z);
    }



}