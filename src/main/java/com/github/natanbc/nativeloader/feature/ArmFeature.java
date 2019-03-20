package com.github.natanbc.nativeloader.feature;

import com.github.natanbc.nativeloader.system.CPUType;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

public enum ArmFeature implements CPUFeature {
    VFP, IWMMXT, NEON, VFPV3, VFPV3D16, VFPV4, IDIVA, IDIVT,
    AES, PMULL, SHA1, SHA2, CRC32;
    
    @Override
    @Nonnull
    public CPUType getCPUType() {
        return CPUType.ARM;
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
