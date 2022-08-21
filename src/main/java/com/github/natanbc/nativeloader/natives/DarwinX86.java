package com.github.natanbc.nativeloader.natives;

import com.github.natanbc.nativeloader.x86.X86Feature;

import java.util.Set;

import static com.github.natanbc.nativeloader.natives.DarwinNatives.*;

class DarwinX86 {
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
