package com.github.natanbc.nativeloader.natives;

import com.github.natanbc.nativeloader.x86.X86Feature;
import com.github.natanbc.nativeloader.x86.X86Microarchitecture;
import com.github.natanbc.nativeloader.x86.X86Vendor;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.EnumSet;
import java.util.Set;

import static com.github.natanbc.nativeloader.natives.Bits.*;
import static com.github.natanbc.nativeloader.natives.X86Natives.*;
import static com.github.natanbc.nativeloader.x86.X86Feature.*;
import static com.github.natanbc.nativeloader.x86.X86Microarchitecture.*;

public class X86 {
    private static final int MASK_XMM = 0x0000_0002;
    private static final int MASK_YMM = 0x0000_0004;
    private static final int MASK_MASKREG = 0x0000_0020;
    private static final int MASK_ZMM0_15 = 0x0000_0040;
    private static final int MASK_ZMM16_31 = 0x0000_0080;
    private static final int MASK_XTILECFG = 0x0002_0000;
    private static final int MASK_XTILEDATA = 0x0004_0000;
    
    private static String vendor() {
        var leaf = cpuid(0, 0);
        var buffer = ByteBuffer.allocate(12).order(ByteOrder.LITTLE_ENDIAN);
        buffer.asIntBuffer().put(leaf.ebx).put(leaf.edx).put(leaf.ecx);
        return readString(buffer);
    }
    
    private static String brand() {
        if(!supportsLeaf(0x80000004)) {
            return "<unknown>";
        }
        
        var buffer = ByteBuffer.allocate(48).order(ByteOrder.LITTLE_ENDIAN);
        var ints = buffer.asIntBuffer();
    
        for(int i = 2; i < 5; i++) {
            var leaf = cpuid(0x80000000 + i, 0);
            ints.put(leaf.eax).put(leaf.ebx).put(leaf.ecx).put(leaf.edx);
        }
        return readString(buffer).strip();
    }
    
    private static String readString(ByteBuffer buffer) {
        int length = buffer.capacity();
        while(length > 0 && buffer.get(length - 1) == 0) length--;
        return new String(buffer.array(), buffer.arrayOffset(), length, StandardCharsets.US_ASCII);
    }
    
    private static class OsPreserves {
        private boolean sse, avx, avx512, amx;
    }
    
    private static class Leaves {
        private static final Set<X86Vendor> PARSE_FEATURES_FROM = EnumSet.of(
                X86Vendor.INTEL, X86Vendor.AMD, X86Vendor.HYGON, X86Vendor.ZHAOXIN, X86Vendor.CENTAUR
        );
        private final String vendorString = vendor();
        private final X86Vendor vendor = X86Vendor.fromCpuidName(vendorString);
        private final String brand = brand();
        private final Leaf leaf1 = cpuid(1, 0);
        private final Leaf leaf7_0 = cpuid(7, 0);
        private final Leaf leaf7_1 = cpuid(7, 1);
        private final Leaf leaf8000001 = cpuid(0x8000_0001, 0);
        private final OsPreserves osPreserves = new OsPreserves();
        
        private final int family;
        private final int model;
        private final int stepping;
        
        private Leaves() {
            int extendedFamily = extractBitRange(leaf1.eax, 27, 20);
            int extendedModel = extractBitRange(leaf1.eax, 19, 16);
            int family = extractBitRange(leaf1.eax, 11, 8);
            int model = extractBitRange(leaf1.eax, 7, 4);
            
            if(family == 15) {
                this.family = extendedFamily + family;
            } else {
                this.family = family;
            }
            
            if(family == 6 || family == 15) {
                this.model = (extendedModel << 4) + model;
            } else {
                this.model = model;
            }
            
            this.stepping = extractBitRange(leaf1.eax, 3, 0);
        }
        
