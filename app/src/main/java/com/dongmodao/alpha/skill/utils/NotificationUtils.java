package com.dongmodao.alpha.skill.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;


import com.dongmodao.alpha.skill.BuildConfig;
import com.dongmodao.alpha.skill.R;

import java.util.Objects;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * author: dongmodao
 * date: 2018/12/27
 * time: 14:14
 * 通知处理类
 */
public class NotificationUtils {

    private static final String CHANNEL_ID = BuildConfig.APPLICATION_ID + ".notification";

    public static void showNotification(Context context, String title, String content, Class toClass, int notificationId) {

        try {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

            if (notificationManager != null) {
                Intent intent = new Intent(context, toClass);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);


                NotificationCompat.Builder mBuilder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID);
                } else {
                    mBuilder = new NotificationCompat.Builder(context);
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, title, NotificationManager.IMPORTANCE_LOW);
                    Objects.requireNonNull(notificationManager).createNotificationChannel(mChannel);
                    mBuilder.setChannelId(CHANNEL_ID).build();
                }

                mBuilder.setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(content)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                notificationManager.notify(notificationId, mBuilder.build());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
