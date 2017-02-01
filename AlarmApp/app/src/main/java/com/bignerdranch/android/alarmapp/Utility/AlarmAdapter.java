package com.bignerdranch.android.alarmapp.Utility;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.bignerdranch.android.alarmapp.DB.Alarm;
import com.bignerdranch.android.alarmapp.DB.AlarmSchema;
import com.bignerdranch.android.alarmapp.DetailActivity;
import com.bignerdranch.android.alarmapp.R;

import java.util.Calendar;

import io.realm.Realm;

/**
 * Created by DaHoon on 2017-01-23.
 */

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder> {

    private static int mCount;
    private static Cursor mCursor;
    private static Context mContext;
    private static final int DAY_COUNT = 7;
    private static final int[] toggleLabelViewId = {
            R.id.label_day_sun ,
            R.id.label_day_mon ,
            R.id.label_day_tue ,
            R.id.label_day_wed ,
            R.id.label_day_thu ,
            R.id.label_day_fri ,
            R.id.label_day_sat
    };

    public AlarmAdapter(Context context) {
        mContext = context;
    }

    @Override
    public AlarmAdapter.AlarmViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_alarm, parent, false);
        return new AlarmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AlarmAdapter.AlarmViewHolder holder, int position) {

        if(mCursor.moveToPosition(position)) {

            int idIndex = mCursor.getColumnIndex(AlarmSchema.COLUMN_ID);
            int amOrPmIndex = mCursor.getColumnIndex(AlarmSchema.COLUMN_AM_OR_PM);
            int hourIndex = mCursor.getColumnIndex(AlarmSchema.COLUMN_HOUR);
            int minuteIndex = mCursor.getColumnIndex(AlarmSchema.COLUMN_MINUTE);
            int stateIndex = mCursor.getColumnIndex(AlarmSchema.COLUMN_STATE_FLAG);
            int[] dayIndex = {
                    mCursor.getColumnIndex(AlarmSchema.COLUMN_SUN),
                    mCursor.getColumnIndex(AlarmSchema.COLUMN_MON),
                    mCursor.getColumnIndex(AlarmSchema.COLUMN_TUE),
                    mCursor.getColumnIndex(AlarmSchema.COLUMN_WED),
                    mCursor.getColumnIndex(AlarmSchema.COLUMN_THU),
                    mCursor.getColumnIndex(AlarmSchema.COLUMN_FRI),
                    mCursor.getColumnIndex(AlarmSchema.COLUMN_SAT)
            };

            String amOrPm, hourOfDay, minuteOfDay;
            int hourOfDayInt = mCursor.getInt(hourIndex);
            int amOrPmInt = mCursor.getInt(amOrPmIndex);

            amOrPm = ((amOrPmInt == 0) ? "오전" : "오후");

            // leading zero를 해서 한자리의 숫자인 경우 앞에 0을 붙인다.
            hourOfDay = String.format("%02d",
                            ((amOrPmInt == 0) ?
                                    hourOfDayInt :
                                    (hourOfDayInt == 12) ? hourOfDayInt : hourOfDayInt - 12));
            minuteOfDay = String.format("%02d", mCursor.getInt(minuteIndex));


            holder.mTxtListAlarmAmPm.setText(amOrPm);
            holder.mTxtListAlarmClock.setText(hourOfDay + ":" + minuteOfDay);
            holder.mSwitchListAlarm.setChecked((mCursor.getString(stateIndex).equals("true")));

            int color, colorResource;
            for(int i=0; i<DAY_COUNT; i++) {
                if(mCursor.getString(dayIndex[i]).equals("true")){
                    colorResource = R.color.blue;
                }else{
                    colorResource = R.color.gray;
                }

                if(Build.VERSION.SDK_INT >= 23) {
                    color = mContext.getResources().getColor(colorResource, null);
                }else{
                    color = mContext.getResources().getColor(colorResource);
                }

                holder.mTxtDayOfRepeat[i].setTextColor(color);
            }
            holder.itemView.setTag(mCursor.getLong(idIndex));
        }
    }

    @Override
    public int getItemCount() {
        return mCount;
    }

    public class AlarmViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
                                                                    SwitchCompat.OnCheckedChangeListener{
        TextView mTxtListAlarmAmPm;
        TextView mTxtListAlarmClock;
        SwitchCompat mSwitchListAlarm;

        TextView[] mTxtDayOfRepeat = new TextView[DAY_COUNT];

        public AlarmViewHolder(View itemView) {
            super(itemView);
            mTxtListAlarmAmPm = (TextView) itemView.findViewById(R.id.txt_list_alarm_am_pm);
            mTxtListAlarmClock = (TextView) itemView.findViewById(R.id.txt_list_alarm_clock);
            mSwitchListAlarm = (SwitchCompat) itemView.findViewById(R.id.switch_list_alarm);

            for(int i=0; i<DAY_COUNT; i++) {
                mTxtDayOfRepeat[i] = (TextView) itemView.findViewById(toggleLabelViewId[i]);
            }

            itemView.setOnClickListener(this);
            mSwitchListAlarm.setOnCheckedChangeListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = DetailActivity.newIntent(mContext);
            intent.putExtra("Modify", true);
            intent.putExtra("id", v.getTag().toString());

            mContext.startActivity(intent);
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(itemView.getTag() == null){ return; }

            String id = itemView.getTag().toString();
            Realm realm = Realm.getDefaultInstance();
            Alarm alarm = null;

            realm.beginTransaction();
            alarm = realm.where(Alarm.class).equalTo("id", Long.valueOf(id)).findFirst();
            alarm.setStateFlag(isChecked);
            realm.commitTransaction();
            realm.close();
        }
    }

    public void swapCursor(Cursor data){
        mCursor = data;
        mCount = data.getCount();

        if(data != null){
            this.notifyDataSetChanged();
        }
    }
}
