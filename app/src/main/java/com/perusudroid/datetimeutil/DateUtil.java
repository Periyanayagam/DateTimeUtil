package com.perusudroid.datetimeutil;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.DatePicker;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Intel on 17-02-2018.
 */

public class DateUtil {

    private static final String TAG = DateUtil.class.getSimpleName();
    private static Calendar calendar = Calendar.getInstance();

    private DateUtil(DateTimeHelper dateTimeHelper) {
        //calendar = Calendar.getInstance();
        showDatePickerDialog(dateTimeHelper);
        // crap(dateTimeHelper.getContext());
    }

    private static String getDateFormat(String inputPattern, String outputPattern, String date) {

        //String date = DateFormat.getDateInstance(DateFormat.MEDIUM).format(calendar.getTime());


        DateFormat inputFormatter1 = new SimpleDateFormat(inputPattern, Locale.getDefault());
        Date date1 = null;
        try {
            date1 = inputFormatter1.parse(date);
            DateFormat outputFormatter1 = new SimpleDateFormat(outputPattern, Locale.getDefault());
            return outputFormatter1.format(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;

    }

    public static Date getCurrentDateTime() {
        //Sun May 27 11:57:03 IST 2018
        return new Date();
    }

    public static String getCurrentDateTimeString(String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern, Locale.getDefault());
        return df.format(Calendar.getInstance().getTime());
    }

    public static Date getDateInFormat(String inputPattern, String outputPattern, String date) {
        return convertStringToDate(getDateFormat(inputPattern, outputPattern, date));
    }

    public static String getDateInFormatStr(String inputPattern, String outputPattern, String date) {
        return getDateFormat(inputPattern, outputPattern, date);
    }

    public static Calendar getCalendar() {
        if (calendar == null) {
            calendar = Calendar.getInstance();
        }
        return calendar;
    }

    private static DatePickerDialog getDatePickerDialog(Context context, DateTimeListener dateTimeListener, int who) {

        return new DatePickerDialog(context, new DateClass(dateTimeListener, who),
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }

    //-18
    private static Calendar getRestrictedCalendar(int restriction) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, restriction);
        return cal;
    }