        private Set<X86Feature> features() {
            var set = EnumSet.noneOf(X86Feature.class);
            if(vendor.isHypervisor() || PARSE_FEATURES_FROM.contains(vendor)) {
                parseLeaf1(set);
                parseLeaf7(set);
                parseLeaf80000001(set);
                if(vendor == X86Vendor.AMD || vendor == X86Vendor.HYGON) {
                    //must be *after* parseLeaf1
                    parseExtraAmd(set);
                }
            }
            return set;
        }
    
        private void parseLeaf1(Set<X86Feature> features) {
            if(leaf1.ecx(1)) features.add(PCLMULQDQ);
            if(leaf1.ecx(6)) features.add(SMX);
            if(leaf1.ecx(13)) features.add(CX16);
            if(leaf1.ecx(18)) features.add(DCA);
            if(leaf1.ecx(22)) features.add(MOVBE);
            if(leaf1.ecx(23)) features.add(POPCNT);
            if(leaf1.ecx(25)) features.add(AES);
            if(leaf1.ecx(29)) features.add(F16C);
            if(leaf1.ecx(30)) features.add(RDRND);
        
            if(leaf1.edx(0)) features.add(FPU);
            if(leaf1.edx(4)) features.add(TSC);
            if(leaf1.edx(8)) features.add(CX8);
            if(leaf1.edx(19)) features.add(CLFSH);
            if(leaf1.edx(23)) features.add(MMX);
            if(leaf1.edx(27)) features.add(SS);
        
            var xsave = leaf1.ecx(26);
            var osxsave = leaf1.ecx(27);
            var hasXcr0 = xsave && osxsave;
        
            if(hasXcr0) {
                var xcr0eax = (int) xgetbv(0);
                osPreserves.sse = hasMask(xcr0eax, MASK_XMM);
                osPreserves.avx = hasMask(xcr0eax, MASK_XMM | MASK_YMM);
                osPreserves.avx512 = hasMask(xcr0eax, MASK_XMM | MASK_YMM | MASK_MASKREG | MASK_ZMM0_15 | MASK_ZMM16_31);
                osPreserves.amx = hasMask(xcr0eax, MASK_XMM | MASK_YMM | MASK_MASKREG | MASK_ZMM0_15 |
                                                           MASK_ZMM16_31 | MASK_XTILECFG | MASK_XTILEDATA);
                //TODO: macos check
                //noinspection ConstantConditions
                if(false) {
                    osPreserves.avx512 = DarwinNatives.getSysctl(DarwinNatives.AVX512F);
                }
                
                
                if(osPreserves.sse) {
                    if(leaf1.edx(25)) features.add(SSE);
                    if(leaf1.edx(26)) features.add(SSE2);
                    if(leaf1.ecx(0)) features.add(SSE3);
                    if(leaf1.ecx(9)) features.add(SSSE3);
                    if(leaf1.ecx(19)) features.add(SSE4_1);
                    if(leaf1.ecx(20)) features.add(SSE4_2);
                }
                if(osPreserves.avx) {
                    if(leaf1.ecx(12)) features.add(FMA3);
                    if(leaf1.ecx(28)) features.add(AVX);
                    if(leaf7_0.ebx(5)) features.add(AVX2);
                    if(leaf7_1.eax(4)) features.add(AVXVNNI);
                }
                if(osPreserves.avx512) {
                    if(leaf7_0.ebx(16)) features.add(AVX512F);
                    if(leaf7_0.ebx(17)) features.add(AVX512DQ);
                    if(leaf7_0.ebx(21)) features.add(AVX512IFMA);
                    if(leaf7_0.ebx(26)) features.add(AVX512PF);
                    if(leaf7_0.ebx(27)) features.add(AVX512ER);
                    if(leaf7_0.ebx(28)) features.add(AVX512CD);
                    if(leaf7_0.ebx(30)) features.add(AVX512BW);
                    if(leaf7_0.ebx(31)) features.add(AVX512VL);
    
                    if(leaf7_0.ecx(1)) features.add(AVX512VBMI);
                    if(leaf7_0.ecx(6)) features.add(AVX512VBMI2);
                    if(leaf7_0.ecx(11)) features.add(AVX512VNNI);
                    if(leaf7_0.ecx(12)) features.add(AVX512BITALG);
                    if(leaf7_0.ecx(14)) features.add(AVX512VPOPCNTDQ);
    
                    if(leaf7_0.edx(2)) features.add(AVX512_4VNNIW);
                    if(leaf7_0.edx(3)) features.add(AVX512_4VBMI2);
                    if(leaf7_0.edx(3)) features.add(AVX512_4FMAPS);
                    if(leaf7_0.edx(8)) features.add(AVX512_VP2INTERSECT);
                    
                    if(leaf7_1.eax(5)) features.add(AVX512_BF16);
                }
                if(osPreserves.amx) {
                    //checking in case of a hackintosh on newer cpus
                    if(leaf7_0.edx(22)) features.add(AMX_BF16);
                    if(leaf7_0.edx(24)) features.add(AMX_TILE);
                    if(leaf7_0.edx(25)) features.add(AMX_INT8);
                }
            } else {
                parseFeaturesFromOs(features);
                if(features.contains(SSE)) {
                    osPreserves.sse = true;
                }
            }
        }
    
