/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.blackchip.system;

/**
 *
 * @author mcgann
 */
public class Bank
{
    public String name;
    public int pageCount = 0;
    public Page pages[];

    private Bank()
    {
    }

    public Bank(String name, int pageCount)
    {
        this.name = name;
        this.pageCount = pageCount;
        this.pages = new Page[pageCount];
    }

    public void store(int address, int value)
    {
        int page = sys.highByte(address);
        int offset = sys.lowByte(address);

        checkAccess(address, page, offset);

        pages[page].mem[offset] = (short)(value & sys.BYTE_MASK);
    }

    public int load(int address)
    {
        int page = sys.highByte(address);
        int offset = sys.lowByte(address);

        return pages[page].mem[offset];
    }

    private void checkAccess(int address, int page, int offset)
    {
        if ( page >= pageCount )
        {
            throw new IllegalArgumentException("Invalid address: " +
                    sys.toHex16(address) + " (page: " + sys.toHex(page) +
                    ", offset: " + sys.toHex(offset) + ")");
        }
    }
}
