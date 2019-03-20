package com.github.natanbc.nativeloader;

import com.github.natanbc.nativeloader.system.SystemType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

@FunctionalInterface
public interface LibraryBinaryLoader {
    @Nullable
    InputStream loadLibrary(@Nonnull SystemType type, @Nonnull String name) throws IOException;
    
    @Nonnull
    static LibraryBinaryLoader fromResources() {
        return fromResources(LibraryBinaryLoader.class, "/natives/");
    }
    
    @Nonnull
    static LibraryBinaryLoader fromResources(@Nonnull String root) {
        return fromResources(LibraryBinaryLoader.class, root);
    }
    
    @Nonnull
    static LibraryBinaryLoader fromResources(@Nonnull Class<?> owner) {
        return fromResources(owner, "/natives/");
    }
    
    @Nonnull
    static LibraryBinaryLoader fromResources(@Nonnull Class<?> owner, @Nonnull String root) {
        return (system, name) -> {
            String resourcePath = root + system.formatSystemName() + "/" + system.formatLibraryName(name);
            return owner.getResourceAsStream(resourcePath);
        };
    }
    
    @Nonnull
    static LibraryBinaryLoader fromPath(@Nonnull Path path) {
        return (system, name) -> {
            String resourcePath = system.formatSystemName() + "/" + system.formatLibraryName(name);
            return new FileInputStream(path.resolve(resourcePath).normalize().toFile());
        };
    }
}
