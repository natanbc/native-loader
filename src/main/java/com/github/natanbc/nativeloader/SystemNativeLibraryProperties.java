package com.github.natanbc.nativeloader;

import javax.annotation.Nullable;

public class SystemNativeLibraryProperties implements NativeLibraryProperties {
    private final String libraryName;
    private final String propertyPrefix;
    
    public SystemNativeLibraryProperties(@Nullable String libraryName, String propertyPrefix) {
        this.libraryName = libraryName;
        this.propertyPrefix = propertyPrefix;
    }
    
    @Override
    public String libraryPath() {
        return get("path");
    }
    
    @Override
    public String libraryDirectory() {
        return get("dir");
    }
    
    @Override
    public String extractionPath() {
        return get("extractPath");
    }
    
    @Override
    public String operatingSystemName() {
        return get("os");
    }
    
    @Override
    public String libraryFileNamePrefix() {
        return get("libPrefix");
    }
    
    @Override
    public String libraryFileNameSuffix() {
        return get("libSuffix");
    }
    
    @Override
    public String architectureName() {
        return get("arch");
    }
    
    private String get(String property) {
        if(libraryName == null) {
            return System.getProperty(propertyPrefix + property);
        }
        return System.getProperty(
                propertyPrefix + libraryName + "." + property,
                System.getProperty(propertyPrefix + property)
        );
    }
}
