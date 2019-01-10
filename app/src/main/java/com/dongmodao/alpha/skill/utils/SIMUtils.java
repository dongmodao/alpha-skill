package com.dongmodao.alpha.skill.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * author : tangqihao
 *
 * @time : 2019/1/10
 * @project : SIMUtils
 */
public class SIMUtils {

    /**
     * 获取当前插入的 SIM 卡中的所有 SubscriptionInfo, 先按照卡槽排序再按照 subId 排序（也就是说在第一次插卡之后更换了卡槽）
     * @param context context
     * @return List<SubscriptionInfo>
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    public static List<SubscriptionInfo> getAllSubIdInfos(Context context) {

        try {
            SubscriptionManager subscriptionManager = (SubscriptionManager) context.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                return subscriptionManager.getActiveSubscriptionInfoList();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     *  获取默认数据流量卡 subId
     * @param context context
     * @return subId 无卡/无效：-1，与是否打开 wifi/data 无关，
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    public static int getDefaultDataSubId(Context context){

        int subscriberId = -1;
        try {
            if (Build.VERSION.SDK_INT >= 24) {
                subscriberId = SubscriptionManager.getDefaultDataSubscriptionId();
            }else{
                try {
                    SubscriptionManager subscriptionManager = (SubscriptionManager)context.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);
                    Class cls  =  SubscriptionManager.class.getClass();
                    Method method = cls.getDeclaredMethod("getDefaultDataSubId");
                    subscriberId = (Integer) method.invoke(subscriptionManager);
                }catch (Exception e){e.printStackTrace();}
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return subscriberId;
    }

    /**
     * 普遍使用的获取 SIM 网络运营商 MCC+MNC 的方法
     * @param context context
     * @param subId SIM subId
     * @return MCC+MNC
     */
    public static String getOperator(Context context, int subId) {
        if (!isUsableSubIdValue(subId))
            return null;

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                return getOperatorGeN(context, subId);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                return getOperatorGMR1(context, subId);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据 subId 获取 API >= N; GeN = great or equal N
     * @param context context
     * @param subId SIM subId
     * @return MCC+MNC
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private static String getOperatorGeN(Context context, int subId) {
        try {
            Class<?> classType = Class.forName("android.telephony.TelephonyManager");
            Constructor<?> con = classType.getConstructor(Context.class);
            TelephonyManager telephonyManager = (TelephonyManager) con.newInstance(context);

            Method method = telephonyManager.getClass().getMethod("getSimOperatorNumeric", int.class);
            return (String) method.invoke(telephonyManager, subId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据 subId 获取 API >= M; GMR1 = great than MR1
     * @param context context
     * @param subId SIM subId
     * @return MCC+MNC
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private static String getOperatorGMR1(Context context, int subId) {
        try {
            Class<?> classType = Class.forName("android.telephony.TelephonyManager");
            Constructor<?> con = classType.getConstructor(Context.class);
            TelephonyManager telephonyManager = (TelephonyManager) con.newInstance(context);
            Method method = telephonyManager.getClass().getMethod("getSimOperator", int.class);
            return (String) method.invoke(telephonyManager, subId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 普遍使用的获取 SIM 网络运营商名称，如 CMCC
     * @param context context
     * @param subId SIM subId
     * @return the Service Provider Name (SPN)
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    public static String getOperatorName(Context context, int subId) {
        if (!isUsableSubIdValue(subId))
            return null;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                return getOperatorNameGeN(context, subId);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                return getOperatorNameGMR1(context, subId);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据 subId 获取 API >= N; GeN = great or equal N
     * @param context context
     * @param subId SIM subId
     * @return the Service Provider Name (SPN)
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private static String getOperatorNameGeN(Context context, int subId) {
        try {
            Class<?> classType = Class.forName("android.telephony.TelephonyManager");
            Constructor<?> con = classType.getConstructor(Context.class);         // api 24 的 构造方法
            TelephonyManager telephonyManager = (TelephonyManager) con.newInstance(context);
            Method method = telephonyManager.getClass().getMethod("getSimOperatorName", int.class);
            return (String) method.invoke(telephonyManager, subId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据 subId 获取 API >= M; GMR1 = great than MR1
     * @param context context
     * @param subId SIM subId
     * @return the Service Provider Name (SPN)
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private static String getOperatorNameGMR1(Context context, int subId) {
        try {
            Class<?> classType = Class.forName("android.telephony.TelephonyManager");
            Constructor<?> con = classType.getConstructor(Context.class);
            TelephonyManager telephonyManager = (TelephonyManager) con.newInstance(context);
            Method method = telephonyManager.getClass().getMethod("getSimOperatorNameForSubscription", int.class);
            return (String) method.invoke(telephonyManager, subId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * @return true if subId is an usable subId value else false. A
     * usable subId means its neither a INVALID_SUBSCRIPTION_ID nor a DEFAULT_SUB_ID.
     */
    public static boolean isUsableSubIdValue(int subId) {
        return subId >= 0 && subId <= (Integer.MAX_VALUE - 1);
    }

    /**
     * 拿到当前默认的 MCC+MNC
     * @param context context
     * @return MCC + MNC
     */
    public static String getDefaultOperator(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return telephonyManager.getSimOperator();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     *  拿到当前默认的运营商 SPN
     * @param context context
     * @return SPN
     */
    public static String getDefaultOperatorName(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return telephonyManager.getSimOperatorName();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
