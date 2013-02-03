/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.blackchip.mtd4000;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.Timer;
import org.blackchip.system.Bank;
import org.blackchip.system.sys;

/**
 *
 * @author mcgann
 */
public final class video
{
    public static Color[] pal = new Color[16];

    public static Bank bank = null;

    public static int borderSize = 20;
    public static int leftInset = 0;
    public static int rightInset = 0;
    public static int topInset = 0;
    public static int bottomInset = 0;

    public static int textWidth = 80;
    public static int textHeight = 40;

    public static Font font = new Font("Monospaced", Font.BOLD, 16);

    private static ConsoleFrame frame = null;
    private static Timer timer = null;

    static
    {
//        loadFont();
    }

    private video()
    {
    }

    public static void start()
    {
        frame = new ConsoleFrame();

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                frame.setVisible(true);
            }
        });

        timer = new Timer(20, new ActionListener()
        {
            public void actionPerformed(ActionEvent arg0) {
                frame.repaint();
            }
        });
        timer.start();
    }

    public static void stop()
    {
        timer.stop();
        timer = null;
        frame.setVisible(false);
        frame.dispose();
        frame = null;
    }

    private static void loadFont()
    {
        InputStream is = null;
        try
        {
            is = video.class.getResourceAsStream("/Adore64.TTF");
            if ( is == null )
            {
                throw new IllegalStateException("No font!");
            }
            font = Font.createFont(Font.TRUETYPE_FONT, is);
            font = font.deriveFont(14f);
            is.close();
        }
        catch ( Exception ie )
        {
            throw new IllegalStateException(ie);
        }
        finally
        {
            sys.release(is);
        }
    }

}
