package com.github.natanbc.nativeloader.system;

public enum X86Microarchitecture {
    X86_UNKNOWN,
    INTEL_CORE,      // CORE
    INTEL_PNR,       // PENRYN
    INTEL_NHM,       // NEHALEM
    INTEL_ATOM_BNL,  // BONNELL
    INTEL_WSM,       // WESTMERE
    INTEL_SNB,       // SANDYBRIDGE
    INTEL_IVB,       // IVYBRIDGE
    INTEL_ATOM_SMT,  // SILVERMONT
    INTEL_HSW,       // HASWELL
    INTEL_BDW,       // BROADWELL
    INTEL_SKL,       // SKYLAKE
    INTEL_ATOM_GMT,  // GOLDMONT
    INTEL_KBL,       // KABY LAKE
    INTEL_CFL,       // COFFEE LAKE
    INTEL_WHL,       // WHISKEY LAKE
    INTEL_CNL,       // CANNON LAKE
    INTEL_ICL,       // ICE LAKE
    INTEL_TGL,       // TIGER LAKE
    INTEL_SPR,       // SAPPHIRE RAPIDS
    AMD_HAMMER,      // K8
    AMD_K10,         // K10
    AMD_BOBCAT,      // K14
    AMD_BULLDOZER,   // K15
    AMD_JAGUAR,      // K16
    AMD_ZEN,         // K17
    ;
    
    public static X86Microarchitecture fromNative(String name) {
        try {
            return valueOf(name);
        } catch(IllegalArgumentException e) {
            return X86_UNKNOWN;
        }
    }
}
