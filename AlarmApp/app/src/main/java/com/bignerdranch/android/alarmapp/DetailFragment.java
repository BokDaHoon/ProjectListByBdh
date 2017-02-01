package com.bignerdranch.android.alarmapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bignerdranch.android.alarmapp.DB.Alarm;
import com.bignerdranch.android.alarmapp.DB.AlarmManagerUtil;
import com.bignerdranch.android.alarmapp.DB.AlarmSchema;
import com.bignerdranch.android.alarmapp.Receiver.AlarmBroadCastReceiver;
import com.bignerdranch.android.alarmapp.Utility.CustomTimePicker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.identity.intents.Address;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * @title : AlarmApp
 * @author : Bok Da Hoon
 * @date : 2017.01.28
 */


public class DetailFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
                                    GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnMapLongClickListener {

    private static final int ACTIVE_GPS_REQUEST = 9;
    private static final int PERMISSION_USING_MAP = 10;

    private CustomTimePicker mTimePicker;
    private EditText mEditTextOfMemo;
    private Realm mRealm;
    private boolean mModifyFlag = false;
    private static String mId;

    static final LatLng LAT_LNG_SEOUL = new LatLng(37.56, 126.97);
    private static final String TAG = "AlarmApp Debug";
    private GoogleApiClient mGoogleApiClient = null;
    private Location mLocation;
    private boolean mIsGrantedMapPermission;
    private SupportMapFragment mSupportMapFragment;

    private boolean mIsActiveGsp;

    private Marker mMarker;
    private GoogleMap mGoogleMap;
    private LocationManager mLocationManager;
    private Toast mToast;

    private static final int DAY_COUNT = 7;
    private static ToggleButton[] mToggleButton = new ToggleButton[DAY_COUNT];
    private static final int[] toggleViewId = {
            R.id.repeat_day_sun ,
            R.id.repeat_day_mon ,
            R.id.repeat_day_tue ,
            R.id.repeat_day_wed ,
            R.id.repeat_day_thu ,
            R.id.repeat_day_fri ,
            R.id.repeat_day_sat
    };

    @Override
    public void onStart() {
        //mGoogleApiClient.connect(); // GoogleApiClient Connect
        super.onStart();
    }

    @Override
    public void onStop() {
        //mGoogleApiClient.disconnect(); // GoogleApiClient Disconnect
        super.onStop();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mRealm = Realm.getDefaultInstance();
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.from(getContext()).inflate(R.layout.fragment_detail, container, false);
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bindView(view); // view 바인딩

        if(getActivity().getIntent().hasExtra("Modify")){ // 만약 '수정' 상태면
            mId = getActivity().getIntent().getStringExtra("id");
            mModifyFlag = true;
            new AlarmSetTask().execute();                  // id에 해당하는 내용 Set
        }else{                                             // 만약 '등록' 상태면
            mId = "";
            mModifyFlag = false;
            checkGps(); // GPS 활성화 여부 체크
        }

        buildingGoogleApiClient(); // GoogleApiClient 빌드

        return view;
    }

    /**
     * xml에 있는 item들 변수에 bind
     * @param view : fragment_detail.xml을 inflate한 View
     */
    private void bindView(View view) {
        mToast = Toast.makeText(getContext(), null, Toast.LENGTH_SHORT);
        mEditTextOfMemo = (EditText) view.findViewById(R.id.edit_memo);
        mTimePicker = (CustomTimePicker) view.findViewById(R.id.time_set_timepicker);
        mSupportMapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.map);
        for(int i=0; i<DAY_COUNT; i++) {
            mToggleButton[i] = (ToggleButton) view.findViewById(toggleViewId[i]);
            mToggleButton[i].setOnCheckedChangeListener(new CustomCheckedListener());
        }
    }

    /**
     * mGoogleMap 변수를 초기화하고, 지도에 marker를 변경하는 메서드를 호출.
     * @param map : GoogleMap 지도 변수
     */
    @Override
    public void onMapReady(GoogleMap map) {
        mGoogleMap = map;
        mGoogleMap.setOnMapLongClickListener(this);
        if (mLocation != null) {
            Log.d("DetailFragment", "onMapReady: mLocation is not null");
            updateMarker(true);
        } else {
            Log.d("DetailFragment", "onMapReady: mLocation is null");
            updateMarker(false);
        }
    }

    /**
     * manifest에 Google Map을 사용하기 위한 퍼미션을 추가했는지 검사한다.
     */
    private void checkMapPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                                        android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getActivity(),
                                        android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{
                    android.Manifest.permission.ACCESS_COARSE_LOCATION,
                                        android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_USING_MAP);
        } else {
            mIsGrantedMapPermission = true;
        }
    }

    /**
     * @method checkGps :
     */
    private void checkGps() {
        mLocationManager = (LocationManager) getActivity().getSystemService(getContext().LOCATION_SERVICE);
        if (!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage(getString(R.string.gps_active_alert_message));
            builder.setPositiveButton(getString(R.string.active_label), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent gpsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivityForResult(gpsIntent, ACTIVE_GPS_REQUEST);
                }
            });
            builder.setNegativeButton(getString(R.string.cancel_label), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mIsActiveGsp = false;
                    mToast.cancel();
                    mToast.setText(getString(R.string.passive_gps_message));
                    mToast.show();
                    mSupportMapFragment.getMapAsync(DetailFragment.this);
                }
            });
            builder.show();
        } else {
            mIsActiveGsp = true;
            mSupportMapFragment.getMapAsync(DetailFragment.this);
        }

    }

    protected synchronized void buildingGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .enableAutoManage(mSupportMapFragment.getActivity(), this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    private void updateLocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkMapPermission();
        } else {
            mIsGrantedMapPermission = true;
        }

        if (!mIsGrantedMapPermission)
            return;

        if (mId == "" || mId == null) {
            if (mIsActiveGsp) {
                while (true) {
                    mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                    if (mLocation != null)
                        break;
                }

            }
        }

        mSupportMapFragment.getMapAsync(this);
    }

    /**
     * location값을 불러올 수 있는지 확인하고, 가능하면 현재 위치를,
     * 불가능하면 서울의 위치를 default로 marker 설정.
     * @param isNotNullLocation : Location값을 불러올 수 있는지 여부
     */
    private void updateMarker(boolean isNotNullLocation) {
        LatLng locationLatLng;
        if (isNotNullLocation) {
            locationLatLng = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
        } else {
            locationLatLng = LAT_LNG_SEOUL;
        }
        if (mMarker != null)
            mMarker.remove();
        mMarker = mGoogleMap.addMarker(new MarkerOptions().position(locationLatLng).title(getAddress(locationLatLng)));
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(locationLatLng));
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(17), 2000, null);
    }

    private String getAddress(LatLng latLng) {
        String address = null;
        Geocoder geocoder = new Geocoder(getActivity(), Locale.KOREA);
        List<android.location.Address> addressList;
        try {
            addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (addressList != null && addressList.size() > 0) {
                address = addressList.get(0).getAddressLine(0);
            }

        } catch (IOException e) {
            Log.d("DetailFragment", "주소를 가져올 수 없습니다.");
            e.printStackTrace();
        }
        return address;
    }

    /**
     * 성공적으로 GoogleApiClient 객체 연결되었을 때 실행
     */
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        updateLocation();
    }

    /**
     * 구글 플레이 서비스 연결이 해제되었을 때, 재연결 시도
     * @param i
     */
    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }

    /**
     * 구글 플레이의 연결이 실패했을 때 호출되는 메서드.
     * @param connectionResult : 연결에 실패했을 때, Error Code를 매개변수로 받는다.
     */
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "연결에 실패했습니다. : ERROR CODE = " + connectionResult.getErrorCode());
    }

    /**
     * GoogleMap.OnMapLongClickListener 인터페이스를 implements한 메서드.
     * Map을 길게 클릭할 시 호출된다.
     * @param latLng : 클릭한 곳의 위도, 경도
     */
    @Override
    public void onMapLongClick(LatLng latLng) {
        if (mLocation == null) {
            mLocation = new Location("");
        }
        mLocation.setLatitude(latLng.latitude);
        mLocation.setLongitude(latLng.longitude);
        updateMarker(true);
    }

    /**
     * ToggleButton을 클릭했을 때 Listener로 사용될 Class
     */
    private class CustomCheckedListener implements CompoundButton.OnCheckedChangeListener {

        /**
         * Toggle Button을 클릭했을 때 호출.
         * @param buttonView : 클릭한 view
         * @param isChecked : ToggleButton의 Check유무
         */
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            int color = 0;

            switch (buttonView.getId()) {
                case R.id.repeat_day_mon:
                case R.id.repeat_day_tue:
                case R.id.repeat_day_wed:
                case R.id.repeat_day_thu:
                case R.id.repeat_day_fri:
                    color = R.color.black;
                    break;
                case R.id.repeat_day_sat:
                    color = R.color.blue;
                    break;
                case R.id.repeat_day_sun:
                    color = R.color.red;
                    break;
            }

            toggleNumberColor(buttonView, isChecked, color);
        }

        /**
         * ToggleButton의 색상을 변경하기 위해 호출.
         * 만약 check된 상태라면 색상을 R.drawable.round_button_on로
         * check가 안된 상태라면 색상을 R.drawable.round_button_off로 설정한다.
         * @param viewIn
         * @param isChecked
         * @param color
         */
        private void toggleNumberColor(CompoundButton viewIn, boolean isChecked, int color) {
            if (isChecked) {
                ((ToggleButton) viewIn).setBackgroundDrawable(ResourcesCompat.getDrawable(getResources()
                        , R.drawable.round_button_on
                        , null));
                ((ToggleButton) viewIn).setTextColor(ContextCompat.getColor(getContext(), R.color.blue));
            } else {
                ((ToggleButton) viewIn).setBackgroundDrawable(ResourcesCompat.getDrawable(getResources()
                        , R.drawable.round_button_off
                        , null));
                ((ToggleButton) viewIn).setTextColor(ContextCompat.getColor(getContext(), color));
            }

        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.detail_alarm_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            getActivity().finish();
        }else if(item.getItemId() == R.id.menu_item_alarm_add) {
            if(!mModifyFlag){
                addAlarm();
            }else{
                modifyAlarm();
            }

            getActivity().finish();
            return true;
        }else if(item.getItemId() == R.id.menu_item_alarm_delete) {

            final RealmResults<Alarm> deleteAlarm = mRealm.where(Alarm.class).equalTo("id",
                    Long.valueOf(mId)).findAll(); // 삭제할 객체 GET

            try{
                mRealm.beginTransaction();
                deleteAlarm.deleteAllFromRealm(); // 조건에 해당하는 모든 객체 delete
                mRealm.commitTransaction();
                getActivity().finish();
            }finally{
                unregisterAlarmManager();
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 알람을 '등록'할 시 호출되는 메서드
     */
    private void addAlarm() {
        ContentValues cv = getAlarmContentValues();

        try{
            getActivity().getApplicationContext().
                    getContentResolver().insert(AlarmSchema.CONTENT_URI, cv);
        }finally{
            registerAlarmManager(cv);
        }
    }

    /**
     * 알람을 '수정'할 시 호출되는 메서드
     */
    private void modifyAlarm() {
        ContentValues cv = getAlarmContentValues();

        try{
            getActivity().getApplicationContext().
                    getContentResolver().update(AlarmSchema.CONTENT_URI, cv, mId, null);
        }finally{
            registerAlarmManager(cv);
        }
    }

    /**
     * 등록이나 수정 시 반영해야할 데이터를
     * ContentValues에 담아서 리턴해준다.
     * @return 변경 혹은 추가 내용 사항을 ContentValues의 형태로 넘긴다.
     */
    private ContentValues getAlarmContentValues(){
        ContentValues cv = new ContentValues();

        long key;
        if(mModifyFlag){ // 수정인 경우 기존 id 사용.
            key = Long.valueOf(mId);

        }else{ // 삽입인 경우 id값 생성.

            Number realmMaxId = mRealm.where(Alarm.class).max("id");
            if(realmMaxId != null){
                key = realmMaxId.longValue() + 1;
            }else{
                key = 1;
            }
        }

        cv.put(AlarmSchema.COLUMN_ID, key);

        int hourOfDay, minuteOfDay;

        if(Build.VERSION.SDK_INT >= 23){
            hourOfDay = mTimePicker.getHour();
            minuteOfDay = mTimePicker.getMinute();
        }else{
            hourOfDay = mTimePicker.getCurrentHour();
            minuteOfDay = mTimePicker.getCurrentMinute();
        }

        int amOrPm = (hourOfDay < 12) ? 0 : 1; // 0 : AM , 1 : PM

        cv.put(AlarmSchema.COLUMN_AM_OR_PM, amOrPm); // AM or PM
        cv.put(AlarmSchema.COLUMN_HOUR, hourOfDay); // Hour
        cv.put(AlarmSchema.COLUMN_MINUTE, minuteOfDay); // Minute
        cv.put(AlarmSchema.COLUMN_ALARM_ID, 0); // Alarm ID
        cv.put(AlarmSchema.COLUMN_MEMO_CONTENT, mEditTextOfMemo.getText().toString()); // Memo Content

        cv.put(AlarmSchema.COLUMN_SUN, mToggleButton[0].isChecked());
        cv.put(AlarmSchema.COLUMN_MON, mToggleButton[1].isChecked());
        cv.put(AlarmSchema.COLUMN_TUE, mToggleButton[2].isChecked());
        cv.put(AlarmSchema.COLUMN_WED, mToggleButton[3].isChecked());
        cv.put(AlarmSchema.COLUMN_THU, mToggleButton[4].isChecked());
        cv.put(AlarmSchema.COLUMN_FRI, mToggleButton[5].isChecked());
        cv.put(AlarmSchema.COLUMN_SAT, mToggleButton[6].isChecked());

        cv.put(AlarmSchema.COLUMN_LAT, mLocation.getLatitude());
        cv.put(AlarmSchema.COLUMN_LNG, mLocation.getLongitude());

        if(!mModifyFlag){
            cv.put(AlarmSchema.COLUMN_STATE_FLAG, true); // State Flag's Default Value is true
        }

        return cv;
    }

    /**
     * '등록' 혹은 '수정' 시에 Alarmmanager를 등록한다.
     * @param cv : AlarmManager를 설정할 때 필요한 알람정보를 받는다.
     */
    private void registerAlarmManager(ContentValues cv){

        // Add this day of the week line to your existing code
        boolean[] weekday = {       false,                        // hasRepeatDay
                                     mToggleButton[0].isChecked(), // Sun
                                     mToggleButton[1].isChecked(), // Mon
                                     mToggleButton[2].isChecked(), // Tue
                                     mToggleButton[3].isChecked(), // Wed
                                     mToggleButton[4].isChecked(), // Thu
                                     mToggleButton[5].isChecked(), // Fri
                                     mToggleButton[6].isChecked()  // Sat
        };

        boolean hasRepeatDay = false;

        for(int i=0; i<DAY_COUNT; i++) {
            if(mToggleButton[i].isChecked()){
                hasRepeatDay = true;
            }
        }

        if(hasRepeatDay) {
            weekday[0] = true;
        }
        Intent receiverIntent = new Intent(getActivity(), AlarmBroadCastReceiver.class);
        receiverIntent.putExtra("weekday", weekday);
        receiverIntent.putExtra("id", mId);
        receiverIntent.putExtra("hour", cv.getAsInteger(AlarmSchema.COLUMN_HOUR));
        receiverIntent.putExtra("minute", cv.getAsInteger(AlarmSchema.COLUMN_MINUTE));

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(),
                cv.getAsInteger(AlarmSchema.COLUMN_ID),
                receiverIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManagerUtil.from(getActivity()).setAlarm(cv.getAsInteger(AlarmSchema.COLUMN_HOUR),
                                                      cv.getAsInteger(AlarmSchema.COLUMN_MINUTE),
                                                      pendingIntent,
                                                      weekday);
    }

    /**
     * 알람을 삭제 시에 AlarmManagere를 해지하기 위해 호출.
     */
    private void unregisterAlarmManager() {
        Intent receiverIntent = new Intent(getActivity(), AlarmBroadCastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(),
                Integer.valueOf(mId),
                receiverIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManagerUtil.from(getActivity()).unregisterAlarm(pendingIntent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }

    /**
     * '수정' 작업 시에 데이터를 Set하기 위해 AsyncTask 사용
     */
    private class AlarmSetTask extends AsyncTask<Long, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Long... params) {

            String id = getActivity().getIntent().getStringExtra("id");

            Uri uri = AlarmSchema.CONTENT_URI;
            uri = uri.buildUpon().appendPath(id).build();

            Cursor result = getActivity().getApplicationContext().
                            getContentResolver().query(uri,
                                                       null,
                                                       null,
                                                       null,
                                                       null);

            return result;
        }

        @Override
        protected void onPostExecute(Cursor result) {
            super.onPostExecute(result);

            if(result.moveToFirst()){

                int hourIndex = result.getColumnIndex(AlarmSchema.COLUMN_HOUR);
                int minuteIndex = result.getColumnIndex(AlarmSchema.COLUMN_MINUTE);
                int[] dayIndex = {result.getColumnIndex(AlarmSchema.COLUMN_SUN),
                        result.getColumnIndex(AlarmSchema.COLUMN_MON),
                        result.getColumnIndex(AlarmSchema.COLUMN_TUE),
                        result.getColumnIndex(AlarmSchema.COLUMN_WED),
                        result.getColumnIndex(AlarmSchema.COLUMN_THU),
                        result.getColumnIndex(AlarmSchema.COLUMN_FRI),
                        result.getColumnIndex(AlarmSchema.COLUMN_SAT)
                };
                int memoContentIndex = result.getColumnIndex(AlarmSchema.COLUMN_MEMO_CONTENT);
                int latIndex = result.getColumnIndex(AlarmSchema.COLUMN_LAT);
                int lngIndex = result.getColumnIndex(AlarmSchema.COLUMN_LNG);

                if(Build.VERSION.SDK_INT >= 23) {
                    mTimePicker.setHour(result.getInt(hourIndex));
                    mTimePicker.setMinute(result.getInt(minuteIndex));
                }else {
                    mTimePicker.setCurrentHour(result.getInt(hourIndex));
                    mTimePicker.setCurrentMinute(result.getInt(minuteIndex));
                }

                // 요일반복 토글버튼 SET
                for(int i=0; i<DAY_COUNT; i++) {
                    mToggleButton[i].setChecked(result.getString(dayIndex[i]).equals("true"));
                }

                mEditTextOfMemo.setText(result.getString(memoContentIndex));

                if (!result.isNull(latIndex) && !result.isNull(lngIndex)) {
                    mLocation = new Location("");
                    mLocation.setLatitude(result.getDouble(latIndex));
                    mLocation.setLongitude(result.getDouble(lngIndex));
                    mSupportMapFragment.getMapAsync(DetailFragment.this);
                }

            }
        }
    }
}
