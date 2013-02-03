/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.blackchip.jv6502;

import org.blackchip.system.map;
import org.blackchip.jv6502.inst.*;
import org.blackchip.jv6502.inst.jsc.*;
import org.blackchip.system.sys;

/**
 *
 * @author mcgann
 */
public class CPUSetup {

    public static void standard()
    {
        cpu.param.exceptionOnTrap = true;
        cpu.param.logOnTrap = true;
        
        cpu.protectMemory(map.CODE_AREA_START, map.CODE_AREA_END);
        mmu.clear();
        reboot();
    }
    
    public static void reboot()
    {
        mapOpcodes();
        initVectors();

        cpu.sp = 0xff;
        cpu.p.x = true;
        cpu.p.i = false; 
    }

    public static void debug()
    {
        standard();

        cpu.param.exceptionOnTrap = true;
        cpu.param.printExecution  = true;
        cpu.param.logOnBreak      = true;

        cpu.inst[op.JSC    ] = new JSC();
        mapSystemCalls();

        // Immediately start at entry point
        cpu.pc(map.ENTRY);
    }

    private static void mapOpcodes()
    {
        for ( int i = 0; i < 256; i++ )
        {
            cpu.inst[i] = new ILL(i);
        }

        if ( cpu.param.enableJavaSystemCall ) 
        {
            sys.log("Installing java system calls");
            cpu.inst[op.JSC    ] = new JSC();
            mapSystemCalls();
        }

        cpu.inst[op.BRK    ] = new BRK();
        cpu.inst[op.NOP    ] = new NOP();

        cpu.inst[op.LDA_IMM] = new LDA(op.LDA_IMM);
        cpu.inst[op.LDA_ZP ] = new LDA(op.LDA_ZP );
        cpu.inst[op.LDA_ZPX] = new LDA(op.LDA_ZPX);
        cpu.inst[op.LDA_ABS] = new LDA(op.LDA_ABS);
        cpu.inst[op.LDA_ABX] = new LDA(op.LDA_ABX);
        cpu.inst[op.LDA_ABY] = new LDA(op.LDA_ABY);
        cpu.inst[op.LDA_IZX] = new LDA(op.LDA_IZX);
        cpu.inst[op.LDA_IZY] = new LDA(op.LDA_IZY);

        cpu.inst[op.LDX_IMM] = new LDX(op.LDX_IMM);
        cpu.inst[op.LDX_ZP ] = new LDX(op.LDX_ZP );
        cpu.inst[op.LDX_ZPY] = new LDX(op.LDX_ZPY);
        cpu.inst[op.LDX_ABS] = new LDX(op.LDX_ABS);
        cpu.inst[op.LDX_ABY] = new LDX(op.LDX_ABY);

        cpu.inst[op.LDY_IMM] = new LDY(op.LDY_IMM);
        cpu.inst[op.LDY_ZP ] = new LDY(op.LDY_ZP );
        cpu.inst[op.LDY_ZPX] = new LDY(op.LDY_ZPX);
        cpu.inst[op.LDY_ABS] = new LDY(op.LDY_ABS);
        cpu.inst[op.LDY_ABX] = new LDY(op.LDY_ABX);

        cpu.inst[op.STA_ZP ] = new STA(op.STA_ZP );
        cpu.inst[op.STA_ZPX] = new STA(op.STA_ZPX);
        cpu.inst[op.STA_ABS] = new STA(op.STA_ABS);
        cpu.inst[op.STA_ABX] = new STA(op.STA_ABX);
        cpu.inst[op.STA_ABY] = new STA(op.STA_ABY);
        cpu.inst[op.STA_IZX] = new STA(op.STA_IZX);
        cpu.inst[op.STA_IZY] = new STA(op.STA_IZY);

        cpu.inst[op.STX_ZP ] = new STX(op.STX_ZP );
        cpu.inst[op.STX_ZPY] = new STX(op.STX_ZPY);
        cpu.inst[op.STX_ABS] = new STX(op.STX_ABS);

        cpu.inst[op.STY_ZP ] = new STY(op.STY_ZP );
        cpu.inst[op.STY_ZPX] = new STY(op.STY_ZPX);
        cpu.inst[op.STY_ABS] = new STY(op.STY_ABS);

        cpu.inst[op.CLC    ] = new CLC();
        cpu.inst[op.SEC    ] = new SEC();
        cpu.inst[op.CLI    ] = new CLI();
        cpu.inst[op.SEI    ] = new SEI();
        cpu.inst[op.CLV    ] = new CLV();
        cpu.inst[op.CLD    ] = new CLD();
        cpu.inst[op.SED    ] = new SED();

        cpu.inst[op.DEX    ] = new DEX();
        cpu.inst[op.INX    ] = new INX();
        cpu.inst[op.DEY    ] = new DEY();
        cpu.inst[op.INY    ] = new INY();

        cpu.inst[op.DEC_ZP ] = new DEC(op.DEC_ZP );
        cpu.inst[op.DEC_ZPX] = new DEC(op.DEC_ZPX);
        cpu.inst[op.DEC_ABS] = new DEC(op.DEC_ABS);
        cpu.inst[op.DEC_ABX] = new DEC(op.DEC_ABX);

        cpu.inst[op.INC_ZP ] = new INC(op.INC_ZP );
        cpu.inst[op.INC_ZPX] = new INC(op.INC_ZPX);
        cpu.inst[op.INC_ABS] = new INC(op.INC_ABS);
        cpu.inst[op.INC_ABX] = new INC(op.INC_ABX);

        cpu.inst[op.TAX    ] = new TAX();
        cpu.inst[op.TXA    ] = new TXA();
        cpu.inst[op.TAY    ] = new TAY();
        cpu.inst[op.TYA    ] = new TYA();

        cpu.inst[op.TXS    ] = new TXS();
        cpu.inst[op.TSX    ] = new TSX();
        cpu.inst[op.PHA    ] = new PHA();
        cpu.inst[op.PLA    ] = new PLA();
        cpu.inst[op.PHP    ] = new PHP();
        cpu.inst[op.PLP    ] = new PLP();

        cpu.inst[op.JMP_ABS] = new JMP(op.JMP_ABS);
        cpu.inst[op.JMP_IND] = new JMP(op.JMP_IND);
        
        cpu.inst[op.CMP_IMM] = new CMP(op.CMP_IMM);
        cpu.inst[op.CMP_ZP ] = new CMP(op.CMP_ZP );
        cpu.inst[op.CMP_ZPX] = new CMP(op.CMP_ZPX);
        cpu.inst[op.CMP_ABS] = new CMP(op.CMP_ABS);
        cpu.inst[op.CMP_ABX] = new CMP(op.CMP_ABX);
        cpu.inst[op.CMP_ABY] = new CMP(op.CMP_ABY);
        cpu.inst[op.CMP_IZX] = new CMP(op.CMP_IZX);
        cpu.inst[op.CMP_IZY] = new CMP(op.CMP_IZY);

        cpu.inst[op.CPX_IMM] = new CPX(op.CPX_IMM);
        cpu.inst[op.CPX_ZP ] = new CPX(op.CPX_ZP );
        cpu.inst[op.CPX_ABS] = new CPX(op.CPX_ABS);

        cpu.inst[op.CPY_IMM] = new CPY(op.CPY_IMM);
        cpu.inst[op.CPY_ZP ] = new CPY(op.CPY_ZP );
        cpu.inst[op.CPY_ABS] = new CPY(op.CPY_ABS);

        cpu.inst[op.BNE    ] = new BNE();
        cpu.inst[op.BEQ    ] = new BEQ();
        cpu.inst[op.BPL    ] = new BPL();
        cpu.inst[op.BMI    ] = new BMI();
        cpu.inst[op.BVC    ] = new BVC();
        cpu.inst[op.BVS    ] = new BVS();
        cpu.inst[op.BCC    ] = new BCC();
        cpu.inst[op.BCS    ] = new BCS();

        cpu.inst[op.ADC_IMM] = new ADC(op.ADC_IMM);
        cpu.inst[op.ADC_ZP ] = new ADC(op.ADC_ZP );
        cpu.inst[op.ADC_ZPX] = new ADC(op.ADC_ZPX);
        cpu.inst[op.ADC_ABS] = new ADC(op.ADC_ABS);
        cpu.inst[op.ADC_ABX] = new ADC(op.ADC_ABX);
        cpu.inst[op.ADC_ABY] = new ADC(op.ADC_ABY);
        cpu.inst[op.ADC_IZX] = new ADC(op.ADC_IZX);
        cpu.inst[op.ADC_IZY] = new ADC(op.ADC_IZY);

        cpu.inst[op.SBC_IMM] = new SBC(op.SBC_IMM);
        cpu.inst[op.SBC_ZP ] = new SBC(op.SBC_ZP );
        cpu.inst[op.SBC_ZPX] = new SBC(op.SBC_ZPX);
        cpu.inst[op.SBC_ABS] = new SBC(op.SBC_ABS);
        cpu.inst[op.SBC_ABX] = new SBC(op.SBC_ABX);
        cpu.inst[op.SBC_ABY] = new SBC(op.SBC_ABY);
        cpu.inst[op.SBC_IZX] = new SBC(op.SBC_IZX);
        cpu.inst[op.SBC_IZY] = new SBC(op.SBC_IZY);

        cpu.inst[op.AND_IMM] = new AND(op.AND_IMM);
        cpu.inst[op.AND_ZP ] = new AND(op.AND_ZP );
        cpu.inst[op.AND_ZPX] = new AND(op.AND_ZPX);
        cpu.inst[op.AND_ABS] = new AND(op.AND_ABS);
        cpu.inst[op.AND_ABX] = new AND(op.AND_ABX);
        cpu.inst[op.AND_ABY] = new AND(op.AND_ABY);
        cpu.inst[op.AND_IZX] = new AND(op.AND_IZX);
        cpu.inst[op.AND_IZY] = new AND(op.AND_IZY);

        cpu.inst[op.EOR_IMM] = new EOR(op.EOR_IMM);
        cpu.inst[op.EOR_ZP ] = new EOR(op.EOR_ZP );
        cpu.inst[op.EOR_ZPX] = new EOR(op.EOR_ZPX);
        cpu.inst[op.EOR_ABS] = new EOR(op.EOR_ABS);
        cpu.inst[op.EOR_ABX] = new EOR(op.EOR_ABX);
        cpu.inst[op.EOR_ABY] = new EOR(op.EOR_ABY);
        cpu.inst[op.EOR_IZX] = new EOR(op.EOR_IZX);
        cpu.inst[op.EOR_IZY] = new EOR(op.EOR_IZY);

        cpu.inst[op.ORA_IMM] = new ORA(op.ORA_IMM);
        cpu.inst[op.ORA_ZP ] = new ORA(op.ORA_ZP );
        cpu.inst[op.ORA_ZPX] = new ORA(op.ORA_ZPX);
        cpu.inst[op.ORA_ABS] = new ORA(op.ORA_ABS);
        cpu.inst[op.ORA_ABX] = new ORA(op.ORA_ABX);
        cpu.inst[op.ORA_ABY] = new ORA(op.ORA_ABY);
        cpu.inst[op.ORA_IZX] = new ORA(op.ORA_IZX);
        cpu.inst[op.ORA_IZY] = new ORA(op.ORA_IZY);

        cpu.inst[op.BIT_ZP ] = new BIT(op.BIT_ZP );
        cpu.inst[op.BIT_ABS] = new BIT(op.BIT_ABS);

        cpu.inst[op.ASL_ACC] = new ASL(op.ASL_ACC);
        cpu.inst[op.ASL_ZP ] = new ASL(op.ASL_ZP );
        cpu.inst[op.ASL_ZPX] = new ASL(op.ASL_ZPX);
        cpu.inst[op.ASL_ABS] = new ASL(op.ASL_ABS);
        cpu.inst[op.ASL_ABX] = new ASL(op.ASL_ABX);

        cpu.inst[op.LSR_ACC] = new LSR(op.LSR_ACC);
        cpu.inst[op.LSR_ZP ] = new LSR(op.LSR_ZP );
        cpu.inst[op.LSR_ZPX] = new LSR(op.LSR_ZPX);
        cpu.inst[op.LSR_ABS] = new LSR(op.LSR_ABS);
        cpu.inst[op.LSR_ABX] = new LSR(op.LSR_ABX);
 
        cpu.inst[op.ROL_ACC] = new ROL(op.ROL_ACC);
        cpu.inst[op.ROL_ZP ] = new ROL(op.ROL_ZP );
        cpu.inst[op.ROL_ZPX] = new ROL(op.ROL_ZPX);
        cpu.inst[op.ROL_ABS] = new ROL(op.ROL_ABS);
        cpu.inst[op.ROL_ABX] = new ROL(op.ROL_ABX);

        cpu.inst[op.ROR_ACC] = new ROR(op.ROR_ACC);
        cpu.inst[op.ROR_ZP ] = new ROR(op.ROR_ZP );
        cpu.inst[op.ROR_ZPX] = new ROR(op.ROR_ZPX);
        cpu.inst[op.ROR_ABS] = new ROR(op.ROR_ABS);
        cpu.inst[op.ROR_ABX] = new ROR(op.ROR_ABX);

        cpu.inst[op.JSR    ] = new JSR();
        cpu.inst[op.RTS    ] = new RTS();
        cpu.inst[op.RTI    ] = new RTI();

        // 65C02
        cpu.inst[op.PHX    ] = new PHX();
        cpu.inst[op.PHY    ] = new PHY();
        cpu.inst[op.PLX    ] = new PLX();
        cpu.inst[op.PLY    ] = new PLY();
        
        cpu.inst[op.BRA    ] = new BRA();
        

    }

    private static void mapSystemCalls()
    {
        for ( int i = 0; i < 256; i++ )
        {
            JSC.call[i] = null;
        }

        JSC.call[sysop.LDPROC] = new LDPROC();
        JSC.call[sysop.STPROC] = new STPROC();
        JSC.call[sysop.PRTCPU] = new PRTCPU();
        JSC.call[sysop.SFTIRQ] = new SFTIRQ();
        JSC.call[sysop.NUMEXE] = new NUMEXE();
        JSC.call[sysop.IDLEPC] = new IDLEPC();

    }

    private static void initVectors()
    {
        mmu.store16(map.RSTVEC, map.ENTRY);
        mmu.store16(map.IRQVEC, map.IRQISR);
    }

}
