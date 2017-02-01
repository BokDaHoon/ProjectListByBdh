package com.bignerdranch.android.alarmapp.DB;

import android.net.Uri;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by DaHoon on 2017-01-25.
 */
public class AlarmSchema {

    public static final String AUTHORITY = "com.bignerdranch.android.alarmapp";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_TASK = "task";
    public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TASK).build();

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_AM_OR_PM = "amOrPm";
    public static final String COLUMN_HOUR = "hour";
    public static final String COLUMN_MINUTE = "minute";
    public static final String COLUMN_ALARM_ID = "alarmId";
    public static final String COLUMN_MEMO_CONTENT = "memoContent";

    public static final String COLUMN_SUN = "sun";
    public static final String COLUMN_MON = "mon";
    public static final String COLUMN_TUE = "tue";
    public static final String COLUMN_WED = "wed";
    public static final String COLUMN_THU = "thu";
    public static final String COLUMN_FRI = "fri";
    public static final String COLUMN_SAT = "sat";

    public static final String COLUMN_STATE_FLAG = "stateFlag";

    public static final String COLUMN_LAT = "lat";
    public static final String COLUMN_LNG = "lng";


    public static final String[] sColumns = new String[] {"id",
                                                             "amOrPm",
                                                             "hour",
                                                             "minute",
                                                             "alarmId",
                                                             "memoContent",

                                                             "sun",
                                                             "mon",
                                                             "tue",
                                                             "wed",
                                                             "thu",
                                                             "fri",
                                                             "sat",

                                                             "stateFlag",

                                                             "lat",
                                                             "lng"
    };
}
