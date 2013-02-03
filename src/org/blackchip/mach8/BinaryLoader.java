/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.blackchip.mach8;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.blackchip.jv6502.mmu;
import org.blackchip.jv6502.op;
import org.blackchip.jv6502.util.Assembler;
import org.blackchip.system.sys;

/**
 *
 * @author mcgann
 */
public class BinaryLoader
{
    public int pc = 0;

    private BinaryLoader()
    {
    }

    public BinaryLoader(int origin)
    {
        this.pc = origin;
    }

    public void link(String resource, int jumpTableAddress) throws IOException
    {
        int endAddress = doLoad(resource, pc);
        Assembler a = new Assembler(jumpTableAddress);
//        a.pb(op.JSR).pw(pc);
//        a.pb(op.RTS);
        a.pb(op.JMP_ABS).pw(pc);
        a.pb(op.NOP);

        sys.log("[load] $%s - $%s ($%s): %s", sys.toHex(pc), sys.toHex(endAddress),
                sys.toHex(jumpTableAddress), resource);
        pc = endAddress + 1;

    }

    public void load(String resource) throws IOException
    {
        int endAddress = doLoad(resource, pc);
        sys.log("[load] $%s - $%s: %s", sys.toHex(pc), sys.toHex(endAddress),
                resource);
        pc = endAddress + 1;
    }

    public void absl(String resource, int startAddress) throws IOException
    {
       int endAddress = doLoad(resource, startAddress);
       sys.log("[load] $%s - $%s: %s", sys.toHex(startAddress), sys.toHex(endAddress),
               resource);
    }

    private static int doLoad(String name, int address) throws IOException
    {
        InputStream is = null;

        try
        {
            is = mach8.class.getResourceAsStream(name);
            if ( is == null )
            {
                throw new IOException("Unable to load: " + name);
            }
            is = new BufferedInputStream(is);
            int start = address;
            int c = 0;
            while ( (c = is.read()) >= 0 )
            {
                mmu.store(address++, c);
            }
            int end = address - 1;
            is.close();
            return end;
        }
        finally
        {
            sys.release(is);
        }

    }


}
