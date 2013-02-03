/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.blackchip.system;

import java.io.Closeable;
import java.io.IOException;

/**
 *
 * @author mcgann
 */
public abstract class sys
{
    public static final int BYTE_MASK         = 0x000000ff;
    public static final int WORD_MASK         = 0x0000ffff;
    public static final int HIGH_BYTE_MASK    = 0x0000ff00;
    public static final int LOW_NIBBLE_MASK   = 0x0000000f;
    public static final int HIGH_NIBBLE_MASK  = 0x000000f0;

    public static final int BIT0              =   1;
    public static final int BIT1              =   2;
    public static final int BIT2              =   4;
    public static final int BIT3              =   8;
    public static final int BIT4              =  16;
    public static final int BIT5              =  32;
    public static final int BIT6              =  64;
    public static final int BIT7              = 128;
    
    public static int toBCD[]      = new int[0x100];
    public static int fromBCD[]    = new int[0x100];
    public static String charmap[] = new String[0x100];

    private sys()
    {
    }

    static
    {
        initBCD();
        initCharmap();
    }

    public static void log(String message)
    {
        System.out.println(message);
    }

    public static void log(String format, Object... args)
    {
        System.out.printf(format, args);
        System.out.println();
    }

    public static void nl()
    {
        System.out.println();
    }

    public static void println(String message)
    {
        System.out.println(message);
    }

    public static void println(String format, Object... args)
    {
        System.out.printf(format, args);
        System.out.println();
    }

    public static void printf(String format, Object... args)
    {
        System.out.printf(format, args);
    }

    public static String sprintf(String format, Object...args)
    {
        return String.format(format, args);
    }

    public static int to16(int low, int high)
    {
        return ( high << 8 ) + low;
    }

    public static String toHex(int value)
    {
        return String.format("%02X", value);
    }

    public static String toHex16(int value)
    {
        return String.format("%04X", value);
    }

    private static void initBCD()
    {
        for ( int i = 0; i < 256; i++ )
        {
            String s = String.format("%X", i);
            try
            {
                toBCD[i] = Integer.parseInt(s);
                fromBCD[toBCD[i]] = i;
            }
            catch ( NumberFormatException nfe )
            {
                toBCD[i] = -1;
                fromBCD[i] = -1;
            }
        }
    }

    private static void initCharmap()
    {
        for ( int i = 0; i < 256; i++ )
        {
            if ( i < 32 || i > 0x7f )
            {
                charmap[i] = ".";
            }
            else
            {
                charmap[i] = new String(new byte[] { (byte)i });
            }
        }
    }

    public static int nib(int high, int low)
    {
        return (high << 4 | low) & sys.BYTE_MASK;
    }

    public static int highByte(int value)
    {
        return (value >> 8) & sys.BYTE_MASK;
    }

    public static int lowByte(int value)
    {
        return value & sys.BYTE_MASK;
    }

    public static int lowNibble(int value)
    {
        return value & sys.LOW_NIBBLE_MASK;
    }

    public static int highNibble(int value)
    {
        return ( value >> 4 ) & sys.LOW_NIBBLE_MASK;
    }

    public static void release(Closeable c)
    {
        if ( c != null )
        {
            try
            {
                c.close();
            }
            catch ( IOException ie )
            {
            }
        }
    }


}
