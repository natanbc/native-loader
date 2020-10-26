package com.github.natanbc.nativeloader.system;

public enum CacheType {
    UNKNOWN(-1),
    NULL(0),
    DATA(1),
    INSTRUCTION(2),
    UNIFIED(3),
    TLB(4),
    DTLB(5),
    STLB(6),
    PREFETCH(7);
    
    private static final CacheType[] values = values();
    private final int nativeValue;
    
    CacheType(int nativeValue) {
        this.nativeValue = nativeValue;
    }
    
    public static CacheType fromNative(int value) {
        for(CacheType type : values) {
            if(type.nativeValue == value) return type;
        }
        return UNKNOWN;
    }
}
