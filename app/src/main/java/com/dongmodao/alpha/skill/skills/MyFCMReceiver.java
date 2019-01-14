package com.dongmodao.alpha.skill.skills;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

/**
 * author : tangqihao
 * 监听 FCM 不管前后台的 show Message / data Message
 * @time : 2019/1/11
 * @project : AlphaSkill
 */
public class MyFCMReceiver extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
//        Log.e("-----", "onReceive: 自建 Receiver");
//       context.startActivity(new Intent(context, MainActivity.class));
    }
}
