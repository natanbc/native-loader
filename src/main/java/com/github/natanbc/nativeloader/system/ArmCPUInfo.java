package com.github.natanbc.nativeloader.system;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import java.util.Map;

public class ArmCPUInfo extends CPUInfo {
    private final int cpuId;
    private final int implementer;
    private final int architecture;
    private final int variant;
    private final int part;
    private final int revision;
    
    public ArmCPUInfo(@Nonnull Map<String, Boolean> features, @Nonnull CacheInfo cacheInfo,
                      int cpuId, int implementer, int architecture,
                      int variant, int part, int revision) {
        super(DefaultArchitectureTypes.ARM, features, cacheInfo);
        this.cpuId = cpuId;
        this.implementer = implementer;
        this.architecture = architecture;
        this.variant = variant;
        this.part = part;
        this.revision = revision;
    }
    
    @CheckReturnValue
    public int getCpuId() {
        return cpuId;
    }
    
    @CheckReturnValue
    public int getImplementer() {
        return implementer;
    }
    
    @CheckReturnValue
    public int getArchitecture() {
        return architecture;
    }
    
    @CheckReturnValue
    public int getVariant() {
        return variant;
    }
    
    @CheckReturnValue
    public int getPart() {
        return part;
    }
    
    @CheckReturnValue
    public int getRevision() {
        return revision;
    }
    
    @Override
    public String toString() {
        return "ArmCPU{features = " + FeatureFormatter.formatFeatures(this) + ", cpuId = " + getCpuId() +
                ", implementer = " + getImplementer() + ", architecture = " + getArchitecture() +
                ", variant = " + getVariant() + ", part = " + getPart() + ", revision = " + getRevision() +
                ", cache = " + getCacheInfo() + "}";
    }
}
