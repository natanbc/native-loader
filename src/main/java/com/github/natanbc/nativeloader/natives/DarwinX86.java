package com.github.natanbc.nativeloader.natives;

import com.github.natanbc.nativeloader.x86.X86Feature;

import java.lang.annotation.Native;
import java.util.Set;

import static com.github.natanbc.nativeloader.natives.DarwinNatives.*;

class DarwinX86 {
    @Native static final String SSE = "hw.optional.sse";
    @Native static final String SSE2 = "hw.optional.sse2";
    @Native static final String SSE3 = "hw.optional.sse3";
    @Native static final String SSSE3 = "hw.optional.supplementalsse3";
    @Native static final String SSE_4_1 = "hw.optional.sse4_1";
    @Native static final String SSE_4_2 = "hw.optional.sse4_2";
    @Native static final String AVX512F = "hw.optional.avx512f";
    
    static void addFeatures(Set<X86Feature> features) {
        if(hasFeature(SSE)) {
            features.add(X86Feature.SSE);
        }
        if(hasFeature(SSE2)) {
            features.add(X86Feature.SSE2);
        }
        if(hasFeature(SSE3)) {
            features.add(X86Feature.SSE3);
        }
        if(hasFeature(SSSE3)) {
            features.add(X86Feature.SSSE3);
        }
        if(hasFeature(SSE_4_1)) {
            features.add(X86Feature.SSE4_1);
        }
        if(hasFeature(SSE_4_2)) {
            features.add(X86Feature.SSE4_2);
        }
    }
}
