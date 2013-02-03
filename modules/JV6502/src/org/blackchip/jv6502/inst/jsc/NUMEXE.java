/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.blackchip.jv6502.inst.jsc;

import org.blackchip.jv6502.cpu;
import org.blackchip.system.sys;

/**
 *
 * @author mcgann
 */
public class NUMEXE implements Runnable
{

    public void run() 
    {
        sys.log("Execution count: " + cpu.executionCount);
    }
}
