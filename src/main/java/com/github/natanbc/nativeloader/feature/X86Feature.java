package com.github.natanbc.nativeloader.feature;

import com.github.natanbc.nativeloader.system.CPUType;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

public enum X86Feature implements CPUFeature {
    AES, ERMS, F16C, FMA3, VPCLMULQDQ, BMI1, BMI2, SSSE3, SSE4_1, SSE4_2,
    AVX, AVX2, AVX512F, AVX512CD, AVX512ER, AVX512PF, AVX512BW, AVX512DQ, AVX512VL,
    AVX512IFMA, AVX512VBMI, AVX512VBMI2, AVX512VNNI, AVX512BITALG, AVX512VPOPCNTDQ,
    AVX512_4VNNIW, AVX512_4VBMI2, SMX, SGX, CX16, SHA, POPCNT, MOVBE, RDRND;
    
    @Override
    @Nonnull
    public CPUType getCPUType() {
        return CPUType.X86;
    }
    
    @Nonnull
    @Override
    public String getName() {
        return name();
    }
    
    @Override
    @Nonnegative
    public int getMask() {
        return 1 << ordinal();
    }
}
