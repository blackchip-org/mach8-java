/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.blackchip.jv6502.util;

import java.util.HashMap;
import java.util.Map;
import org.blackchip.jv6502.Instruction;
import org.blackchip.jv6502.InstructionVisitor;
import org.blackchip.jv6502.label;
import org.blackchip.jv6502.op;
import org.blackchip.system.sys;

/**
 *
 * @author mcgann
 */
public class InstructionPrinter implements InstructionVisitor
{
    private Map<Integer,String> symbols = new HashMap<Integer,String>();
    private String result = "";

    public void importSymbolTable(Map<String,Integer> map)
    {
        for ( Map.Entry<String,Integer> entry: map.entrySet() )
        {
            symbols.put(entry.getValue(), entry.getKey());
        }
    }

    public void clearSymbolTable()
    {
        symbols.clear();
    }

    public boolean hasSymbols()
    {
        return !symbols.isEmpty();
    }

    public String lookup(int address)
    {
        return symbols.get(address);
    }

    private void invalid(Instruction i)
    {
        throw new IllegalArgumentException(String.format(
                "Invalid opcode for %s: %02X", i.getLabel(), i.getOpcode()));
    }

    public String getResult()
    {
        return result;
    }

    private void printf(String format, Object... args)
    {
        result = sys.sprintf(format, args);
    }

    private String label(int address)
    {
        String sym = symbols.get(address);
        if ( sym == null ) 
        {
            sym = ( address < 0x100 ) ? "$" + sys.toHex(address) 
                                      : "$" + sys.toHex16(address);
        }
        return sym;
    }
    
    private void printIMM(String inst, int a0)
    {
        printf("%s #$%02X", inst, a0);
    }

    private void printZP(String inst, int a0)
    {
        printf("%s %s", inst, label(a0));
    }

    private void printZPX(String inst, int a0)
    {
        printf("%s %s,X", inst, label(a0));
    }

    private void printABS(String inst, int a0, int a1)
    {
        printf("%s %s", inst, label(sys.to16(a0, a1)));
    }

    private void printABX(String inst, int a0, int a1)
    {
        printf("%s %s,X", inst, label(sys.to16(a0, a1)));
    }

    private void printABY(String inst, int a0, int a1)
    {
        printf("%s %s,Y", inst, label(sys.to16(a0, a1)));
    }

    private void printIZX(String inst, int a0)
    {
        printf("%s (%s,X)", inst, label(a0));
    }

    private void printIZY(String inst, int a0)
    {
        printf("%s (%s),Y", inst, label(a0));
    }

    public void visitLDA(Instruction i, int... a)
    {
        String l = i.getLabel();
        switch ( i.getOpcode() )
        {
            case op.LDA_IMM: printIMM(l, a[0]);       break;
            case op.LDA_ZP : printZP (l, a[0]);       break;
            case op.LDA_ZPX: printZPX(l, a[0]);       break;
            case op.LDA_ABS: printABS(l, a[0], a[1]); break;
            case op.LDA_ABX: printABX(l, a[0], a[1]); break;
            case op.LDA_ABY: printABY(l, a[0], a[1]); break;
            case op.LDA_IZX: printIZX(l, a[0]);       break;
            case op.LDA_IZY: printIZY(l, a[0]);       break;

            default: invalid(i);
        }
    }

    public void visitLDX(Instruction i, int... a)
    {
        String l = i.getLabel();
        switch ( i.getOpcode() )
        {
            case op.LDX_IMM: printIMM(l, a[0]);       break;
            case op.LDX_ZP : printZP (l, a[0]);       break;
            case op.LDX_ZPY: printZPX(l, a[0]);       break;
            case op.LDX_ABS: printABS(l, a[0], a[1]); break;
            case op.LDX_ABY: printABY(l, a[0], a[1]); break;

            default: invalid(i);
        }
    }

