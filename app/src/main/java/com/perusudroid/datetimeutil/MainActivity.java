package com.perusudroid.datetimeutil;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, DateUtil.DateTimeListener {

    private static final String TAG = "DatePicker";
    private Button btnShow;
    private RadioGroup rGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnShow = findViewById(R.id.btnShow);
        rGroup = findViewById(R.id.rGroup);
        btnShow.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (rGroup.getCheckedRadioButtonId()) {
            case R.id.rdoOne:
                new DateUtil.Builder(this)
                        .callback(this)
                        .setWho(1) // switch between who for different requests
                        .setMin(DateUtil.getDaysRestriction(0)) // disable all previous dates
                        .build();
                break;
            case R.id.rdoOnex:
                new DateUtil.Builder(this)
                        .callback(this)
                        .setWho(2)
                        .setMax(DateUtil.getDaysRestriction(0)) // disable all next dates
                        .build();
                break;
            case R.id.rdoTwo:
                new DateUtil.Builder(this)
                        .callback(this)
                        .setWho(3)
                        .setMin(DateUtil.getDaysRestriction(-2)) //  enable all upcoming dates + previous 2 date
                        .build();
                break;
            case R.id.rdoTwox:
                new DateUtil.Builder(this)
                        .callback(this)
                        .setWho(4)
                        .setMin(DateUtil.getDaysRestriction(-2)) // enable previous 2 dates
                        .setMax(DateUtil.getDaysRestriction(0)) // disable all upcoming dates
                        .build();
                break;
            case R.id.rdoThree:
                new DateUtil.Builder(this)
                        .callback(this)
                        .setWho(5)
                        .setMax(DateUtil.getDaysRestriction(+2)) // current date + 2 next date
                        .build();
                break;
            case R.id.rdoThreex:
                new DateUtil.Builder(this)
                        .callback(this)
                        .setWho(6)
                        .setMin(DateUtil.getDaysRestriction(0)) //disable all previous dates
                        .setMax(DateUtil.getDaysRestriction(+2)) // current date + 2 next date
                        .build();
                break;
            case R.id.rdoFour:
                new DateUtil.Builder(this)
                        .callback(this)
                        .setWho(7)
                        .setMin(DateUtil.getDaysRestriction(0)) // disable previous dates
                        .setMax(DateUtil.getDaysRestriction(0)) //disable upcoming dates (Enables only today)
                        .build();
                break;
            case R.id.rdoFourx:
                new DateUtil.Builder(this)
                        .callback(this)
                        .setWho(8)
                        .setMin(DateUtil.restrictTodayDate().getTime()) //Disables today
                        .build();
                break;
            case R.id.rdoFive:
                new DateUtil.Builder(this)
                        .callback(this)
                        .setWho(9)
                        .setMin(DateUtil.getDaysRestriction(-1))  // previous one day
                        .setMax(DateUtil.getDaysRestriction(+1)) // upcoming one day + current date
                        .build();
                break;
            case R.id.rdoYear1:
                new DateUtil.Builder(this)
                        .callback(this)
                        .setWho(10)
                        .setMin(DateUtil.getYearRestriction(-5)) //current year - 5
                        .build();
                break;
            case R.id.rdoYear2:
                new DateUtil.Builder(this)
                        .callback(this)
                        .setWho(11)
                        .setMax(DateUtil.getYearRestriction(5)) // current year + 5
                        .build();
                break;

            case R.id.rdoTime:
                // time selection between 4.15 - 5.20
                new TimeUtil.Builder(this)
                        .callback(this)
                        .setWho(1)
                        .setMinMin(1) // Minimum minutes
                        .setMaxMin(58) // Maximum minutes
                        .setMinHrs(-1) // Min Hrs for both AM and PM
                        .setMaxHrs(13) //Max Hrs for both AM and PM
                        .build();
                break;
        }
    }

    @Override
    public void onDateSet(String date, int who) {

        switch (who){
            case 1:
                break;
        }

        Log.d(TAG, "onDateSet 1: date " + date + " who " + who);
        Log.d(TAG, "onDateSet 2: " + DateUtil.getDateTimeString(date));
        Log.d(TAG, "onDateSet 3: " + DateUtil.getDateInFormat(DateUtil.FORMATTER.DATABASE, DateUtil.FORMATTER.DATABASE, date));
        Log.d(TAG, "onDateSet 4: " + DateUtil.getDateInFormat(DateUtil.FORMATTER.DATABASE, DateUtil.FORMATTER.DATE, date));


    }

    @Override
    public void onTimeSet(String time, int who) {
        Log.d(TAG, "onTimeSet: time "+ time);

    }
}
