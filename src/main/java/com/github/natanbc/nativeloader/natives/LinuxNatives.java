package com.github.natanbc.nativeloader.natives;

import com.github.natanbc.nativeloader.x86.X86Feature;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.annotation.Native;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

class LinuxNatives {
    private static final Map<String, X86Feature> x86Map = Map.of(
            "sse", X86Feature.SSE,
            "sse2", X86Feature.SSE2,
            "pni", X86Feature.SSE3,
            "ssse3", X86Feature.SSSE3,
            "sse4_1", X86Feature.SSE4_1,
            "sse4_2", X86Feature.SSE4_2
    );
    private static final AtomicReference<Boolean> HAS_GETAUXVAL = new AtomicReference<>();
    
    @Native static final int AT_HWCAP = 16;
    @Native static final int AT_HWCAP2 = 26;
    
    private static native boolean hasGetauxval();
    private static native long getauxval0(long type);
    
    private static int getauxvalFromProcSelf(int type) {
        try(var file = new RandomAccessFile("/proc/self/auxv", "r")) {
            while(file.getFilePointer() < file.length()) {
                var typ = Integer.reverseBytes(file.readInt());
                var val = Integer.reverseBytes(file.readInt());
                
                if(typ == type) {
                    return val;
                }
            }
            return 0;
        } catch(IOException ignored) {
            return 0;
        }
    }
    
    static int getauxval(int type) {
        var hasGetauxval = HAS_GETAUXVAL.get();
        if(hasGetauxval == null) {
            hasGetauxval = hasGetauxval();
            HAS_GETAUXVAL.set(hasGetauxval);
        }
        if(hasGetauxval) {
            return (int)getauxval0(type);
        } else {
            return getauxvalFromProcSelf(type);
        }
    }
    
    static void addX86Features(Set<X86Feature> features) {
        try(var reader = Files.newBufferedReader(Path.of("/proc/cpuinfo"), StandardCharsets.UTF_8)) {
            String line;
            while((line = reader.readLine()) != null) {
                var nameValue = line.split(":");
                if(nameValue.length != 2) continue;
                if(!nameValue[0].strip().equals("flags")) continue;
                for(var featureName : nameValue[1].split(" ")) {
                    var feature = x86Map.get(featureName);
                    if(feature != null) {
                        features.add(feature);
                    }
                }
                break;
            }
        } catch(IOException ignored) {
            //too bad, assume no features present
        }
    }
}
