/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.blackchip.jv6502.inst;

/**
 *
 * @author mcgann
 */
import org.blackchip.jv6502.CPUSetup;
import org.blackchip.jv6502.cpu;
import org.blackchip.jv6502.mmu;
import org.blackchip.jv6502.util.Assembler;
import org.blackchip.jv6502.util.Disassembler;
import org.blackchip.jv6502.op;
import org.blackchip.system.sys;
import org.blackchip.jv6502.sysop;
import org.junit.Test;
import static org.junit.Assert.*;

public class VTest
{

    @Test
    public void test()
    {
        sys.println("\n***** V Flag Test *****\n");

        CPUSetup.debug();

        Assembler a = new Assembler();
        Disassembler d = new Disassembler();

        a.define("ERROR", 0x8000);
        a.define("S1",    0x8002);
        a.define("S2",    0x8004);
        a.define("U1",    0x8006);
        a.define("U2",    0x8008);

        a.label("TEST");
        a.pb(op.CLD    );
        a.pb(op.LDA_IMM).pb(0x01);
        a.pb(op.STA_ABS).abs("ERROR");
        a.pb(op.LDA_IMM).pb(0x80);
        a.pb(op.STA_ABS).abs("S1");
        a.pb(op.STA_ABS).abs("S2");
        a.pb(op.LDA_IMM).pb(0x00);
        a.pb(op.STA_ABS).abs("U1");
        a.pb(op.STA_ABS).abs("U2");
        a.pb(op.LDY_IMM).pb(0x01);

        a.label("LOOP");
        a.pb(op.JSR    ).abs("ADD");
        a.pb(op.CPX_IMM).pb(0x01);
        a.pb(op.BEQ    ).rel("DONE");
        a.pb(op.JSR    ).abs("SUB");
        a.pb(op.CPX_IMM).pb(0x01);
        a.pb(op.BEQ    ).rel("DONE");
        a.pb(op.INC_ABS).abs("S1");
        a.pb(op.INC_ABS).abs("U1");
        a.pb(op.BNE    ).rel("LOOP");
        a.pb(op.INC_ABS).abs("S2");
        a.pb(op.INC_ABS).abs("U2");
        a.pb(op.BNE    ).rel("LOOP");
        a.pb(op.DEY    );
        a.pb(op.BPL    ).rel("LOOP");
        a.pb(op.LDA_IMM).pb(0x00);
        a.pb(op.STA_ABS).abs("ERROR");
        a.label("DONE");
        a.brk();

        a.label("ADD");
        a.pb(op.CPY_IMM).pb(0x01);
        a.pb(op.LDA_ABS).abs("S1");
        a.pb(op.ADC_ABS).abs("S2");
        a.pb(op.LDX_IMM).pb(0x00);
        a.pb(op.BVC    ).rel("ADD1");
        a.pb(op.INX    );

        a.label("ADD1");
        a.pb(op.CPY_IMM).pb(0x01);
        a.pb(op.LDA_ABS).abs("U1");
        a.pb(op.ADC_ABS).abs("U2");
        a.pb(op.BCS    ).rel("ADD3");
        a.pb(op.BMI    ).rel("ADD2");
        a.pb(op.INX    );

        a.label("ADD2");
        a.pb(op.RTS    );

        a.label("ADD3");
        a.pb(op.BPL    ).rel("ADD4");
        a.pb(op.INX    );

        a.label("ADD4");
        a.pb(op.RTS    );

        a.label("SUB");
        a.pb(op.CPY_IMM).pb(0x01);
        a.pb(op.LDA_ABS).abs("S1");
        a.pb(op.SBC_ABS).abs("S2");
        a.pb(op.JSC    ).pb(sysop.PRTCPU);
        a.pb(op.LDX_IMM).pb(0x00);
        a.pb(op.BVC    ).rel("SUB1");
        a.pb(op.INX    );

        a.label("SUB1");
        a.pb(op.CPY_IMM).pb(0x01);
        a.pb(op.LDA_ABS).abs("U1");
        a.pb(op.SBC_ABS).abs("U2");
        a.pb(op.JSC    ).pb(sysop.PRTCPU);
        a.pb(op.PHA    );
        a.pb(op.LDA_IMM).pb(0xff);
        a.pb(op.SBC_IMM).pb(0x00);
        a.pb(op.CMP_IMM).pb(0xfe);
        a.pb(op.BNE    ).rel("SUB4");
        a.pb(op.PLA    );
        a.pb(op.BMI    ).rel("SUB3");

        a.label("SUB2");
        a.pb(op.INX    );

        a.label("SUB3");
        a.pb(op.RTS    );

        a.label("SUB4");
        a.pb(op.PLA    );
        a.pb(op.BCC    ).rel("SUB2");
        a.pb(op.BPL    ).rel("SUB5");
        a.pb(op.INX    );

        a.label("SUB5");
        a.pb(op.RTS    );

        a.resolve();
        d.print(a); 
////        cpu.param.printExecution = false;
//        cpu.resume();
//
//        int error = mmu.load(0x8000);
//        int s1    = mmu.load(0x8002);
//        int s2    = mmu.load(0x8004);
//        int u1    = mmu.load(0x0006);
//        int u2    = mmu.load(0x0008);
//
//        sys.nl();
//        sys.printf("error: %s, s1: %s, s2: %s, u1: %s, u2: %s\n",
//                sys.toHex(error), sys.toHex(s1), sys.toHex(s2),
//                sys.toHex(u1), sys.toHex(u2));
//
//        assertTrue(true);
    }
}
