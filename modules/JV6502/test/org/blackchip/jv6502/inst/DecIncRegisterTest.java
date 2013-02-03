/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.blackchip.jv6502.inst;

import org.blackchip.jv6502.CPUSetup;
import org.blackchip.jv6502.cpu;
import org.blackchip.system.map;
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
public class DecIncRegisterTest {

    public DecIncRegisterTest() {
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
        sys.println("\n***** INX/DEX/INY/DEY *****");

        CPUSetup.debug();

        Assembler a = new Assembler();
        Disassembler d = new Disassembler();

        sys.println("\n===== INX once");
        a.pb(op.LDX_IMM).pb(0xfe);
        a.pb(op.INX);
        a.brk();

        d.print(a); cpu.reset(); 
        assertEquals(0xff, cpu.x);
        assertTrue( cpu.p.n && !cpu.p.z);

        sys.println("\n===== INX wrap around");
        a.pb(op.INX);
        a.brk();

        d.print(a); cpu.resume(); 
        assertEquals(0x00, cpu.x);
        assertTrue(!cpu.p.n &&  cpu.p.z);

        sys.println("\n===== INX, DEX, same?");
        a.pb(op.INX);
        a.pb(op.DEX);
        a.brk();

        d.print(a); cpu.resume();
        assertEquals(0x00, cpu.x);
        assertTrue(!cpu.p.n &&  cpu.p.z);

        sys.println("\n===== DEX wrap around");
        a.pb(op.DEX);
        a.brk();
        
        d.print(a); cpu.resume(); 
        assertEquals(0xff, cpu.x);
        assertTrue( cpu.p.n && !cpu.p.z);

        sys.println("\n===== INY once");
        a.pb(op.LDY_IMM).pb(0xfe);
        a.pb(op.INY);
        a.brk();

        d.print(a); cpu.resume(); 
        assertEquals(0xff, cpu.y);
        assertTrue( cpu.p.n && !cpu.p.z);

        sys.println("\n===== INY wrap around");
        a.pb(op.INY);
        a.brk();

        d.print(a); cpu.resume(); 
        assertEquals(0x00, cpu.y);
        assertTrue(!cpu.p.n &&  cpu.p.z);

        sys.println("\n===== INY, DEY, same?");
        a.pb(op.INY);
        a.pb(op.DEY);
        a.brk();

        d.print(a); cpu.resume();
        assertEquals(0x00, cpu.y);
        assertTrue(!cpu.p.n &&  cpu.p.z);

        sys.println("\n===== DEY wrap around");
        a.pb(op.DEY);
        a.brk();

        d.print(a); cpu.resume(); 
        assertEquals(0xff, cpu.y);
        assertTrue( cpu.p.n && !cpu.p.z);
        
    }


}