// $Id: cpu.java 18 2010-12-30 01:55:27Z mcgann $

package org.blackchip.jv6502;

import org.blackchip.system.map;
import org.blackchip.system.sys;
import org.blackchip.jv6502.util.Disassembler;

/*******************************************************************************
 *
 * 6502 CPU.
 * 
 * @version {@code $Revision: 18 $ $Date: 2010-12-29 20:55:27 -0500 (Wed, 29 Dec 2010) $}
 * @see <a href='http://www.geocities.com/oneelkruns/asm1step.html'> Assembly
 * in one step.</a>
 *
 ******************************************************************************/
public final class cpu
{
    /**
     * Program counter
     */
    private static int _pc;

    /**
     * Accumulator register
     */
    public static int a;

    /**
     * X register
     */
    public static int x;

    /**
     * Y register
     */
    public static int y;

    /**
     * Stack pointer
     */
    public static int sp;

    /**
     * Processor status register
     */
    public static final P p = new P();

    public static final Parameters param = new Parameters();
    public static Exception exception = null;

    /**
     * The interrupt request line.
     */
    private static boolean irq = false;
    public static boolean running = false;

    public static long executionCount = 0;

    private static boolean stepTo = false;
    private static int frameCount = 0;
    private static Thread thread = null;

    public static boolean protectMemory = false;
    public static int protectLow = 0;
    public static int protectHigh = 0;

    /**
     * Opcode map. Processor decodes an opcode by executing the Runnable 
     * indexed by opcode in this array. 
     */
    public static Instruction[] inst = new Instruction[0x100];


    private cpu()
    {
    }

    public static void pc(int value) {
        if ( protectMemory )
        {
            if ( value >= map.EASTER_JUMP && value <= map.EASTER_JUMP_3 ) {
                _pc = value;
                return; 
            }
            if ( value < protectLow - 1 || value > protectHigh )
            {
                String message = "Segmentation fault: PC from $" +
                        sys.toHex16(_pc) + " to $" + sys.toHex16(value);
                cpu.trap(trap.SEGMENTATION_FAULT, message);
//                sys.log(message);
//                TrapException e =
//                        new TrapException(trap.SEGMENTATION_FAULT, message);
//                e.printStackTrace();
//                throw e;
                return;
            }
        }
        _pc = value;
    }

    public static int pc() {
        return _pc;
    }

    /**
     * Increments the program counter and returns the next value from 
     * memory. 
     * 
     * @return the fetched byte. 
     */
    public static int fetch()
    {
        pc(pc() + 1);
        return mmu.load(pc());
    }

    /**
     * Performs a fetch on a 16-byte value.
     *
     * @return the fetched word. 
     */
    public static int fetch16()
    {
        int mem1 = fetch();
        int mem2 = fetch();
        return ( mem2 << 8 ) + mem1;
    }

    /**
     * Pushes a value to the stack.
     */
    public static void push(int value)
    {
        mmu.store(map.STACK_BOTTOM, sp, value);
        sp = ( sp - 1 ) & sys.BYTE_MASK;
    }

    /**
     * Pulls a value from the stack.
     */
    public static int pop()
    {
        sp = ( sp + 1 ) & sys.BYTE_MASK;
        return mmu.load(map.STACK_BOTTOM, sp);
    }

    public static void reset()
    {
        // Load the starting address from the reset vector. Subtract one
        // to start at exactly that address.
        int resetAddress = mmu.load16(map.RSTVEC);
        pc(resetAddress - 1);
        sys.log("CPU Reset: Vector $%s contains $%s", sys.toHex16(map.RSTVEC),
                sys.toHex(resetAddress));
        sys.log("IRQ Vector contains $%S", sys.toHex(mmu.load16(map.IRQVEC)));
        
        run();
    }

    public static void resume()
    {
        pc(pc() - 1);
        run();
    }

    public static void trap(int trapCode, String message, Object... format)
    {
        exception = new TrapException(trapCode,
                String.format(message, format));
        cpu.p.b = true;
    }


    private static void run()
    {
        thread = Thread.currentThread();
        running = true;
        run0();
        param.singleStep = false;
        running = false;
        thread = null;
    }
    
