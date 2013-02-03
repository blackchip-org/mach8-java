/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.blackchip.jv6502;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mcgann
 */
public class mmuTest {

    public mmuTest() {
    }

    @Test
    public void clearTest()
    {
        
    }

    @Test
    public void test()
    {
        mmu.clear();

        mmu.store16(0x4000, 0x1234);
        assertEquals(0x34, mmu.load(0x4000));
        assertEquals(0x12, mmu.load(0x4001));
    }
}