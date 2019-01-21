//
// Created by Palmax-UI on 2019/1/4.
//

#include "com_dongmodao_alpha_skill_skills_JNIUtils.h"
#include<android/log.h>
#include <cstring>
#include <stdlib.h>

#define TAG "myDemo-jni"
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, TAG ,__VA_ARGS__)

extern "C"
JNIEXPORT jstring JNICALL
Java_com_dongmodao_alpha_skill_skills_JNIUtils_getRealStr(JNIEnv *env, jclass type, jstring origin_) {

    const char * origin = env->GetStringUTFChars(origin_, 0);
    int n = static_cast<int>(strlen(origin));

    char l[9];
    for (int i = n - 8, j = 0; i < n ; i++) {
        l[j] = origin[i];
        j++;
    }
    l[8] = '\0';

    int m = atoi(l);

    char rst[m + 1];
    int s = n - 8 - m, e = n - 8;
    for (int i = s, j = 0; i < e; i++) {
        rst[j] = origin[i];
        j++;
    }
    rst[m] = '\0';

    LOGE("---> n = %d, %s, ln = %d; m = %d", n, rst, strlen(l), m);
    jstring returnValue = env->NewStringUTF(rst);
    env->ReleaseStringUTFChars(origin_, origin);
    return returnValue;
}
