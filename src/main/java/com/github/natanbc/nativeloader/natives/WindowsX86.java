package com.github.natanbc.nativeloader.natives;

import com.github.natanbc.nativeloader.x86.X86Feature;

import java.util.Set;

import static com.github.natanbc.nativeloader.natives.WindowsNatives.*;

public class WindowsX86 {
    static void addFeatures(Set<X86Feature> features) {
        if(isProcessorFeaturePresent(PF_XMMI_INSTRUCTIONS_AVAILABLE)) {
            features.add(X86Feature.SSE);
        }
        if(isProcessorFeaturePresent(PF_XMMI64_INSTRUCTIONS_AVAILABLE)) {
            features.add(X86Feature.SSE2);
        }
        if(isProcessorFeaturePresent(PF_SSE3_INSTRUCTIONS_AVAILABLE)) {
            features.add(X86Feature.SSE3);
        }
        if(isProcessorFeaturePresent(PF_SSSE3_INSTRUCTIONS_AVAILABLE)) {
            features.add(X86Feature.SSSE3);
        }
        if(isProcessorFeaturePresent(PF_SSE4_1_INSTRUCTIONS_AVAILABLE)) {
            features.add(X86Feature.SSE4_1);
        }
        if(isProcessorFeaturePresent(PF_SSE4_2_INSTRUCTIONS_AVAILABLE)) {
            features.add(X86Feature.SSE4_2);
        }
    }
}
