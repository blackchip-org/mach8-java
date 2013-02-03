/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.blackchip.jv6502;

/**
 *
 * @author mcgann
 */
public interface trap
{
    static final int SOFTWARE_INTERRUPT     = 0x00;
    static final int INTERNAL_ERROR         = 0x01;
    static final int JAVA_TRAP_REQUEST      = 0x02;
    static final int UNIMPLEMENTED_FEATURE  = 0x03;
    static final int ILLEGAL_INSTRUCTION    = 0x04;
    static final int INVALID_SYS_CALL       = 0x05;
    static final int INVALID_BCD_NUMBER     = 0x06;
    static final int STACK_UNDERFLOW        = 0x07;
    static final int STACK_OVERFLOW         = 0x08;
    static final int SEGMENTATION_FAULT     = 0x09;

}