    public static long getYearRestriction(int year) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, year);
        return cal.getTimeInMillis();
    }

    public static long restrictTillToday() {
        Date date = DateUtil.convertStringToDate(DateUtil.getCurrentDateTimeString(DateUtil.FORMATTER.DATABASE));
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.getTimeInMillis();
    }

    public static Date restrictTodayDate() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 1);
        return c.getTime();
    }

    public static String convertDateToString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(FORMATTER.DATABASE, Locale.getDefault());
        return format.format(date);
    }

    public static Date convertStringToDate(String date) {

        SimpleDateFormat format = new SimpleDateFormat(FORMATTER.DATABASE, Locale.getDefault());
        try {
            Log.d(TAG, "convertStringToDate: " + format.parse(date));
            return format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static long getDaysRestrictionx(int days, Date date) {
        Log.d(TAG, "getDaysRestrictionx: " + days + " date " + date);

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_WEEK, days);
        return c.getTimeInMillis();

    }

    /**
     * @param date - Pass date to which picker's maxDate restriction should be
     * @return
     */

    public static long getDaysRestrictionUptoDate(Date date) {
        //Log.d(TAG, "getDaysRestriction: " + days);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.getTimeInMillis();
    }

    public static long getDaysRestriction(int days) {
        //Log.d(TAG, "getDaysRestriction: " + days);

        if (days != 0) {
            Date today = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(today);
            c.add(Calendar.DAY_OF_WEEK, days);
            return c.getTimeInMillis();
        } else {
            return System.currentTimeMillis() - 1000;
        }
    }


    private void showDatePickerDialog(DateTimeHelper dateTimeHelper) {

        Date today = new Date();

        if (dateTimeHelper.getCurrentDate() == null || dateTimeHelper.getCurrentDate().toString().isEmpty()) {
            Log.d(TAG, "showDatePickerDialog: using default datetime");
            today = new Date();
        } else {
            Log.d(TAG, "showDatePickerDialog: using existing datetime");
/*
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);*/
            calendar.setTime(dateTimeHelper.getCurrentDate());
            //calendar.setTime();
            Log.d(TAG, "showDatePickerDialog: timex ->" + calendar.getTime());
            SimpleDateFormat dateFormat = new SimpleDateFormat(FORMATTER.DATABASE, Locale.getDefault());
            today = convertStringToDate(dateFormat.format(calendar.getTime()));
        }
        calendar.setTime(today);
        Log.d(TAG, "showDatePickerDialog: today " + today);

        //c.add(Calendar.DAY_OF_WEEK, +1);


        DatePickerDialog dpd = getDatePickerDialog(dateTimeHelper.getContext(), dateTimeHelper.getDateTimeListener(), dateTimeHelper.getWho());

        if ((dateTimeHelper.getMinDate() != 0)) {
            dpd.getDatePicker().setMinDate(dateTimeHelper.getMinDate());
        }

        if ((dateTimeHelper.getMaxDate() != 0)) {
            dpd.getDatePicker().setMaxDate(dateTimeHelper.getMaxDate());
        }
        dpd.show();
    }


    public static void normalDateDialog(Context context) {

        Log.d(TAG, "crap: ");

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

        DatePickerDialog dialog = new DatePickerDialog(context, date, calendar
                .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        Calendar minCal = Calendar.getInstance();
        minCal.set(Calendar.DAY_OF_WEEK, minCal.get(Calendar.DAY_OF_WEEK) + 1);

        Calendar maxCal = Calendar.getInstance();
        maxCal.set(Calendar.DAY_OF_WEEK, maxCal.get(Calendar.DAY_OF_WEEK) + 1);


        dialog.getDatePicker().setMinDate(minCal.getTimeInMillis());
        //dialog.getDatePicker().setMaxDate(maxCal.getTimeInMillis());

        dialog.show();

    }

    private static String getDateString(String strDate) {
        Date date = stringToDate(strDate);
        return getDateString(date);

    }

    private static String getDateString(Date date) {
        String stringDate;
        if (isToday(date)) {
            stringDate = "Today";
        } else if (isYesterday(date)) {
            stringDate = "Yesterday";
        } else {
            stringDate = String.valueOf(getDayNumber(date) + " " + getMonthString(date) + " " + getYear(date));
        }
        return stringDate;
    }

    private static String getDayName(Date date) {
        return (String) android.text.format.DateFormat.format("EEEE", date); // Thursday
    }

    private static int getDayNumber(Date date) {
        // String day = (String) DateFormat.format("dd", date); // 20
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        //Log.d("Utils", "getDayNumber: " + android.text.format.DateFormat.format("MMM", date));
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    private static String getMonthString(Date date) {
        return (String) android.text.format.DateFormat.format("MMM", date);
    }

    private static int getMonthNumber(Date date) {
        //String monthNumber = (String) DateFormat.format("MM", date); // 06
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH);
    }

    private static int getYear(Date date) {
        //  String year = (String) DateFormat.format("yyyy", date); // 2013
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    public static String getDateTimeString(String date) {
        try {
            return getDateString(date) + " " + getTimeString(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getTimeString(String strDate) {
        Date date = stringToDate(strDate);
        return getTimeString(date);
    }

    private static String getTimeString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm a", Locale.getDefault());
        String stringTime = sdf.format(date);
        stringTime = stringTime.replace(".m.", "m");
        return stringTime;
    }

    public static Date stringToDate(String time) {

        // this is for d
        if (time.length() == 10) time = time + " 00:00:00";

        SimpleDateFormat formatter = new SimpleDateFormat(FORMATTER.DATABASE, Locale.getDefault());
        Date createDate = null;
        try {
            createDate = formatter.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return createDate;
    }

    private static boolean isToday(Date date) {

        Calendar c1 = Calendar.getInstance(); // today
        c1.add(Calendar.DAY_OF_YEAR, 0); // today

        Calendar c2 = Calendar.getInstance();
        c2.setTime(date); // your date

        return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) && c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR);
    }

    private static boolean isYesterday(Date date) {

        Calendar c1 = Calendar.getInstance(); // today
        c1.add(Calendar.DAY_OF_YEAR, -1); // yesterday

        Calendar c2 = Calendar.getInstance();
        c2.setTime(date); // your date

        return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) && c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR);
    }

    public static int getNormalHrs(int hours) {
        return (hours == 0 || hours == 12) ? 12 : hours % 12;
    }

    public static long getDiffx(String dateStart, String dateStop) {
        Log.d(TAG, "getDiffx: dateStart " + dateStart + " dateStop " + dateStop);
        Calendar calFrom = Calendar.getInstance();
        Calendar calTo = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(FORMATTER.DATABASE, Locale.ENGLISH);
        try {
            calFrom.setTime(sdf.parse(dateStart));// all done
            calTo.setTime(sdf.parse(dateStop));// all done
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //int res = calFrom.getTimeInMillis() - calTo.getTimeInMillis();

        Log.d(TAG, "getDiffx: long " + String.valueOf(calFrom.getTimeInMillis() - calTo.getTimeInMillis()) +
                "int " + String.valueOf((int) calFrom.getTimeInMillis() - calTo.getTimeInMillis()));

        return calFrom.getTimeInMillis() - calTo.getTimeInMillis();
    }


    public static int getDiff(String dateStart, String dateStop) {

        Log.d(TAG, "getDiff: dateStart " + dateStart + " dateStop " + dateStop);

        //HH converts hour in 24 hours format (0-23), day calculation
        SimpleDateFormat format = new SimpleDateFormat(FORMATTER.DATE, Locale.getDefault());

        Date d1 = null;
        Date d2 = null;

        try {
            d1 = format.parse(dateStart);
            d2 = format.parse(dateStop);

            //in milliseconds
            long diff = d2.getTime() - d1.getTime();

            //long diffSeconds = diff / 1000 % 60;
            //long diffMinutes = diff / (60 * 1000) % 60;
            //long diffHours = diff / (60 * 60 * 1000) % 24;
            int diffDays = (int) diff / (24 * 60 * 60 * 1000);

            System.out.print(diffDays + " days, ");

            return diffDays;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    public interface FORMATTER {
        String TODAY = "TODAY";
        String DATABASE = "yyyy-MM-dd HH:mm:ss";
        String DATE_R = "yyyy-mm-dd";
        String DATE = "dd-MMM-yyyy";
        String TIME_STRING = "h:mm a";
        String RAILWAY_TIME = "H:mm";
        String NORMAL_TIME = "K:mm";
        String CALENDAR_TIME = "EEE MMM dd hh:mm:ss 'GMT'Z yyyy";
    }

    public interface DateTimeListener {

        void onDateSet(String date, int who);

        void onTimeSet(String time, int who);

    }

    private static class DateClass implements DatePickerDialog.OnDateSetListener {

        private DateTimeListener dateTimeListener;
        private int who;

        public DateClass(DateTimeListener dateTimeListener, int who) {
            this.dateTimeListener = dateTimeListener;
            this.who = who;
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            SimpleDateFormat dateFormatter = new SimpleDateFormat(FORMATTER.DATABASE, Locale.getDefault());
            Log.d(TAG, "onDateSet: " + calendar.getTime());
            calendar.setTime(calendar.getTime());
            dateTimeListener.onDateSet(dateFormatter.format(calendar.getTime()), who);

            /*String date = DateFormat.getDateInstance(DateFormat.SHORT).format(calendar.getTime());
            dateTimeListener.onDateSet(date, who);*/

        }
    }

    public static class Builder {

        private DateTimeHelper dateTimeHelper = new DateTimeHelper();

        public Builder(Context mContext) {
            dateTimeHelper.setContext(mContext);
        }

        @NonNull
        public DateUtil.Builder callback(DateTimeListener dateTimeListener) {
            dateTimeHelper.setDateTimeListener(dateTimeListener);
            return this;
        }

        @NonNull
        public DateUtil.Builder setMinTime(long time) {
            dateTimeHelper.setMinMin(time);
            return this;
        }

        @NonNull
        public DateUtil.Builder setMaxTime(long time) {
            dateTimeHelper.setMaxMin(time);
            return this;
        }

        @NonNull
        public DateUtil.Builder setWho(int who) {
            dateTimeHelper.setWho(who);
            return this;
        }

        @NonNull
        public DateUtil.Builder setCurrentDate(Date date) {
            if (date != null) {
                dateTimeHelper.setCurrentDate(date);
            }
            return this;
        }

        @NonNull
        public DateUtil.Builder setMax(long max) {
            dateTimeHelper.setMaxDate(max);
            return this;
        }

        @NonNull
        public DateUtil.Builder setMin(long min) {
            dateTimeHelper.setMinDate(min);
            return this;
        }

        public DateUtil build() {
            return new DateUtil(dateTimeHelper);
        }
    }

}