        private void parseLeaf7(Set<X86Feature> features) {
            if(leaf7_0.ebx(2)) features.add(SGX);
            if(leaf7_0.ebx(3)) features.add(BMI1);
            if(leaf7_0.ebx(4)) features.add(HLE);
            if(leaf7_0.ebx(8)) features.add(BMI2);
            if(leaf7_0.ebx(9)) features.add(ERMS);
            if(leaf7_0.ebx(11)) features.add(RTM);
            if(leaf7_0.ebx(18)) features.add(RDSEED);
            if(leaf7_0.ebx(19)) features.add(ADX);
            if(leaf7_0.ebx(23)) features.add(CLFLUSHOPT);
            if(leaf7_0.ebx(24)) features.add(CLWB);
            if(leaf7_0.ebx(29)) features.add(SHA);
        
            if(leaf7_0.ecx(9)) features.add(VAES);
            if(leaf7_0.ecx(10)) features.add(VPCLMULQDQ);
        }
    
        private void parseLeaf80000001(Set<X86Feature> features) {
            if(leaf8000001.ecx(5)) features.add(LZCNT);
        }
        
        private void parseExtraAmd(Set<X86Feature> features) {
            if(osPreserves.sse) {
                if(leaf8000001.ecx(6)) features.add(SSE4A);
            }
            if(osPreserves.avx) {
                if(leaf8000001.ecx(16)) features.add(FMA4);
            }
        }
        
        @SuppressWarnings({"DuplicateCondition", "ConstantConditions"})
        private void parseFeaturesFromOs(Set<X86Feature> features) {
            //TODO: detect OS
            if(false) {
                WindowsNatives.addX86Features(features);
            } else if(false) {
                DarwinNatives.addX86Features(features);
            } else if(false) {
                LinuxNatives.addX86Features(features);
            } else if(false) {
                FreeBSD.addX86Features(features);
            }
        }
        
        private X86Microarchitecture uarch() {
            switch(vendor) {
                case AMD: return amdUarch();
                case CENTAUR:
                case ZHAOXIN:
                    switch(family) {
                        case 0x06:
                            switch(model) {
                                case 0x0F:
                                case 0x19:
                                    return ZHAOXIN_ZHANGJIANG;
                            }
                            break;
                        case 0x07:
                            switch(model) {
                                case 0x1B:
                                    return ZHAOXIN_WUDAOKOU;
                                case 0x3B:
                                    return ZHAOXIN_LUJIAZUI;
                                case 0x5B:
                                    return ZHAOXIN_YONGFENG;
                            }
                            break;
                    }
                    return ZHAOXIN_UNKNOWN;
                case HYGON:
                    if(family == 0x18) {
                        switch(model) {
                            case 0x00:
                            case 0x01:
                                return AMD_ZEN;
                        }
                    }
                    return UNKNOWN;
                case INTEL: return intelUarch();
                default: return UNKNOWN;
            }
        }
        
