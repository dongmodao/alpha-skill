package com.dongmodao.alpha.skill.utils;

import com.google.firebase.messaging.FirebaseMessaging;

import java.util.List;

/**
 * author: dongmodao
 * date: 2018/12/18
 * time: 11:00
 * 辅助 FCM 的工具类
 */
public class FCMUtils {

    private static final String TAG = "FCMUtils";

    public static final boolean IS_DEBUG = false;

    /**
     * 根据主题进行多设备推送消息
     * 如果用户手机没有 Google Play Service 或者没有正确权限，则拿不到回调
     * E/FirebaseInstanceId: Google Play services missing or without correct permission.
     * @param topic 主题名称，此处为星座
     * @param listener 结果回调，操作成功或者失败
     */
    public static void subscribeToTopic(String topic, FCMCallbackListener listener) {
        if (IS_DEBUG) {
            topic += "_DEBUG";
        }
        FirebaseMessaging.getInstance().subscribeToTopic(topic)
                .addOnCompleteListener(task -> {
                    if (listener == null)
                        return;
                    if (task.isSuccessful()) {
                        listener.onSucceed();
                    } else {
                        listener.onFail();
                    }
                });

    }

    /**
     * 取消对某个主题的订阅，不再接收推送的消息
     * @param topic 主题名称
     * @param listener 操作回调
     */
    public static void unsubscribeFromTopic(String topic, FCMCallbackListener listener) {
        if (IS_DEBUG) {
            topic += "_DEBUG";
        }
        FirebaseMessaging.getInstance().unsubscribeFromTopic(topic)
                .addOnCompleteListener(task -> {
                    if (listener == null)
                        return ;
                    if (task.isSuccessful()) {
                        listener.onSucceed();
                    } else {
                        listener.onFail();
                    }

                });
    }

    public interface FCMCallbackListener {
        void onSucceed();
        void onFail();
    }

//    /**
//     * 取消订阅所有主题
//     */
//    public static void unsubscribeAllTopics() {
//        List<String> topics = Const.TOPICS;
//        for (String str : topics) {
//            if (IS_DEBUG) {
//                str += "_DEBUG";
//            }
//            unsubscribeFromTopic(str, null);    // 不回调，因为没有提供查询是否订阅某个主题的方法
//        }
//    }
}
