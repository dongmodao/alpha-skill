//
// Created by Palmax-UI on 2019/1/4.
//

#include "com_dongmodao_alpha_skill_skills_JNIUtils.h"

extern "C"
JNIEXPORT jstring JNICALL
Java_com_dongmodao_alpha_skill_skills_JNIUtils_getRealStr(JNIEnv *env, jclass type, jstring origin_) {

    const char *origin = env->GetStringUTFChars(origin_, 0);

    jstring returnValue =env->NewStringUTF("Hello from C++");
    env->ReleaseStringUTFChars(origin_, origin);
    return returnValue;
}
