package com.bignerdranch.android.alarmapp;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.tsengvn.typekit.TypekitContextWrapper;

/**
 * Created by DaHoon on 2017-01-23.
 */

public class DetailActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, DetailActivity.class);
        return intent;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    @Override
    protected Fragment createFragment() {
        return new DetailFragment();
    }
}
