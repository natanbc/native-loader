package com.github.natanbc.nativeloader.natives;

import java.util.concurrent.atomic.AtomicReference;

class X86Natives {
    private static final AtomicReference<Integer> MAX_CPUID_LEAF = new AtomicReference<>();
    private static final AtomicReference<Integer> MAX_CPUID_LEAF_EXT = new AtomicReference<>();
    
    private static native boolean unsafeCpuid(int eax, int ecx, int[] out);
    
    static native long xgetbv(int xcr);
    
    static boolean supportsLeaf(int eax) {
        AtomicReference<Integer> maxRef;
        int leaf;
        if(Integer.compareUnsigned(eax, 0x8000_0000) >= 0) {
            maxRef = MAX_CPUID_LEAF_EXT;
            leaf = 0x8000_0000;
        } else {
            maxRef = MAX_CPUID_LEAF;
            leaf = 0;
        }
        Integer max = maxRef.get();
        if(max == null) {
            var out = new int[4];
            if(!unsafeCpuid(leaf, 0, out)) {
                throw new AssertionError("Fetching max " + (leaf == 0 ? "" : "extended ") + "cpuid leaf failed");
            }
            max = out[0];
            maxRef.set(max);
        }
        return Integer.compareUnsigned(eax, max) <= 0;
    }
    
    private static boolean cpuid0(int eax, int ecx, int[] out) {
        if(!supportsLeaf(eax)) {
            return true;
        }
        return unsafeCpuid(eax, ecx, out);
    }
    
    static Leaf cpuid(int eax, int ecx) {
        var out = new int[4];
        if(!cpuid0(eax, ecx, out)) {
            throw new IllegalArgumentException(String.format("cpuid(0x%08X, 0x%08X) failed", eax, ecx));
        }
        return new Leaf(out[0], out[1], out[2], out[3]);
    }
    
    static class Leaf {
        final int eax, ebx, ecx, edx;
    
        Leaf(int eax, int ebx, int ecx, int edx) {
            this.eax = eax;
            this.ebx = ebx;
            this.ecx = ecx;
            this.edx = edx;
        }
    
        public boolean eax(int bit) {
            return bit(eax, bit);
        }
    
        public boolean ebx(int bit) {
            return bit(ebx, bit);
        }
    
        public boolean ecx(int bit) {
            return bit(ecx, bit);
        }
        
        public boolean edx(int bit) {
            return bit(edx, bit);
        }
        
        private static boolean bit(int val, int bit) {
            if(bit >= 32) {
                throw new IllegalArgumentException("Invalid bit");
            }
            return Bits.isSet(val, bit);
        }
    }
}
