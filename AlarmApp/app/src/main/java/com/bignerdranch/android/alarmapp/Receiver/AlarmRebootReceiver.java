package com.bignerdranch.android.alarmapp.Receiver;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.bignerdranch.android.alarmapp.Service.AlarmTaskService;
import com.bignerdranch.android.alarmapp.Service.AlarmTasks;

/**
 * Created by DaHoon on 2017-01-27.
 */

public class AlarmRebootReceiver extends BroadcastReceiver {

    private static final int ACTION_REBOOT_PENDING_INTENT_ID = 12;

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent rebootIntent = new Intent(context, AlarmTaskService.class);
        rebootIntent.setAction(AlarmTasks.ACTION_REBOOT_ALARM);

        context.startService(rebootIntent);
    }
}
