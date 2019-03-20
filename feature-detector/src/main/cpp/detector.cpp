#include <cstdint>

#include "jni.h"
#include "cpu_features_macros.h"

#ifdef CPU_FEATURES_ARCH_X86
  #include "cpuinfo_x86.h"
  #define ARCH 1
  const auto info = cpu_features::GetX86Info();
  const auto features = info.features;
#endif

#ifdef CPU_FEATURES_ARCH_ARM
  #include "cpuinfo_arm.h"
  #define ARCH 2
  const auto info = cpu_features::GetArmInfo();
  const auto features = info.features;
#endif

#ifdef CPU_FEATURES_ARCH_AARCH64
  #include "cpuinfo_aarch64.h"
  #define ARCH 3
  const auto info = cpu_features::GetAarch64Info();
  const auto features = info.features;
#endif

extern "C" {

JNIEXPORT jint JNICALL Java_com_github_natanbc_nativeloader_Natives_arch(JNIEnv* env, jclass thiz) {
    return ARCH;
}

#ifdef CPU_FEATURES_ARCH_X86
  JNIEXPORT jlong JNICALL Java_com_github_natanbc_nativeloader_Natives_featureBits(JNIEnv* env, jclass thiz) {
      jlong result = 0;
      jlong offset = 0;
      result |= (((uint64_t)features.aes & 1) << offset); offset++;
      result |= (((uint64_t)features.erms & 1) << offset); offset++;
      result |= (((uint64_t)features.f16c & 1) << offset); offset++;
      result |= (((uint64_t)features.fma3 & 1) << offset); offset++;
      result |= (((uint64_t)features.vpclmulqdq & 1) << offset); offset++;
      result |= (((uint64_t)features.bmi1 & 1) << offset); offset++;
      result |= (((uint64_t)features.bmi2 & 1) << offset); offset++;
      result |= (((uint64_t)features.ssse3 & 1) << offset); offset++;
      result |= (((uint64_t)features.sse4_1 & 1) << offset); offset++;
      result |= (((uint64_t)features.sse4_2 & 1) << offset); offset++;
      result |= (((uint64_t)features.avx & 1) << offset); offset++;
      result |= (((uint64_t)features.avx2 & 1) << offset); offset++;
      result |= (((uint64_t)features.avx512f & 1) << offset); offset++;
      result |= (((uint64_t)features.avx512cd & 1) << offset); offset++;
      result |= (((uint64_t)features.avx512er & 1) << offset); offset++;
      result |= (((uint64_t)features.avx512pf & 1) << offset); offset++;
      result |= (((uint64_t)features.avx512bw & 1) << offset); offset++;
      result |= (((uint64_t)features.avx512dq & 1) << offset); offset++;
      result |= (((uint64_t)features.avx512vl & 1) << offset); offset++;
      result |= (((uint64_t)features.avx512ifma & 1) << offset); offset++;
      result |= (((uint64_t)features.avx512vbmi & 1) << offset); offset++;
      result |= (((uint64_t)features.avx512vbmi2 & 1) << offset); offset++;
      result |= (((uint64_t)features.avx512vnni & 1) << offset); offset++;
      result |= (((uint64_t)features.avx512bitalg & 1) << offset); offset++;
      result |= (((uint64_t)features.avx512vpopcntdq & 1) << offset); offset++;
      result |= (((uint64_t)features.avx512_4vnniw & 1) << offset); offset++;
      result |= (((uint64_t)features.avx512_4vbmi2 & 1) << offset); offset++;
      result |= (((uint64_t)features.smx & 1) << offset); offset++;
      result |= (((uint64_t)features.sgx & 1) << offset); offset++;
      result |= (((uint64_t)features.cx16 & 1) << offset); offset++;
      result |= (((uint64_t)features.sha & 1) << offset); offset++;
      result |= (((uint64_t)features.popcnt & 1) << offset); offset++;
      result |= (((uint64_t)features.movbe & 1) << offset); offset++;
      result |= (((uint64_t)features.rdrnd & 1) << offset); //offset++;
      return result;
  }

  JNIEXPORT jint JNICALL Java_com_github_natanbc_nativeloader_Natives_x86Microarchitecture(JNIEnv* env, jclass thiz) {
      return cpu_features::GetX86Microarchitecture(&info);
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
  JNIEXPORT jlong JNICALL Java_com_github_natanbc_nativeloader_Natives_featureBits(JNIEnv* env, jclass thiz) {
      jlong result = 0;
      jlong offset = 0;
      result |= (((uint64_t)features.vfp & 1) << offset); offset++;
      result |= (((uint64_t)features.iwmmxt & 1) << offset); offset++;
      result |= (((uint64_t)features.neon & 1) << offset); offset++;
      result |= (((uint64_t)features.vfpv3 & 1) << offset); offset++;
      result |= (((uint64_t)features.vfpv3d16 & 1) << offset); offset++;
      result |= (((uint64_t)features.vfpv4 & 1) << offset); offset++;
      result |= (((uint64_t)features.idiva & 1) << offset); offset++;
      result |= (((uint64_t)features.idivt & 1) << offset); offset++;
      result |= (((uint64_t)features.aes & 1) << offset); offset++;
      result |= (((uint64_t)features.pmull & 1) << offset); offset++;
      result |= (((uint64_t)features.sha1 & 1) << offset); offset++;
      result |= (((uint64_t)features.sha2 & 1) << offset); offset++;
      result |= (((uint64_t)features.crc32 & 1) << offset); //offset++;
      return result;
  }

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

  JNIEXPORT jlong JNICALL Java_com_github_natanbc_nativeloader_Natives_featureBits(JNIEnv* env, jclass thiz) {
      jlong result = 0;
      jlong offset = 0;
      result |= (((uint64_t)features.fp & 1) << offset); offset++;
      result |= (((uint64_t)features.asimd & 1) << offset); offset++;
      result |= (((uint64_t)features.aes & 1) << offset); offset++;
      result |= (((uint64_t)features.pmull & 1) << offset); offset++;
      result |= (((uint64_t)features.sha1 & 1) << offset); offset++;
      result |= (((uint64_t)features.sha2 & 1) << offset); offset++;
      result |= (((uint64_t)features.crc32 & 1) << offset); //offset++;
      return result;
  }

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