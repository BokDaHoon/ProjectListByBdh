package com.bignerdranch.android.alarmapp.Utility;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TimePicker;

import com.bignerdranch.android.alarmapp.R;

import java.lang.reflect.Field;

/**
 * Created by DaHoon on 2017-01-23.
 */

public class CustomTimePicker extends TimePicker {

    private final int m_iColor = 0xff2196F3;
    NumberPicker mClsAmPm;
    NumberPicker mClsHour;
    NumberPicker mClsMin;


    public CustomTimePicker(Context context) {
        super(context);
        create(context, null);
    }

    public CustomTimePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        create(context, attrs);
    }

    public CustomTimePicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        create(context, attrs);
    }

    private void create(Context clsContext, AttributeSet attrs) {
        try {
            Class<?> clsParent = Class.forName("com.android.internal.R$id");
            mClsAmPm = (NumberPicker) findViewById(clsParent.getField("amPm").getInt(null));
            mClsHour = (NumberPicker) findViewById(clsParent.getField("hour").getInt(null));
            mClsMin = (NumberPicker) findViewById(clsParent.getField("minute").getInt(null));

            Class<?> clsNumberPicker = Class.forName("android.widget.NumberPicker");
            Field clsSelectionDivider = clsNumberPicker.getDeclaredField("mSelectionDivider");

            clsSelectionDivider.setAccessible(true);
            ColorDrawable clsDrawable = new ColorDrawable(m_iColor);

            clsSelectionDivider.set(mClsAmPm, clsDrawable);
            clsSelectionDivider.set(mClsHour, clsDrawable);
            clsSelectionDivider.set(mClsMin, clsDrawable);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