        private X86Microarchitecture amdUarch() {
            switch(family) {
                case 0x0F:
                    switch(model) {
                        case 0x04:
                        case 0x05:
                        case 0x07:
                        case 0x08:
                        case 0x0C:
                        case 0x0E:
                        case 0x0F:
                        case 0x14:
                        case 0x15:
                        case 0x17:
                        case 0x18:
                        case 0x1B:
                        case 0x1C:
                        case 0x1F:
                        case 0x21:
                        case 0x23:
                        case 0x24:
                        case 0x25:
                        case 0x27:
                        case 0x2B:
                        case 0x2C:
                        case 0x2F:
                        case 0x41:
                        case 0x43:
                        case 0x48:
                        case 0x4B:
                        case 0x4C:
                        case 0x4F:
                        case 0x5D:
                        case 0x5F:
                        case 0x68:
                        case 0x6B:
                        case 0x6F:
                        case 0x7F:
                        case 0xC1:
                            return AMD_HAMMER;
                    }
                    break;
                case 0x10:
                    switch(model) {
                        case 0x02:
                        case 0x04:
                        case 0x05:
                        case 0x06:
                        case 0x08:
                        case 0x09:
                        case 0x0A:
                            return AMD_K10;
                    }
                    break;
                case 0x11:
                    if(model == 0x03) {
                        return AMD_K11;
                    }
                    break;
                case 0x12:
                    switch(model) {
                        case 0x00:
                        case 0x01:
                            return AMD_K12;
                    }
                    break;
                case 0x14:
                    switch(model) {
                        case 0x00:
                        case 0x01:
                        case 0x02:
                            return AMD_BOBCAT;
                    }
                    break;
                case 0x15:
                    switch(model) {
                        case 0x01:
                            return AMD_BULLDOZER;
                        case 0x02:
                        case 0x10:
                        case 0x11:
                        case 0x13:
                            return AMD_PILEDRIVER;
                        case 0x30:
                        case 0x38:
                            return AMD_STEAMROLLER;
                        case 0x60:
                        case 0x65:
                        case 0x70:
                            return AMD_EXCAVATOR;
                    }
                    break;
                case 0x16:
                    switch(model) {
                        case 0x00:
                        case 0x26:
                            return AMD_JAGUAR;
                        case 0x30:
                            return AMD_PUMA;
                    }
                    break;
                case 0x17:
                    switch(model) {
                        case 0x01:
                        case 0x11:
                        case 0x18:
                        case 0x20:
                            return AMD_ZEN;
                        case 0x08:
                            return AMD_ZEN_PLUS;
                        case 0x31:
                        case 0x47:
                        case 0x60:
                        case 0x68:
                        case 0x71:
                        case 0x90:
                        case 0x98:
                            return AMD_ZEN2;
                    }
                    break;
                case 0x19:
                    switch(model) {
                        case 0x00:
                        case 0x01:
                        case 0x08:
                        case 0x21:
                        case 0x30:
                        case 0x40:
                        case 0x44:
                        case 0x50:
                            return AMD_ZEN3;
                        case 0x10:
                            return AMD_ZEN4;
                    }
                    break;
            }
            return AMD_UNKNOWN;
        }
        
