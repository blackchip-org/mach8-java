/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.blackchip.mtd4000;

import org.blackchip.system.vmap;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.font.LineMetrics;
import javax.swing.JPanel;
import org.blackchip.system.map;
import org.blackchip.jv6502.mmu;
import org.blackchip.system.sys;

/**
 *
 * @author mcgann
 */
public class ConsolePanel extends JPanel
{

    public int fontWidth;
    public int fontHeight;
    public int screenHeight;
    public int screenWidth;

    private int originX;
    private int originY;
    private int descent;
    private int ascent;
    private int leading;

    private boolean compute = true;

    public char[] text = new char[video.textWidth * video.textHeight];

    public ConsolePanel()
    {
    }

    public void computeMetrics(Graphics g)
    {
        FontMetrics fm = g.getFontMetrics(video.font);

        StringBuilder sb = new StringBuilder();
        for ( int i = 0; i <= 0xff; i++ )
        {
            sb.append(sys.charmap[i]);
        }
        LineMetrics lm = fm.getLineMetrics(sb.toString(), g);

        descent = (int)lm.getDescent();
        ascent = (int)lm.getAscent();
        leading = (int)lm.getLeading();

        fontHeight = descent + ascent + leading + 3;
        fontWidth = fm.charWidth('X');

        screenWidth =  ( fontWidth * video.textWidth ) +
                       ( video.borderSize * 2 ) +
                       ( video.leftInset + video.rightInset );
        
        screenHeight = ( fontHeight * video.textHeight ) +
                       ( video.borderSize * 2 ) +
                       ( video.topInset + video.bottomInset );

        originX = video.borderSize + video.leftInset;
        originY = video.borderSize + video.topInset + fontHeight;

        System.out.println("Width: " + screenWidth + ", height: " + screenHeight);
    }

    int bgcolor = 0;
    int fgcolor = 0;

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponents(g);

        if ( compute )
        {
            computeMetrics(g);
            compute = false;
        }

        int cursorX = mmu.load(map.CURSOR_X);
        int cursorY = mmu.load(map.CURSOR_Y);
        int jiffyCount = mmu.load(map.JIFFY_COUNT);

        bgcolor = mmu.load(map.BG_COLOR) & sys.LOW_NIBBLE_MASK;
        fgcolor = mmu.load(map.FG_COLOR) & sys.LOW_NIBBLE_MASK;

        g.setColor(video.pal[bgcolor]);
        g.fillRect(0, 0, screenWidth, screenHeight);
        drawBorder(g);

        g.setFont(video.font);
        for ( int y = 0; y < video.textHeight; y++ )
        {
            for ( int x = 0; x < video.textWidth; x++ )
            {
                boolean cursorReverse = cursorX == x && cursorY == y && (jiffyCount / 4) % 2 == 0;
                drawCharacter(g, x, y, cursorReverse);
            }
        }
    }

    private void drawBorder(Graphics g)
    {
        g.setColor(video.pal[mmu.load(map.BORDER_COLOR) & sys.LOW_NIBBLE_MASK]);

        // Left
        g.fillRect(0, 0, video.borderSize, screenHeight);

        // Top
        g.fillRect(0, 0, screenWidth, video.borderSize);

        // Right
        g.fillRect(screenWidth - video.borderSize, 0, screenWidth, screenHeight);
        
        // Bottom
        g.fillRect(0, screenHeight - video.borderSize, screenWidth, screenHeight);
    }

    private void drawCharacter(Graphics g, int x, int y, boolean cursorReverse)
    {
        int textAddr = y * video.textWidth + x + vmap.TEXT_START;
        int v = video.bank.load(textAddr);
//        if ( v < 32 || v > 0x7f ) {
//            throw new IllegalStateException("OOOOPS: $" + sys.toHex(v));
//        }
        String c = sys.charmap[v];
        
        int colorAddr = y * video.textWidth + x + vmap.COLOR_START;
        int value = video.bank.load(colorAddr);
        int bg = 0;
        int fg = 0;
        if ( cursorReverse ) 
        { 
            bg = fgcolor;
            fg = bgcolor;
        } 
        else if ( (value & sys.HIGH_NIBBLE_MASK) != 0 )
        {
            fg = bgcolor;
            bg = value & sys.LOW_NIBBLE_MASK;
        }
        else
        {
            fg = value & sys.LOW_NIBBLE_MASK;
            bg = bgcolor;
        }

        int cx = originX + (x * fontWidth);
        int tcy = originY + (y * fontHeight) - 3;
        int bcy = originX + (y * fontHeight);

        g.setColor(video.pal[bg]);
        g.fillRect(cx, bcy, fontWidth, fontHeight);
        g.setColor(video.pal[fg]);
        g.drawString(c, cx, tcy);
  

    }



}
