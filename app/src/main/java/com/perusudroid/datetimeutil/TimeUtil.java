package com.perusudroid.datetimeutil;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

/**
 * Created by Intel on 07-05-2018.
 */

public class TimeUtil {

    private static final String TAG = TimeUtil.class.getSimpleName();
    private static Calendar calendar;
    private static DateTimeHelper dateTimeHelper;
    private static Context mContext;
    private static AlertDialog alertDialog;

    private TimeUtil(DateTimeHelper dateTimeHelperx) {
        dateTimeHelper = dateTimeHelperx;
        calendar = Calendar.getInstance();
        showTimePickerDialog(dateTimeHelper.getContext(), dateTimeHelper.getDateTimeListener(), dateTimeHelper.getWho());
        // crap(dateTimeHelper.getContext());
    }

    private static void showTimePickerDialog(final Context context, final DateUtil.DateTimeListener dateTimeListener, final int who) {

        calendar = Calendar.getInstance();
        mContext = context;

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setPositiveButton("Set", null);
        dialogBuilder.setNegativeButton("Close", null);
        alertDialog = dialogBuilder.create();
        View mView = LayoutInflater.from(context).inflate(R.layout.date_time_dialog, null);
        final TimePicker tp = mView.findViewById(R.id.TimePicker);
        tp.setOnTimeChangedListener(
                new TimePicker.OnTimeChangedListener() {
                    @Override
                    public void onTimeChanged(TimePicker timePicker, int hourOfDay, int minute) {
                        Log.d(TAG, "onTimeChanged: hourOfDay " + hourOfDay);
                        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), hourOfDay, minute);
                    }
                }
        );
        alertDialog.setView(mView);
        alertDialog.show();

        Button positiveButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isValidTime(DateUtil.getNormalHrs(tp.getCurrentHour()), tp.getCurrentMinute())) {
                    calendar.set(Calendar.HOUR_OF_DAY, tp.getCurrentHour());
                    calendar.set(Calendar.MINUTE, tp.getCurrentMinute());
                    String time = DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime());
                    Log.d(TAG, "onTimeSet: time " + time);
                    alertDialog.dismiss();
                    if (dateTimeListener != null) {
                        dateTimeListener.onTimeSet(time, who);
                    }
                }
            }
        });

        Button negativeButton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

    }


    private static boolean isValidTime(int hourOfDay, int minute) {

        Log.d(TAG, "isValidTime: hourOfDay " + hourOfDay + " minute " + minute);

        if (mContext == null) {
            return false;
        }

        if (dateTimeHelper.getMinMin() != -1 && minute < dateTimeHelper.getMinMin()) {
            Toast.makeText(mContext, "Minute cant be less than minMinute -> " + dateTimeHelper.getMinMin(), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (dateTimeHelper.getMaxMin() != -1 && minute > dateTimeHelper.getMaxMin()) {
            Toast.makeText(mContext, "Minute cant be greater than maxMinute -> " + dateTimeHelper.getMaxMin(), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (dateTimeHelper.getMinHrs() != -1 && hourOfDay < dateTimeHelper.getMinHrs()) {
            Toast.makeText(mContext, "Hour cant be less than minHour " + dateTimeHelper.getMinHrs(), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (dateTimeHelper.getMaxHrs() != -1 && hourOfDay > dateTimeHelper.getMaxHrs()) {
            Toast.makeText(mContext, "Hour cant be greater than maxHour -> " + dateTimeHelper.getMaxHrs(), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    public static class Builder {

        private DateTimeHelper dateTimeHelper = new DateTimeHelper();

        public Builder(Context mContext) {
            dateTimeHelper.setContext(mContext);
        }

        @NonNull
        public TimeUtil.Builder callback(DateUtil.DateTimeListener dateTimeListener) {
            dateTimeHelper.setDateTimeListener(dateTimeListener);
            return this;
        }

        @NonNull
        public TimeUtil.Builder setWho(int who) {
            dateTimeHelper.setWho(who);
            return this;
        }

        @NonNull
        public TimeUtil.Builder setMinMin(long time) {
            if (time > 0 && time <= 60) {
                dateTimeHelper.setMinMin(time);
            }
            return this;
        }

        @NonNull
        public TimeUtil.Builder setMaxMin(long time) {
            if (time > 0 && time <= 60) {
                dateTimeHelper.setMaxMin(time);
            }
            return this;
        }

        @NonNull
        public TimeUtil.Builder setMinHrs(long time) {
            if (time > 0 && time <= 12) {
                dateTimeHelper.setMinHrs(time);
            }
            return this;
        }

        @NonNull
        public TimeUtil.Builder setMaxHrs(long time) {
            if (time > 0 && time <= 12) {
                dateTimeHelper.setMaxHrs(time);
            }
            return this;
        }


        public TimeUtil build() {
            return new TimeUtil(dateTimeHelper);
        }
    }


}
