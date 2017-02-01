package com.bignerdranch.android.alarmapp.Service;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import com.bignerdranch.android.alarmapp.DB.AlarmManagerUtil;
import com.bignerdranch.android.alarmapp.DB.AlarmSchema;
import com.bignerdranch.android.alarmapp.Receiver.AlarmBroadCastReceiver;
import com.bignerdranch.android.alarmapp.Utility.AlarmSoundPlay;

/**
 * Created by DaHoon on 2017-01-27.
 */

public class AlarmTasks {
    public static final String ACTION_CANCEL_ALARM = "cancel_alarm";
    public static final String ACTION_REBOOT_ALARM = "reboot_alarm";
    public static final String ACTION_REGISTER_ALRAM = "register_alarm";

    private static final int DAY_COUNT = 7;

    public static void executeTask(Context context, String action, Intent intent) {
        if(ACTION_REGISTER_ALRAM.equals(action)){
            setActionRegisterAlram(context, intent);


        }else if(ACTION_CANCEL_ALARM.equals(action)) { // Alarm을 취소하는 경우
            new AlarmSoundPlay(context).cancelPlay();


        }else if(ACTION_REBOOT_ALARM.equals(action)){ // 재부팅된 경우
            Cursor cursor = context.getContentResolver().query(AlarmSchema.CONTENT_URI, null, null, null, null);
            setAlarmByCursor(context, cursor);
        }

    }

    private static void setActionRegisterAlram(Context context, Intent intent){
        Intent receiverIntent = new Intent(context, AlarmBroadCastReceiver.class);

        boolean[] weekday = intent.getBooleanArrayExtra("weekday");
        String mId = intent.getStringExtra("id");
        int hour = intent.getIntExtra("hour", 0);
        int minute = intent.getIntExtra("minute", 0);

        receiverIntent.putExtra("weekday", weekday);
        receiverIntent.putExtra("id", mId);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                Integer.valueOf(mId),
                receiverIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManagerUtil.from(context).setAlarm(hour,
                minute,
                pendingIntent,
                weekday);
    }

    // 재부팅 됐을 때 AlarmManager Set
    private static void setAlarmByCursor(Context context, Cursor cursor) {
        while(cursor.moveToNext()){
            boolean[] weekday = {
                    false,                                                                           // hasRepeatDay
                    cursor.getString(cursor.getColumnIndex(AlarmSchema.COLUMN_SUN)).equals("true"), // Sun
                    cursor.getString(cursor.getColumnIndex(AlarmSchema.COLUMN_MON)).equals("true"), // Mon
                    cursor.getString(cursor.getColumnIndex(AlarmSchema.COLUMN_TUE)).equals("true"), // Tue
                    cursor.getString(cursor.getColumnIndex(AlarmSchema.COLUMN_WED)).equals("true"), // Wed
                    cursor.getString(cursor.getColumnIndex(AlarmSchema.COLUMN_THU)).equals("true"), // Thu
                    cursor.getString(cursor.getColumnIndex(AlarmSchema.COLUMN_FRI)).equals("true"), // Fri
                    cursor.getString(cursor.getColumnIndex(AlarmSchema.COLUMN_SAT)).equals("true")  // Sat
            };

            boolean hasRepeatDay = false;

            for(int i=0; i<DAY_COUNT; i++) {
                if(weekday[i]){
                    hasRepeatDay = true;
                }
            }

            if(hasRepeatDay) {
                weekday[0] = true;
            }

            String id = cursor.getString(cursor.getColumnIndex("id"));
            Intent receiverIntent = new Intent(context, AlarmBroadCastReceiver.class);
            receiverIntent.putExtra("weekday", weekday);
            receiverIntent.putExtra("id", id);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    cursor.getInt(cursor.getColumnIndex("id")),
                    receiverIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);


            AlarmManagerUtil.from(context).setAlarm(cursor.getInt(cursor.getColumnIndex("hour")),
                    cursor.getInt(cursor.getColumnIndex("minute")),
                    pendingIntent,
                    weekday);
        }
    }

    private void unregisterAlarmManager() {
    }
}
