package com.github.natanbc.nativeloader;

import com.github.natanbc.nativeloader.system.CPUType;

import javax.annotation.Nonnull;

/**
 * @see com.github.natanbc.nativeloader.feature.Aarch64Feature
 * @see com.github.natanbc.nativeloader.arm.ArmFeature
 * @see com.github.natanbc.nativeloader.x86.X86Feature
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
}
