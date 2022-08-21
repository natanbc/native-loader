package com.github.natanbc.nativeloader.natives;

import com.github.natanbc.nativeloader.arm64.Arm64Feature;

import java.lang.annotation.Native;
import java.util.Set;

import static com.github.natanbc.nativeloader.natives.WindowsNatives.*;

class WindowsArm64 {
    @Native static final int PF_ARM_64BIT_LOADSTORE_ATOMIC = 25;
    @Native static final int PF_ARM_DIVIDE_INSTRUCTION_AVAILABLE = 24;
    @Native static final int PF_ARM_EXTERNAL_CACHE_AVAILABLE = 26;
    @Native static final int PF_ARM_FMAC_INSTRUCTIONS_AVAILABLE = 27;
    @Native static final int PF_ARM_VFP_32_REGISTERS_AVAILABLE = 18;
    @Native static final int PF_ARM_V8_INSTRUCTIONS_AVAILABLE = 29;
    @Native static final int PF_ARM_V8_CRYPTO_INSTRUCTIONS_AVAILABLE = 30;
    @Native static final int PF_ARM_V8_CRC32_INSTRUCTIONS_AVAILABLE = 31;
    @Native static final int PF_ARM_V81_ATOMIC_INSTRUCTIONS_AVAILABLE = 34;
    @Native static final int PF_ARM_V82_DP_INSTRUCTIONS_AVAILABLE = 43;
    @Native static final int PF_ARM_V83_JSCVT_INSTRUCTIONS_AVAILABLE = 44;
    
    static void addFeatures(Set<Arm64Feature> features) {
        if(isProcessorFeaturePresent(PF_ARM_V8_CRYPTO_INSTRUCTIONS_AVAILABLE)) {
            features.add(Arm64Feature.AES);
            features.add(Arm64Feature.SHA1);
            features.add(Arm64Feature.SHA2);
        }
        if(isProcessorFeaturePresent(PF_ARM_V8_CRC32_INSTRUCTIONS_AVAILABLE)) {
            features.add(Arm64Feature.CRC32);
        }
        if(isProcessorFeaturePresent(PF_ARM_V81_ATOMIC_INSTRUCTIONS_AVAILABLE)) {
            features.add(Arm64Feature.ATOMICS);
        }
        if(isProcessorFeaturePresent(PF_ARM_V82_DP_INSTRUCTIONS_AVAILABLE)) {
            features.add(Arm64Feature.ASIMDDP);
        }
        if(isProcessorFeaturePresent(PF_ARM_V83_JSCVT_INSTRUCTIONS_AVAILABLE)) {
            features.add(Arm64Feature.JSCVT);
        }
    }
}
