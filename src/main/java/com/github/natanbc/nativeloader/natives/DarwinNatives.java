package com.github.natanbc.nativeloader.natives;

import com.github.natanbc.nativeloader.x86.X86Feature;

import java.lang.annotation.Native;
import java.util.Set;

class DarwinNatives {
    @Native private static final int SYSCTL_FALSE = 0;
    @Native private static final int SYSCTL_TRUE = 1;
    @Native private static final int SYSCTL_OOM = 2;
    @Native private static final int SYSCTL_FAIL = 3;
    
    @Native static final String SSE = "hw.optional.sse";
    @Native static final String SSE2 = "hw.optional.sse2";
    @Native static final String SSE3 = "hw.optional.sse3";
    @Native static final String SSSE3 = "hw.optional.supplementalsse3";
    @Native static final String SSE_4_1 = "hw.optional.sse4_1";
    @Native static final String SSE_4_2 = "hw.optional.sse4_2";
    @Native static final String AVX512F = "hw.optional.avx512f";
    
    private static native int getSysctl0(String name);
    
    static boolean getSysctl(String name) {
        switch(getSysctl0(name)) {
            case SYSCTL_FALSE:
            case SYSCTL_FAIL:
                return false;
            case SYSCTL_TRUE: return true;
            case SYSCTL_OOM: throw new OutOfMemoryError();
            default: throw new AssertionError("should not get here");
        }
    }
    
    static void addX86Features(Set<X86Feature> features) {
        if(getSysctl(SSE)) {
            features.add(X86Feature.SSE);
        }
        if(getSysctl(SSE2)) {
            features.add(X86Feature.SSE2);
        }
        if(getSysctl(SSE3)) {
            features.add(X86Feature.SSE3);
        }
        if(getSysctl(SSSE3)) {
            features.add(X86Feature.SSSE3);
        }
        if(getSysctl(SSE_4_1)) {
            features.add(X86Feature.SSE4_1);
        }
        if(getSysctl(SSE_4_2)) {
            features.add(X86Feature.SSE4_2);
        }
    }
}
