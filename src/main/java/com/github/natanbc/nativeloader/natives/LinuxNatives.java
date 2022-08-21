package com.github.natanbc.nativeloader.natives;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.annotation.Native;
import java.util.concurrent.atomic.AtomicReference;

class LinuxNatives {
    @Native static final int AT_HWCAP = 16;
    @Native static final int AT_HWCAP2 = 26;
    
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
}
