package com.github.natanbc.nativeloader.natives;

import com.github.natanbc.nativeloader.x86.X86Feature;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Set;

class FreeBSDX86 {
    private static final Map<String, X86Feature> x86Map = Map.of(
            "SSE", X86Feature.SSE,
            "SSE2", X86Feature.SSE2,
            "SSE3", X86Feature.SSE3,
            "SSSE3", X86Feature.SSSE3,
            "SSE4.1", X86Feature.SSE4_1,
            "SSE4.2", X86Feature.SSE4_2
    );
    
    static void addFeatures(Set<X86Feature> features) {
        try(var reader = Files.newBufferedReader(Path.of("/var/run/dmesg.boot"), StandardCharsets.UTF_8)) {
            String line;
            while((line = reader.readLine()) != null) {
                if(!line.startsWith("  Features")) continue;
                var start = line.indexOf('<');
                var end = line.indexOf('>');
                if(start < 0 || end < 0) continue;
                
                for(var featureName : line.substring(start + 1, end).split(",")) {
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
