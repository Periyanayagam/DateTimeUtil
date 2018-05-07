package com.perusudroid.datetimeutil;

import android.content.Context;

/**
 * Created by Intel on 07-03-2018.
 */

public class DateTimeHelper {

    private long minDate = 0,maxDate = 0;
    private long minMin = -1, maxMin = -1;
    private long minHrs= -1, maxHrs = -1;
    private int dateTask = 1;
    private int timeTask = 2;
    private DateUtil.DateTimeListener dateTimeListener;
    private Context mContext;
    private int who;

    public long getMinHrs() {
        return minHrs;
    }

    public void setMinHrs(long minHrs) {
        this.minHrs = minHrs;
    }

    public long getMaxHrs() {
        return maxHrs;
    }

    public void setMaxHrs(long maxHrs) {
        this.maxHrs = maxHrs;
    }

    public int getDateTask() {
        return dateTask;
    }

    public void setDateTask(int dateTask) {
        this.dateTask = dateTask;
    }

    public int getTimeTask() {
        return timeTask;
    }

    public void setTimeTask(int timeTask) {
        this.timeTask = timeTask;
    }

    public int getWho() {
        return who;
    }

    public void setWho(int who) {
        this.who = who;
    }

    public long getMinMin() {
        return minMin;
    }

    public void setMinMin(long minMin) {
        this.minMin = minMin;
    }

    public long getMaxMin() {
        return maxMin;
    }

    public void setMaxMin(long maxMin) {
        this.maxMin = maxMin;
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    public DateUtil.DateTimeListener getDateTimeListener() {
        return dateTimeListener;
    }

    public void setDateTimeListener(DateUtil.DateTimeListener dateTimeListener) {
        this.dateTimeListener = dateTimeListener;
    }

    public long getMinDate() {
        return minDate;
    }

    public void setMinDate(long minDate) {
        this.minDate = minDate;
    }

    public long getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(long maxDate) {
        this.maxDate = maxDate;
    }
}
