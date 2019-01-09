package com.dongmodao.alpha.skill.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

/**
 * author : tangqihao
 *
 * @time : 2019/1/9
 * @project : AlphaSkill
 */
public class PermissionUtils {

    public static boolean hasPermission(Context context, String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }
}
