#include <cstdint>

#include "jni.h"
#include "cpu_features_macros.h"

#ifdef CPU_FEATURES_ARCH_X86
  #include "cpuinfo_x86.h"
  #define HAS_CACHE_INFO 1
  using FeatureEnum = cpu_features::X86FeaturesEnum;
  const auto arch = 1;
  const auto featureCount = cpu_features::X86_LAST_;
  const auto info = cpu_features::GetX86Info();
  const auto features = info.features;
  const auto cache = cpu_features::GetX86CacheInfo();
  inline jboolean feature_check(jint feature) {
    return cpu_features::GetX86FeaturesEnumValue(&features, static_cast<FeatureEnum>(feature));
  }
  inline const char* feature_name(jint feature) {
    return cpu_features::GetX86FeaturesEnumName(static_cast<FeatureEnum>(feature));
  }
#endif

#ifdef CPU_FEATURES_ARCH_ARM
  #include "cpuinfo_arm.h"
  using FeatureEnum = cpu_features::ArmFeaturesEnum;
  const auto arch = 2;
  const auto featureCount = cpu_features::ARM_LAST_;
  const auto info = cpu_features::GetArmInfo();
  const auto features = info.features;
  inline jboolean feature_check(jint feature) {
    return cpu_features::GetArmFeaturesEnumValue(&features, static_cast<FeatureEnum>(feature));
  }
  inline const char* feature_name(jint feature) {
    return cpu_features::GetArmFeaturesEnumName(static_cast<FeatureEnum>(feature));
  }
#endif

#ifdef CPU_FEATURES_ARCH_AARCH64
  #include "cpuinfo_aarch64.h"
  using FeatureEnum = cpu_features::Aarch64FeaturesEnum;
  const auto arch = 3;
  const auto featureCount = cpu_features::AARCH64_LAST_;
  const auto info = cpu_features::GetAarch64Info();
  const auto features = info.features;
  inline jboolean feature_check(jint feature) {
    return cpu_features::GetAarch64FeaturesEnumValue(&features, static_cast<FeatureEnum>(feature));
  }
  inline const char* feature_name(jint feature) {
    return cpu_features::GetAarch64FeaturesEnumName(static_cast<FeatureEnum>(feature));
  }
#endif

