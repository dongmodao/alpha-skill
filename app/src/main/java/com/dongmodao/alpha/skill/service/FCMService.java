package com.dongmodao.alpha.skill.service;

import android.content.Intent;
import android.util.Log;

import com.dongmodao.alpha.skill.MainActivity;
import com.dongmodao.alpha.skill.utils.LogUtils;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

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
        Log.e(TAG, "onMessageReceived: 收到通知 通知 = " + (remoteMessage.getNotification() != null));
        if (remoteMessage.getNotification() == null) {
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.e(TAG, "onNewToken: " + s);
    }
}
