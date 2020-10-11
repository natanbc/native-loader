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
    public String getLibraryPath() {
        return get("path");
    }
    
    @Override
    public String getLibraryDirectory() {
        return get("dir");
    }
    
    @Override
    public String getExtractionPath() {
        return get("extractPath");
    }
    
    @Override
    public String getOperatingSystemName() {
        return get("os");
    }
    
    @Override
    public String getLibraryFileNamePrefix() {
        return get("libPrefix");
    }
    
    @Override
    public String getLibraryFileNameSuffix() {
        return get("libSuffix");
    }
    
    @Override
    public String getArchitectureName() {
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
