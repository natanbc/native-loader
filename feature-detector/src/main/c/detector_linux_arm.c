#include <jni.h>

unsigned long getauxval(unsigned long type) __attribute__((weak));

static_assert(sizeof(unsigned long) == sizeof(jint));

JNIEXPORT jboolean JNICALL Java_com_github_natanbc_nativeloader_natives_LinuxNatives_hasGetauxval(
    JNIEnv* env, jclass thiz
) {
    (void)env;
    (void)thiz;
    return getauxval ? JNI_TRUE : JNI_FALSE;
}

JNIEXPORT jint JNICALL Java_com_github_natanbc_nativeloader_natives_LinuxNatives_getauxval(
    JNIEnv* env, jclass thiz, jint type
) {
    (void)env;
    (void)thiz;
    if(!getauxval) {
        return 0;
    }
    return (jint)getauxval((unsigned long)type);
}