    public void visitLDY(Instruction i, int... a)
    {
        String l = i.getLabel();
        switch ( i.getOpcode() )
        {
            case op.LDY_IMM: printIMM(l, a[0]);       break;
            case op.LDY_ZP : printZP (l, a[0]);       break;
            case op.LDY_ZPX: printZPX(l, a[0]);       break;
            case op.LDY_ABS: printABS(l, a[0], a[1]); break;
            case op.LDY_ABX: printABX(l, a[0], a[1]); break;

            default: invalid(i);
        }
    }

    public void visitSTA(Instruction i, int... a)
    {
        String l = i.getLabel();
        switch ( i.getOpcode() )
        {
            case op.STA_ZP : printZP (l, a[0]);       break;
            case op.STA_ZPX: printZPX(l, a[0]);       break;
            case op.STA_ABS: printABS(l, a[0], a[1]); break;
            case op.STA_ABX: printABX(l, a[0], a[1]); break;
            case op.STA_ABY: printABY(l, a[0], a[1]); break;
            case op.STA_IZX: printIZX(l, a[0]);       break;
            case op.STA_IZY: printIZY(l, a[0]);       break;

            default: invalid(i);
        }
    }

    public void visitSTX(Instruction i, int... a)
    {
        String l = i.getLabel();
        switch ( i.getOpcode() )
        {
            case op.STX_ZP : printZP (l, a[0]);       break;
            case op.STX_ZPY: printZPX(l, a[0]);       break;
            case op.STX_ABS: printABS(l, a[0], a[1]); break;

            default: invalid(i);
        }
    }

    public void visitSTY(Instruction i, int... a)
    {
        String l = i.getLabel();
        switch ( i.getOpcode() )
        {
            case op.STY_ZP : printZP (l, a[0]);       break;
            case op.STY_ZPX: printZPX(l, a[0]);       break;
            case op.STY_ABS: printABS(l, a[0], a[1]); break;

            default: invalid(i);
        }
    }

    public void visitDEC(Instruction i, int... a)
    {
        String l = i.getLabel();
        switch ( i.getOpcode() )
        {
            case op.DEC_ZP : printZP (l, a[0]);         break;
            case op.DEC_ZPX: printZPX(l, a[0]);         break;
            case op.DEC_ABS: printABS(l, a[0], a[1]);   break;
            case op.DEC_ABX: printABX(l, a[0], a[1]);   break;

            default: invalid(i);
        }
    }

    public void visitINC(Instruction i, int... a)
    {
        String l = i.getLabel();
        switch ( i.getOpcode() )
        {
            case op.INC_ZP : printZP (l, a[0]);         break;
            case op.INC_ZPX: printZPX(l, a[0]);         break;
            case op.INC_ABS: printABS(l, a[0], a[1]);   break;
            case op.INC_ABX: printABX(l, a[0], a[1]);   break;

            default: invalid(i);
        }
    }

    public void visitASL(Instruction i, int... a)
    {
        String l = i.getLabel();
        switch ( i.getOpcode() )
        {
            case op.ASL_ACC: printf("%s A", l);     break;
            case op.ASL_ZP : printZP (l, a[0]);         break;
            case op.ASL_ZPX: printZPX(l, a[0]);         break;
            case op.ASL_ABS: printABS(l, a[0], a[1]);   break;
            case op.ASL_ABX: printABX(l, a[0], a[1]);   break;

            default: invalid(i);
        }
    }

    public void visitLSR(Instruction i, int... a)
    {
        String l = i.getLabel();
        switch ( i.getOpcode() )
        {
            case op.LSR_ACC: printf("%s A", l);     break;
            case op.LSR_ZP : printZP (l, a[0]);         break;
            case op.LSR_ZPX: printZPX(l, a[0]);         break;
            case op.LSR_ABS: printABS(l, a[0], a[1]);   break;
            case op.LSR_ABX: printABX(l, a[0], a[1]);   break;

            default: invalid(i);
        }
    }

