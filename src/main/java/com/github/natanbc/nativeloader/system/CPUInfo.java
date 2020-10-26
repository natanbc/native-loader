package com.github.natanbc.nativeloader.system;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.Map;

public class CPUInfo {
    private final ArchitectureType arch;
    private final Map<String, Boolean> features;
    private final CacheInfo cacheInfo;
    
    public CPUInfo(@Nonnull ArchitectureType arch, @Nonnull Map<String, Boolean> features,
                   @Nonnull CacheInfo cacheInfo) {
        this.arch = arch;
        this.features = Collections.unmodifiableMap(features);
        this.cacheInfo = cacheInfo;
    }
    
    @Nonnull
    @CheckReturnValue
    public ArchitectureType arch() {
        return arch;
    }
    
    @Nonnull
    @CheckReturnValue
    public Map<String, Boolean> features() {
        return features;
    }
    
    @Nonnull
    @CheckReturnValue
    public CacheInfo cacheInfo() {
        return cacheInfo;
    }
}
