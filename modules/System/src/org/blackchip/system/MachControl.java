/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.blackchip.system;

import java.io.IOException;

/**
 *
 * @author mcgann
 */
public interface MachControl
{

    void addListener(MachListener listner);
    void start();
    void resume();
    void stepOver();
    void stepOut();
    void load(String file) throws IOException;
    
    void reboot() throws IOException;

}
