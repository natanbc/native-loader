package com.github.natanbc.nativeloader.natives;

import java.lang.annotation.Native;

class WindowsNatives {
    @Native static final int PF_CHANNELS_ENABLED = 16;
    @Native static final int PF_FASTFAIL_AVAILABLE = 23;
    @Native static final int PF_FLOATING_POINT_EMULATED = 1;
    @Native static final int PF_SECOND_LEVEL_ADDRESS_TRANSLATION = 20;
    @Native static final int PF_VIRT_FIRMWARE_ENABLED = 21;
    
    static native boolean isProcessorFeaturePresent(int feature);
}
