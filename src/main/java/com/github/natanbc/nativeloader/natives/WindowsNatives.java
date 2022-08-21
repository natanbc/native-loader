package com.github.natanbc.nativeloader.natives;

import java.lang.annotation.Native;

class WindowsNatives {
    @Native static final int PF_ARM_64BIT_LOADSTORE_ATOMIC = 25;
    @Native static final int PF_ARM_DIVIDE_INSTRUCTION_AVAILABLE = 24;
    @Native static final int PF_ARM_EXTERNAL_CACHE_AVAILABLE = 26;
    @Native static final int PF_ARM_FMAC_INSTRUCTIONS_AVAILABLE = 27;
    @Native static final int PF_ARM_VFP_32_REGISTERS_AVAILABLE = 18;
    @Native static final int PF_3DNOW_INSTRUCTIONS_AVAILABLE = 7;
    @Native static final int PF_CHANNELS_ENABLED = 16;
    @Native static final int PF_COMPARE_EXCHANGE_DOUBLE = 2;
    @Native static final int PF_COMPARE_EXCHANGE128 = 14;
    @Native static final int PF_COMPARE64_EXCHANGE128 = 15;
    @Native static final int PF_FASTFAIL_AVAILABLE = 23;
    @Native static final int PF_FLOATING_POINT_EMULATED = 1;
    @Native static final int PF_FLOATING_POINT_PRECISION_ERRATA = 0;
    @Native static final int PF_MMX_INSTRUCTIONS_AVAILABLE = 3;
    @Native static final int PF_NX_ENABLED = 12;
    @Native static final int PF_PAE_ENABLED = 9;
    @Native static final int PF_RDTSC_INSTRUCTION_AVAILABLE = 8;
    @Native static final int PF_RDWRFSGSBASE_AVAILABLE = 22;
    @Native static final int PF_SECOND_LEVEL_ADDRESS_TRANSLATION = 20;
    @Native static final int PF_SSE3_INSTRUCTIONS_AVAILABLE = 13;
    @Native static final int PF_VIRT_FIRMWARE_ENABLED = 21;
    @Native static final int PF_XMMI_INSTRUCTIONS_AVAILABLE = 6;
    @Native static final int PF_XMMI64_INSTRUCTIONS_AVAILABLE = 10;
    @Native static final int PF_XSAVE_ENABLED = 17;
    @Native static final int PF_ARM_V8_INSTRUCTIONS_AVAILABLE = 29;
    @Native static final int PF_ARM_V8_CRYPTO_INSTRUCTIONS_AVAILABLE = 30;
    @Native static final int PF_ARM_V8_CRC32_INSTRUCTIONS_AVAILABLE = 31;
    @Native static final int PF_ARM_V81_ATOMIC_INSTRUCTIONS_AVAILABLE = 34;
    
    @Native static final int PF_SSSE3_INSTRUCTIONS_AVAILABLE = 36;
    @Native static final int PF_SSE4_1_INSTRUCTIONS_AVAILABLE = 37;
    @Native static final int PF_SSE4_2_INSTRUCTIONS_AVAILABLE = 38;
    
    static native boolean isProcessorFeaturePresent(int feature);
}