    private static void run0()
    {
        cpu.p.b = false;
        exception = null;

        Disassembler d = new Disassembler(0);
        d.setPrintingSymbols(false);
        
        if ( param.printExecution )
        {
            sys.nl();
        }
        
        try
        {
            while ( true )
            {
                int opcode = fetch();
                if ( param.printExecution )
                {
                    sys.printf("%9s", "[run] ");
                    d.setAddress(pc());
                    d.print();
                }
                if ( stepTo ) 
                {
                    if ( opcode == op.JSR )
                    {
                        frameCount++;
                    }
                }
                inst[opcode].run();
                executionCount++;
                if ( p.b == true )
                {
                    running = false; 
                    if ( param.exceptionOnTrap )
                    {
                        checkTrap();
                    }
                    if ( param.logOnBreak )
                    {
                        String message = ( exception == null )
                                ? "No Exception"
                                : exception.getMessage();

                        sys.log("\nBREAK: " + message);
                        print();
                    }
                    pc(pc() + 1);
                    return;
                }
                if ( cpu.irq && !cpu.p.i )
                {
                    processInterrupt();
                }
                if ( stepTo ) 
                {
                    if ( opcode == op.RTS )
                    {
                        frameCount--;
                    }
                }
                if ( param.singleStep || (stepTo && frameCount == 0) )
                {
                    stepTo = false;
                    pc(pc() + 1);
                    return;
                }
            }
        }
        catch ( Exception e )
        {
            exception = e;
            if ( param.exceptionOnTrap )
            {
                if ( param.logOnTrap )
                {
                    sys.log("\nTRAP: " + exception.getMessage());
                    print();
                    e.printStackTrace(System.err);
                }
                if ( e instanceof TrapException )
                {
                    throw (TrapException)e;
                }
                else
                {
                    throw new TrapException(trap.INTERNAL_ERROR,
                            "Internal Error: " + e.getMessage(), e);
                }
            }
        }
    }

    public static void interrupt()
    {
        if ( cpu.running && cpu.thread != null )
        {
            cpu.irq = true;
            thread.interrupt();
        }
    }

    private static void processInterrupt()
    {
        int pushAddress = cpu.pc();
        cpu.push((pushAddress & sys.HIGH_BYTE_MASK) >> 8);
        cpu.push(pushAddress & sys.BYTE_MASK);
        cpu.push(p.get());
        cpu.p.i = true;
        cpu.irq = false;

        cpu.pc(mmu.load16(map.IRQVEC) - 1);

    }

    public static void stepOut()
    {
        stepTo = true;
        frameCount = 1;
        resume();
    }
    
    public static void stepOver()
    {
        stepTo = true;
        frameCount = 0;
        resume();
    }

    public static void protectMemory(int allowedLow, int allowedHigh)
    {
        protectLow = allowedLow;
        protectHigh = allowedHigh;
        protectMemory = true;
    }

    /**
     * Returns a string showing the processor status.
     *
     * <p>
     * String returned is in the following format:
     * <pre>
     *   PC  AC XR YR SP  N V J B D I Z C
     *  0000 00 00 00 00  . * . . * . . .
     * </pre>
     * For the processor status bits, a period indicates the bit is clear,
     * and an asterix indicates the bit is set.
     * </p>
     *
     * @return the string as documented above.
     */
    public static String status()
    {
        return " PC  SR AC XR YR SP   N V - B D I Z C\n" +
               String.format("%04X %02X %02X %02X %02X %02X   %c %c %c %c %c %c %c %c",
                             (short)pc(), (byte)p.get(), (byte)a, (byte)x, (byte)y, (byte)sp,
                             bit(p.n), bit(p.v), bit(p.x), bit(p.b),
                             bit(p.d), bit(p.i), bit(p.z), bit(p.c));
    }

    /**
     * Prints the value of {@code status()} to standard out.
     */
    public static void print()
    {
        System.out.println(status());
    }

    private static void checkTrap()
    {
        if ( exception != null && exception instanceof TrapException )
        {
            TrapException te = (TrapException)exception;
            if ( te.getCode() != trap.SOFTWARE_INTERRUPT )
            {
                throw te;
            }
        }
    }

    private static char bit(boolean v) {
        return v ? '*' : '.';
    }
}
