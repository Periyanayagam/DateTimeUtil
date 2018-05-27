package com.perusudroid.datetimeutil;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, DateUtil.DateTimeListener {

    private static final String TAG = "DatePicker";
    private Button btnShow;
    private RadioGroup rGroup;
    private String selectedDate;
    private RadioButton rdoFrom,rdoTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnShow = findViewById(R.id.btnShow);
        rGroup = findViewById(R.id.rGroup);
        rdoFrom = findViewById(R.id.rdoFrom);
        rdoTo = findViewById(R.id.rdoTo);
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

            case R.id.rdoFrom:
                new DateUtil.Builder(this)
                        .callback(this)
                        .setWho(12)
                        .setCurrentDate(getCurrentDate(1))
                        .setMax(DateUtil.restrictTillToday())
                        .build();
                break;
            case R.id.rdoTo:

                if (selectedDate != null) {
                    Log.d(TAG, "onClick: selectedDate " + selectedDate);
                    new DateUtil.Builder(this)
                            .callback(this)
                            .setWho(13)
                            .setCurrentDate(getCurrentDate(2))
                            .setMin(DateUtil.getDaysRestrictionx(0, DateUtil.convertStringToDate(DateUtil.convertDateToString(getCurrentDate(1)))))
                            .setMax(DateUtil.restrictTillToday()) // disable all next dates
                            .build();
                }else{
                    Toast.makeText(this, "Select from date first", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private Date getCurrentDate(int i) {

        if(i == 1){
            if (!rdoFrom.getText().toString().isEmpty()) {
                return DateUtil.getDateInFormat(DateUtil.FORMATTER.DATE, DateUtil.FORMATTER.DATABASE, rdoFrom.getText().toString().trim());
            }else{
                return null;
            }
        }

        if (i == 2) {
            if (!rdoTo.getText().toString().isEmpty()) {
                return DateUtil.getDateInFormat(DateUtil.FORMATTER.DATE, DateUtil.FORMATTER.DATABASE, rdoTo.getText().toString().trim());
            }else{
                return null;
            }
        }

        return null;
    }

    @Override
    public void onDateSet(String date, int who) {

        switch (who) {
            case 12:
                rdoFrom.setText(DateUtil.getDateInFormatStr(DateUtil.FORMATTER.DATABASE, DateUtil.FORMATTER.DATE, date));
                break;
            case 13:
                rdoTo.setText(DateUtil.getDateInFormatStr(DateUtil.FORMATTER.DATABASE, DateUtil.FORMATTER.DATE, date));
                break;
        }


        Log.d(TAG, "onDateSet 1: date " + date + " who " + who);
        Log.d(TAG, "onDateSet 2: " + DateUtil.getDateTimeString(date));
        Log.d(TAG, "onDateSet 3: " + DateUtil.getDateInFormatStr(DateUtil.FORMATTER.DATABASE, DateUtil.FORMATTER.DATABASE, date));
        Log.d(TAG, "onDateSet 4: " + DateUtil.getDateInFormatStr(DateUtil.FORMATTER.DATABASE, DateUtil.FORMATTER.DATE, date));

        this.selectedDate = DateUtil.getDateInFormatStr(DateUtil.FORMATTER.DATABASE, DateUtil.FORMATTER.DATE, date);

    }

    @Override
    public void onTimeSet(String time, int who) {
        Log.d(TAG, "onTimeSet: time " + time);

    }
}
