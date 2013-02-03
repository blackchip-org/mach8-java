/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.blackchip.mtd4000;

import org.blackchip.system.vmap;
import org.blackchip.system.map;
import org.blackchip.jv6502.mmu;
import org.blackchip.system.Bank;
import org.blackchip.system.Page;
import org.blackchip.system.sys;

/**
 *
 * @author mcgann
 */
public class VideoSetup
{

    public static void standard()
    {
        setupPalette();
        createMemory();
        setupTextMemory();
        setupColorMemory();

    }

    private static void setupPalette()
    {
        video.pal[color.BLACK      ] = color.RGB_BLACK;
        video.pal[color.WHITE      ] = color.RGB_WHITE;
        video.pal[color.RED        ] = color.RGB_RED;
        video.pal[color.CYAN       ] = color.RGB_CYAN;
        video.pal[color.PURPLE     ] = color.RGB_PURPLE;
        video.pal[color.GREEN      ] = color.RGB_GREEN;
        video.pal[color.BLUE       ] = color.RGB_BLUE;
        video.pal[color.YELLOW     ] = color.RGB_YELLOW;
        video.pal[color.ORANGE     ] = color.RGB_ORANGE;
        video.pal[color.BROWN      ] = color.RGB_BROWN;
        video.pal[color.LIGHT_RED  ] = color.RGB_LIGHT_RED;
        video.pal[color.DARK_GRAY  ] = color.RGB_DARK_GRAY;
        video.pal[color.GRAY       ] = color.RGB_GRAY;
        video.pal[color.LIGHT_GREEN] = color.RGB_LIGHT_GREEN;
        video.pal[color.LIGHT_BLUE ] = color.RGB_LIGHT_BLUE;
        video.pal[color.LIGHT_GRAY ] = color.RGB_LIGHT_GRAY;

    }

    private static void createMemory()
    {
        int pages = 25;
        video.bank = new Bank("Video", pages);
        for ( int i = 0; i < pages; i++ )
        {
            video.bank.pages[i] = new Page("Video Page");
            mmu.bank.pages[sys.highByte(map.TEXT_START) + i] = video.bank.pages[i];

        }

    }

    private static void setupTextMemory()
    {
        for ( int address = vmap.TEXT_START; address <= vmap.TEXT_END; address++ )
        {
            video.bank.store(address, 32);
        }
    }

    private static void setupColorMemory()
    {
        mmu.store(map.BORDER_COLOR, color.LIGHT_GREEN);
        mmu.store(map.BG_COLOR, color.DARK_GRAY);
        mmu.store(map.FG_COLOR, color.LIGHT_GREEN);
        
//        for ( int address = vmap.COLOR_START; address <= vmap.COLOR_END; address++ )
//        {
//            video.bank.store(address, defaultColor);
//        }
    }





}
