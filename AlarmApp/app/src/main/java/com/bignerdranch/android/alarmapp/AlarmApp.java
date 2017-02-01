package com.bignerdranch.android.alarmapp;

import android.app.Application;

import com.bignerdranch.android.alarmapp.DB.AlarmMigration;
import com.tsengvn.typekit.Typekit;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by DaHoon on 2017-01-23.
 */

public class AlarmApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name("alarmdb")
                .migration(new AlarmMigration())
                .schemaVersion(1)
                .build();

        Realm.setDefaultConfiguration(realmConfiguration);
    }
}
