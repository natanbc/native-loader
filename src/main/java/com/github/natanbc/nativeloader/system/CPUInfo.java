package com.github.natanbc.nativeloader.system;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.Map;

public class CPUInfo {
    private final ArchitectureType arch;
    private final Map<String, Boolean> features;
    
    public CPUInfo(@Nonnull ArchitectureType arch, Map<String, Boolean> features) {
        this.arch = arch;
        this.features = Collections.unmodifiableMap(features);
    }
    
    @Nonnull
    @CheckReturnValue
    public ArchitectureType getArch() {
        return arch;
    }
    
    @CheckReturnValue
    public Map<String, Boolean> getFeatures() {
        return features;
    }
}
