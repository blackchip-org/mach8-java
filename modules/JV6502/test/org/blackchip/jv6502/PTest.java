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
public class PTest {

    public PTest() {
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

    /**
     * Test of get method, of class P.
     */
    @Test
    public void testGet() {
        System.out.println("get");

        P p = new P();
        assertEquals(0, p.get());
        p.c = true;
        assertEquals(1, p.get());
        p.c = false;
        p.z = true;
        assertEquals(2, p.get());
        p.z = false;
        p.i = true;
        assertEquals(4, p.get());
        p.i = false;
        p.d = true;
        assertEquals(8, p.get());
        p.d = false;
        p.b = true;
        assertEquals(16, p.get());
        p.b = false;

        p.v = true;
        assertEquals(64, p.get());
        p.v = false;
        p.n = true;
        assertEquals(128, p.get());

        p.v = true;
        p.b = true;
        p.d = true;
        p.i = true;
        p.z = true;
        p.c = true;
        p.x = true;
        assertEquals(255, p.get());


    }

    /**
     * Test of set method, of class P.
     */
    @Test
    public void testSet() {
        System.out.println("set");

        P p = new P();

        p.set(0);
        assertTrue(!p.c && !p.z && !p.i && !p.d && !p.b && !p.v && !p.n);
        p.set(1);
        assertTrue( p.c && !p.z && !p.i && !p.d && !p.b && !p.v && !p.n);
        p.set(2);
        assertTrue(!p.c &&  p.z && !p.i && !p.d && !p.b && !p.v && !p.n);
        p.set(4);
        assertTrue(!p.c && !p.z &&  p.i && !p.d && !p.b && !p.v && !p.n);
        p.set(8);
        assertTrue(!p.c && !p.z && !p.i &&  p.d && !p.b && !p.v && !p.n);
        p.set(16);
        assertTrue(!p.c && !p.z && !p.i && !p.d &&  p.b && !p.v && !p.n);
        p.set(32);
        assertTrue(!p.c && !p.z && !p.i && !p.d && !p.b && !p.v && !p.n);
        p.set(64);
        assertTrue(!p.c && !p.z && !p.i && !p.d && !p.b &&  p.v && !p.n);
        p.set(128);
        assertTrue(!p.c && !p.z && !p.i && !p.d && !p.b && !p.v &&  p.n);
        p.set(255);
        assertTrue( p.c &&  p.z &&  p.i &&  p.d &&  p.b &&  p.v &&  p.n);


    }

}