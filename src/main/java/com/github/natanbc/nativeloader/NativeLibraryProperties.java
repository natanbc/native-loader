package com.github.natanbc.nativeloader;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;

public interface NativeLibraryProperties {
    /**
     * @return Explicit filesystem path for the library. If this is set, this is loaded directly and no resource
     * extraction and/or system name detection is performed. If this returns <code>null</code>, library directory
     * is checked next.
     */
    @Nullable
    @CheckReturnValue
    String libraryPath();
    
    /**
     * @return Explicit directory containing the native library. The specified directory must contain the system name
     * directories, thus the library to be loaded is actually located at
     * <code>directory/{systemName}/{libPrefix}{libName}{libSuffix}</code>. If this returns <code>null</code>,
     * then {@link LibraryBinaryLoader#loadLibrary(com.github.natanbc.nativeloader.system.SystemType, String)} is called
     * to obtain the
     * stream to the library file, which is then written to disk for loading.
     */
    @Nullable
    @CheckReturnValue
    String libraryDirectory();
    
    /**
     * @return Base directory where to write the library if it is obtained through
     * {@link LibraryBinaryLoader#loadLibrary(com.github.natanbc.nativeloader.system.SystemType, String)}. The library
     * file itself will
     * actually be written to a subdirectory with a randomly generated name. The specified directory does not
     * have to exist, but in that case the current process must have privileges to create it. If this returns
     * <code>null</code>, then <code>{tmpDir}/lava-jni-natives</code> is used.
     */
    @Nullable
    @CheckReturnValue
    String extractionPath();
    
    /**
     * @return System name. If this is set, no operating system detection is performed.
     */
    @Nullable
    @CheckReturnValue
    String operatingSystemName();
    
    /**
     * @return Library file name prefix to use.
     *         Only used when {@link #operatingSystemName()} is provided and doesn't
     *         match a default OS.
     */
    @Nullable
    @CheckReturnValue
    String libraryFileNamePrefix();
    
    /**
     * @return Library file name suffix to use.
     *         Only used when {@link #operatingSystemName()} is provided and doesn't
     *         match a default OS.
     */
    @Nullable
    @CheckReturnValue
    String libraryFileNameSuffix();
    
    /**
     * @return Architecture name to use. If this is set, no architecture detection is performed.
     */
    @Nullable
    @CheckReturnValue
    String architectureName();
}
