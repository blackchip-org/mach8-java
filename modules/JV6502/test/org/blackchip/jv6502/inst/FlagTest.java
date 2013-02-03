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
import org.blackchip.jv6502.sysop;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mcgann
 */
public class FlagTest {

    public FlagTest() {
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
        sys.println("\n***** Set/Clear Flags *****");

        CPUSetup.debug();

        Assembler a = new Assembler();
        Disassembler d = new Disassembler();

        sys.println("\n===== SEC");
        a.pb(op.CLC);
        a.pb(op.SEC);
        a.brk();

        d.print(a); cpu.resume();
        assertTrue(cpu.p.c);
        
        sys.println("\n===== CLC");
        a.pb(op.CLC);
        a.brk();

        d.print(a); cpu.resume();
        assertFalse(cpu.p.c);
        
        sys.println("\n===== SEI");
        a.pb(op.CLI);
        a.pb(op.SEI);
        a.brk();

        d.print(a); cpu.resume(); 
        assertTrue(cpu.p.i);
        
        sys.println("\n===== CLI");
        a.pb(op.CLI);
        a.brk();

        d.print(a); cpu.resume(); 
        assertFalse(cpu.p.i);
        
        sys.println("\n===== CLV");
        cpu.p.v = true;
        a.pb(op.CLV);
        a.brk();

        d.print(a); cpu.resume(); 
        assertFalse(cpu.p.v);
        
        sys.println("\n===== SED");
        a.pb(op.CLD);
        a.pb(op.SED);
        a.brk();

        d.print(a); cpu.resume(); 
        assertTrue(cpu.p.d);
        
        sys.println("\n===== CLD");
        a.pb(op.CLD);
        a.brk();

        d.print(a); cpu.resume(); 
        assertFalse(cpu.p.d);

    }
}