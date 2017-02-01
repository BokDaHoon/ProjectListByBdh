package com.bignerdranch.android.alarmapp.Service;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by DaHoon on 2017-01-27.
 */

public class AlarmTaskService extends IntentService {

    public AlarmTaskService(){ super("AlarmTaskService"); }

    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent.getAction();
        AlarmTasks.executeTask(this, action, intent);
    }
}
