package org.blackchip.system;

public interface MachListener
{
    void started();
    
    void stopped();

    void exception(Exception e);
}