/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.blackchip.mtd4000;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JFrame;

/**
 *
 * @author mcgann
 */
public class ConsoleFrame extends JFrame
{

    public ConsoleFrame()
    {
        this.setTitle("Mach-8");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        Container pane = this.getContentPane();

        ConsolePanel cp = new ConsolePanel();

        // Too lazy to do this properly
        String os = System.getProperty("os.name");
        if ( os.equals("Linux") ) {
            cp.setPreferredSize(new Dimension(840, 840));
        } else if ( os.contains("Windows") ) {
            cp.setPreferredSize(new Dimension(840, 960));
        } else {
            cp.setPreferredSize(new Dimension(840, 760));
        }

        pane.add(cp, BorderLayout.CENTER);

        this.addKeyListener(new KeyboardSupport());
        
        this.pack();
    }



}
