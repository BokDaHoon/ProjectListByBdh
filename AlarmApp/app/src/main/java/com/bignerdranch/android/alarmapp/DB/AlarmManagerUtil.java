package com.bignerdranch.android.alarmapp.DB;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build;

import java.util.GregorianCalendar;

/**
 * Created by DaHoon on 2017-01-27.
 */

/**
 * AlarmManager에 alarm을 등록하는데 도움을 주는 Class
 * 요일반복 또는 한번 울림 알람을 설정할 때 사용한다.
 */
public class AlarmManagerUtil {

    private static AlarmManagerUtil mAlarmManagerUtil;
    private static Context mContext;

    // 날짜는 같지만 시간이 지나 다음주에 알람을 설정해야 하는 경우
    private static final int STATE_SAME_IDX_NEXT_WEEK = 0;
    // 날짜가 같고 시간이 아직 지나지 않아 당일에 알람을 설정할 수 있는 경우
    private static final int STATE_SAME_IDX_TODAY = 1;
    // 날짜가 다를 경우
    private static final int STATE_DIFF_IDX = 2;


    public static AlarmManagerUtil from(Context context) {
        if(mAlarmManagerUtil == null){
            mAlarmManagerUtil = new AlarmManagerUtil();
        }

        if(mContext != context){
            mContext = context;
        }

        return mAlarmManagerUtil;
    }

    /**
     * Alarm을 셋팅할 때 호출되는 함수
     * @param hourOfDay : 알람이 울릴 시간
     * @param minute    : 알람이 울릴 분
     * @param alarmPendingIntent : BroadcastReceiver로 보내기 위한 정보가 담은 Pending Intent
     * @param weekday   : 0번째 index의 경우 값이 true일 경우 요일반복, flase일 경우 한번울림
     */
    public void setAlarm(int hourOfDay, int minute, PendingIntent alarmPendingIntent, boolean[] weekday) {
        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(mContext.ALARM_SERVICE);
        if(!weekday[0]){
            setOnceAlarm(hourOfDay, minute, alarmPendingIntent, alarmManager);
        }else{
            setDayRepeatingAlarm(hourOfDay, minute, alarmPendingIntent, alarmManager, weekday);
        }
    }

