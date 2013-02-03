/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.blackchip.jv6502.util;

import org.blackchip.system.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.blackchip.system.map;
import org.blackchip.jv6502.mmu;
import org.blackchip.jv6502.op;

/**
 *
 * @author mcgann
 */
public class Assembler
{

    private int address;
    private Map<String,Integer> symbols = new HashMap<String,Integer>();
    private List<UnresolvedReference> unresolved = new LinkedList<UnresolvedReference>();

    public Assembler(int address)
    {
        this.address = address;
    }

    public Assembler()
    {
        this(map.ENTRY);
    }

    public Assembler pb(int byteValue)
    {
        mmu.store(address++, byteValue);
        return this;
    }

    public Assembler pw(int wordValue)
    {
        mmu.store16(address, wordValue);
        address += 2;
        return this;
    }

    public Assembler label(String name)
    {
        return define(name, address);
    }

    public Assembler define(String name, int location)
    {
        if ( symbols.containsKey(name) )
        {
            throw new IllegalArgumentException("Label already defined: " +
                    name);
        }
        symbols.put(name, location);
        return this;
    }

    public Assembler rel(String name)
    {
        if ( !symbols.containsKey(name) )
        {
            UnresolvedReference ur = new UnresolvedReference(
                    ReferenceType.RELATIVE, name, address);
            unresolved.add(ur);
            pb(0x00);
        }
        else
        {
            int a = symbols.get(name);
            pb(a - address -1);
        }
        return this;
    }

    public Assembler abs(String name)
    {
        if ( !symbols.containsKey(name) )
        {
            UnresolvedReference ur = new UnresolvedReference(
                    ReferenceType.ABSOLUTE, name, address);
            unresolved.add(ur);
            pw(0x0000);
        }
        else
        {
            int a = symbols.get(name);
            pw(a);
        }
        return this;
    }

    public Assembler brk()
    {
        pb(op.BRK);
        pb(op.NOP);
        return this;
    }

    public Assembler setAddress(int address)
    {
        this.address = address;
        return this;
    }

    public Assembler setAddress(String label)
    {
        if ( !symbols.containsKey(label) )
        {
            throw new IllegalArgumentException("Undefined label: " + label);
        }
        this.address = symbols.get(label);
        return this;
    }

    public int getAddress()
    {
        return address;
    }

    public Map<String,Integer> getSymbolTable()
    {
        return Collections.unmodifiableMap(symbols);
    }

    public void resolve()
    {
        int origAddress = address;

        for ( UnresolvedReference ref: unresolved )
        {
            if ( !symbols.containsKey(ref.getName()) )
            {
                throw new IllegalStateException("Unresolved reference to: " +
                        ref.getName());
            }
            address = ref.getAddress();
            if ( ref.getReferenceType() == ReferenceType.RELATIVE )
            {
                pb(symbols.get(ref.getName()) - address - 1);
            }
            else
            {
                pw(symbols.get(ref.getName()));
            }
        }
        this.address = origAddress;
    }

}
