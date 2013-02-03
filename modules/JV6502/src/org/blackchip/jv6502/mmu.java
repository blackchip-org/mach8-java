/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.blackchip.jv6502;

import org.blackchip.system.Bank;
import org.blackchip.system.Page;
import org.blackchip.system.map;
import org.blackchip.system.sys;


/**
 *
 * @author mcgann
 */
public final class mmu
{
    public static final int ADDRESS_SPACE = 0x10000;

    public static Bank bank = new Bank("Bank 0", 256);

    public static boolean debug = false;

    private mmu()
    {
    }

    public static void store(int address, int value)
    {
        if ( debug )
        {
            sys.log("mem[%04X] <= %02X", address, value);
        }
        bank.store(address, value);
    }

    public static void store(int address, int offset, int value)
    {
        store(address + (offset & sys.BYTE_MASK), value);
    }

    public static void store16(int address, int value)
    {
        store(address, value & sys.BYTE_MASK);
        store(address + 1, (value & sys.HIGH_BYTE_MASK) >> 8);
    }

    public static void storeZP(int address, int value)
    {
        store(address & sys.BYTE_MASK, value);
    }

    public static void storeZP(int address, int offset, int value)
    {
        storeZP(address + (offset & sys.BYTE_MASK), value);
    }

    public static void storeIZX(int address, int offset, int value)
    {
        store(load16ZP(address + (offset & sys.BYTE_MASK)), value);
    }

    public static void storeIZY(int address, int offset, int value)
    {
        store(load16ZP(address) + (offset & sys.BYTE_MASK), value);
    }

    public static int load(int address)
    {
        if ( debug )
        {
            sys.log("mem[%04X] => %02X", address,
                    bank.load(address));
        }        
        return bank.load(address);
    }

    public static int load(int address, int offset)
    {
        return load(address + (offset & sys.BYTE_MASK));
    }

    public static int load16(int address)
    {
        return (load(address + 1) << 8) + load(address);
    }
    
    public static int loadZP(int address)
    {
        return load(address & sys.BYTE_MASK);
    }
    
    public static int loadZP(int address, int offset)
    {
        return loadZP(address + (offset & sys.BYTE_MASK));
    }

    public static int loadIZX(int address, int offset)
    {
        return load(load16ZP(address + (offset & sys.BYTE_MASK)));
    }

    public static int loadIZY(int address, int offset)
    {
        return load(load16ZP(address) + (offset & sys.BYTE_MASK));
    }

    private static int load16ZP(int address)
    {
        return (loadZP(address + 1) << 8) + loadZP(address);
    }

    public static void clear()
    {
        for ( int i = 0; i < 256; i++ )
        {
            mmu.bank.pages[i] = new Page("Page");
        }
        debug = false;
    }
    
    public static void erase()
    {
        Page empty = new Page("Empty");
        for ( int i = 0; i < 256; i++ ) 
        {
            System.arraycopy(empty.mem, 0, mmu.bank.pages[i].mem, 0, 256);
        }
    }

    public static String dump(int startAddress, int endAddress)
    {
        StringBuilder sb = new StringBuilder();

        if ( startAddress > endAddress )
        {
            throw new IllegalArgumentException("startAddress > endAddress");
        }
        if ( startAddress == endAddress )
        {
            return "";
        }

        boolean previousDebug = debug;
        if ( debug ) {
            debug = false;
        }

        int startPara = startAddress >> 3;
        int endPara = endAddress >> 3;

        for ( int para = startPara; para <= endPara; para++ ) {
            int paraAddress = para << 3;
            sb.append(String.format("%04X: ", (short)paraAddress));
            for ( int i = 0; i < 8; i++ ) {
                int address = paraAddress + i;
                int value = mmu.load(address);
                sb.append(String.format("%02X ", value));
            }
            sb.append("    ");
            for ( int i = 0; i < 8; i++ ) {
                int address = paraAddress + i;
                int value = mmu.load(address);
                sb.append(sys.charmap[value]);
            }
            sb.append("\n");
        }
//        sys.nl();
        
        debug = previousDebug;
        return sb.toString();
    }

    public static void print(int startAddress, int endAddress)
    {
        System.out.print(dump(startAddress, endAddress));
    }


}
