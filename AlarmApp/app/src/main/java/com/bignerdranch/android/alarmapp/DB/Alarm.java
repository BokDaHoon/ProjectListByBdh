package com.bignerdranch.android.alarmapp.DB;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by DaHoon on 2017-01-25.
 */
@RealmClass
public class Alarm extends RealmObject {
    @PrimaryKey
    private long id;

    private int amOrPm; // am or pm
    private int hour; // 시간
    private int minute; // 분
    private long alarmId; // AlarmManagerID
    private String memoContent; // Memo 내용

    private boolean sun; // 일요일
    private boolean mon; // 월요일
    private boolean tue; // 화요일
    private boolean wed; // 수요일
    private boolean thu; // 목요일
    private boolean fri; // 금요일
    private boolean sat; // 토요일

    private boolean stateFlag;

    private Double lat;
    private Double lng;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getAmOrPm() {
        return amOrPm;
    }

    public void setAmOrPm(int amOrPm) {
        this.amOrPm = amOrPm;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public long getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(long alarmId) {
        this.alarmId = alarmId;
    }

    public String getMemoContent() {
        return memoContent;
    }

    public void setMemoContent(String memoContent) {
        this.memoContent = memoContent;
    }

    public boolean isSun() {
        return sun;
    }

    public void setSun(boolean sun) {
        this.sun = sun;
    }

    public boolean isMon() {
        return mon;
    }

    public void setMon(boolean mon) {
        this.mon = mon;
    }

    public boolean isTue() {
        return tue;
    }

    public void setTue(boolean tue) {
        this.tue = tue;
    }

    public boolean isWed() {
        return wed;
    }

    public void setWed(boolean wed) {
        this.wed = wed;
    }

    public boolean isThu() {
        return thu;
    }

    public void setThu(boolean thu) {
        this.thu = thu;
    }

    public boolean isFri() {
        return fri;
    }

    public void setFri(boolean fri) {
        this.fri = fri;
    }

    public boolean isSat() {
        return sat;
    }

    public void setSat(boolean sat) {
        this.sat = sat;
    }

    public boolean isStateFlag() {
        return stateFlag;
    }

    public void setStateFlag(boolean stateFlag) {
        this.stateFlag = stateFlag;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
}
