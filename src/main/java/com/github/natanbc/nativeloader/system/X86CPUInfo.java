package com.github.natanbc.nativeloader.system;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import java.util.Map;

public class X86CPUInfo extends CPUInfo {
    private final X86Microarchitecture microarchitecture;
    private final int family;
    private final int model;
    private final int stepping;
    private final String vendor;
    private final String brandString;
    
    public X86CPUInfo(boolean is64Bit, @Nonnull Map<String, Boolean> features, @Nonnull CacheInfo cacheInfo,
                      @Nonnull X86Microarchitecture microarchitecture, int family, int model,
                      int stepping, @Nonnull String vendor, @Nonnull String brandString) {
        super(is64Bit ? DefaultArchitectureTypes.X86_64 : DefaultArchitectureTypes.X86_32, features, cacheInfo);
        this.microarchitecture = microarchitecture;
        this.family = family;
        this.model = model;
        this.stepping = stepping;
        this.vendor = vendor;
        this.brandString = brandString;
    }
    
    @Nonnull
    @CheckReturnValue
    public X86Microarchitecture microarchitecture() {
        return microarchitecture;
    }
    
    @CheckReturnValue
    public int family() {
        return family;
    }
    
    @CheckReturnValue
    public int model() {
        return model;
    }
    
    @CheckReturnValue
    public int stepping() {
        return stepping;
    }
    
    @Nonnull
    @CheckReturnValue
    public String vendor() {
        return vendor;
    }
    
    @Nonnull
    @CheckReturnValue
    public String brandString() {
        return brandString;
    }
    
    @Override
    public String toString() {
        return "X86CPU{features = " + FeatureFormatter.formatFeatures(this) +
                ", microarchitecture = " + microarchitecture() + ", family = " + family() +
                ", model = " + model() + ", stepping = " + stepping() + ", vendor = " + vendor() +
                ", brandString = " + brandString() + ", cache = " + cacheInfo() + "}";
    }
}
