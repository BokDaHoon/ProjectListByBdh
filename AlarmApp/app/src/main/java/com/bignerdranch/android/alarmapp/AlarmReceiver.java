package com.bignerdranch.android.alarmapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

/**
 * Created by DaHoon on 2017-01-24.
 */

public class AlarmReceiver extends BroadcastReceiver {
    public AlarmReceiver() { }

    private NotificationManager mManager;
    private static int MY_NOTIFICATION_ID = 111;

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationmanager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, ListActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder builder = new Notification.Builder(context);
        builder.setTicker("HETT").setWhen(System.currentTimeMillis())
                .setContentTitle("푸쉬 제목").setContentText("푸쉬내용")
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE).setContentIntent(pendingIntent).setAutoCancel(true);

        if (Build.VERSION.SDK_INT < 16) {
            notificationmanager.notify(MY_NOTIFICATION_ID, builder.getNotification());
        } else {
            notificationmanager.notify(MY_NOTIFICATION_ID, builder.build());
        }

    }
}