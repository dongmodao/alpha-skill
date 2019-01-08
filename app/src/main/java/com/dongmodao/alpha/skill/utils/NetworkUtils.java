package com.dongmodao.alpha.skill.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.telephony.TelephonyManager;

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


    /**
     * 获取运营商名字
     *
     * @param context context
     * @return mame of operator
     */
    public static String getOperatorName(Context context) {
         // getSimOperatorName()就可以直接获取到运营商的名字
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getSimOperatorName();
    }

    /**
     * 获取运营商名字
     *
     * @param context context
     * @return IMSI of operator
     */
    public static String getOperatorIMSI(Context context) {
        // 使用IMSI获取，getSimOperator()，然后根据返回值判断，例如"46000"为移动
        // IMSI相关链接：http://baike.baidu.com/item/imsi
        // IMSI = MCC(3) + MNC(2,3) = 460 07
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getSimOperator();
    }

    // TODO: 2019/1/9 dual SIM network data and operator  https://stackoverflow.com/questions/11305407/android-dual-sim-card-api
}
