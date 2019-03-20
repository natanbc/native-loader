package com.github.natanbc.nativeloader.system;

public class SystemType {
    private final ArchitectureType architectureType;
    private final OperatingSystemType osType;
    
    public SystemType(ArchitectureType architectureType, OperatingSystemType osType) {
        this.architectureType = architectureType;
        this.osType = osType;
    }
    
    public ArchitectureType getArchitectureType() {
        return architectureType;
    }
    
    public OperatingSystemType getOsType() {
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
    
    public static SystemType detect() {
        return new SystemType(DefaultArchitectureTypes.detect(), DefaultOperatingSystemTypes.detect());
    }
    
    private static class UnknownOperatingSystem implements OperatingSystemType {
        private final String libraryFilePrefix;
        private final String libraryFileSuffix;
        
        private UnknownOperatingSystem(String libraryFilePrefix, String libraryFileSuffix) {
            this.libraryFilePrefix = libraryFilePrefix;
            this.libraryFileSuffix = libraryFileSuffix;
        }
        
        @Override
        public String identifier() {
            return null;
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
