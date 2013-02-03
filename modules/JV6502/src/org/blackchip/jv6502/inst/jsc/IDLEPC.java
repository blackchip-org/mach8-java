/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.blackchip.jv6502.inst.jsc;

import org.blackchip.jv6502.mmu;
import org.blackchip.system.map;
import org.blackchip.system.sys;

/**
 *
 * @author mcgann
 */
public class IDLEPC implements Runnable
{
    private long idleCount = 0;

    public void run()
    {
        idleCount++;
        if ( idleCount % 10 == 0 )
        {
            int jiffyCount = mmu.load(map.JIFFY_COUNT);
//            sys.log("[cpu] Idle, JIFFY_COUNT = " + jiffyCount);
        }
        try
        {
            // Will be awaken earlier by clock IRQ
            Thread.sleep(1000);
        }
        catch ( InterruptedException ie )
        {
            // Do nothing
        }
    }
}
