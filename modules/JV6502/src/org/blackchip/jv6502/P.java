// $Id: P.java 16 2010-12-30 00:45:02Z mcgann $
package org.blackchip.jv6502;

/*******************************************************************************
 *
 * Processor status register.
 *
 * @version {@code $Revision: 16 $ $Date: 2010-12-29 19:45:02 -0500 (Wed, 29 Dec 2010) $}
 *
 ******************************************************************************/
public class P
{

    /**
     * Negative flag. Set if bit 7 of the accumulator is set.
     */
    public boolean n;

    /**
     * Overflow flag. Set if the addition of two liked-signed numers or the
     * subtration of two unlike-signed numbers produces a result greater than
     * +127 or less than -128.
     */
    public boolean v;

    /**
     * BRK command flag. Set if an interrupt was caused by a BRK, reset if
     * caused by an external interrupt.
     */
    public boolean b;

    /**
     * BCD mode flag. If set, binary coded decimal math is active.
     */
    public boolean d;

    /**
     * IRQ disable flag. Set if maskable interrupts are disabled.
     */
    public boolean i;

    /**
     * Zero flag. Set if the result of the last operation
     * (load/inc/dec/add/sub) was zero.
     */
    public boolean z;

    /**
     * Carry flag. Set if the add produced a carry, or if the subtraction
     * produced a borrow. Also holds bits after a logical shift.
     */
    public boolean c;

    /**
     * Unused flag, always true
     */
    public boolean x;

    public int get()
    {
        return ( n ? 1 : 0 ) << 7 |
               ( v ? 1 : 0 ) << 6 |
               ( x ? 1 : 0 ) << 5 |
               ( b ? 1 : 0 ) << 4 |
               ( d ? 1 : 0 ) << 3 |
               ( i ? 1 : 0 ) << 2 |
               ( z ? 1 : 0 ) << 1 |
               ( c ? 1 : 0 );
    }

    public void set(int p)
    {
        c = ( p &   1 ) != 0;
        z = ( p &   2 ) != 0;
        i = ( p &   4 ) != 0;
        d = ( p &   8 ) != 0;
        b = ( p &  16 ) != 0;
        v = ( p &  64 ) != 0;
        n = ( p & 128 ) != 0;
    }

    public void print()
    {
        System.out.print("n: " + n + "\n" +
                         "v: " + v + "\n" +
                         "-: " + x + "\n" +
                         "b: " + b + "\n" +
                         "d: " + d + "\n" +
                         "i: " + i + "\n" +
                         "z: " + z + "\n" +
                         "c: " + c + "\n");
    }
}
