package com.example.mybank.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.mybank.R;
import com.example.mybank.adapter.MutasiAdapter;
import com.example.mybank.databinding.ActivityMutationBinding;
import com.example.mybank.model.History;
import com.example.mybank.model.Mutasi;
import com.example.mybank.model.MutasiResponse;
import com.example.mybank.viewmodels.NasabahViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class MutationActivity extends AppCompatActivity {
    private NasabahViewModel nbVM;
    private ActivityMutationBinding binding;

    private MutasiAdapter mutasiAdapter;
    private ArrayList<Mutasi> mutasis = new ArrayList<>();

    long today;
    long monthBefore;
    private Pair<Long, Long> todayPair;
    private Pair<Long, Long> mothBeforePair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mutation);
        binding = ActivityMutationBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        init();
        getClearedUTC();
        initSetting();
        onGroupClick();
    }

    void init() {
        nbVM = ViewModelProviders.of(this).get(NasabahViewModel.class);
        nbVM.init();
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

        binding.setDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickerRange.show(getSupportFragmentManager(), pickerRange.toString());
            }
        });
        pickerRange.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
            @Override
            public void onPositiveButtonClick(Pair<Long,Long> selection) {
                SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                simpleFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                String first = simpleFormat.format(selection.first);
                String last = simpleFormat.format(selection.second);
                binding.dateTV.setText("Jangka waktu: " + first + " --> " + last);
                getMutasi(first, last);
            }
        });
    }

    private void getMutasi(String first, String last) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            SharedPreferences sharedPreferences = getSharedPreferences("com.example.mybank.login", Context.MODE_PRIVATE);

            String username = sharedPreferences.getString("com.example.mybank.login", "");
            Date start = format.parse(first);
            Date end = format.parse(last);

            History hs = new History(username, first, last);
            nbVM.getMutasi(hs).observe(this, mutasiResponse -> {
                System.out.println("Mutation response: " + mutasiResponse.getMessage());
                MutasiResponse response = mutasiResponse;
                if (response.getResponse() == 200) {
                    if (mutasiAdapter == null) {
                        mutasiAdapter = new MutasiAdapter(MutationActivity.this, mutasis);
                        binding.mutasiRV.setLayoutManager(new LinearLayoutManager(this));
                        binding.mutasiRV.setAdapter(mutasiAdapter);
                        binding.mutasiRV.setItemAnimator(new DefaultItemAnimator());
                        binding.mutasiRV.setNestedScrollingEnabled(true);
                    } else {
                        mutasiAdapter.notifyDataSetChanged();
                    }
                    nbVM = ViewModelProviders.of(this).get(NasabahViewModel.class);
                    nbVM.init();
                    List<Mutasi> newMutasi = mutasiResponse.getData();
                    mutasis.addAll(newMutasi);
                    mutasiAdapter.notifyDataSetChanged();

                }
            });

        } catch (Exception e) {
            System.out.println("ERROR GET MUTASI: " + e);
        }
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