        private X86Microarchitecture intelUarch() {
            switch(family) {
                case 0x04:
                    switch(model) {
                        case 0x01:
                        case 0x02:
                        case 0x03:
                        case 0x04:
                        case 0x05:
                        //skip 6
                        case 0x07:
                        case 0x08:
                        case 0x09:
                            return INTEL_80486;
                    }
                    break;
                case 0x05:
                    switch(model) {
                        case 0x01:
                        case 0x02:
                        case 0x04:
                        case 0x07:
                        case 0x08:
                            return INTEL_P5;
                        case 0x09:
                        case 0x0A:
                            return INTEL_LAKEMONT;
                    }
                    break;
                case 0x06:
                    switch(model) {
                        case 0x1C:
                        case 0x35:
                        case 0x36:
                        case 0x70:
                            return INTEL_ATOM_BONNELL;
                        case 0x37:
                        case 0x4C:
                            return INTEL_ATOM_SILVERMONT;
                        case 0x5C:
                            return INTEL_ATOM_GOLDMONT;
                        case 0x7A:
                            return INTEL_ATOM_GOLDMONT_PLUS;
                        case 0x8A:
                        case 0x96:
                        case 0x9C:
                            return INTEL_ATOM_TREMONT;
                        case 0x0F:
                        case 0x16:
                            return INTEL_CORE;
                        case 0x17:
                        case 0x1D:
                            return INTEL_PENRYN;
                        case 0x1A:
                        case 0x1E:
                        case 0x1F:
                        case 0x2E:
                            return INTEL_NEHALEM;
                        case 0x25:
                        case 0x2C:
                        case 0x2F:
                            return INTEL_WESTMERE;
                        case 0x2A:
                        case 0x2D:
                            return INTEL_SANDY_BRIDGE;
                        case 0x3A:
                        case 0x3E:
                            return INTEL_IVY_BRIDGE;
                        case 0x3C:
                        case 0x3F:
                        case 0x45:
                        case 0x46:
                            return INTEL_HASWELL;
                        case 0x3D:
                        case 0x47:
                        case 0x4F:
                        case 0x56:
                            return INTEL_BROADWELL;
                        case 0x4E:
                        case 0x55:
                        case 0x5E:
                            return INTEL_SKYLAKE;
                        case 0x66:
                            return INTEL_CANNON_LAKE;
                        case 0x8E:
                            switch(stepping) {
                                case 9: return INTEL_KABY_LAKE;
                                case 10: return INTEL_COFFEE_LAKE;
                                case 11: return INTEL_WHISKEY_LAKE;
                                case 12: return INTEL_COMET_LAKE;
                                default: return INTEL_UNKNOWN;
                            }
                        case 0x9E:
                            if(stepping > 9) {
                                return INTEL_COFFEE_LAKE;
                            } else {
                                return INTEL_KABY_LAKE;
                            }
                        case 0xA5:
                        case 0xA6:
                            return INTEL_COMET_LAKE;
                        case 0x7D:
                        case 0x7E:
                        case 0x9D:
                        case 0x6A:
                        case 0x6C:
                            return INTEL_ICE_LAKE;
                        case 0x8C:
                        case 0x8D:
                            return INTEL_TIGER_LAKE;
                        case 0xA7:
                            return INTEL_ROCKET_LAKE;
                        case 0x97:
                        case 0x9A:
                            return INTEL_ALDER_LAKE;
                            
                        case 0x8F:
                            return INTEL_SAPPHIRE_RAPIDS;
                            
                        case 0x85:
                            return INTEL_KNIGHTS_MILL;
                        case 0x57:
                            return INTEL_KNIGHTS_LANDING;
                    }
                    break;
                case 0x0B:
                    switch(model) {
                        case 0x00:
                            return INTEL_KNIGHTS_FERRY;
                        case 0x01:
                            return INTEL_KNIGHTS_CORNER;
                    }
                    break;
                case 0x0F:
                    switch(model) {
                        case 0x01:
                        case 0x02:
                        case 0x03:
                        case 0x04:
                        case 0x06:
                            return INTEL_NETBURST;
                    }
                    break;
            }
            return INTEL_UNKNOWN;
        }
    }
}
