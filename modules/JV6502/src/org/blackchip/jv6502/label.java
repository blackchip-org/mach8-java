/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.blackchip.jv6502;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import org.blackchip.system.map;

/**
 *
 * @author mcgann
 */
public class label
{
    private static final String sysopLabels[] = new String[0x100];
    public static final Map<String,Integer> memoryMapToAddress =
            new HashMap<String,Integer>();
    public static final Map<Integer,String> memoryMapToLabel = 
            new HashMap<Integer,String>();

    static
    {
        initSysop();
        initMemoryMap();
    }

    private static void initSysop()
    {
        for ( int i = 0; i < sysopLabels.length; i++ )
        {
            sysopLabels[i] = String.format("?%02X", i);
        }
        Field[] fields = sysop.class.getDeclaredFields();
        for ( Field field: fields )
        {
            try
            {
                sysopLabels[field.getInt(null)] = field.getName();
            }
            catch ( Exception e )
            {
                throw new IllegalStateException("Reflection error: " +
                        e.getMessage(), e);
            }
        }
    }

    private static void initMemoryMap()
    {
        Field[] fields = map.class.getDeclaredFields();
        for ( Field field: fields )
        {
            try
            {
                memoryMapToAddress.put(field.getName(), field.getInt(null));
                memoryMapToLabel.put(field.getInt(null), field.getName());
            }
            catch ( Exception e )
            {
                throw new IllegalStateException("Reflection error: " +
                        e.getMessage(), e);
            }
        }
    }


    public static String sysop(int op)
    {
        return sysopLabels[op];
    }

    public static int memory(String label)
    {
        Integer val = memoryMapToAddress.get(label);
        return ( val == null ) ? -1 : val;
    }
    
}
