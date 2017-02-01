package com.bignerdranch.android.alarmapp.DB;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by DaHoon on 2017-01-24.
 */

public class AlarmContentProvider extends ContentProvider {
    public static final int TASK_ALL = 100;
    public static final int TASK_WITH_ID = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    // Realm 관련 Class
    private Realm realm;
    private RealmQuery<Alarm> query;
    private RealmResults<Alarm> results;
    private MatrixCursor cursor;

    /**
     * path와 int형 task를 1:1 mapping
     * @return 설정된 uriMathcer 반환
     */
    private static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(AlarmSchema.AUTHORITY, AlarmSchema.PATH_TASK, TASK_ALL);
        uriMatcher.addURI(AlarmSchema.AUTHORITY, AlarmSchema.PATH_TASK + "/#", TASK_WITH_ID);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        realm = Realm.getDefaultInstance();

        cursor = new MatrixCursor(AlarmSchema.sColumns);

        int match = sUriMatcher.match(uri);

        switch(match){
            case TASK_ALL:
                query = realm.where(Alarm.class);
                results = query.findAll();

                for(Alarm alarm : results){
                    Object[] rowData = new Object[]{
                            alarm.getId(),
                            alarm.getAmOrPm(),
                            alarm.getHour(),
                            alarm.getMinute(),
                            alarm.getAlarmId(),
                            alarm.getMemoContent(),

                            alarm.isSun(),
                            alarm.isMon(),
                            alarm.isTue(),
                            alarm.isWed(),
                            alarm.isThu(),
                            alarm.isFri(),
                            alarm.isSat(),

                            alarm.isStateFlag(),

                            alarm.getLat(),
                            alarm.getLng()
                    };
                    cursor.addRow(rowData);
                }

                break;

            case TASK_WITH_ID:
                long id = Long.valueOf(uri.getPathSegments().get(1));

                query = realm.where(Alarm.class).equalTo("id", id);
                Alarm alarm = query.findFirst();

                Object[] rowData = new Object[]{
                        alarm.getId(),
                        alarm.getAmOrPm(),
                        alarm.getHour(),
                        alarm.getMinute(),
                        alarm.getAlarmId(),
                        alarm.getMemoContent(),

                        alarm.isSun(),
                        alarm.isMon(),
                        alarm.isTue(),
                        alarm.isWed(),
                        alarm.isThu(),
                        alarm.isFri(),
                        alarm.isSat(),

                        alarm.isStateFlag(),

                        alarm.getLat(),
                        alarm.getLng()
                };
                cursor.addRow(rowData);
                break;
        }

        realm.close();
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues cv) {
        realm = Realm.getDefaultInstance();
        dataInsertOrUpdate(realm, cv, false, null);

        return Uri.withAppendedPath(uri, String.valueOf(cv.getAsLong(AlarmSchema.COLUMN_ID)));
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues cv, String selection, String[] selectionArgs) {
        realm = Realm.getDefaultInstance();
        dataInsertOrUpdate(realm, cv, true, selection);

        return 1;
    }

    /**
     * Alarm객체의 내용을 '삽입' or '수정'한다.
     * @param realm : DB작업을 위한 realm객체
     * @param cv    : '삽입' or '수정'하기 위한 내용을 담은 객체
     * @param modifyFlag : 수정 여부
     * @param selection : '수정'시에 수정할 조건문
     */
    private void dataInsertOrUpdate(Realm realm, ContentValues cv, boolean modifyFlag, String selection) {
        realm.beginTransaction();
        Alarm alarm = null;

        if(!modifyFlag) {
            alarm = realm.createObject(Alarm.class, cv.getAsLong(AlarmSchema.COLUMN_ID));
        }else{
            alarm = realm.where(Alarm.class).equalTo("id", Long.valueOf(selection)).findFirst();
        }

        alarm.setAmOrPm(cv.getAsInteger(AlarmSchema.COLUMN_AM_OR_PM));
        alarm.setHour(cv.getAsInteger(AlarmSchema.COLUMN_HOUR));
        alarm.setMinute(cv.getAsInteger(AlarmSchema.COLUMN_MINUTE));
        alarm.setAlarmId(cv.getAsLong(AlarmSchema.COLUMN_ALARM_ID));
        alarm.setMemoContent(cv.getAsString(AlarmSchema.COLUMN_MEMO_CONTENT));

        alarm.setSun(cv.getAsBoolean(AlarmSchema.COLUMN_SUN));
        alarm.setMon(cv.getAsBoolean(AlarmSchema.COLUMN_MON));
        alarm.setTue(cv.getAsBoolean(AlarmSchema.COLUMN_TUE));
        alarm.setWed(cv.getAsBoolean(AlarmSchema.COLUMN_WED));
        alarm.setThu(cv.getAsBoolean(AlarmSchema.COLUMN_THU));
        alarm.setFri(cv.getAsBoolean(AlarmSchema.COLUMN_FRI));
        alarm.setSat(cv.getAsBoolean(AlarmSchema.COLUMN_SAT));

        alarm.setLat(cv.getAsDouble(AlarmSchema.COLUMN_LAT));
        alarm.setLng(cv.getAsDouble(AlarmSchema.COLUMN_LNG));

        if(!modifyFlag){
            alarm.setStateFlag(true);
        }

        realm.commitTransaction();
        realm.close();
    }
}
