package com.github.natanbc.nativeloader.x86;

import com.github.natanbc.nativeloader.CPUFeature;
import com.github.natanbc.nativeloader.system.CPUType;

import javax.annotation.Nonnull;

public enum X86Feature implements CPUFeature {
    AES, AMX_BF16, AMX_INT8, AMX_TILE, AVX, AVX2,
    
    AVX512BITALG, AVX512BW, AVX512CD, AVX512DQ, AVX512ER,
    AVX512F, AVX512IFMA, AVX512PF, AVX512VBMI, AVX512VBMI2,
    AVX512VL, AVX512VNNI, AVX512VPOPCNTDQ, AVX512_4FMAPS,
    AVX512_4VBMI2, AVX512_4VNNIW, AVX512_BF16, AVX512_SECOND_FMA,
    AVX512_VP2INTERSECT,
    
    BMI1, BMI2, CLFLUSHOPT, CLFSH, CLWB, CX16, CX8, DCA,
    ERMS, F16C, FMA3, FMA4, FPU, HLE, MMX, MOVBE, PCLMULQDQ,
    POPCNT, RDRND, RDSEED, RTM, SGX, SHA, SMX, SS,
    SSE, SSE2, SSE3, SSE4A, SSE4_1, SSE4_2, SSSE3,
    TSC, VAES, VPCLMULQDQ, ADX, LZCNT, AVXVNNI;
    
    private final String nativeName;
    
    X86Feature(String nativeName) {
        this.nativeName = nativeName == null ? name().toLowerCase() : nativeName;
    }
    
    X86Feature() {
        this(null);
    }
    
    @Override
    @Nonnull
    public CPUType cpuType() {
        return CPUType.X86;
    }
    
    @Nonnull
    @Override
    public String nativeName() {
        return nativeName;
    }
}
