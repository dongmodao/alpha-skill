package com.dongmodao.alpha.skill.service;

import android.content.Intent;
import android.util.Log;

import com.dongmodao.alpha.skill.MainActivity;
import com.dongmodao.alpha.skill.utils.LogUtils;
import com.dongmodao.alpha.skill.utils.NotificationUtils;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

/**
 * author : tangqihao
 *
 * @time : 2019/1/3
 * @project : AlphaSkill
 */
public class FCMService extends FirebaseMessagingService {
    private static final String TAG = "---";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getNotification() == null) {
            if (remoteMessage.getData() != null) {
                NotificationUtils.showNotification(this, "数据消息", getContent(remoteMessage.getData()), MainActivity.class, 101);
            } else {
                NotificationUtils.showNotification(this,  remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(), MainActivity.class, 101);
            }
        }
    }

    private String getContent(Map<String,String> data) {
        StringBuilder builder = new StringBuilder();
        for (String key : data.keySet()) {
            builder.append(key + ": " + data.get(key) + "; ");
        }
        return builder.toString();
    }


    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.e(TAG, "onNewToken: " + s);
    }
}
