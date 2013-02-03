/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.blackchip.jv6502;

import org.blackchip.system.map;
import org.blackchip.system.sys;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mcgann
 */
public class cpuTest {

    public cpuTest() {
    }

    @Test
    public void testStatus() {

    }

    @Test
    public void testStack()
    {
        System.out.println("===== Stack test\n");

        mmu.clear();
        mmu.debug = true;

        mmu.store(map.STACK_BOTTOM, 0xaa);

        cpu.sp = 0xff;
        cpu.push(0x11);
        cpu.push(0x22);
        cpu.push(0x33);

        sys.nl();
        cpu.print();
        sys.nl();
        mmu.print(map.STACK_TOP - 0xf, map.STACK_TOP);
        sys.nl();
        
        assertEquals(0x11, mmu.load(map.STACK_TOP));
        assertEquals(0x22, mmu.load(map.STACK_TOP - 1));
        assertEquals(0x33, mmu.load(map.STACK_TOP - 2));
        sys.nl();

        assertEquals(0x33, cpu.pop());
        assertEquals(0x22, cpu.pop());
        assertEquals(0x11, cpu.pop());

        sys.nl();
        cpu.print();

        int value = cpu.pop();
        cpu.print();

        assertEquals(0xaa, value);

        sys.nl();
        cpu.push(0x44);
        cpu.push(0x55);
        assertEquals(0x55, cpu.pop());
        assertEquals(0x44, cpu.pop());
        
    }

    @Test
    public void testRun()
    {
        System.out.println("\n===== CPU run test\n");
        CPUSetup.standard();
        cpu.reset();
        if ( cpu.exception != null )
        {
            System.out.println("BREAK");
            System.out.println(cpu.exception.getMessage());
            System.out.println();
        }
        cpu.print();
    }

}