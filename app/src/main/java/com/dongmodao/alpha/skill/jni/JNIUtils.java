package com.dongmodao.alpha.skill.jni;

/**
 * author : tangqihao
 *
 * @time : 2019/1/4
 * @project : AlphaSkill
 */
public class JNIUtils {

    static {
        System.loadLibrary("jni-utils-lib");
    }

    public static native String getRealStr(String origin);

}
