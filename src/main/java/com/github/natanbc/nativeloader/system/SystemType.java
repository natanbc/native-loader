package com.github.natanbc.nativeloader.system;

import com.github.natanbc.nativeloader.NativeLibraryProperties;

import java.util.Optional;

public class SystemType {
    private final ArchitectureType architectureType;
    private final OperatingSystemType osType;
    
    public SystemType(ArchitectureType architectureType, OperatingSystemType osType) {
        this.architectureType = architectureType;
        this.osType = osType;
    }
    
    public ArchitectureType architectureType() {
        return architectureType;
    }
    
    public OperatingSystemType osType() {
        return osType;
    }
    
    public String formatSystemName() {
        if(osType.identifier() != null) {
            if(osType == DefaultOperatingSystemTypes.DARWIN) {
                return osType.identifier();
            } else {
                return osType.identifier() + "-" + architectureType.identifier();
            }
        } else {
            return architectureType.identifier();
        }
    }
    
    public String formatLibraryName(String libraryName) {
        return osType.libraryFilePrefix() + libraryName + osType.libraryFileSuffix();
    }
    
    public static SystemType detect(NativeLibraryProperties properties) {
        return new SystemType(
                detectArch(properties),
                detectOS(properties)
        );
    }
    
    private static OperatingSystemType detectOS(NativeLibraryProperties properties) {
        String name = properties.operatingSystemName();
        if(name == null) {
            return DefaultOperatingSystemTypes.detect();
        }
        try {
            return DefaultOperatingSystemTypes.valueOf(name.toUpperCase());
        } catch(IllegalArgumentException e) {
            return new UnknownOperatingSystem(
                    name,
                    Optional.ofNullable(properties.libraryFileNamePrefix()).orElse("lib"),
                    Optional.ofNullable(properties.libraryFileNameSuffix()).orElse(".so")
            );
        }
    }
    
    private static ArchitectureType detectArch(NativeLibraryProperties properties) {
        String name = properties.architectureName();
        if(name == null) {
            return DefaultArchitectureTypes.detect();
        }
        return DefaultArchitectureTypes.parse(name)
                .orElse(new UnknownArchitecture(name));
    }
    
    private static class UnknownArchitecture implements ArchitectureType {
        private final String identifier;
    
        private UnknownArchitecture(String identifier) {
            this.identifier = identifier;
        }
    
        @Override
        public String identifier() {
            return identifier;
        }
        
        @Override
        public CPUType cpuType() {
            return null;
        }
    }
    
    private static class UnknownOperatingSystem implements OperatingSystemType {
        private final String identifier;
        private final String libraryFilePrefix;
        private final String libraryFileSuffix;
        
        private UnknownOperatingSystem(String identifier, String libraryFilePrefix, String libraryFileSuffix) {
            this.identifier = identifier;
            this.libraryFilePrefix = libraryFilePrefix;
            this.libraryFileSuffix = libraryFileSuffix;
        }
        
        @Override
        public String identifier() {
            return identifier;
        }
        
        @Override
        public String libraryFilePrefix() {
            return libraryFilePrefix;
        }
        
        @Override
        public String libraryFileSuffix() {
            return libraryFileSuffix;
        }
    }
}
