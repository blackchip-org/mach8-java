/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.blackchip.mach8;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.blackchip.jv6502.cpu;

/**
 *
 * @author mcgann
 */
public class ClockIRQ implements ActionListener
{

    public void actionPerformed(ActionEvent e)
    {
        if ( cpu.running ) {
            cpu.interrupt();
        }
    }
}