/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.blackchip.jv6502.util;

import org.blackchip.system.*;
import java.util.HashMap;
import java.util.Map;
import org.blackchip.jv6502.Instruction;
import org.blackchip.jv6502.cpu;
import org.blackchip.jv6502.label;
import org.blackchip.system.map;
import org.blackchip.jv6502.mmu;
import org.blackchip.system.sys;

/**
 *
 * @author mcgann
 */
public class Disassembler
{
    private InstructionPrinter printer = new InstructionPrinter();
    private Map<Integer,String> symbols = new HashMap<Integer,String>();

    private int address;
    private boolean printSymbols = true;

    public Disassembler(int address)
    {
        this.address = address;
        symbols.putAll(label.memoryMapToLabel);
        printer.importSymbolTable(label.memoryMapToAddress);
    }

    public Disassembler()
    {
        this(map.ENTRY);
    }
    
    public int getAddress()
    {
        return address;
    }

    public void setAddress(int address)
    {
        this.address = address;
    }

    public void setAddress(Assembler a)
    {
        this.address = a.getAddress();
    }

    public void setPrintingSymbols(boolean v)
    {
        this.printSymbols = v;
    }

    public void print()
    {
        sys.printf(next());
    }
    
    public String next()
    {
        StringBuilder sb = new StringBuilder();

        int startingAddress = address;

        int opcode = mmu.load(address++);
        if ( opcode < 0 || opcode > 255 )
        {
            throw new IllegalArgumentException("Invalid opcode: " + opcode);
        }

        if ( printSymbols )
        {
            String sym = printer.lookup(startingAddress);
            sym = ( sym == null ) ? "" : sym + ":";
            sb.append(sys.sprintf("%8s ", sym));
        }

        Instruction op = cpu.inst[opcode];
        
        int[] operands = new int[op.getOperandLength()];
        for ( int i = 0; i < op.getOperandLength(); i++ )
        {
            operands[i] = mmu.load(address++);
        }
        sb.append(sys.sprintf("%04X: %02X ", startingAddress, opcode));
        String arg1 = op.getOperandLength() == 0
                ? "   " : String.format("%02X ", (byte)operands[0]);
        String arg2 = op.getOperandLength() != 2
                ? "   " : String.format("%02X ", (byte)operands[1]);
        sb.append(sys.sprintf(arg1 + arg2));
        op.accept(startingAddress, printer, operands);
        sb.append(printer.getResult() + "\n");

        return sb.toString();
    }

    public void print(int endingAddress)
    {
        while ( this.address < endingAddress )
        {
            print();
        }
    }

    public void print(Assembler a)
    {
//        printer.clearSymbolTable();
        printer.importSymbolTable(a.getSymbolTable());
        print(a.getAddress());
//        printer.clearSymbolTable();
    }

}
