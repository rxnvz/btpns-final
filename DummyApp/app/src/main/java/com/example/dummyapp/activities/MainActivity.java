package com.example.dummyapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.dummyapp.R;
import com.example.dummyapp.databinding.ActivityMainBinding;
import com.example.dummyapp.model.APIResponse;
import com.example.dummyapp.viewmodels.DummyViewModel;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private DummyViewModel dmVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        init();
        onClickGroup();
    }

    void init() {
        dmVM = ViewModelProviders.of(this).get(DummyViewModel.class);
        dmVM.init();

        SharedPreferences sharedPreferences = getSharedPreferences("com.example.dummyapp.login", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("com.example.dummyapp.login", "");
        System.out.println("Username: " + username);

        dmVM.lihatSaldo(username).observe(this, new Observer<APIResponse>() {
            @Override
            public void onChanged(APIResponse apiResponse) {
                if (apiResponse.getResponse() == 200) {
                    DecimalFormat kurs = (DecimalFormat) DecimalFormat.getCurrencyInstance();
                    DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
                    formatRp.setCurrencySymbol("Rp. ");
                    formatRp.setMonetaryDecimalSeparator(',');
                    formatRp.setGroupingSeparator('.');
                    kurs.setDecimalFormatSymbols(formatRp);
                    Double saldo = Double.parseDouble(apiResponse.getMessage());
                    binding.balanceTV.setText(kurs.format(saldo));
                } else {
                    binding.balanceTV.setText("0");
                }
            }
        });
    }

    void onClickGroup() {
//        binding.exit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }
}