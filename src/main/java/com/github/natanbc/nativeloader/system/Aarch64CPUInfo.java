package com.github.natanbc.nativeloader.system;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import java.util.Map;

public class Aarch64CPUInfo extends CPUInfo {
    private final int implementer;
    private final int variant;
    private final int part;
    private final int revision;
    
    public Aarch64CPUInfo(@Nonnull Map<String, Boolean> features, @Nonnull CacheInfo cacheInfo,
                          int implementer, int variant, int part, int revision) {
        super(DefaultArchitectureTypes.ARMv8_64, features, cacheInfo);
        this.implementer = implementer;
        this.variant = variant;
        this.part = part;
        this.revision = revision;
    }
    
    @CheckReturnValue
    public int getImplementer() {
        return implementer;
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
        return "Aarch64CPU{features = " + FeatureFormatter.formatFeatures(this) +
                ", implementer = " + getImplementer() + ", variant = " + getVariant() +
                ", part = " + getPart() + ", revision = " + getRevision() +
                ", cache = " + getCacheInfo() + "}";
    }
}
