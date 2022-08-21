package com.github.natanbc.nativeloader.natives;

import java.lang.annotation.Native;

class DarwinNatives {
    @Native private static final int SYSCTL_OK = 0;
    @Native private static final int SYSCTL_FAIL = 1;
    @Native private static final int SYSCTL_OOM = 2;
    @Native private static final int SYSCTL_INVALID = 3;
    
    private static native int getSysctl0(String name, int[] out);
    
    static int getSysctl(String name) {
        var out = new int[1];
        switch(getSysctl0(name, out)) {
            case SYSCTL_OK:
                return out[0];
            case SYSCTL_FAIL:
                return 0;
            case SYSCTL_OOM: throw new OutOfMemoryError();
            case SYSCTL_INVALID: throw new AssertionError("native library rejected parameters");
            default: throw new AssertionError("should not get here");
        }
    }
    
    static boolean hasFeature(String name) {
        return getSysctl(name) != 0;
    }
}
