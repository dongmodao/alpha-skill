package com.dongmodao.alpha.skill.skills.syncadapter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * author : tangqihao
 *
 * @time : 2019/1/3
 * @project : AlphaSkill
 */
public class GcmBroadcastReceiver extends BroadcastReceiver {
    // Constants
    // Content provider authority
    public static final String AUTHORITY = "com.dongmodao.alpha.skill.skills.syncadapter.StubProvider";
    // Account type
    public static final String ACCOUNT_TYPE = "dongmodao.com";
    // Account
    public static final String ACCOUNT = "default_account";
    // Incoming Intent key for extended data
    public static final String KEY_SYNC_REQUEST =
            "my_key";

    @Override
    public void onReceive(Context context, Intent intent) {

    }
}
