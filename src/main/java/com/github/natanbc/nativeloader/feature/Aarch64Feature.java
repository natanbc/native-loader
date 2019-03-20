package com.github.natanbc.nativeloader.feature;

import com.github.natanbc.nativeloader.system.CPUType;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

public enum Aarch64Feature implements CPUFeature {
    FP, ASIMD, AES, PMULL, SHA1, SHA2, CRC32;
    
    @Override
    @Nonnull
    public CPUType getCPUType() {
        return CPUType.AARCH64;
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