extern "C" {

JNIEXPORT jint JNICALL Java_com_github_natanbc_nativeloader_Natives_arch(JNIEnv* env, jclass thiz) {
    return arch;
}

JNIEXPORT jint JNICALL Java_com_github_natanbc_nativeloader_Natives_featureCount(JNIEnv* env, jclass thiz) {
    return featureCount;
}

JNIEXPORT jboolean JNICALL Java_com_github_natanbc_nativeloader_Natives_hasFeature(JNIEnv* env, jclass thiz, jint value) {
    return feature_check(value);
}

JNIEXPORT jstring JNICALL Java_com_github_natanbc_nativeloader_Natives_featureName(JNIEnv* env, jclass thiz, jint value) {
    return env->NewStringUTF(feature_name(value));
}

#ifdef HAS_CACHE_INFO

JNIEXPORT jint JNICALL Java_com_github_natanbc_nativeloader_Natives_cacheSize(JNIEnv* env, jclass thiz) {
    return cache.size;
}

JNIEXPORT jint JNICALL Java_com_github_natanbc_nativeloader_Natives_cacheLevelFields(JNIEnv* env, jclass thiz) {
    return 7;
}

JNIEXPORT void JNICALL Java_com_github_natanbc_nativeloader_Natives_cacheLevel(JNIEnv* env, jclass thiz, jint level, jintArray res) {
    const auto level_info = cache.levels[level];
    jint data[] = {
        level_info.level,       level_info.cache_type,
        level_info.cache_size,  level_info.ways,
        level_info.line_size,   level_info.tlb_entries,
        level_info.partitioning
    };
    env->SetIntArrayRegion(res, 0, sizeof(data) / sizeof(jint), data);
}

#else

JNIEXPORT jint JNICALL Java_com_github_natanbc_nativeloader_Natives_cacheSize(JNIEnv* env, jclass thiz) {
    return 0;
}

JNIEXPORT jint JNICALL Java_com_github_natanbc_nativeloader_Natives_cacheLevelFields(JNIEnv* env, jclass thiz) {
    return 0;
}

JNIEXPORT void JNICALL Java_com_github_natanbc_nativeloader_Natives_cacheLevel(JNIEnv* env, jclass thiz, jint level, jintArray res) {
    //nothing
}

#endif

#ifdef CPU_FEATURES_ARCH_X86
  JNIEXPORT jstring JNICALL Java_com_github_natanbc_nativeloader_Natives_x86Microarchitecture(JNIEnv* env, jclass thiz) {
      const char* name = cpu_features::GetX86MicroarchitectureName(cpu_features::GetX86Microarchitecture(&info));
      return env->NewStringUTF(name);
  }

  JNIEXPORT jint JNICALL Java_com_github_natanbc_nativeloader_Natives_x86Family(JNIEnv* env, jclass thiz) {
      return info.family;
  }

  JNIEXPORT jint JNICALL Java_com_github_natanbc_nativeloader_Natives_x86Model(JNIEnv* env, jclass thiz) {
      return info.model;
  }

  JNIEXPORT jint JNICALL Java_com_github_natanbc_nativeloader_Natives_x86Stepping(JNIEnv* env, jclass thiz) {
      return info.stepping;
  }

  JNIEXPORT void JNICALL Java_com_github_natanbc_nativeloader_Natives_x86Vendor(JNIEnv* env, jclass thiz, jbyteArray array, jint offset) {
      env->SetByteArrayRegion(array, offset, 12, (jbyte*)&info.vendor);
  }

  JNIEXPORT void JNICALL Java_com_github_natanbc_nativeloader_Natives_x86BrandString(JNIEnv* env, jclass thiz, jbyteArray array, jint offset) {
      char data[49];
      cpu_features::FillX86BrandString(data);
      env->SetByteArrayRegion(array, offset, 48, (jbyte*)data);
  }
#endif

#ifdef CPU_FEATURES_ARCH_ARM
  JNIEXPORT jint JNICALL Java_com_github_natanbc_nativeloader_Natives_armCpuId(JNIEnv* env, jclass thiz) {
      return (jint)cpu_features::GetArmCpuId(&info);
  }

  JNIEXPORT jint JNICALL Java_com_github_natanbc_nativeloader_Natives_armImplementer(JNIEnv* env, jclass thiz) {
      return info.implementer;
  }

  JNIEXPORT jint JNICALL Java_com_github_natanbc_nativeloader_Natives_armArchitecture(JNIEnv* env, jclass thiz) {
      return info.architecture;
  }

  JNIEXPORT jint JNICALL Java_com_github_natanbc_nativeloader_Natives_armVariant(JNIEnv* env, jclass thiz) {
      return info.variant;
  }

  JNIEXPORT jint JNICALL Java_com_github_natanbc_nativeloader_Natives_armPart(JNIEnv* env, jclass thiz) {
      return info.part;
  }

  JNIEXPORT jint JNICALL Java_com_github_natanbc_nativeloader_Natives_armRevision(JNIEnv* env, jclass thiz) {
      return info.revision;
  }
#endif

#ifdef CPU_FEATURES_ARCH_AARCH64
  JNIEXPORT jint JNICALL Java_com_github_natanbc_nativeloader_Natives_aarch64Implementer(JNIEnv* env, jclass thiz) {
      return info.implementer;
  }

  JNIEXPORT jint JNICALL Java_com_github_natanbc_nativeloader_Natives_aarch64Variant(JNIEnv* env, jclass thiz) {
      return info.variant;
  }

  JNIEXPORT jint JNICALL Java_com_github_natanbc_nativeloader_Natives_aarch64Part(JNIEnv* env, jclass thiz) {
      return info.part;
  }

  JNIEXPORT jint JNICALL Java_com_github_natanbc_nativeloader_Natives_aarch64Revision(JNIEnv* env, jclass thiz) {
      return info.revision;
  }

#endif
}