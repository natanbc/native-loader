package com.github.natanbc.nativeloader.natives;

import com.github.natanbc.nativeloader.x86.X86Feature;

import java.lang.annotation.Native;
import java.util.Set;

import static com.github.natanbc.nativeloader.natives.WindowsNatives.*;

public class WindowsX86 {
    @Native private static final int PF_NX_ENABLED = 12;
    @Native private static final int PF_COMPARE_EXCHANGE_DOUBLE = 2;
    @Native private static final int PF_COMPARE_EXCHANGE128 = 14;
    @Native private static final int PF_COMPARE64_EXCHANGE128 = 15;
    @Native private static final int PF_3DNOW_INSTRUCTIONS_AVAILABLE = 7;
    @Native private static final int PF_FLOATING_POINT_PRECISION_ERRATA = 0;
    @Native private static final int PF_MMX_INSTRUCTIONS_AVAILABLE = 3;
    @Native private static final int PF_PAE_ENABLED = 9;
    @Native private static final int PF_RDTSC_INSTRUCTION_AVAILABLE = 8;
    @Native private static final int PF_RDWRFSGSBASE_AVAILABLE = 22;
    @Native private static final int PF_SSE3_INSTRUCTIONS_AVAILABLE = 13;
    @Native private static final int PF_XMMI_INSTRUCTIONS_AVAILABLE = 6;
    @Native private static final int PF_XMMI64_INSTRUCTIONS_AVAILABLE = 10;
    @Native private static final int PF_XSAVE_ENABLED = 17;
    @Native private static final int PF_SSSE3_INSTRUCTIONS_AVAILABLE = 36;
    @Native private static final int PF_SSE4_1_INSTRUCTIONS_AVAILABLE = 37;
    @Native private static final int PF_SSE4_2_INSTRUCTIONS_AVAILABLE = 38;
    
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
