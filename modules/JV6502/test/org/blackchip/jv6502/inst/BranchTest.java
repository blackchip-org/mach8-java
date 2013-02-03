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
import org.blackchip.jv6502.sysop;
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
public class BranchTest {

    public BranchTest() {
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
        sys.println("\n***** Branches ******");

        CPUSetup.debug();

        Assembler a = new Assembler();
        Disassembler d = new Disassembler();

        sys.println("\n===== BRA");
        a.pb(op.BRA).rel("ALWAYS");
        a.brk();
        a.label("ALWAYS");
        a.pb(op.LDA_IMM).pb(0xaa);
        a.brk();
        a.resolve();
        
        d.print(a); cpu.resume();
        assertEquals(0xaa, cpu.a);
        
        sys.println("\n===== BNE");
        
        a.pb(op.LDX_IMM).pb(0x02);
        a.label("LOOP");
        a.pb(op.DEX    );
        a.pb(op.CPX_IMM).pb(0x00);
        a.pb(op.JSC    ).pb(sysop.PRTCPU);
        a.pb(op.BNE    ).rel("LOOP");
        a.brk();

        d.print(a); cpu.resume();
        assertEquals(0, cpu.x);

        sys.println("\n===== BEQ");
        
        a.pb(op.LDA_IMM).pb(0x44);
        a.pb(op.CMP_IMM).pb(0x44);
        a.pb(op.JSC    ).pb(sysop.PRTCPU);
        a.pb(op.BEQ    ).rel("EQ01");
        a.brk();
        a.label("EQ01");
        a.pb(op.LDX_IMM).pb(0x22);

        a.pb(op.CMP_IMM).pb(0x88);
        a.pb(op.JSC    ).pb(sysop.PRTCPU);
        a.pb(op.BEQ    ).rel("EQ02");
        a.pb(op.LDY_IMM).pb(0x33);
        a.label("EQ02");
        a.brk();
        a.resolve();
        
        d.print(a); cpu.resume();
        assertEquals(0x22, cpu.x);
        assertEquals(0x33, cpu.y);

        sys.println("\n===== BPL");
        a.pb(op.LDA_IMM).pb(0x01);
        a.pb(op.BPL    ).rel("PL01");
        a.brk();
        a.label("PL01");
        a.pb(op.LDX_IMM).pb(0x44);
        a.brk();
        a.resolve();

        d.print(a); cpu.resume(); sys.nl();
        assertEquals(0x44, cpu.x);
        
        a.pb(op.LDA_IMM).pb(0xff);
        a.pb(op.BPL    ).rel("PL02");
        a.pb(op.LDX_IMM).pb(0x55);
        a.brk();
        a.label("PL02");
        a.brk();
        a.resolve();

        d.print(a); cpu.resume();
        assertEquals(0x55, cpu.x);

        sys.println("\n===== BMI");
        cpu.pc(a.getAddress());
        a.pb(op.LDA_IMM).pb(0xff);
        a.pb(op.BMI    ).rel("MI01");
        a.brk();
        a.label("MI01");
        a.pb(op.LDX_IMM).pb(0x66);
        a.brk();
        a.resolve();

        d.print(a); cpu.resume(); sys.nl();
        assertEquals(0x66, cpu.x);

        a.pb(op.LDA_IMM).pb(0x01);
        a.pb(op.BMI    ).rel("MI02");
        a.pb(op.LDX_IMM).pb(0x77);
        a.brk();
        a.label("MI02");
        a.brk();
        a.resolve();

        d.print(a); cpu.resume();
        assertEquals(0x77, cpu.x);

        sys.println("\n===== BVC");
        cpu.pc(a.getAddress());
        a.pb(op.CLC    );
        a.pb(op.LDA_IMM).pb(0x81);
        a.pb(op.ADC_IMM).pb(0xff);
        a.pb(op.BVC    ).rel("VC01");
        a.brk();
        a.label("VC01");
        a.pb(op.LDX_IMM).pb(0x88);
        a.brk();
        a.resolve();

        d.print(a); cpu.resume(); sys.nl();
        assertEquals(0x88, cpu.x);

        a.pb(op.CLC    );
        a.pb(op.LDA_IMM).pb(0x7f);
        a.pb(op.ADC_IMM).pb(0x01);
        a.pb(op.BVC    ).rel("VC02");
        a.pb(op.LDX_IMM).pb(0x99);
        a.brk();
        a.label("VC02");
        a.brk();
        a.resolve();

        d.print(a); cpu.resume();
        assertEquals(0x99, cpu.x);

        sys.println("\n===== BVS");
        cpu.pc(a.getAddress());
        a.pb(op.CLC    );
        a.pb(op.LDA_IMM).pb(0x7f);
        a.pb(op.ADC_IMM).pb(0x01);
        a.pb(op.BVS    ).rel("VS01");
        a.brk();
        a.label("VS01");
        a.pb(op.LDX_IMM).pb(0x11);
        a.brk();
        a.resolve();

        d.print(a); cpu.resume(); sys.nl();
        assertEquals(0x11, cpu.x);

        a.pb(op.CLC    );
        a.pb(op.LDA_IMM).pb(0x81);
        a.pb(op.ADC_IMM).pb(0x01);
        a.pb(op.BVS    ).rel("VS02");
        a.pb(op.LDX_IMM).pb(0x22);
        a.brk();
        a.label("VS02");
        a.brk();
        a.resolve();

        d.print(a); cpu.resume();
        assertEquals(0x22, cpu.x);

        sys.println("\n===== BCC");
        cpu.pc(a.getAddress());
        a.pb(op.CLC    );
        a.pb(op.LDA_IMM).pb(0xfe);
        a.pb(op.ADC_IMM).pb(0x01);
        a.pb(op.BCC    ).rel("CC01");
        a.brk();
        a.label("CC01");
        a.pb(op.LDX_IMM).pb(0x33);
        a.brk();
        a.resolve();

        d.print(a); cpu.resume(); sys.nl();
        assertEquals(0x33, cpu.x);

        a.pb(op.CLC    );
        a.pb(op.LDA_IMM).pb(0xff);
        a.pb(op.ADC_IMM).pb(0x01);
        a.pb(op.BCC    ).rel("CC02");
        a.pb(op.LDX_IMM).pb(0x44);
        a.brk();
        a.label("CC02");
        a.brk();
        a.resolve();

        d.print(a); cpu.resume();
        assertEquals(0x44, cpu.x);

        sys.println("\n===== BCS");
        cpu.pc(a.getAddress());
        a.pb(op.CLC    );
        a.pb(op.LDA_IMM).pb(0xff);
        a.pb(op.ADC_IMM).pb(0x01);
        a.pb(op.BCS    ).rel("CS01");
        a.brk();
        a.label("CS01");
        a.pb(op.LDX_IMM).pb(0x55);
        a.brk();
        a.resolve();

        d.print(a); cpu.resume(); sys.nl();
        assertEquals(0x55, cpu.x);

        a.pb(op.CLC    );
        a.pb(op.LDA_IMM).pb(0x79);
        a.pb(op.ADC_IMM).pb(0x01);
        a.pb(op.BCS    ).rel("CS02");
        a.pb(op.LDX_IMM).pb(0x66);
        a.brk();
        a.label("CS02");
        a.brk();
        a.resolve();

        d.print(a); cpu.resume();
        assertEquals(0x66, cpu.x);
    }
}