package org.blackchip.mach8;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.blackchip.jv6502.cpu;
import org.blackchip.system.MachControl;
import org.blackchip.system.MachListener;
import org.blackchip.system.map;

public class MachControlImpl implements MachControl
{
    private static final MachControl instance = new MachControlImpl();

    private List<MachListener> listeners = new ArrayList<MachListener>();

    private MachControlImpl()
    {
    }

    public static MachControl getInstance()
    {
        return instance;
    }

    public void addListener(MachListener listener)
    {
        this.listeners.add(listener);
    }

    public void start()
    {
        fireStartEvent();
        new Thread()
        {
            @Override
            public void run()
            {
                try {
                    cpu.reset();
                } catch ( Exception e ) {
                    fireExceptionEvent(e);
                }
                fireStopEvent();
            }
        }.start();

    }

    public void resume()
    {
        fireStartEvent();
        new Thread()
        {
            @Override
            public void run()
            {
                try {
                    cpu.resume();
                } catch ( Exception e ) {
                    fireExceptionEvent(e);
                }
                fireStopEvent();
            }
        }.start();
    }
        
    public void reboot()
    {
        mach8.reboot();
    }
    
    public void stepOver()
    {
        fireStartEvent();
        new Thread()
        {
            @Override
            public void run()
            {
                cpu.stepOver();
                fireStopEvent();
            }
        }.start();        
    }
    
    public void stepOut()
    {
        fireStartEvent();
        new Thread()
        {
            @Override
            public void run()
            {
                cpu.stepOut();
                fireStopEvent();
            }
        }.start();        
    }
    
    public void load(String file) throws IOException
    {
        BinaryLoader bl = new BinaryLoader(map.ENTRY);

        bl.load(file);
        cpu.pc(map.ENTRY);
    }
    
    private void fireStartEvent()
    {
        for ( MachListener listener: listeners )
        {
            listener.started();
        }
    }

    private void fireStopEvent()
    {
        for ( MachListener listener: listeners )
        {
            listener.stopped();
        }
    }

    private void fireExceptionEvent(Exception e)
    {
        for ( MachListener listener: listeners )
        {
            listener.exception(e);
        }
    }
}
