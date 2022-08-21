#include <jni.h>
#include <stdint.h>

static void cpuid(int eax, int ecx, int out[4]);
static int64_t xgetbv(int xcr);

#if defined(__clang__) || defined(__GNUC__)

static void cpuid(int eax, int ecx, int out[4]) {
    asm volatile("cpuid"
        : "=a" (out[0]),
          "=b" (out[1]),
          "=c" (out[2]),
          "=d" (out[3])
        : "0" (eax), "2" (ecx)
        : "memory");
}

static int64_t xgetbv(int xcr) {
    uint32_t eax, edx;
    asm volatile("xgetbv" : "=a" (eax), "=d" (edx) : "c" (xcr));
    return (int64_t) (((uint64_t)edx << 32) | eax);
}

#elif defined(_MSC_VER)
#include <intrin.h>

static void cpuid(int eax, int ecx, int out[4]) {
    __cpuidex(out, eax, ecx);
}

static int64_t xgetbv(int xcr) {
    return _xgetbv((unsigned int)xcr);
}

#else
#error "Unsupported compiler, only Clang, GCC and MSVC are supported"
#endif

JNIEXPORT jboolean JNICALL Java_com_github_natanbc_nativeloader_natives_X86Natives_unsafeCpuid(
    JNIEnv* env, jclass thiz, jint eax, jint ecx, jintArray out
) {
    (void)thiz;

    if(!out || (*env)->GetArrayLength(env, out) != 4) {
        return JNI_FALSE;
    }

    int res[4];
    cpuid(eax, ecx, res);

    (*env)->SetIntArrayRegion(env, out, 0, 4, res);

    return JNI_TRUE;
}

JNIEXPORT jlong JNICALL Java_com_github_natanbc_nativeloader_natives_X86Natives_xgetbv(
    JNIEnv* env, jclass thiz, jint xcr
) {
    (void)env;
    (void)thiz;

    return xgetbv(xcr);
}
