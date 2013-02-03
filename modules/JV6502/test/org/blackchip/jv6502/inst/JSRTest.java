/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.blackchip.jv6502.inst;

import org.blackchip.jv6502.CPUSetup;
import org.blackchip.jv6502.cpu;
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
public class JSRTest {

    public JSRTest() {
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
        sys.println("***** JSR/RTS *****");

        CPUSetup.debug();

        Assembler a = new Assembler();
        Disassembler d = new Disassembler();

        a.define("DATA", 0x8000);
        a.define("SUB",  0x5000);

        a.pb(op.LDA_IMM).pb(0x22);
        a.pb(op.STA_ABS).abs("DATA");
        a.pb(op.JSR    ).abs("SUB");
        a.pb(op.INC_ABS).abs("DATA");
        a.pb(op.LDA_ABS).abs("DATA");
        a.brk();

        d.print(a); sys.nl();

        a.setAddress("SUB");
        d.setAddress(a);
        a.pb(op.INC_ABS).abs("DATA");
        a.pb(op.RTS    );

        d.print(a); cpu.resume(); sys.nl();
        mmu.print(0x1f8, 0x1ff);
        
        assertEquals(0x24, cpu.a);

        assertTrue(true);
    }
}