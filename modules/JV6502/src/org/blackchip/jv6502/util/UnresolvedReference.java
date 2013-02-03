/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.blackchip.jv6502.util;

/**
 *
 * @author mcgann
 */
public class UnresolvedReference
{
    private ReferenceType ref;
    private String name;
    private int address;

    private UnresolvedReference()
    {
    }

    public UnresolvedReference(ReferenceType ref, String name, int address)
    {
        this.ref = ref;
        this.name = name;
        this.address = address;
    }

    public ReferenceType getReferenceType()
    {
        return ref;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the address
     */
    public int getAddress() {
        return address;
    }


}
