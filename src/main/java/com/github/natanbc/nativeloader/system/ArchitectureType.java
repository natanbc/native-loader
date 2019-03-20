package com.github.natanbc.nativeloader.system;

public interface ArchitectureType {
    /**
     * @return Identifier used in directory names (combined with OS identifier) for this ABI
     */
    String identifier();
    
    /**
     * @return CPU type used by this ABI
     */
    CPUType cpuType();
}
