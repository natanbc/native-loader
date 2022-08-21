package com.github.natanbc.nativeloader.natives;

class Bits {
    static boolean isSet(long value, int bit) {
        return ((value >>> bit) & 1) == 1;
    }
    
    static int extractBitRange(int value, int msb, int lsb) {
        int mask = (1 << (msb - lsb + 1)) - 1;
        return (value >>> lsb) & mask;
    }
    
    static boolean hasMask(long value, long mask) {
        return (value & mask) == mask;
    }
}
