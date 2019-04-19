package com.dongmodao.alpha.skill.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

import com.dongmodao.subs.annotation.SubsClass;

import java.lang.reflect.Method;

/**
 * author: dongmodao
 * date: 2019/1/8
 * time: 23:49
 */
@SubsClass
public class NetworkUtils {

    /**
     * 判断 wifi
     */
    public static boolean getWifiEnable(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            return !(info == null || !info.isAvailable()) && (info.getType() == ConnectivityManager.TYPE_WIFI);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断流量是否可用
     */
    public static boolean getMobileEnable(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            @SuppressLint("PrivateApi")
            Method getMobileDataEnabledMethod = ConnectivityManager.class.getDeclaredMethod("getMobileDataEnabled");
            getMobileDataEnabledMethod.setAccessible(true);
            return (boolean) getMobileDataEnabledMethod.invoke(connectivityManager);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 设置wifi状态
     * @param context context
     * @param enable enable
     */
    public static boolean setWifiEnable(Context context, Boolean enable) {
        try {
            WifiManager mWifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            return mWifiManager.setWifiEnabled(enable);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断网络是否可用
     */
    public static boolean getNetworkEnable(Context context) {
        return getWifiEnable(context) || getMobileEnable(context);
    }

    /**
     * 获取网络类型
     * @param context context
     * @return -1:None  0: Mobile   1:Wifi
     */
    public static int getNetworkType(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo == null || !networkInfo.isConnected()) {
                return -1;
            }
            return networkInfo.getType();
        } catch (Exception e) {
            e.printStackTrace();
            return  -1;
        }
    }
}
