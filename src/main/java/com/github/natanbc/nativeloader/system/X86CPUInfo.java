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
    
    public X86CPUInfo(boolean is64Bit, Map<String, Boolean> features, @Nonnull X86Microarchitecture microarchitecture,
                      int family, int model, int stepping, @Nonnull String vendor, @Nonnull String brandString) {
        super(is64Bit ? DefaultArchitectureTypes.X86_64 : DefaultArchitectureTypes.X86_32, features);
        this.microarchitecture = microarchitecture;
        this.family = family;
        this.model = model;
        this.stepping = stepping;
        this.vendor = vendor;
        this.brandString = brandString;
    }
    
    @Nonnull
    @CheckReturnValue
    public X86Microarchitecture getMicroarchitecture() {
        return microarchitecture;
    }
    
    @CheckReturnValue
    public int getFamily() {
        return family;
    }
    
    @CheckReturnValue
    public int getModel() {
        return model;
    }
    
    @CheckReturnValue
    public int getStepping() {
        return stepping;
    }
    
    @Nonnull
    @CheckReturnValue
    public String getVendor() {
        return vendor;
    }
    
    @Nonnull
    @CheckReturnValue
    public String getBrandString() {
        return brandString;
    }
    
    @Override
    public String toString() {
        return "X86CPU{features = " + FeatureFormatter.formatFeatures(this) +
                ", microarchitecture = " + getMicroarchitecture() + ", family = " + getFamily() +
                ", model = " + getModel() + ", stepping = " + getStepping() + ", vendor = " + getVendor() +
                ", brandString = " + getBrandString() + "}";
    }
}
