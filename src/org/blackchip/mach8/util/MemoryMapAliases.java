/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.blackchip.mach8.util;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import org.blackchip.system.map;
import org.blackchip.system.sys;

/**
 *
 * @author mcgann
 */
public class MemoryMapAliases
{
    public static void main(String[] args) throws Exception
    {
        String outputFile = args[0];

        PrintStream ps = new PrintStream(new FileOutputStream(outputFile));

        Field[] fields = map.class.getDeclaredFields();
        for ( Field field: fields )
        {
            try
            {
                int address = field.getInt(null);
                String name = field.getName();
                String strAddress = ( address < 256 )
                        ? sys.toHex(address) : sys.toHex16(address);
                ps.printf(".alias %s $%s\n", name, strAddress);

            }
            catch ( Exception e )
            {
                throw new IllegalStateException("Reflection error: " +
                        e.getMessage(), e);
            }
        }
        ps.close();
    }

}
