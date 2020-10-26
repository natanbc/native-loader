package com.github.natanbc.nativeloader.feature;

import com.github.natanbc.nativeloader.system.CPUType;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

/**
 * @see com.github.natanbc.nativeloader.feature.Aarch64Feature
 * @see com.github.natanbc.nativeloader.feature.ArmFeature
 * @see com.github.natanbc.nativeloader.feature.X86Feature
 */
public interface CPUFeature {
    /**
     * @return The type of CPU this feature applies to.
     */
    @Nonnull
    CPUType cpuType();
    
    /**
     * @return The name of this feature, as returned by the detector library
     *         and which should be appended to the loaded natives.
     */
    @Nonnull
    String nativeName();
    
    @Nonnull
    static CPUFeature custom(CPUType type, String nativeName) {
        return new CPUFeature() {
            @Nonnull
            @Override
            public CPUType cpuType() {
                return type;
            }
    
            @Nonnull
            @Override
            public String nativeName() {
                return nativeName;
            }
        };
    }
}
