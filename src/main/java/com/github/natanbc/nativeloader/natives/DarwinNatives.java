package com.github.natanbc.nativeloader.natives;

import java.lang.annotation.Native;

class DarwinNatives {
    @Native private static final int SYSCTL_FALSE = 0;
    @Native private static final int SYSCTL_TRUE = 1;
    @Native private static final int SYSCTL_OOM = 2;
    @Native private static final int SYSCTL_FAIL = 3;
    
    private static native int getSysctl0(String name);
    
    static boolean getSysctl(String name) {
        switch(getSysctl0(name)) {
            case SYSCTL_FALSE:
            case SYSCTL_FAIL:
                return false;
            case SYSCTL_TRUE: return true;
            case SYSCTL_OOM: throw new OutOfMemoryError();
            default: throw new AssertionError("should not get here");
        }
    }
}
