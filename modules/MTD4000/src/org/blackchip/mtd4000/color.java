/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.blackchip.mtd4000;

import java.awt.Color;

/**
 *
 * @author mcgann
 */
public interface color
{
    static final int BLACK          = 0x0;
    static final int WHITE          = 0x1;
    static final int RED            = 0x2;
    static final int CYAN           = 0x3;
    static final int PURPLE         = 0x4;
    static final int GREEN          = 0x5;
    static final int BLUE           = 0x6;
    static final int YELLOW         = 0x7;
    static final int ORANGE         = 0x8;
    static final int BROWN          = 0x9;
    static final int LIGHT_RED      = 0xa;
    static final int DARK_GRAY      = 0xb;
    static final int GRAY           = 0xc;
    static final int LIGHT_GREEN    = 0xd;
    static final int LIGHT_BLUE     = 0xe;
    static final int LIGHT_GRAY     = 0xf;

    static final Color RGB_BLACK          = new Color(  0,   0,   0);
    static final Color RGB_WHITE          = new Color(255, 255, 255);
    static final Color RGB_RED            = new Color(104,  55,  43);
    static final Color RGB_CYAN           = new Color(112, 164, 178);
    static final Color RGB_PURPLE         = new Color(111,  61, 134);
    static final Color RGB_GREEN          = new Color( 88, 141,  67);
    static final Color RGB_BLUE           = new Color( 53,  40, 121);
    static final Color RGB_YELLOW         = new Color(184, 199, 111);
    static final Color RGB_ORANGE         = new Color(111,  79,  37);
    static final Color RGB_BROWN          = new Color( 67,  57,   0);
    static final Color RGB_LIGHT_RED      = new Color(154, 103,  89);
    static final Color RGB_DARK_GRAY      = new Color( 68,  68,  68);
    static final Color RGB_GRAY           = new Color(108, 108, 108);
    static final Color RGB_LIGHT_GREEN    = new Color(154, 210, 132);
    static final Color RGB_LIGHT_BLUE     = new Color(108,  94, 181);
    static final Color RGB_LIGHT_GRAY     = new Color(149, 149, 149);

}