    public void visitROL(Instruction i, int... a)
    {
        String l = i.getLabel();
        switch ( i.getOpcode() )
        {
            case op.ROL_ACC: printf("%s A", l);     break;
            case op.ROL_ZP : printZP (l, a[0]);         break;
            case op.ROL_ZPX: printZPX(l, a[0]);         break;
            case op.ROL_ABS: printABS(l, a[0], a[1]);   break;
            case op.ROL_ABX: printABX(l, a[0], a[1]);   break;

            default: invalid(i);
        }
    }

    public void visitROR(Instruction i, int... a)
    {
        String l = i.getLabel();
        switch ( i.getOpcode() )
        {
            case op.ROR_ACC: printf("%s A", l);     break;
            case op.ROR_ZP : printZP (l, a[0]);         break;
            case op.ROR_ZPX: printZPX(l, a[0]);         break;
            case op.ROR_ABS: printABS(l, a[0], a[1]);   break;
            case op.ROR_ABX: printABX(l, a[0], a[1]);   break;

            default: invalid(i);
        }
    }

    public void visitSimple(Instruction i)
    {
        printf(i.getLabel());
    }

    public void visitADC(Instruction i, int... a)
    {
        String l = i.getLabel();
        switch ( i.getOpcode() )
        {
            case op.ADC_IMM: printIMM(l, a[0]);       break;
            case op.ADC_ZP : printZP (l, a[0]);       break;
            case op.ADC_ZPX: printZPX(l, a[0]);       break;
            case op.ADC_ABS: printABS(l, a[0], a[1]); break;
            case op.ADC_ABX: printABX(l, a[0], a[1]); break;
            case op.ADC_ABY: printABY(l, a[0], a[1]); break;
            case op.ADC_IZX: printIZX(l, a[0]);       break;
            case op.ADC_IZY: printIZY(l, a[0]);       break;
            
            default: invalid(i);
        }
    }

    public void visitSBC(Instruction i, int... a)
    {
        String l = i.getLabel();
        switch ( i.getOpcode() )
        {
            case op.SBC_IMM: printIMM(l, a[0]);       break;
            case op.SBC_ZP : printZP (l, a[0]);       break;
            case op.SBC_ZPX: printZPX(l, a[0]);       break;
            case op.SBC_ABS: printABS(l, a[0], a[1]); break;
            case op.SBC_ABX: printABX(l, a[0], a[1]); break;
            case op.SBC_ABY: printABY(l, a[0], a[1]); break;
            case op.SBC_IZX: printIZX(l, a[0]);       break;
            case op.SBC_IZY: printIZY(l, a[0]);       break;

            default: invalid(i);
        }
    }

    public void visitAND(Instruction i, int... a)
    {
        String l = i.getLabel();
        switch ( i.getOpcode() )
        {
            case op.AND_IMM: printIMM(l, a[0]);       break;
            case op.AND_ZP : printZP (l, a[0]);       break;
            case op.AND_ZPX: printZPX(l, a[0]);       break;
            case op.AND_ABS: printABS(l, a[0], a[1]); break;
            case op.AND_ABX: printABX(l, a[0], a[1]); break;
            case op.AND_ABY: printABY(l, a[0], a[1]); break;
            case op.AND_IZX: printIZX(l, a[0]);       break;
            case op.AND_IZY: printIZY(l, a[0]);       break;

            default: invalid(i);
        }
    }

    public void visitEOR(Instruction i, int... a)
    {
        String l = i.getLabel();
        switch ( i.getOpcode() )
        {
            case op.EOR_IMM: printIMM(l, a[0]);       break;
            case op.EOR_ZP : printZP (l, a[0]);       break;
            case op.EOR_ZPX: printZPX(l, a[0]);       break;
            case op.EOR_ABS: printABS(l, a[0], a[1]); break;
            case op.EOR_ABX: printABX(l, a[0], a[1]); break;
            case op.EOR_ABY: printABY(l, a[0], a[1]); break;
            case op.EOR_IZX: printIZX(l, a[0]);       break;
            case op.EOR_IZY: printIZY(l, a[0]);       break;

            default: invalid(i);
        }
    }

