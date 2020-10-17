# native-loader

Helper for loading native libraries based on architecture, OS and CPU features.

Based on [lavaplayer](https://github.com/sedmelluq/lavaplayer/tree/0eaaf7a7bf9fbb3da5787f0b49e844f0beceb70d/common/src/main/java/com/sedmelluq/lava/common/natives)'s
loader.

# Installation

```gradle
repositories {
  jcenter()
}

dependencies {
  compile 'com.github.natanbc:native-loader:VERSION'
}
```

```xml
<repositories>
    <repository>
        <id>jcenter</id>
        <name>jcenter</name>
        <url>https://jcenter.bintray.com</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.natanbc</groupId>
        <artifactId>native-loader</artifactId>
        <version>VERSION</version>
    </dependency>
</dependencies>
```

# Usage

```java
public class MyNativeLib {
    private static final NativeLibLoader LOADER = NativeLibLoader.create("my-lib");
    
    public MyNativeLib() {
        LOADER.load();
    }
    
    public native void myNativeMethod();
}
```

## CPU feature detection

```java
NativeLibLoader loader = NativeLibLoader.create("my-lib")
    .withFeature(X86Feature.AVX2);
```

This would load `my-lib` on systems without AVX2, or `my-lib-avx2` on systems which support it.

Other features are available in the ArmFeature and Aarch64Feature enums.

You can provide a string as a second argument to withFeature to set the name in the binary.
The resulting name is `baseName + (hasFeature1 ? "-" + feature1Name : "") + ... + (hasFeatureN ? "-" + featureNName : "")`.
Calling withFeature with a single argument will use the enum name in lower case as the name.

# Native library mapping

Libraries are loaded by default from `/natives/<OS>-<ARCH*>/<NAME>`.

For a library named `mylib`:

| OS             | Architecture | File                                    |
|----------------|--------------|-----------------------------------------|
| Darwin (OS X*) | x86-64       | /natives/darwin/libmylib.dylib          |
| Freebsd        | x86-64       | /natives/freebsd-x86-64/libmylib.so     |
| Linux (glibc)  | aarch64      | /natives/linux-aarch64/libmylib.so      |
| Linux (glibc)  | arm          | /natives/linux-arm/libmylib.so          |
| Linux (glibc)  | x86          | /natives/linux-x86/libmylib.so          |
| Linux (glibc)  | x86-64       | /natives/linux-x86-64/libmylib.so       |
| Linux (musl)   | aarch64      | /natives/linux-musl-aarch64/libmylib.so |
| Linux (musl)   | x86-64       | /natives/linux-musl-x86-64/libmylib.so  |
| Windows        | x86          | /natives/win-x86/mylib.dll              |
| Windows        | x86-64       | /natives/win-x86-64/mylib.dll           |

* OS X natives don't have the architecture in the path, only the OS

# Customizing the loading

You can customize from where natives are loaded with the LibraryBinaryLoader and NativeLibraryProperties interfaces.