    /**
     * 한 번 울릴 때 호출
     * @param hourOfDay
     * @param minute
     * @param alarmPendingIntent
     * @param alarmManager
     */
    private void setOnceAlarm(int hourOfDay, int minute, PendingIntent alarmPendingIntent, AlarmManager alarmManager) {
        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ) {
            alarmManager.setAlarmClock(new AlarmManager.AlarmClockInfo(getTriggerAtMillis(hourOfDay, minute)
                                     , alarmPendingIntent), alarmPendingIntent);

        }
        else if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT ) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP,
                                  getTriggerAtMillis(hourOfDay, minute), alarmPendingIntent);
        }
        else {
            alarmManager.set(AlarmManager.RTC_WAKEUP,
                             getTriggerAtMillis(hourOfDay, minute), alarmPendingIntent);
        }
    }

    /**
     * 요일 반복시 호출
     * @param hourOfDay
     * @param minute
     * @param alarmPendingIntent
     * @param alarmManager
     * @param weekday
     */
    private void setDayRepeatingAlarm(int hourOfDay, int minute, PendingIntent alarmPendingIntent, AlarmManager alarmManager,
                                      boolean[] weekday) {
        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ) {
            alarmManager.setAlarmClock(new AlarmManager.AlarmClockInfo(getTriggerAtMillis(hourOfDay, minute),
                                           alarmPendingIntent), alarmPendingIntent);
        }
        else if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT ) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP,
                                  getTriggerAtMillisForDayRepeat(hourOfDay, minute, weekday),
                                  alarmPendingIntent);
        }
        else {
            alarmManager.set(AlarmManager.RTC_WAKEUP,
                             getTriggerAtMillisForDayRepeat(hourOfDay, minute, weekday),
                             alarmPendingIntent);
        }
    }

    private long getTriggerAtMillisForDayRepeat(int hourOfDay, int minute, boolean[] weekday) {
        GregorianCalendar currentCalendar = (GregorianCalendar) GregorianCalendar.getInstance();
        int currAlarmDay = currentCalendar.get(GregorianCalendar.DAY_OF_WEEK);
        int nextAlarmDay = currAlarmDay, length = weekday.length;
        int diffIndex = 0;

        int currentHourOfDay = currentCalendar.get(GregorianCalendar.HOUR_OF_DAY);
        int currentMinute = currentCalendar.get(GregorianCalendar.MINUTE);

        // Calendar 단위에 맞게 1:일, 2:월, 3:화, 4:수, 5:목, 6:금, 7:토
        while(true){
            if(nextAlarmDay == (length - 1)){ // 일주일 단위를 넘어가면 일요일로
                nextAlarmDay = 1;
            }

            if(weekday[nextAlarmDay]){ // 요일알람설정이 true이면
                break;
            }

            nextAlarmDay++;
        }

        if(currAlarmDay > nextAlarmDay) { // 이번주에서 오늘의 요일이 다음 요일반복보다 뒤에 있는 경우
                                          // 다음주의 다음 요일로 index 설정
            diffIndex = (7 % currAlarmDay) + nextAlarmDay;

        }else if(currAlarmDay == nextAlarmDay){ // 다음 알람일이 같으면

            // 날짜는 같지만 시간이 지나 다음주에 알람을 설정해야 하는 경우
            if( currentHourOfDay < hourOfDay || ( currentHourOfDay == hourOfDay && currentMinute < minute ) ) {
                return getTimeInMillisForDayRepeat(STATE_SAME_IDX_TODAY, hourOfDay, minute, 0);


            }else { // 날짜가 같고 시간이 아직 지나지 않아 당일에 알람을 설정할 수 있는 경우
                return getTimeInMillisForDayRepeat(STATE_SAME_IDX_NEXT_WEEK, hourOfDay, minute, 0);
            }

        }else{
            diffIndex = nextAlarmDay - currAlarmDay;
        }

        return getTimeInMillisForDayRepeat(STATE_DIFF_IDX, hourOfDay, minute, diffIndex);
    }

    private long getTimeInMillisForDayRepeat(int stateCode, int hourOfDay, int minute, int diffIndex) {
        GregorianCalendar calendar = (GregorianCalendar) GregorianCalendar.getInstance();

        switch(stateCode) {
            case STATE_SAME_IDX_NEXT_WEEK :
                calendar.add(GregorianCalendar.DAY_OF_YEAR, 7);
                break;

            case STATE_DIFF_IDX :
                calendar.add(GregorianCalendar.DAY_OF_YEAR, diffIndex);
                break;

            case STATE_SAME_IDX_TODAY :
                break;
        }

        calendar.set(GregorianCalendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(GregorianCalendar.MINUTE, minute);
        calendar.set(GregorianCalendar.SECOND, 0);
        calendar.set(GregorianCalendar.MILLISECOND, 0);

        return calendar.getTimeInMillis();
    }

    /**
     * 한번 울림 알람일 시 호출
     * @param hourOfDay
     * @param minute
     * @return 해당 시간을 ms 단위로 바꾸어서 return
     */
    private long getTriggerAtMillis(int hourOfDay, int minute) {
        GregorianCalendar currentCalendar = (GregorianCalendar) GregorianCalendar.getInstance();
        int currentHourOfDay = currentCalendar.get(GregorianCalendar.HOUR_OF_DAY);
        int currentMinute = currentCalendar.get(GregorianCalendar.MINUTE);

        if( currentHourOfDay < hourOfDay || ( currentHourOfDay == hourOfDay && currentMinute < minute ) ) {
            return getTimeInMillis(false, hourOfDay, minute);
        }
        else {
            return getTimeInMillis(true, hourOfDay, minute);
        }
    }

    private long getTimeInMillis(boolean tomorrow, int hourOfDay, int minute) {
        GregorianCalendar calendar = (GregorianCalendar) GregorianCalendar.getInstance();

        if(tomorrow) {
            calendar.add(GregorianCalendar.DAY_OF_YEAR, 1);
        }

        calendar.set(GregorianCalendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(GregorianCalendar.MINUTE, minute);
        calendar.set(GregorianCalendar.SECOND, 0);
        calendar.set(GregorianCalendar.MILLISECOND, 0);

        return calendar.getTimeInMillis();
    }

    public void unregisterAlarm(PendingIntent alarmPendingIntent){
        AlarmManager mAlarmManager = (AlarmManager) mContext.getSystemService(mContext.ALARM_SERVICE);
        mAlarmManager.cancel(alarmPendingIntent);
    }
}
