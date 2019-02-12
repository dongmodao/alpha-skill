package com.dongmodao.alpha.skill.workmanager;

import android.content.Context;
import android.support.annotation.NonNull;

import com.dongmodao.alpha.skill.utils.LogUtils;

import androidx.work.Worker;
import androidx.work.WorkerParameters;

/**
 * @author : tangqihao
 * @date : 2019/2/11
 */
public class FCMWorker extends Worker {

    private Context mContext;
    
    public FCMWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        mContext = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        startFCMService();
        return Result.success();
    }

    private void startFCMService() {
        LogUtils.log(mContext, "startFCMService --- 启动服务");
//        mContext.startService(new Intent(mContext, FCMWorkerService.class));
    }
}
