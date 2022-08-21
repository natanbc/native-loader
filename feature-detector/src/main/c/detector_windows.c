#define WIN32_LEAN_AND_MEAN
#include <Windows.h>
#include <jni.h>

JNIEXPORT jboolean JNICALL Java_com_github_natanbc_nativeloader_natives_WindowsNatives_isProcessorFeaturePresent(
    JNIEnv* env, jclass thiz, jint feature
) {
    (void)env;
    (void)thiz;
    return IsProcessorFeaturePresent(feature) ? JNI_TRUE : JNI_FALSE;
}
