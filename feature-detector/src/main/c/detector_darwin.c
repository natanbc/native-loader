#include <jni.h>
#include <sys/sysctl.h>

static const jint SYSCTL_OK = 0;
static const jint SYSCTL_FAIL = 1;
static const jint SYSCTL_OOM = 2;
static const jint SYSCTL_INVALID = 3;

JNIEXPORT jint JNICALL Java_com_github_natanbc_nativeloader_natives_DarwinNatives_getSysctl0(
    JNIEnv* env, jclass thiz, jstring sysctl, jintArray out
) {
    (void)thiz;

    if(!out || (*env)->GetArrayLength(env, out) != 1) {
        return SYSCTL_INVALID;
    }

    const char* utf = (*env)->GetStringUTFChars(env, sysctl, 0);
    if(!utf) {
        return SYSCTL_OOM;
    }

    int value;
    int failure = sysctlbyname(utf, &value, sizeof(value), NULL, 0);
    (*env)->ReleaseStringUTFChars(env, sysctl, utf);

    jint result;
    if(failure) {
        result = SYSCTL_FAIL;
    } else {
        result = SYSCTL_OK;
        (*env)->SetIntArrayRegion(env, out, 0, 1, &value);
    }

    return result;
}
