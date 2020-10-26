package com.github.natanbc.nativeloader.system;

import java.util.Collections;
import java.util.List;

public class CacheInfo {
    private final List<CacheLevelInfo> caches;
    
    public CacheInfo(List<CacheLevelInfo> caches) {
        this.caches = Collections.unmodifiableList(caches);
    }
    
    public List<CacheLevelInfo> caches() {
        return caches;
    }
    
    @Override
    public String toString() {
        return "CacheInfo" + caches;
    }
}
