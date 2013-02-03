/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.blackchip.jv6502;

import org.blackchip.system.sys;
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
public class sysTest {

    public sysTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testBCDTables()
    {
        assertEquals(40, sys.toBCD[0x40]);
        assertEquals(99, sys.toBCD[0x99]);
        assertEquals(-1, sys.toBCD[0x1c]);

        assertEquals(0x40, sys.fromBCD[40]);
        assertEquals(0x99, sys.fromBCD[99]);
        assertEquals(0x90, sys.fromBCD[90 % 100]);
        assertEquals(-1,   sys.fromBCD[123]);
    }
    
}
