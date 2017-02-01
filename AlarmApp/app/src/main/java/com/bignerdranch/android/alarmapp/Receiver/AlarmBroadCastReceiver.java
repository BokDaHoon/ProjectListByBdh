package com.bignerdranch.android.alarmapp.Receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.bignerdranch.android.alarmapp.DB.AlarmManagerUtil;
import com.bignerdranch.android.alarmapp.DB.AlarmSchema;
import com.bignerdranch.android.alarmapp.R;
import com.bignerdranch.android.alarmapp.Service.AlarmTaskService;
import com.bignerdranch.android.alarmapp.Service.AlarmTasks;
import com.bignerdranch.android.alarmapp.Utility.AlarmSoundPlay;

import java.util.Calendar;

/**
 * Created by DaHoon on 2017-01-24.
 */

public class AlarmBroadCastReceiver extends BroadcastReceiver {

    private static final int ACTION_CANCEL_PENDING_INTENT_ID = 11;

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        boolean[] weekday = intent.getBooleanArrayExtra("weekday");
        String id = intent.getStringExtra("id");
        int hour = intent.getIntExtra("hour", -1);
        int minute = intent.getIntExtra("minute", -1);

        // 요일반복인 경우 다음알람을 설정.
        if(weekday[0]){
            Calendar cal = Calendar.getInstance();

            Intent nextAlarmIntent = new Intent(context, AlarmTaskService.class);
            nextAlarmIntent.setAction(AlarmTasks.ACTION_REGISTER_ALRAM);
            nextAlarmIntent.putExtra("id", id);
            nextAlarmIntent.putExtra("KEY_DAY_INT", Integer.valueOf(id));
            nextAlarmIntent.putExtra("hour", hour);
            nextAlarmIntent.putExtra("minute", minute);
            nextAlarmIntent.putExtra("weekday", weekday);

            context.startService(nextAlarmIntent);
            unregisterAlarmManager(context, id);
        }

        if (!isOnStateFlag(id, context)) { // 상태가 off이면 종료
            return;
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(android.R.mipmap.sym_def_app_icon)
                .setAutoCancel(true)
                .setContentTitle("Alarm")
                .setContentText("알람 시간입니다.")
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .addAction(cancelReminderAction(context))
                .setDefaults(Notification.DEFAULT_VIBRATE);

        if (Build.VERSION.SDK_INT < 16) {
            nm.notify(111, builder.getNotification());
        } else {
            nm.notify(111, builder.build());
        }

        new AlarmSoundPlay(context).soundPlay();
    }

    private static NotificationCompat.Action cancelReminderAction(Context context) {

        Intent cancelPlayIntent = new Intent(context, AlarmTaskService.class);

        cancelPlayIntent.setAction(AlarmTasks.ACTION_CANCEL_ALARM);

        PendingIntent cancelPendingIntent = PendingIntent.getService(
                context,
                ACTION_CANCEL_PENDING_INTENT_ID,
                cancelPlayIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Action cancelAction = new NotificationCompat.Action(R.drawable.ic_cancel_black_24px,
                "Cancel",
                cancelPendingIntent);

        return cancelAction;
    }

    private boolean isOnStateFlag(String id, Context context) {
        Uri uri = AlarmSchema.CONTENT_URI;
        uri = uri.buildUpon().appendPath(id).build();

        Cursor result = context.getContentResolver().query(uri,
                null,
                null,
                null,
                null);

        if(result.moveToFirst()) {
            if (!result.getString(result.getColumnIndex("stateFlag")).equals("true")) { // 상태가 off이면 종료
                return false;
            }
        }
        return true;
    }

    private void unregisterAlarmManager(Context context, String mId) {
        Intent receiverIntent = new Intent(context, AlarmBroadCastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                Integer.valueOf(mId),
                receiverIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManagerUtil.from(context).unregisterAlarm(pendingIntent);
    }
}