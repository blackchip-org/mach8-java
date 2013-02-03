/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.blackchip.jv6502;

/**
 *
 * @author mcgann
 */
public class TrapException extends RuntimeException
{
    private int code;

    private TrapException()
    {
    }

    public TrapException(int code, String message)
    {
        super(message);
        this.code = code;
    }

    public TrapException(int code, String message, Throwable t)
    {
        super(message, t);
        this.code = code;
    }

    public int getCode()
    {
        return code;
    }

}
