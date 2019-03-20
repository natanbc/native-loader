package com.github.natanbc.nativeloader;

import com.github.natanbc.nativeloader.system.CPUInfo;

class DetectorLoader {
    private static volatile boolean loadedDetector;
    private static volatile CPUInfo cachedInfo;
    
    static CPUInfo loadDetector(LibraryBinaryLoader loader) {
        if(loadedDetector) return cachedInfo;
        return load0(loader);
    }
    
    private static synchronized CPUInfo load0(LibraryBinaryLoader loader) {
        if(loadedDetector) return cachedInfo;
        try {
            NativeLibLoader.create(loader, "detector").load();
        } catch(RuntimeException e) {
            try {
                NativeLibLoader.create("detector").load(); //try using defaults
            } catch(RuntimeException e2) {
                e.addSuppressed(e2);
                throw e;
            }
        }
        CPUInfo info = Natives.createSystemInfo();
        cachedInfo = info;
        loadedDetector = true;
        return info;
    }
}
