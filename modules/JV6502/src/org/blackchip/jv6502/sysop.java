/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.blackchip.jv6502;

/**
 *
 * @author mcgann
 */
public interface sysop
{
    static final int LDPROC = 0x01;
    static final int STPROC = 0x02;
    static final int PRTCPU = 0x03;
    static final int SFTIRQ = 0x04;
    static final int NUMEXE = 0x05;
    static final int IDLEPC = 0x06;

}
