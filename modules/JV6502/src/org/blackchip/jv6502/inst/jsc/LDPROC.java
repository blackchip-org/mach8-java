/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.blackchip.jv6502.inst.jsc;

import org.blackchip.jv6502.cpu;

/**
 *
 * @author mcgann
 */
public class LDPROC implements Runnable
{
    public void run()
    {
        cpu.a = cpu.p.get();
    }
}