    public void visitORA(Instruction i, int... a)
    {
        String l = i.getLabel();
        switch ( i.getOpcode() )
        {
            case op.ORA_IMM: printIMM(l, a[0]);       break;
            case op.ORA_ZP : printZP (l, a[0]);       break;
            case op.ORA_ZPX: printZPX(l, a[0]);       break;
            case op.ORA_ABS: printABS(l, a[0], a[1]); break;
            case op.ORA_ABX: printABX(l, a[0], a[1]); break;
            case op.ORA_ABY: printABY(l, a[0], a[1]); break;
            case op.ORA_IZX: printIZX(l, a[0]);       break;
            case op.ORA_IZY: printIZY(l, a[0]);       break;

            default: invalid(i);
        }
    }

    public void visitBIT(Instruction i, int... a)
    {
        String l = i.getLabel();
        switch ( i.getOpcode() )
        {
            case op.BIT_ZP : printZP (l, a[0]);       break;
            case op.BIT_ABS: printABS(l, a[0], a[1]); break;

            default: invalid(i);
        }
    }

    public void visitCMP(Instruction i, int... a)
    {
        String l = i.getLabel();
        switch ( i.getOpcode() )
        {
            case op.CMP_IMM: printIMM(l, a[0]);       break;
            case op.CMP_ZP : printZP (l, a[0]);       break;
            case op.CMP_ZPX: printZPX(l, a[0]);       break;
            case op.CMP_ABS: printABS(l, a[0], a[1]); break;
            case op.CMP_ABX: printABX(l, a[0], a[1]); break;
            case op.CMP_ABY: printABY(l, a[0], a[1]); break;
            case op.CMP_IZX: printIZX(l, a[0]);       break;
            case op.CMP_IZY: printIZY(l, a[0]);       break;

            default: invalid(i);
        }
    }

    public void visitCPX(Instruction i, int... a)
    {
        String l = i.getLabel();
        switch ( i.getOpcode() )
        {
            case op.CPX_IMM: printIMM(l, a[0]);       break;
            case op.CPX_ZP : printZP (l, a[0]);       break;
            case op.CPX_ABS: printABS(l, a[0], a[1]); break;

            default: invalid(i);
        }
    }

    public void visitCPY(Instruction i, int... a)
    {
        String l = i.getLabel();
        switch ( i.getOpcode() )
        {
            case op.CPY_IMM: printIMM(l, a[0]);       break;
            case op.CPY_ZP : printZP (l, a[0]);       break;
            case op.CPY_ABS: printABS(l, a[0], a[1]); break;

            default: invalid(i);
        }
    }

    public void visitJMP(Instruction i, int... a)
    {
        String l = i.getLabel();
        switch ( i.getOpcode() )
        {
            case op.JMP_ABS: printABS(l, a[0], a[1]); break;
            case op.JMP_IND:
                printf("JMP ($%04X)", sys.to16(a[0], a[1])); break;

            default: invalid(i);
        }
    }

    public void visitJSR(Instruction i, int... a)
    {
        int address = sys.to16(a[0], a[1]);
        String sym = symbols.get(address);
        if ( sym == null )
        {
            sym = "$" + sys.toHex16(address);
        }
        printf("%s %s", i.getLabel(), sym);
    }

    public void visitBranch(int opaddr, Instruction i, int... a)
    {
        int abs = opaddr + (byte)a[0] + 2;
        String sym = symbols.get(abs);
        if ( sym == null )
        {
            printf("%s $%04X", i.getLabel(), abs);
        }
        else
        {
            printf("%s %s", i.getLabel(), sym);
        }
    }

    public void visitILL(Instruction i)
    {
        printf("?%02X", i.getOpcode());
    }

    public void visitJSC(int... a)
    {
        printf("JSC %s", label.sysop(a[0]));
    }

}
