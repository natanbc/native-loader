package com.github.natanbc.nativeloader.natives;

import com.github.natanbc.nativeloader.x86.X86Feature;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Set;

//not really natives, but its a fallback for them
class LinuxNatives {
    private static final Map<String, X86Feature> x86Map = Map.of(
            "sse", X86Feature.SSE,
            "sse2", X86Feature.SSE2,
            "pni", X86Feature.SSE3,
            "ssse3", X86Feature.SSSE3,
            "sse4_1", X86Feature.SSE4_1,
            "sse4_2", X86Feature.SSE4_2
    );
    
    static native boolean hasGetauxval();
    static native int getauxval(int type);
    
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
