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
     * 获取运营商 IMSI
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
     *  默认数据流量卡卡号
     * @param context
     * @return 卡1：0， 卡2：1，无卡/无效：-1，与是否打开 wifi/data 无关，
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    public static int getDefaultDataID(Context context){
        SubscriptionManager subscriptionManager = (SubscriptionManager)context.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);
        int subscriberId = -1;
        if (Build.VERSION.SDK_INT > 24) {
            subscriberId = SubscriptionManager.getDefaultDataSubscriptionId();
        }else{
            try {
                Class cls  =  SubscriptionManager.class.getClass();
                Method method = cls.getDeclaredMethod("getDefaultDataSubId");
                subscriberId = (Integer) method.invoke(subscriptionManager);
            }catch (Exception e){e.printStackTrace();}
        }
        return subscriberId;
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

    // TODO: 2019/1/9 1. API >= 22 SubscribtionManager 进行 双卡管理


    /**
     * 根据 subId 获取运营商 MCC+MNC
     * Availability: SIM state must TelephonyManager.SIM_STATE_READY，内部有判断 subid |-> default data id |-> default sms id |-> default voice id |-> default subscription id
     * @param context
     * @param subId 卡 Id; 0|1
     * @return MCC+MNC
     */
    public static String getSIMOperator(Context context, int subId) {
        try {
            Class classType = Class.forName("android.telephony.TelephonyManager");
            Constructor<TelephonyManager> con = classType.getConstructor(Context.class, int.class);
//            Constructor<TelephonyManager> con = classType.getConstructor(Context.class);
            TelephonyManager telephonyManager = con.newInstance(context);
            int state = telephonyManager.getSimState();
            Log.e("---", "getSIMOperator: state = " + state);
            return telephonyManager.getSimOperator();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Log.e("---", "getOperatorName: return null");
        return null;
    }

//    api24
    public static String getOperator(Context context, int subId) {
        try {
            Class classType = Class.forName("android.telephony.TelephonyManager");
            Constructor<TelephonyManager> con = classType.getConstructor(Context.class, int.class);
//            Constructor<TelephonyManager> con = classType.getConstructor(Context.class, int.class);
            TelephonyManager telephonyManager = con.newInstance(context, subId);
            int state = telephonyManager.getSimState();
            Log.e("---", "getSIMOperator: state = " + state);
            Log.e("---", String.format("getOperator: 0 ---> subId = %d", subId));
            if (!testU(subId)) {
                subId = SubscriptionManager.getDefaultDataSubscriptionId();
                Log.e("---", String.format("getOperator: 1 ---> subId = %d", subId));
                if (!testU(subId)) {
                    subId = SubscriptionManager.getDefaultSmsSubscriptionId();
                    Log.e("---", String.format("getOperator: 2 ---> subId = %d", subId));
                    if (!testU(subId)) {
                        subId = SubscriptionManager.getDefaultVoiceSubscriptionId();
                        Log.e("---", String.format("getOperator: 3 ---> subId = %d", subId));
                        if (!testU(subId)) {
                            subId = SubscriptionManager.getDefaultSubscriptionId();
                            Log.e("---", String.format("getOperator: 4 ---> subId = %d", subId));
                        }
                    }
                }
            }
            return (String) telephonyManager.getClass().getMethod("getSimOperatorNumeric", int.class).invoke(telephonyManager, subId);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Log.e("---", "getOperatorName: return null");
        return null;
    }

    private static boolean testU(int subId) {
        return subId >= 0 && subId <= Integer.MAX_VALUE - 1;
    }
}
