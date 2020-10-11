package com.github.natanbc.nativeloader.system;

import javax.annotation.Nullable;

public interface ArchitectureType {
    /**
     * @return Identifier used in directory names (combined with OS identifier) for this ABI
     */
    String identifier();
    
    /**
     * @return CPU type used by this ABI
     */
    @Nullable
    CPUType cpuType();
}
