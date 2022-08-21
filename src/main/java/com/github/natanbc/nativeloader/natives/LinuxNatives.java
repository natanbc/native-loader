package com.github.natanbc.nativeloader.natives;

import com.github.natanbc.nativeloader.arm.ArmFeature;
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
    @Native private static final int AT_HWCAP = 16;
    @Native private static final int AT_HWCAP2 = 26;
    
    @Native private static final int ARM_HWCAP_SWP = 1;
    @Native private static final int ARM_HWCAP_HALF = 1 << 1;
    @Native private static final int ARM_HWCAP_THUMB = 1 << 2;
    @Native private static final int ARM_HWCAP_26BIT = 1 << 3;
    @Native private static final int ARM_HWCAP_FAST_MULT = 1 << 4;
    @Native private static final int ARM_HWCAP_FPA = 1 << 5;
    @Native private static final int ARM_HWCAP_VFP = 1 << 6;
    @Native private static final int ARM_HWCAP_EDSP = 1 << 7;
    @Native private static final int ARM_HWCAP_JAVA = 1 << 8;
    @Native private static final int ARM_HWCAP_IWMMXT = 1 << 9;
    @Native private static final int ARM_HWCAP_CRUNCH = 1 << 10;
    @Native private static final int ARM_HWCAP_THUMBEE = 1 << 11;
    @Native private static final int ARM_HWCAP_NEON = 1 << 12;
    @Native private static final int ARM_HWCAP_VFPV3 = 1 << 13;
    @Native private static final int ARM_HWCAP_VFPV3D16 = 1 << 14;
    @Native private static final int ARM_HWCAP_TLS = 1 << 15;
    @Native private static final int ARM_HWCAP_VFPV4 = 1 << 16;
    @Native private static final int ARM_HWCAP_IDIVA = 1 << 17;
    @Native private static final int ARM_HWCAP_IDIVT = 1 << 18;
    @Native private static final int ARM_HWCAP_VFPD32 = 1 << 19;
    @Native private static final int ARM_HWCAP_LPAE = 1 << 20;
    @Native private static final int ARM_HWCAP_EVTSTRM = 1 << 21;
    @Native private static final int ARM_HWCAP2_AES = 1;
    @Native private static final int ARM_HWCAP2_PMULL = 1 << 1;
    @Native private static final int ARM_HWCAP2_SHA1 = 1 << 2;
    @Native private static final int ARM_HWCAP2_SHA2 = 1 << 3;
    @Native private static final int ARM_HWCAP2_CRC32 = 1 << 4;
    
    private static final Map<String, ArmFeatureInfo> armMap = Map.ofEntries(
            feature(ArmFeature.SWP,       AT_HWCAP,  ARM_HWCAP_SWP),
            feature(ArmFeature.HALF,      AT_HWCAP,  ARM_HWCAP_HALF),
            feature(ArmFeature.THUMB,     AT_HWCAP,  ARM_HWCAP_THUMB),
            feature(ArmFeature.ARM_26BIT, AT_HWCAP,  ARM_HWCAP_26BIT),
            feature(ArmFeature.FASTMULT,  AT_HWCAP,  ARM_HWCAP_FAST_MULT),
            feature(ArmFeature.FPA,       AT_HWCAP,  ARM_HWCAP_FPA),
            feature(ArmFeature.VFP,       AT_HWCAP,  ARM_HWCAP_VFP),
            feature(ArmFeature.EDSP,      AT_HWCAP,  ARM_HWCAP_EDSP),
            feature(ArmFeature.JAVA,      AT_HWCAP,  ARM_HWCAP_JAVA),
            feature(ArmFeature.IWMMXT,    AT_HWCAP,  ARM_HWCAP_IWMMXT),
            feature(ArmFeature.CRUNCH,    AT_HWCAP,  ARM_HWCAP_CRUNCH),
            feature(ArmFeature.THUMBEE,   AT_HWCAP,  ARM_HWCAP_THUMBEE),
            feature(ArmFeature.NEON,      AT_HWCAP,  ARM_HWCAP_NEON),
            feature(ArmFeature.VFPV3,     AT_HWCAP,  ARM_HWCAP_VFPV3),
            feature(ArmFeature.VFPV3D16,  AT_HWCAP,  ARM_HWCAP_VFPV3D16),
            feature(ArmFeature.TLS,       AT_HWCAP,  ARM_HWCAP_TLS),
            feature(ArmFeature.VFPV4,     AT_HWCAP,  ARM_HWCAP_VFPV4),
            feature(ArmFeature.IDIVA,     AT_HWCAP,  ARM_HWCAP_IDIVA),
            feature(ArmFeature.IDIVT,     AT_HWCAP,  ARM_HWCAP_IDIVT),
            feature(ArmFeature.VFPD32,    AT_HWCAP,  ARM_HWCAP_VFPD32),
            feature(ArmFeature.LPAE,      AT_HWCAP,  ARM_HWCAP_LPAE),
            feature(ArmFeature.EVTSTRM,   AT_HWCAP,  ARM_HWCAP_EVTSTRM),
            feature(ArmFeature.AES,       AT_HWCAP2, ARM_HWCAP2_AES),
            feature(ArmFeature.PMULL,     AT_HWCAP2, ARM_HWCAP2_PMULL),
            feature(ArmFeature.SHA1,      AT_HWCAP2, ARM_HWCAP2_SHA1),
            feature(ArmFeature.SHA2,      AT_HWCAP2, ARM_HWCAP2_SHA2),
            feature(ArmFeature.CRC32,     AT_HWCAP2, ARM_HWCAP2_CRC32)
    );
    private static final Map<String, X86Feature> x86Map = Map.of(
            "sse", X86Feature.SSE,
            "sse2", X86Feature.SSE2,
            "pni", X86Feature.SSE3,
            "ssse3", X86Feature.SSSE3,
            "sse4_1", X86Feature.SSE4_1,
            "sse4_2", X86Feature.SSE4_2
    );
    private static final AtomicReference<Boolean> HAS_GETAUXVAL = new AtomicReference<>();
    
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
    
    static Arm.CpuID readArmCpuID() {
        var cpuid = new Arm.CpuID();
        try(var reader = Files.newBufferedReader(Path.of("/proc/cpuinfo"), StandardCharsets.UTF_8)) {
            String line;
            while((line = reader.readLine()) != null) {
                var nameValue = line.split(":");
                if(nameValue.length != 2) continue;
                switch(nameValue[0].strip()) {
                    case "CPU implementer":
                        cpuid.implementer = Integer.parseUnsignedInt(nameValue[1].strip());
                        break;
                    case "CPU variant":
                        cpuid.variant = Integer.parseUnsignedInt(nameValue[1].strip());
                        break;
                    case "CPU part":
                        cpuid.part = Integer.parseUnsignedInt(nameValue[1].strip());
                        break;
                    case "CPU revision":
                        cpuid.revision = Integer.parseUnsignedInt(nameValue[1].strip());
                        break;
                    case "CPU architecture":
                        cpuid.architecture = Integer.parseUnsignedInt(nameValue[1].strip());
                        break;
                    case "Processor":
                    case "model name":
                        cpuid.armv6 = nameValue[1].contains("(v6l)");
                        break;
                    case "Hardware":
                        cpuid.goldfish = nameValue[1].equals("Goldfish");
                        break;
                }
            }
        } catch(IOException ignored) {}
        
        if(cpuid.architecture >= 7 && cpuid.armv6) {
            cpuid.architecture = 6;
        }
        
        return cpuid;
    }
    
    static void addArmFeatures(Set<ArmFeature> features) {
        try(var reader = Files.newBufferedReader(Path.of("/proc/cpuinfo"), StandardCharsets.UTF_8)) {
            String line;
            while((line = reader.readLine()) != null) {
                var nameValue = line.split(":");
                if(nameValue.length != 2) continue;
                if(!nameValue[0].strip().equals("Features")) continue;
                for(var featureName : nameValue[1].split(" ")) {
                    var info = armMap.get(featureName);
                    if(info != null) {
                        features.add(info.feature);
                    }
                }
            }
        } catch(IOException ignored) {}
        var hwcap = getauxval(AT_HWCAP);
        var hwcap2 = getauxval(AT_HWCAP2);
        for(var info : armMap.values()) {
            var cap = info.hwcap == AT_HWCAP ? hwcap : hwcap2;
            if((cap & info.hwcapBit) == info.hwcapBit) {
                features.add(info.feature);
            }
        }
        
        var cpuid = readArmCpuID();
        if(cpuid.implementer == 0x41 && cpuid.variant == 0x0 && cpuid.part == 0xC08 && cpuid.revision == 0x0) {
            if(cpuid.architecture >= 7 && cpuid.goldfish) {
                features.add(ArmFeature.IDIVA);
            }
        }
        if(cpuid.implementer == 0x51 && cpuid.variant == 0x1 && cpuid.part == 0x04D && cpuid.revision == 0x0) {
            features.remove(ArmFeature.NEON);
        }
        if(cpuid.implementer == 0x51 && cpuid.architecture == 7 && (cpuid.part == 0x04D || cpuid.part == 0x06F)) {
            features.add(ArmFeature.IDIVA);
            features.add(ArmFeature.IDIVT);
        }
        
        if(features.contains(ArmFeature.VFPV4) || features.contains(ArmFeature.NEON)) {
            features.add(ArmFeature.VFPV3);
        }
        if(features.contains(ArmFeature.VFPV3)) {
            features.add(ArmFeature.VFP);
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
    
    private static Map.Entry<String, ArmFeatureInfo> feature(ArmFeature feature, int hwcap, int hwcapBit) {
        return Map.entry(feature.nativeName(), new ArmFeatureInfo(feature, hwcap, hwcapBit));
    }
    
    private static class ArmFeatureInfo {
        private final ArmFeature feature;
        private final int hwcap;
        private final int hwcapBit;
    
        private ArmFeatureInfo(ArmFeature feature, int hwcap, int hwcapBit) {
            this.feature = feature;
            this.hwcap = hwcap;
            this.hwcapBit = hwcapBit;
        }
    }
}
