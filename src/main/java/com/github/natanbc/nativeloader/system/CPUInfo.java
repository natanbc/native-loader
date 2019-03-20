package com.github.natanbc.nativeloader.system;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;

public class CPUInfo {
    private final ArchitectureType arch;
    private final long featureBits;
    
    public CPUInfo(@Nonnull ArchitectureType arch, long featureBits) {
        this.arch = arch;
        this.featureBits = featureBits;
    }
    
    @Nonnull
    @CheckReturnValue
    public ArchitectureType getArch() {
        return arch;
    }
    
    @CheckReturnValue
    public long getFeatureBits() {
        return featureBits;
    }
}
