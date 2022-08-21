package com.github.natanbc.nativeloader.feature;

import com.github.natanbc.nativeloader.CPUFeature;
import com.github.natanbc.nativeloader.system.CPUType;

import javax.annotation.Nonnull;

public enum ArmFeature implements CPUFeature {
    AES, ARM_26BIT("26bit"), CRC32, CRUNCH, EDSP,
    EVTSTRM, FASTMULT, FPA, HALF, IDIVA, IDIVT,
    IWMMXT, JAVA, LPAE, NEON, PMULL, SHA1, SHA2,
    SWP, THUMB, THUMBEE, TLS, VFP, VFPD32, VFPV3,
    VFPV3D16, VFPV4;
    
    private final String nativeName;
    
    ArmFeature(String nativeName) {
        this.nativeName = nativeName == null ? name().toLowerCase() : nativeName;
    }
    
    ArmFeature() {
        this(null);
    }
    
    @Override
    @Nonnull
    public CPUType cpuType() {
        return CPUType.ARM;
    }
    
    @Nonnull
    @Override
    public String nativeName() {
        return nativeName;
    }
}
