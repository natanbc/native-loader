package com.github.natanbc.nativeloader.natives;

import com.github.natanbc.nativeloader.arm64.Arm64Feature;

import java.lang.annotation.Native;
import java.util.Map;

import static com.github.natanbc.nativeloader.natives.LinuxNatives.*;

class LinuxArm64 {
    @Native
    private static final int ARM64_HWCAP_FP = 1;
    @Native private static final int ARM64_HWCAP_ASIMD = 1 << 1;
    @Native private static final int ARM64_HWCAP_EVTSTRM = 1 << 2;
    @Native private static final int ARM64_HWCAP_AES = 1 << 3;
    @Native private static final int ARM64_HWCAP_PMULL = 1 << 4;
    @Native private static final int ARM64_HWCAP_SHA1 = 1 << 5;
    @Native private static final int ARM64_HWCAP_SHA2 = 1 << 6;
    @Native private static final int ARM64_HWCAP_CRC32 = 1 << 7;
    @Native private static final int ARM64_HWCAP_ATOMICS = 1 << 8;
    @Native private static final int ARM64_HWCAP_FPHP = 1 << 9;
    @Native private static final int ARM64_HWCAP_ASIMDHP = 1 << 10;
    @Native private static final int ARM64_HWCAP_CPUID = 1 << 11;
    @Native private static final int ARM64_HWCAP_ASIMDRDM = 1 << 12;
    @Native private static final int ARM64_HWCAP_JSCVT = 1 << 13;
    @Native private static final int ARM64_HWCAP_FCMA = 1 << 14;
    @Native private static final int ARM64_HWCAP_LRCPC = 1 << 15;
    @Native private static final int ARM64_HWCAP_DCPOP = 1 << 16;
    @Native private static final int ARM64_HWCAP_SHA3 = 1 << 17;
    @Native private static final int ARM64_HWCAP_SM3 = 1 << 18;
    @Native private static final int ARM64_HWCAP_SM4 = 1 << 19;
    @Native private static final int ARM64_HWCAP_ASIMDDP = 1 << 20;
    @Native private static final int ARM64_HWCAP_SHA512 = 1 << 21;
    @Native private static final int ARM64_HWCAP_SVE = 1 << 22;
    @Native private static final int ARM64_HWCAP_ASIMDFHM = 1 << 23;
    @Native private static final int ARM64_HWCAP_DIT = 1 << 24;
    @Native private static final int ARM64_HWCAP_USCAT = 1 << 25;
    @Native private static final int ARM64_HWCAP_ILRCPC = 1 << 26;
    @Native private static final int ARM64_HWCAP_FLAGM = 1 << 27;
    @Native private static final int ARM64_HWCAP_SSBS = 1 << 28;
    @Native private static final int ARM64_HWCAP_SB = 1 << 29;
    @Native private static final int ARM64_HWCAP_PACA = 1 << 30;
    @Native private static final int ARM64_HWCAP_PACG = 1 << 31;
    
    @Native private static final int ARM64_HWCAP2_DCPODP = 1;
    @Native private static final int ARM64_HWCAP2_SVE2 = 1 << 1;
    @Native private static final int ARM64_HWCAP2_SVEAES = 1 << 2;
    @Native private static final int ARM64_HWCAP2_SVEPMULL = 1 << 3;
    @Native private static final int ARM64_HWCAP2_SVEBITPERM = 1 << 4;
    @Native private static final int ARM64_HWCAP2_SVESHA3 = 1 << 5;
    @Native private static final int ARM64_HWCAP2_SVESM4 = 1 << 6;
    @Native private static final int ARM64_HWCAP2_FLAGM2 = 1 << 7;
    @Native private static final int ARM64_HWCAP2_FRINT = 1 << 8;
    @Native private static final int ARM64_HWCAP2_SVEI8MM = 1 << 9;
    @Native private static final int ARM64_HWCAP2_SVEF32MM = 1 << 10;
    @Native private static final int ARM64_HWCAP2_SVEF64MM = 1 << 11;
    @Native private static final int ARM64_HWCAP2_SVEBF16 = 1 << 12;
    @Native private static final int ARM64_HWCAP2_I8MM = 1 << 13;
    @Native private static final int ARM64_HWCAP2_BF16 = 1 << 14;
    @Native private static final int ARM64_HWCAP2_DGH = 1 << 15;
    @Native private static final int ARM64_HWCAP2_RNG = 1 << 16;
    @Native private static final int ARM64_HWCAP2_BTI = 1 << 17;
    @Native private static final int ARM64_HWCAP2_MTE = 1 << 18;
    @Native private static final int ARM64_HWCAP2_ECV = 1 << 19;
    @Native private static final int ARM64_HWCAP2_AFP = 1 << 20;
    @Native private static final int ARM64_HWCAP2_RPRES = 1 << 21;
    
