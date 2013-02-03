/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.blackchip.jv6502.inst;

import org.blackchip.jv6502.CPUSetup;
import org.blackchip.jv6502.cpu;
import org.blackchip.jv6502.op;
import org.blackchip.jv6502.sysop;
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
public class RTITest {

    public RTITest() {
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
        sys.println("\n***** RTI *****");

        CPUSetup.debug();

        Assembler a = new Assembler();
        Disassembler d = new Disassembler();

        a.pb(op.JSC    ).pb(sysop.SFTIRQ);
        a.pb(op.LDY_IMM).pb(0x22);
        a.brk();
        d.print(a);

        a.setAddress(map.IRQISR);
        a.pb(op.LDX_IMM).pb(0x11);
        a.pb(op.RTI    );
        d.print(a);

        cpu.resume();
        assertEquals(0x11, cpu.x);
        assertEquals(0x22, cpu.y);


        assertTrue(true);
    }

}