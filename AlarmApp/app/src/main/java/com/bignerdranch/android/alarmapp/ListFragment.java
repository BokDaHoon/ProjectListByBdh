package com.bignerdranch.android.alarmapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.android.alarmapp.DB.AlarmSchema;
import com.bignerdranch.android.alarmapp.Utility.AlarmAdapter;

/**
 * Created by DaHoon on 2017-01-23.
 */

public class ListFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor>{

    private RecyclerView mAlarmList;
    private AlarmAdapter mAlarmAdapter;
    private FloatingActionButton mAddAlarm;

    private static final int TASK_LOADER_ID = 0;

    @Override
    public void onResume() {
        super.onResume();

        LoaderManager loaderManager = getActivity().getSupportLoaderManager();
        Loader<Cursor> loader = loaderManager.getLoader(TASK_LOADER_ID);
        if(loader == null){
            getActivity().getSupportLoaderManager().initLoader(TASK_LOADER_ID, null, this);
        }else{
            getActivity().getSupportLoaderManager().restartLoader(TASK_LOADER_ID, null, this);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.from(getContext()).inflate(R.layout.fragment_list, container, false);

        mAlarmList = (RecyclerView) view.findViewById(R.id.alarm_list);
        mAddAlarm = (FloatingActionButton) view.findViewById(R.id.add_alarm);
        mAlarmAdapter = new AlarmAdapter(getContext());

        mAddAlarm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = DetailActivity.newIntent(getContext());
                startActivity(intent);
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mAlarmList.setLayoutManager(layoutManager);

        updateUI();

        return view;
    }

    void updateUI(){
        mAlarmList.setAdapter(mAlarmAdapter);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Cursor>(getContext()) {
            Cursor mTaskData;

            @Override
            protected void onStartLoading() {
                if(mTaskData != null){
                    deliverResult(mTaskData);
                }else{
                    forceLoad();
                }
            }

            @Override
            public Cursor loadInBackground() {
                try{
                    return getActivity().getApplicationContext().
                            getContentResolver().query(AlarmSchema.CONTENT_URI,
                            null,
                            null,
                            null,
                            null);
                }catch(Exception e){
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public void deliverResult(Cursor data) {
                mTaskData = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAlarmAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {}
}
