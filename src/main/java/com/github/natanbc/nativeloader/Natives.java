package com.github.natanbc.nativeloader;

import com.github.natanbc.nativeloader.system.Aarch64CPUInfo;
import com.github.natanbc.nativeloader.system.ArmCPUInfo;
import com.github.natanbc.nativeloader.system.CPUInfo;
import com.github.natanbc.nativeloader.system.CPUType;
import com.github.natanbc.nativeloader.system.CacheInfo;
import com.github.natanbc.nativeloader.system.CacheLevelInfo;
import com.github.natanbc.nativeloader.system.CacheType;
import com.github.natanbc.nativeloader.system.DefaultArchitectureTypes;
import com.github.natanbc.nativeloader.system.X86CPUInfo;
import com.github.natanbc.nativeloader.x86.X86Microarchitecture;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.lang.annotation.Native;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Natives {
    @Native private static final int OFFSET_CACHE_LEVEL = 0;
    @Native private static final int OFFSET_CACHE_TYPE = 1;
    @Native private static final int OFFSET_CACHE_SIZE = 2;
    @Native private static final int OFFSET_CACHE_WAYS = 3;
    @Native private static final int OFFSET_CACHE_LINE_SIZE = 4;
    @Native private static final int OFFSET_CACHE_TLB_ENTRIES = 5;
    @Native private static final int OFFSET_CACHE_PARTITIONING = 6;
    
    private static native int arch();
    
    private static native int featureCount();
    
    private static native boolean hasFeature(int feature);
    
    private static native String featureName(int feature);
    
    private static native String x86Microarchitecture();
    
    private static native int x86Family();
    
    private static native int x86Model();
    
    private static native int x86Stepping();
    
    private static native void x86Vendor(@Nonnull byte[] dest, @Nonnegative int offset); //dest must have length >= offset + 12
    
    private static native void x86BrandString(@Nonnull byte[] dest, @Nonnegative int offset); //dest must have length >= offset + 48
    
    private static native int armCpuId();
    
    private static native int armImplementer();
    
    private static native int armArchitecture();
    
    private static native int armVariant();
    
    private static native int armPart();
    
    private static native int armRevision();
    
    private static native int aarch64Implementer();
    
    private static native int aarch64Variant();
    
    private static native int aarch64Part();
    
    private static native int aarch64Revision();
    
    private static native int cacheSize();
    
    private static native int cacheLevelFields();
    
    private static native void cacheLevel(int level, int[] res);
    
    private static CacheInfo parseCacheInfo() {
        int count = cacheSize();
        List<CacheLevelInfo> caches = new ArrayList<>(count);
        int[] fields = new int[cacheLevelFields()];
        for(int i = 0; i < count; i++) {
            cacheLevel(i, fields);
            caches.add(new CacheLevelInfo(
                    fields[OFFSET_CACHE_LEVEL],
                    CacheType.fromNative(fields[OFFSET_CACHE_TYPE]),
                    fields[OFFSET_CACHE_SIZE],
                    fields[OFFSET_CACHE_WAYS],
                    fields[OFFSET_CACHE_LINE_SIZE],
                    fields[OFFSET_CACHE_TLB_ENTRIES],
                    fields[OFFSET_CACHE_PARTITIONING]
            ));
        }
        return new CacheInfo(caches);
    }
    
    //must be called after loading the native lib
    @Nonnull
    static CPUInfo createSystemInfo() {
        CPUType arch = CPUType.values()[arch() - 1];
        Map<String, Boolean> features = new HashMap<>();
        for(int i = 0, j = featureCount(); i < j; i++) {
            features.put(featureName(i), hasFeature(i));
        }
        CacheInfo cache = parseCacheInfo();
        switch(arch) {
            case X86: //x86
                boolean is64Bit = DefaultArchitectureTypes.detect() == DefaultArchitectureTypes.X86_64;
                byte[] vendor = new byte[12];
                byte[] brandString = new byte[48];
                x86Vendor(vendor, 0);
                x86BrandString(brandString, 0);
                return new X86CPUInfo(
                        is64Bit, features, cache, X86Microarchitecture.UNKNOWN,
                        x86Family(), x86Model(), x86Stepping(), fromCString(vendor),
                        fromCString(brandString)
                );
            case ARM:
                return new ArmCPUInfo(
                        features, cache, armCpuId(), armImplementer(), armArchitecture(),
                        armVariant(), armPart(), armRevision()
                );
            case AARCH64:
                return new Aarch64CPUInfo(
                        features, cache, aarch64Implementer(), aarch64Variant(),
                        aarch64Part(), aarch64Revision()
                );
            default:
                throw new IllegalArgumentException("Unknown architecture " + arch);
        }
    }
    
    private static String fromCString(byte[] data) {
        int i = 0;
        while(i < data.length && data[i] != 0) {
            i++;
        }
        return new String(data, 0, i, StandardCharsets.US_ASCII);
    }
}
