/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.blackchip.mtd4000;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.Queue;
import org.blackchip.jv6502.cpu;
import org.blackchip.jv6502.mmu;
import org.blackchip.system.PETSCII;
import org.blackchip.system.map;
import org.blackchip.system.sys;

/**
 *
 * @author mcgann
 */
public class KeyboardSupport extends KeyAdapter {

    private final Queue<Integer> buffer = new LinkedList<Integer>();

    public KeyboardSupport() {
        new Thread(new KeyProcessor()).start();
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        char keyChar = e.getKeyChar();

        // Convert line feed to CR
        if ( (int)keyChar == 0x0a ) {
            keyChar = 0x0d;
        }
        //sys.log("Key typed: " + sys.toHex(keyChar) + ", ctrl: " + e.isControlDown() + ", alt: " + e.isAltDown());
        if ( keyChar == KeyEvent.VK_BACK_SPACE) {
            keyStroke(PETSCII.DEL);
        // Color keys (1 - 8)
        } else if ( e.isControlDown() && (keyChar >= 0x31 && keyChar <= 0x38) ) {
            keyStroke(0x80 + keyChar - 0x31);
        } else if ( e.isAltDown() && (keyChar >= 0x31 && keyChar <= 0x38 ) ) {
            keyStroke(0x88 + keyChar - 0x31);
        } else if ( e.isControlDown() && keyChar == 0x39 ) {
            keyStroke(0x01); 
        } else { 
            keyStroke(keyChar);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if ( keyCode == KeyEvent.VK_UP ) {
            keyStroke(PETSCII.UP);
        } else if ( keyCode == KeyEvent.VK_DOWN ) {
            keyStroke(PETSCII.DOWN);
        } else if ( keyCode == KeyEvent.VK_LEFT ) {
            keyStroke(PETSCII.LEFT);
        } else if ( keyCode == KeyEvent.VK_RIGHT ) {
            keyStroke(PETSCII.RIGHT);
        //} else if ( keyCode == KeyEvent.VK_BACK_SPACE ) {
        //    keyStroke(PETSCII.DEL);
        } else if ( keyCode == KeyEvent.VK_HOME ) {
            keyStroke(PETSCII.HOME);
        }
    }

    private void keyStroke(int keyStroke) {
        synchronized ( buffer ) {
            buffer.add(keyStroke);
        }
    }

    class KeyProcessor implements Runnable {
        public void run() {
            sys.log("Key processor started");

            while ( true ) {
                int sleepTime = 0;
                if ( !buffer.isEmpty() && cpu.running && !cpu.p.i &&
                     mmu.load(map.KEY_PRESS) != 0xff )
                {
                    synchronized ( buffer ) {
                        int keyStroke = buffer.remove();
                        mmu.store(map.KEY_PRESS, 0xff);
                        mmu.store(map.KEY_STROKE, keyStroke);
                        cpu.interrupt();
                    }
                } else if ( !buffer.isEmpty() ) {
                    sleepTime = 10;
                } else {
                    sleepTime = 100;
                }
                try {
                    Thread.sleep(sleepTime);
                } catch ( InterruptedException ie ) {
                    //
                }
            }
        }
    }
}
