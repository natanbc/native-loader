#include <jni.h>

unsigned long getauxval(unsigned long type) __attribute__((weak));

JNIEXPORT jboolean JNICALL Java_com_github_natanbc_nativeloader_natives_LinuxNatives_hasGetauxval(
    JNIEnv* env, jclass thiz
) {
    (void)env;
    (void)thiz;
    return getauxval ? JNI_TRUE : JNI_FALSE;
}

JNIEXPORT jlong JNICALL Java_com_github_natanbc_nativeloader_natives_LinuxNatives_getauxval(
    JNIEnv* env, jclass thiz, jlong type
) {
    (void)env;
    (void)thiz;
    if(!getauxval) {
        return 0;
    }
    return (jlong)getauxval((unsigned long)type);
}
