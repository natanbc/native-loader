package com.github.natanbc.nativeloader.feature;

import com.github.natanbc.nativeloader.system.CPUType;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

public interface CPUFeature {
    @Nonnull
    CPUType getCPUType();
    
    @Nonnull
    String getName();
    
    @Nonnull
    String getNativeName();
}
