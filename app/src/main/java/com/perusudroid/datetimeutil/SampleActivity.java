package com.perusudroid.datetimeutil;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

import java.text.DateFormat;
import java.util.Calendar;

public class SampleActivity extends AppCompatActivity {

    private static final String TAG = SampleActivity.class.getSimpleName();
    private static Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
    }

    public static void normalDateDialog(Context context) {

        DatePickerDialog.OnDateSetListener date;

        calendar = Calendar.getInstance();

        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String date = DateFormat.getDateInstance(DateFormat.MEDIUM).format(calendar.getTime());

                Log.d(TAG, "onDateSet: date " + date);

            }

        };
/*
        DatePickerDialog dialog = new DatePickerDialog(context, date, calendar
                .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));*/

        DatePickerDialog dialog = new DatePickerDialog(context, date, 2019,
                calendar.get(Calendar.MONTH),
                21);

        Calendar minCal = Calendar.getInstance();
        minCal.set(Calendar.DAY_OF_WEEK, minCal.get(Calendar.DAY_OF_WEEK) + 1);

        Calendar maxCal = Calendar.getInstance();
        maxCal.set(Calendar.DAY_OF_WEEK, maxCal.get(Calendar.DAY_OF_WEEK) + 1);


        //dialog.getDatePicker().setMinDate(minCal.getTimeInMillis());
        //dialog.getDatePicker().setMaxDate(maxCal.getTimeInMillis());

        dialog.show();

    }

    public void showClicked(View view) {
        normalDateDialog(this);
    }
}
