package com.dongmodao.alpha.skill.service;

import android.util.Log;

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
        Log.e(TAG, "onMessageReceived: " + remoteMessage.getNotification().getBody());
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
    }
}
