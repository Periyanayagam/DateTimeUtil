package com.perusudroid.datetimeutil;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TimeActivity extends AppCompatActivity {

    private static final String TAG = TimeActivity.class.getSimpleName();
    private static int minHour = 11;
    private static int minMinute = 47;
    private static int maxHour = 11;
    private static int maxMinute = 50;
    private AlertDialog alertDialog;
    // Calendar reference
    private Calendar mCalendar;

    private TimePickerDialog.OnTimeSetListener mOnTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
            Log.d(TAG, "onTimeSet: hourOfDay " + hourOfDay + " minute " + minute);

            if (isValidTime(hourOfDay, minute)) {
                mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                mCalendar.set(Calendar.MINUTE, minute);
                String time = DateFormat.getTimeInstance(DateFormat.SHORT).format(mCalendar.getTime());
                Log.d(TAG, "onTimeSet: time " + time);
                alertDialog.dismiss();
            }

        }
    };

    private boolean isValidTime(int hourOfDay, int minute) {

        if (minHour != -1 && hourOfDay < minHour) {
            Toast.makeText(TimeActivity.this, "Hour cant be less than minHour", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (minMinute != -1 && minute < minMinute) {
            Toast.makeText(TimeActivity.this, "minute cant be less than minMinute", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (maxHour != -1 && hourOfDay < maxHour) {
            Toast.makeText(TimeActivity.this, "Hour cant be less than maxHour", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (maxMinute != -1 && minute > maxMinute) {
            Toast.makeText(TimeActivity.this, "minute cant be greater than maxMinute", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_time);
        findViewById(R.id.datepicker).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        buttonClicked();
                    }
                }
        );

    }

    public void buttonClicked() {
        mCalendar = Calendar.getInstance();
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setPositiveButton("Set", null);
        dialogBuilder.setNegativeButton("Close", null);
        alertDialog = dialogBuilder.create();
        View mView = getLayoutInflater().inflate(R.layout.date_time_dialog, null);
        final TimePicker tp = mView.findViewById(R.id.TimePicker);
        tp.setOnTimeChangedListener(
                new TimePicker.OnTimeChangedListener() {
                    @Override
                    public void onTimeChanged(TimePicker timePicker, int hourOfDay, int minute) {
                        Log.d(TAG, "onTimeChanged: hourOfDay " + hourOfDay);
                        mCalendar.set(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH), hourOfDay, minute);
                    }
                }
        );
        alertDialog.setView(mView);
        alertDialog.show();

        Button positiveButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        // override the text color of positive button
        positiveButton.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
        // provides custom implementation to positive button click
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnTimeSetListener.onTimeSet(tp, tp.getCurrentHour(), tp.getCurrentMinute());
            }
        });

        Button negativeButton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        // override the text color of negative button
        negativeButton.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        // provides custom implementation to negative button click
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });


    }
}
