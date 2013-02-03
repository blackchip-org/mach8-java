/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.blackchip.mtd4000;

import org.blackchip.jv6502.CPUSetup;
import org.blackchip.system.map;
import org.blackchip.jv6502.mmu;
import org.blackchip.system.sys;
import org.blackchip.system.vmap;

/**
 *
 * @author mcgann
 */
public class RunStandalone {

    public static void main(String args[]) throws InterruptedException
    {

        CPUSetup.standard();
        VideoSetup.standard();

        
        for ( int i = 33; i <= 0x7f; i++ )
        {
            mmu.store(map.TEXT_START + i - 33, i);
        }
        int y = 0;
        for ( int i = 33; i <= 0x7f; i++, y++ )
        {
            mmu.store(map.COLOR_START + y, y);
        }
        
        video.start();
        for ( int i = 0; i < 16; i++ )
        {
            mmu.store(map.BORDER_COLOR, i);
            Thread.currentThread().sleep(1000);
        }
    }

}
