#include <jni.h>
#include <sys/sysctl.h>

static const jint SYSCTL_FALSE = 0;
static const jint SYSCTL_TRUE = 1;
static const jint SYSCTL_OOM = 2;
static const jint SYSCTL_FAIL = 3;

JNIEXPORT jint JNICALL Java_com_github_natanbc_nativeloader_natives_DarwinNatives_getSysctl(
    JNIEnv* env, jclass thiz, jstring sysctl
) {
    const char* utf = (*env)->GetStringUTFChars(env, sysctl, 0);
    if(!utf) {
        return SYSCTL_OOM;
    }

    int value;
    int failure = sysctlbyname(utf, &value, sizeof(value), NULL, 0);

    jint result;
    if(failure) {
        result = SYSCTL_FAIL;
    } else {
        result = value ? SYSCTL_TRUE : SYSCTL_FALSE;
    }

    (*env)->ReleaseStringUTFChars(env, sysctl, utf);

    return result;
}
