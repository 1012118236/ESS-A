package com.hehei;

public class test2 {
    private static final int OVERFLOW_YIELD_RATE = 7; // must be power 2 - 1

    /** The number of bits to use for reader count before overflowing */
    private static final int LG_READERS = 7;

    // Values for lock state and stamp operations
    private static final long RUNIT = 1L;
    private static final long WBIT  = 1L << LG_READERS;
    private static final long RBITS = WBIT - 1L;
    private static final long RFULL = RBITS - 1L;
    private static final long ABITS = RBITS | WBIT;
    private static final long SBITS = ~RBITS; // note overlap with ABITS
    public static void main(String[] args) {
        long state = 1;
        System.out.println("RUNIT    "+RUNIT);
        System.out.println("WBIT    "+WBIT);
        System.out.println("RBITS    "+RBITS);
        System.out.println("RFULL    "+RFULL);
        System.out.println("ABITS    "+ABITS);
        System.out.println("SBITS    "+SBITS);
    }
}
