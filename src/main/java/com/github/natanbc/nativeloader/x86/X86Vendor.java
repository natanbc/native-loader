package com.github.natanbc.nativeloader.x86;

import java.util.HashMap;
import java.util.Map;

public enum X86Vendor {
    AMD("AuthenticAMD", "AMDisbetter!"),
    AO486("MiSTer AO486", "GenuineAO486"),
    CENTAUR("CentaurHauls"),
    CYRIX("CyrixInstead"),
    ELBRUS("E2K MACHINE "),
    HYGON("HygonGenuine"),
    INTEL("GenuineIntel"),
    NEXGEN("NexGenDriven"),
    NSC("Geode by NSC"),
    RISE("RiseRiseRise"),
    SIS("SiS SiS SiS "),
    TRANSMETA("TransmetaCPU", "GenuineTMx86"),
    UMC("UMC UMC UMC "),
    VIA("VIA VIA VIA "),
    VORTEX("Vortex86 SoC"),
    ZHAOXIN("  Shanghai  "),
    //hypervisors
    APPLE_ROSETTA2(true, "VirtualApple"),
    BHYVE(true, "bhyve bhyve "),
    HYPERV(true, "Microsoft Hv"),
    KVM(true, " KVMKVMKVM  "),
    PARALLELS(true, " prl hyperv ", " lrpepyh vr "),
    QEMU(true, "TCGTCGTCGTCG"),
    QNX(true, " QNXQVMBSQG "),
    VIRTUALBOX(true, "VBoxVBoxVBox"),
    VMWARE(true, "VMwareVMware"),
    XEN(true, "XenVMMXenVMM"),
    //others
    UNKNOWN,
    ;
    
    private static final Map<String, X86Vendor> VENDOR_MAP = new HashMap<>();
    private final boolean hypervisor;
    private final String[] cpuidNames;
    
    static {
        for(var v : values()) {
            for(var name : v.cpuidNames) {
                VENDOR_MAP.put(name, v);
            }
        }
    }
    
    X86Vendor(boolean hypervisor, String... cpuidNames) {
        this.hypervisor = hypervisor;
        this.cpuidNames = cpuidNames;
        for(var name : cpuidNames) {
            if(name.length() != 12) {
                throw new AssertionError("CPUID names have 12 characters");
            }
            for(var i = 0; i < 12; i++) {
                if(name.charAt(i) > 127) {
                    throw new AssertionError("CPUID names must be ASCII");
                }
            }
        }
    }
    
    X86Vendor(String... cpuidNames) {
        this(false, cpuidNames);
    }
    
    public boolean isHypervisor() {
        return hypervisor;
    }
    
    public static X86Vendor fromCpuidName(String name) {
        return VENDOR_MAP.getOrDefault(name, UNKNOWN);
    }
}