    private static final Map<String, Arm64FeatureInfo> arm64Map = Map.ofEntries(
            feature(Arm64Feature.FP,         AT_HWCAP,  ARM64_HWCAP_FP),
            feature(Arm64Feature.ASIMD,      AT_HWCAP,  ARM64_HWCAP_ASIMD),
            feature(Arm64Feature.EVTSTRM,    AT_HWCAP,  ARM64_HWCAP_EVTSTRM),
            feature(Arm64Feature.AES,        AT_HWCAP,  ARM64_HWCAP_AES),
            feature(Arm64Feature.PMULL,      AT_HWCAP,  ARM64_HWCAP_PMULL),
            feature(Arm64Feature.SHA1,       AT_HWCAP,  ARM64_HWCAP_SHA1),
            feature(Arm64Feature.SHA2,       AT_HWCAP,  ARM64_HWCAP_SHA2),
            feature(Arm64Feature.CRC32,      AT_HWCAP,  ARM64_HWCAP_CRC32),
            feature(Arm64Feature.ATOMICS,    AT_HWCAP,  ARM64_HWCAP_ATOMICS),
            feature(Arm64Feature.FPHP,       AT_HWCAP,  ARM64_HWCAP_FPHP),
            feature(Arm64Feature.ASIMDHP,    AT_HWCAP,  ARM64_HWCAP_ASIMDHP),
            feature(Arm64Feature.CPUID,      AT_HWCAP,  ARM64_HWCAP_CPUID),
            feature(Arm64Feature.ASIMDRDM,   AT_HWCAP,  ARM64_HWCAP_ASIMDRDM),
            feature(Arm64Feature.JSCVT,      AT_HWCAP,  ARM64_HWCAP_JSCVT),
            feature(Arm64Feature.FCMA,       AT_HWCAP,  ARM64_HWCAP_FCMA),
            feature(Arm64Feature.LRCPC,      AT_HWCAP,  ARM64_HWCAP_LRCPC),
            feature(Arm64Feature.DCPOP,      AT_HWCAP,  ARM64_HWCAP_DCPOP),
            feature(Arm64Feature.SHA3,       AT_HWCAP,  ARM64_HWCAP_SHA3),
            feature(Arm64Feature.SM3,        AT_HWCAP,  ARM64_HWCAP_SM3),
            feature(Arm64Feature.SM4,        AT_HWCAP,  ARM64_HWCAP_SM4),
            feature(Arm64Feature.ASIMDDP,    AT_HWCAP,  ARM64_HWCAP_ASIMDDP),
            feature(Arm64Feature.SHA512,     AT_HWCAP,  ARM64_HWCAP_SHA512),
            feature(Arm64Feature.SVE,        AT_HWCAP,  ARM64_HWCAP_SVE),
            feature(Arm64Feature.ASIMDFHM,   AT_HWCAP,  ARM64_HWCAP_ASIMDFHM),
            feature(Arm64Feature.DIT,        AT_HWCAP,  ARM64_HWCAP_DIT),
            feature(Arm64Feature.USCAT,      AT_HWCAP,  ARM64_HWCAP_USCAT),
            feature(Arm64Feature.ILRCPC,     AT_HWCAP,  ARM64_HWCAP_ILRCPC),
            feature(Arm64Feature.FLAGM,      AT_HWCAP,  ARM64_HWCAP_FLAGM),
            feature(Arm64Feature.SSBS,       AT_HWCAP,  ARM64_HWCAP_SSBS),
            feature(Arm64Feature.SB,         AT_HWCAP,  ARM64_HWCAP_SB),
            feature(Arm64Feature.PACA,       AT_HWCAP,  ARM64_HWCAP_PACA),
            feature(Arm64Feature.PACG,       AT_HWCAP,  ARM64_HWCAP_PACG),
            feature(Arm64Feature.DCPODP,     AT_HWCAP2, ARM64_HWCAP2_DCPODP),
            feature(Arm64Feature.SVE2,       AT_HWCAP2, ARM64_HWCAP2_SVE2),
            feature(Arm64Feature.SVEAES,     AT_HWCAP2, ARM64_HWCAP2_SVEAES),
            feature(Arm64Feature.SVEPMULL,   AT_HWCAP2, ARM64_HWCAP2_SVEPMULL),
            feature(Arm64Feature.SVEBITPERM, AT_HWCAP2, ARM64_HWCAP2_SVEBITPERM),
            feature(Arm64Feature.SVESHA3,    AT_HWCAP2, ARM64_HWCAP2_SVESHA3),
            feature(Arm64Feature.SVESM4,     AT_HWCAP2, ARM64_HWCAP2_SVESM4),
            feature(Arm64Feature.FLAGM2,     AT_HWCAP2, ARM64_HWCAP2_FLAGM2),
            feature(Arm64Feature.FRINT,      AT_HWCAP2, ARM64_HWCAP2_FRINT),
            feature(Arm64Feature.SVEI8MM,    AT_HWCAP2, ARM64_HWCAP2_SVEI8MM),
            feature(Arm64Feature.SVEF32MM,   AT_HWCAP2, ARM64_HWCAP2_SVEF32MM),
            feature(Arm64Feature.SVEF64MM,   AT_HWCAP2, ARM64_HWCAP2_SVEF64MM),
            feature(Arm64Feature.SVEBF16,    AT_HWCAP2, ARM64_HWCAP2_SVEBF16),
            feature(Arm64Feature.I8MM,       AT_HWCAP2, ARM64_HWCAP2_I8MM),
            feature(Arm64Feature.BF16,       AT_HWCAP2, ARM64_HWCAP2_BF16),
            feature(Arm64Feature.DGH,        AT_HWCAP2, ARM64_HWCAP2_DGH),
            feature(Arm64Feature.RNG,        AT_HWCAP2, ARM64_HWCAP2_RNG),
            feature(Arm64Feature.BTI,        AT_HWCAP2, ARM64_HWCAP2_BTI),
            feature(Arm64Feature.MTE,        AT_HWCAP2, ARM64_HWCAP2_MTE),
            feature(Arm64Feature.ECV,        AT_HWCAP2, ARM64_HWCAP2_ECV),
            feature(Arm64Feature.AFP,        AT_HWCAP2, ARM64_HWCAP2_AFP),
            feature(Arm64Feature.RPRES,      AT_HWCAP2, ARM64_HWCAP2_RPRES)
    );
    
    private static Map.Entry<String, Arm64FeatureInfo> feature(Arm64Feature feature, int hwcap, int hwcapBit) {
        return Map.entry(feature.nativeName(), new Arm64FeatureInfo(feature, hwcap, hwcapBit));
    }
    
    private static class Arm64FeatureInfo {
        private final Arm64Feature feature;
        private final int hwcap;
        private final int hwcapBit;
        
        private Arm64FeatureInfo(Arm64Feature feature, int hwcap, int hwcapBit) {
            this.feature = feature;
            this.hwcap = hwcap;
            this.hwcapBit = hwcapBit;
        }
    }
}
