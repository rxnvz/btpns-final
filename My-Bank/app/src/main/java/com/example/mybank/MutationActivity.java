package com.example.mybank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class MutationActivity extends AppCompatActivity {
    private MaterialButton dateBtn;
    private TextView dateTV;

    long today;
    long monthBefore;
    private Pair<Long, Long> todayPair;
    private Pair<Long, Long> mothBeforePair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mutation);
        findViewById();
        getClearedUTC();
        initSetting();
        onGroupClick();
    }

    void findViewById() {
        dateBtn = findViewById(R.id.setDateBtn);
        dateTV = findViewById(R.id.dateTV);
    }

    private static Calendar getClearedUTC() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.clear();
        return calendar;
    }

    private void initSetting() {
        today = MaterialDatePicker.todayInUtcMilliseconds();

        Calendar calendar = getClearedUTC();
        calendar.roll(Calendar.MONTH,  -1);
        monthBefore = calendar.getTimeInMillis();

        todayPair = new Pair<>(today, today);
        mothBeforePair = new Pair<>(monthBefore, monthBefore);
    }

    void onGroupClick(){
        initSetting();

        MaterialDatePicker.Builder<Pair<Long, Long>> builderRange = MaterialDatePicker.Builder.dateRangePicker();
        builderRange.setCalendarConstraints(oneMonthBeforeTodayConstraints().build());
        MaterialDatePicker<Pair<Long, Long>> pickerRange = builderRange.build();

        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickerRange.show(getSupportFragmentManager(), pickerRange.toString());
            }
        });
        pickerRange.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
            @Override
            public void onPositiveButtonClick(Pair<Long,Long> selection) {
                SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd");
                String first = simpleFormat.format(selection.first);
                String last = simpleFormat.format(selection.second);
                dateTV.setText("Jangka waktu: " + first + " sampai " + last);
            }
        });
    }

    private CalendarConstraints.Builder oneMonthBeforeTodayConstraints() {
        CalendarConstraints.Builder constraintsBuilderRange = new CalendarConstraints.Builder();

        Calendar maxDate = Calendar.getInstance();
        Calendar minDate = Calendar.getInstance();
        minDate.add(Calendar.DAY_OF_MONTH, -30); // subtracting 30 days

        constraintsBuilderRange.setStart(minDate.getTimeInMillis());
        constraintsBuilderRange.setEnd(maxDate.getTimeInMillis());

        constraintsBuilderRange.setValidator(new RangeValidator(minDate.getTimeInMillis(), maxDate.getTimeInMillis()));

        return constraintsBuilderRange;
    }

    static class RangeValidator implements CalendarConstraints.DateValidator {

        long minDate, maxDate;

        RangeValidator(long minDate, long maxDate) {
            this.minDate = minDate;
            this.maxDate = maxDate;
        }
        RangeValidator(Parcel parcel) {
        }
        @Override
        public boolean isValid(long date) {
            return !(minDate > date || maxDate < date);
        }
        @Override
        public int describeContents() {
            return 0;
        }
        @Override
        public void writeToParcel(Parcel dest, int flags) {
        }

        public static final Parcelable.Creator<RangeValidator> CREATOR = new Parcelable.Creator<RangeValidator>() {

            @Override
            public RangeValidator createFromParcel(Parcel parcel) {
                return new RangeValidator(parcel);
            }

            @Override
            public RangeValidator[] newArray(int size) {
                return new RangeValidator[size];
            }
        };


    }
}