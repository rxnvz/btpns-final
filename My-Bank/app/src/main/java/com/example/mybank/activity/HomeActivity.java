package com.example.mybank.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.mybank.R;
import com.example.mybank.databinding.ActivityHomeBinding;
import com.example.mybank.model.APIResponse;
import com.example.mybank.viewmodels.NasabahViewModel;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
    private NasabahViewModel nbVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        init();
        onCLickGroup();
    }

    void init() {
        nbVM = ViewModelProviders.of(this).get(NasabahViewModel.class);
        nbVM.init();

        SharedPreferences sharedPreferences = getSharedPreferences("com.example.mybank.login", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("com.example.mybank.login", "");
        System.out.println("Username: " + username);

        nbVM.lihatSaldo(username).observe(this, new Observer<APIResponse>() {
            @Override
            public void onChanged(APIResponse apiResponse) {
                DecimalFormat kurs = (DecimalFormat) DecimalFormat.getCurrencyInstance();
                DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
                formatRp.setCurrencySymbol("Rp. ");
                formatRp.setMonetaryDecimalSeparator(',');
                formatRp.setGroupingSeparator('.');
                kurs.setDecimalFormatSymbols(formatRp);
                Double saldo = Double.parseDouble(apiResponse.getMessage());
                binding.balanceTV.setText(kurs.format(saldo));
            }
        });
    }

    void onCLickGroup() {
        binding.transferCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TransferActivity.class);
                startActivity(intent);
            }
        });

        binding.mutasiCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MutationActivity.class);
                startActivity(intent);
            }
        });
    }
}