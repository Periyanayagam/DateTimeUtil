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
    private static Calendar calendar;

    private DateUtil(DateTimeHelper dateTimeHelper) {
        calendar = Calendar.getInstance();
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

    public static String getDateInFormat(String inputPattern, String outputPattern, String date) {
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

    public static Date restrictTodayDate() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 1);
        return c.getTime();
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
        //minCal.set(Calendar.DAY_OF_WEEK, minCal.get(Calendar.DAY_OF_WEEK) + 1);

        Calendar maxCal = Calendar.getInstance();
        //maxCal.set(Calendar.DAY_OF_WEEK, maxCal.get(Calendar.DAY_OF_WEEK) + 1);


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

    private static Date stringToDate(String time) {

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

    private void showDatePickerDialog(DateTimeHelper dateTimeHelper) {

        calendar = Calendar.getInstance();

        DatePickerDialog dpd = getDatePickerDialog(dateTimeHelper.getContext(), dateTimeHelper.getDateTimeListener(), dateTimeHelper.getWho());

        //getMinDate 0 maxDate 6105290
        //Log.d("DateUtil", "showDatePickerDialog: getMinDate " + dateTimeHelper.getMinDate() + " maxDate " + dateTimeHelper.getMaxDate());

       /* Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, dateTimeHelper.getMaxDate());*/

        Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.DAY_OF_WEEK, +1);

        if ((dateTimeHelper.getMinDate() != 0)) {
            dpd.getDatePicker().setMinDate(dateTimeHelper.getMinDate());
        }

        Log.d("DateUtil", "showDatePickerDialog: " + c.getTimeInMillis());

        if ((dateTimeHelper.getMaxDate() != 0)) {
            dpd.getDatePicker().setMaxDate(dateTimeHelper.getMaxDate());
        }
        dpd.show();
    }

    public interface FORMATTER {
        String TODAY = "TODAY";
        String DATABASE = "yyyy-MM-dd HH:mm:ss";
        String DATE_R = "yyyy-mm-dd";
        String DATE = "dd-MMM-yyyy";
        String TIME_STRING = "h:mm a";
        String RAILWAY_TIME = "H:mm";
        String NORMAL_TIME = "K:mm";
    }

    public static int getNormalHrs(int hrs){
        return hrs%12;
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
            calendar.set(year, monthOfYear, dayOfMonth);

            SimpleDateFormat dateFormatter = new SimpleDateFormat(FORMATTER.DATABASE, Locale.getDefault());
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
