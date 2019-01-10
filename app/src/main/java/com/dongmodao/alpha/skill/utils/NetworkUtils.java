package com.dongmodao.alpha.skill.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * author: dongmodao
 * date: 2019/1/8
 * time: 23:49
 */
public class NetworkUtils {

    /**
     * 检测当前网络是否是移动网络，WIFI 和 数据 同时打开时 返回的也是 false 应为流量来源于 wifi
     * @param context
     * @return
     */
    public static boolean isMobileDataEnable(Context context) {

        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            return connectivityManager.getNetworkInfo(
                    ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @SuppressLint("MissingPermission")
    public static int getSIMCount(Context context) {
        if (PermissionUtils.hasPermission(context, Manifest.permission.READ_PHONE_STATE)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return context.getSystemService(SubscriptionManager.class).getActiveSubscriptionInfoCount();
            }
        }
        return -1;
    }

    /**
     * Returns the number of phones available.
     * Returns 0 if none of voice, sms, data is not supported
     * Returns 1 for Single standby mode (Single SIM functionality)
     * Returns 2 for Dual standby mode.(Dual SIM functionality)
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static int getPhoneCount(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getPhoneCount();
    }
}
