/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.blackchip.system;

/**
 *
 * @author mcgann
 */
public class Page
{
    public String name;
    public short[] mem = new short[0x100];

    private Page()
    {
    }

    public Page(String name)
    {
        this.name = name;
    }
}
