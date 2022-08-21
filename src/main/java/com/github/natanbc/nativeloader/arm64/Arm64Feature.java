package com.github.natanbc.nativeloader.arm64;

import com.github.natanbc.nativeloader.CPUFeature;
import com.github.natanbc.nativeloader.system.CPUType;

import javax.annotation.Nonnull;

public enum Arm64Feature implements CPUFeature {
    AES, AFP, ASIMD, ASIMDDP, ASIMDFHM, ASIMDHP, ASIMDRDM,
    ATOMICS, BF16, BTI, CPUID, CRC32, DCPODP, DCPOP,
    DGH, DIT, ECV, EVTSTRM, FCMA, FLAGM, FLAGM2, FP,
    FPHP, FRINT, I8MM, ILRCPC, JSCVT, LRCPC, MTE, PACA,
    PACG, PMULL, RNG, RPRES, SB, SHA1, SHA2, SHA3, SHA512,
    SM3, SM4, SSBS, SVE, SVE2, SVEAES, SVEBF16,
    SVEBITPERM, SVEF32MM, SVEF64MM, SVEI8MM,
    SVEPMULL, SVESHA3, SVESM4, USCAT;
    
    private final String nativeName;
    
    Arm64Feature(String nativeName) {
        this.nativeName = nativeName == null ? name().toLowerCase() : nativeName;
    }
    
    Arm64Feature() {
        this(null);
    }
    
    @Override
    @Nonnull
    public CPUType cpuType() {
        return CPUType.AARCH64;
    }
    
    @Nonnull
    @Override
    public String nativeName() {
        return nativeName;
    }
}
