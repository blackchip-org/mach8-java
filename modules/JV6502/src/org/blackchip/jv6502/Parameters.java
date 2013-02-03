/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.blackchip.jv6502;

/**
 *
 * @author mcgann
 */
public class Parameters
{
    Parameters()
    {
    }

    public boolean exceptionOnTrap = false;
    public boolean printExecution = false;
    public boolean logOnBreak = false;
    public boolean logOnTrap = true;
    public boolean trapOnStackError = false;
    public boolean singleStep = false;
    public boolean enableJavaSystemCall = false;

}
