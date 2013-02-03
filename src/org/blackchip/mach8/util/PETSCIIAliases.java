/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.blackchip.mach8.util;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import org.blackchip.system.PETSCII;
import org.blackchip.system.sys;

/**
 *
 * @author mcgann
 */
public class PETSCIIAliases
{

    public static void main(String[] args) throws Exception
    {
        String outputFile = args[0];

        PrintStream ps = new PrintStream(new FileOutputStream(outputFile));

        Field[] fields = PETSCII.class.getFields();
        for ( Field field: fields )
        {
            try
            {
                int value = field.getInt(null);
                String name = field.getName();
                ps.println(".alias KEY_" + name + " $" + sys.toHex(value));
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
