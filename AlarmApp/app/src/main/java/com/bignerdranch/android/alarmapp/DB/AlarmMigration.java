package com.bignerdranch.android.alarmapp.DB;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

/**
 * Created by DaHoon on 2017-01-26.
 */

public class AlarmMigration implements RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {

        RealmSchema schema = realm.getSchema();

        if(oldVersion==0){
            RealmObjectSchema alarmSchema = schema.get("Alarm");
            alarmSchema
                    .addField("lat",double.class)
                    .addField("lng",double.class);

            oldVersion++;
        }

    }
}
