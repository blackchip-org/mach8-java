/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.blackchip.mach8;

import java.io.IOException;
import javax.swing.Timer;
import javax.swing.UIManager;
import org.blackchip.jv6502.CPUSetup;
import org.blackchip.jv6502.cpu;
import org.blackchip.jv6502.mmu;
import org.blackchip.jv6502.op;
import org.blackchip.jv6502.util.Assembler;
import org.blackchip.monitor.MonitorFrame;
import org.blackchip.mtd4000.VideoSetup;
import org.blackchip.mtd4000.video;
import org.blackchip.system.MachControl;
import org.blackchip.system.map;
import org.blackchip.system.sys;

/**
 *
 * @author mcgann
 */
public class mach8
{

    private static MonitorFrame mf = null;
    private static final MachControl control = MachControlImpl.getInstance();
    
    public static void main(String[] args) throws IOException
    {
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Mach-8");
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch ( Exception e ) {
            sys.log("Unable to set system look and feel: " + 
                    e.getMessage());
        }

        cpu.param.enableJavaSystemCall = true;
        CPUSetup.standard();
        
        start();

        new Timer(100, new ClockIRQ()).start();
    }
    
    public static void start() throws IOException
    {
 
        VideoSetup.standard();

        BinaryLoader bl = new BinaryLoader(0);
        loadKernel();

        bl.absl("/asm/system/start.mob",       map.ENTRY);        
//        bl.absl("/asm/test/test.mob",       map.ENTRY_POINT + 2);
        
        


        mf = new MonitorFrame(control);
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                mf.setVisible(true);
            }
        });

        video.start();
        control.start();

        
    }

    public static void loadKernel() throws IOException
    {
        BinaryLoader bl = new BinaryLoader(map.KERNEL_START);

        bl.load("/asm/kernel/irqisr.mob");
        bl.link("/asm/kernel/cprint.mob",   map.CPRINT);
        bl.link("/asm/kernel/mult8x8.mob",  map.MULT_8x8);
        bl.link("/asm/kernel/curpos.mob",   map.CURPOS);
        bl.link("/asm/kernel/sprint.mob",   map.SPRINT);
        bl.link("/asm/kernel/memcpy.mob",   map.MEMCPY);
        bl.link("/asm/kernel/scroll.mob",   map.SCROLL);
        bl.link("/asm/kernel/banner.mob",   map.BANNER);
        bl.link("/asm/kernel/ready.mob",    map.READY);
        bl.link("/asm/kernel/idle.mob",     map.IDLE);
        bl.link("/asm/kernel/curup.mob",    map.CURUP);
        bl.link("/asm/kernel/curdwn.mob",   map.CURDWN);
        bl.link("/asm/kernel/curlft.mob",   map.CURLFT);
        bl.link("/asm/kernel/currgt.mob",   map.CURRGT);
        bl.link("/asm/kernel/curhme.mob",   map.CURHME);
        bl.link("/asm/kernel/curdel.mob",   map.CURDEL);
        bl.link("/asm/kernel/sysret.mob",   map.SYSRET);
        bl.link("/asm/kernel/easter.mob",   map.EASTER);

        Assembler a = new Assembler(0x42);
        a.pb(op.JMP_ABS).pw(map.EASTER); 

    }

    public static void reboot() 
    {
        video.stop();
        mf.setVisible(false);
        mf.dispose();
        mf = null;
        
        mmu.erase();
        CPUSetup.reboot();
        try 
        { 
            start();
        }
        catch ( IOException ie ) 
        {
            throw new IllegalStateException(ie);
        }
    }

}
