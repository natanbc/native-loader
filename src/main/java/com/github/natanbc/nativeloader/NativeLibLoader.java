package com.github.natanbc.nativeloader;

import com.github.natanbc.nativeloader.feature.CPUFeature;
import com.github.natanbc.nativeloader.system.CPUInfo;
import com.github.natanbc.nativeloader.system.SystemType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class NativeLibLoader {
    private static final Logger log = LoggerFactory.getLogger(NativeLibLoader.class);
    
    private final Object lock = new Object();
    private final List<OptionalPart> parts = new ArrayList<>();
    private final NativeLibraryProperties properties;
    private final LibraryBinaryLoader loader;
    private final String baseName;
    private volatile Throwable previousFailure;
    private volatile Boolean previousResult;
    private Predicate<SystemType> systemFilter;
    
    private NativeLibLoader(NativeLibraryProperties properties, LibraryBinaryLoader loader, String baseName) {
        this.properties = properties;
        this.loader = loader;
        this.baseName = baseName;
    }
    
    @Nonnull
    @CheckReturnValue
    public NativeLibLoader withFeature(@Nonnull CPUFeature feature) {
        return withFeature(feature, feature.getName().toLowerCase());
    }
    
    @Nonnull
    @CheckReturnValue
    public NativeLibLoader withFeature(@Nonnull CPUFeature feature, @Nonnull String part) {
        parts.add(new OptionalPart(feature, part));
        return this;
    }
    
    @Nonnull
    @CheckReturnValue
    public NativeLibLoader systemFilter(@Nonnull Predicate<SystemType> filter) {
        systemFilter = filter;
        return this;
    }
    
    public void load() {
        Boolean result = previousResult;
        
        if(result == null) {
            synchronized(lock) {
                result = previousResult;
                
                if(result == null) {
                    log.info("Native library {}: loading with parts {}", baseName, parts);
                    
                    try {
                        load0();
                        previousResult = true;
                    } catch(Throwable e) {
                        log.error("Native library {}: loading failed.", e);
                        
                        previousFailure = e;
                        previousResult = false;
                        throw uncheck(e);
                    }
                    return;
                }
            }
        }
        
        if(!result) {
            throw uncheck(previousFailure);
        }
    }
    
    private void load0() {
        String explicitPath = properties.getLibraryPath();
        
        if(explicitPath != null) {
            log.debug("Native library {}: explicit path provided {}", baseName, explicitPath);
            
            loadFromFile(Paths.get(explicitPath).toAbsolutePath());
        } else {
            SystemType type = detectMatchingSystemType();
            
            if(type != null) {
                String explicitDirectory = properties.getLibraryDirectory();
                
                if(explicitDirectory != null) {
                    log.debug("Native library {}: explicit directory provided {}", baseName, explicitDirectory);
                    
                    loadFromFile(Paths.get(explicitDirectory, type.formatLibraryName(baseName)).toAbsolutePath());
                } else {
                    loadFromFile(extractLibraryFromResources(type));
                }
            }
        }
    }
    
    private SystemType detectMatchingSystemType() {
        SystemType systemType;
        
        try {
            systemType = SystemType.detect();
        } catch(IllegalArgumentException e) {
            if(systemFilter != null) {
                log.info("Native library {}: could not detect sytem type, but system filter is present - assuming it does " +
                        "not match and skipping library.", baseName);
                
                return null;
            } else {
                throw e;
            }
        }
        
        if(systemFilter != null && !systemFilter.test(systemType)) {
            log.debug("Native library {}: system filter does not match detected system {}, skipping", baseName,
                    systemType.formatSystemName());
            return null;
        }
        
        return systemType;
    }
    
    private void loadFromFile(Path libraryFilePath) {
        log.debug("Native library {}: attempting to load library at {}", baseName, libraryFilePath);
        System.load(libraryFilePath.toAbsolutePath().toString());
        log.info("Native library {}: successfully loaded.", baseName);
    }
    
    private Path extractLibraryFromResources(SystemType type) {
        StringBuilder libraryNameBuilder = new StringBuilder(baseName);
        for(OptionalPart part : parts) {
            CPUInfo info = DetectorLoader.loadDetector(loader);
            if(part.feature.getCPUType() == info.getArch().cpuType() && (info.getFeatureBits() & part.feature.getMask()) != 0) {
                libraryNameBuilder.append('-').append(part.part);
            }
        }
        String libraryName = libraryNameBuilder.toString();
        log.debug("Native library {}: resolved file to {}", baseName, libraryName);
        try(InputStream libraryStream = loader.loadLibrary(type, libraryName)) {
            if(libraryStream == null) {
                throw new UnsatisfiedLinkError("Required library was not found");
            }
            
            Path extractedLibraryPath = prepareExtractionDirectory().resolve(type.formatLibraryName(libraryName));
            
            try(FileOutputStream fileStream = new FileOutputStream(extractedLibraryPath.toFile())) {
                byte[] buffer = new byte[1024];
                int r;
                while((r = libraryStream.read(buffer)) != -1) {
                    fileStream.write(buffer, 0, r);
                }
            }
            
            return extractedLibraryPath;
        } catch(IOException e) {
            throw new IllegalStateException(e);
        }
    }
    
    private Path prepareExtractionDirectory() throws IOException {
        Path extractionDirectory = detectExtractionBaseDirectory().resolve(String.valueOf(System.currentTimeMillis()));
        
        if(!Files.isDirectory(extractionDirectory)) {
            log.debug("Native library {}: extraction directory {} does not exist, creating.", baseName,
                    extractionDirectory);
            
            try {
                createDirectoriesWithFullPermissions(extractionDirectory);
            } catch(FileAlreadyExistsException ignored) {
                // All is well
            } catch(IOException e) {
                throw new IOException("Failed to create directory for unpacked native library.", e);
            }
        } else {
            log.debug("Native library {}: extraction directory {} already exists, using.", baseName, extractionDirectory);
        }
        
        return extractionDirectory;
    }
    
    private Path detectExtractionBaseDirectory() {
        String explicitExtractionBase = properties.getExtractionPath();
        
        if(explicitExtractionBase != null) {
            log.debug("Native library {}: explicit extraction path provided - {}", baseName, explicitExtractionBase);
            return Paths.get(explicitExtractionBase).toAbsolutePath();
        }
        
        Path path = Paths.get(System.getProperty("java.io.tmpdir", "/tmp"), "jni-natives")
                .toAbsolutePath();
        
        log.debug("Native library {}: detected {} as base directory for extraction.", baseName, path);
        return path;
    }
    
    @Nonnull
    @CheckReturnValue
    public static CPUInfo loadSystemInfo() {
        return loadSystemInfo(LibraryBinaryLoader.fromResources());
    }
    
    @Nonnull
    @CheckReturnValue
    public static CPUInfo loadSystemInfo(@Nonnull LibraryBinaryLoader loader) {
        return DetectorLoader.loadDetector(loader);
    }
    
    @Nonnull
    @CheckReturnValue
    public static NativeLibLoader create(@Nonnull String libraryName) {
        return create(LibraryBinaryLoader.fromResources(), libraryName);
    }
    
    @Nonnull
    @CheckReturnValue
    public static NativeLibLoader create(@Nonnull Class<?> owner, @Nonnull String libraryName) {
        return create(LibraryBinaryLoader.fromResources(owner), libraryName);
    }
    
    @Nonnull
    @CheckReturnValue
    public static NativeLibLoader create(@Nonnull LibraryBinaryLoader loader, @Nonnull String libraryName) {
        return create(new SystemNativeLibraryProperties(libraryName, "nativeloader."), loader, libraryName);
    }
    
    @Nonnull
    @CheckReturnValue
    public static NativeLibLoader create(@Nonnull NativeLibraryProperties properties,
                                         @Nonnull LibraryBinaryLoader loader, @Nonnull String libraryName) {
        return new NativeLibLoader(properties, loader, libraryName);
    }
    
    private static void createDirectoriesWithFullPermissions(Path path) throws IOException {
        boolean isPosix = FileSystems.getDefault().supportedFileAttributeViews().contains("posix");
        
        if(!isPosix) {
            Files.createDirectories(path);
        } else {
            Files.createDirectories(path, PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rwxrwxrwx")));
        }
    }
    
    private static class OptionalPart {
        private final CPUFeature feature;
        private final String part;
        
        private OptionalPart(CPUFeature feature, String part) {
            this.feature = feature;
            this.part = part;
        }
        
        @Override
        public String toString() {
            return "Part{feature=" + (feature.getCPUType().name() + "." + feature.getName())
                    + ", part=" + part + "}";
        }
    }

    private static <E extends Throwable> RuntimeException uncheck(Throwable e) throws E {
        throw (E) e;
    }
}
