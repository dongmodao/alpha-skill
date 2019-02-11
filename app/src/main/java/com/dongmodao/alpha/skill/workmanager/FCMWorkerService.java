package com.dongmodao.alpha.skill.workmanager;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.dongmodao.alpha.skill.utils.LogUtils;

/**
 * @author : tangqihao
 * @date : 2019/2/11
 */
public class FCMWorkerService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.log(this, System.currentTimeMillis() + " 启动服务");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.log(this, System.currentTimeMillis() + " 服务命令");
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        LogUtils.log(this, System.currentTimeMillis() + " 服务死亡");
        super.onDestroy();
        startService(new Intent(this, FCMWorkerService.class));
    }
}
