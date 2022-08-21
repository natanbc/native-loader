package com.github.natanbc.nativeloader.natives;

import com.github.natanbc.nativeloader.arm64.Arm64Feature;

import java.lang.annotation.Native;
import java.util.Map;
import java.util.Set;

import static java.util.Map.entry;

class DarwinArm64 {
    @Native private static final String IMPLEMENTER = "hw.cputype";
    @Native private static final String VARIANT = "hw.cpusubtype";
    @Native private static final String PART = "hw.cpufamily";
    @Native private static final String REVISION = "hw.cpusubfamily";
    
    private static final Map<String, Arm64Feature> arm64Map = Map.ofEntries(
            entry("hw.optional.floatingpoint",   Arm64Feature.FP),
            entry("hw.optional.AdvSIMD",         Arm64Feature.ASIMD),
            entry("hw.optional.arm.FEAT_AES",    Arm64Feature.AES),
            entry("hw.optional.arm.FEAT_PMULL",  Arm64Feature.PMULL),
            entry("hw.optional.arm.FEAT_SHA1",   Arm64Feature.SHA1),
            entry("hw.optional.arm.FEAT_SHA2",   Arm64Feature.SHA2),
            entry("hw.optional.armv8_crc32",     Arm64Feature.CRC32),
            entry("hw.optional.armv8_1_atomics", Arm64Feature.ATOMICS),
            entry("hw.optional.neon_hpfp",       Arm64Feature.FPHP),
            entry("hw.optional.arm.FEAT_JSCVT",  Arm64Feature.JSCVT),
            entry("hw.optional.arm.FEAT_FCMA",   Arm64Feature.FCMA),
            entry("hw.optional.arm.FEAT_LRCPC",  Arm64Feature.LRCPC),
            entry("hw.optional.armv8_2_sha3",    Arm64Feature.SHA3),
            entry("hw.optional.armv8_2_sha512",  Arm64Feature.SHA512),
            entry("hw.optional.armv8_2_fhm",     Arm64Feature.ASIMDFHM),
            entry("hw.optional.arm.FEAT_FLAGM",  Arm64Feature.FLAGM),
            entry("hw.optional.arm.FEAT_FLAGM2", Arm64Feature.FLAGM2),
            entry("hw.optional.arm.FEAT_SSBS",   Arm64Feature.SSBS),
            entry("hw.optional.arm.FEAT_SB",     Arm64Feature.SB),
            entry("hw.optional.arm.FEAT_I8MM",   Arm64Feature.I8MM),
            entry("hw.optional.arm.FEAT_BF16",   Arm64Feature.BF16),
            entry("hw.optional.arm.FEAT_BTI",    Arm64Feature.BTI)
    );
    
    static Arm64.CpuID readCpuID() {
        var cpuid = new Arm64.CpuID();
        cpuid.implementer = DarwinNatives.getSysctl(IMPLEMENTER);
        cpuid.variant = DarwinNatives.getSysctl(VARIANT);
        cpuid.part = DarwinNatives.getSysctl(PART);
        cpuid.revision = DarwinNatives.getSysctl(REVISION);
        return cpuid;
    }
    
    static void addFeatures(Set<Arm64Feature> features) {
        for(var entry : arm64Map.entrySet()) {
            if(DarwinNatives.hasFeature(entry.getKey())) {
                features.add(entry.getValue());
            }
        }
    }
}
