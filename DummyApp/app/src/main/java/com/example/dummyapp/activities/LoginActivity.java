package com.example.dummyapp.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.dummyapp.R;
import com.example.dummyapp.databinding.ActivityLoginBinding;
import com.example.dummyapp.model.APIResponse;
import com.example.dummyapp.model.Login;
import com.example.dummyapp.viewmodels.DummyViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private DummyViewModel dmVM;
    private TextView forgot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        init();
        findViewById();
        onClickGroup();
    }

    void init() {
        dmVM = ViewModelProviders.of(this).get(DummyViewModel.class);
        dmVM.init();
    }

    void findViewById() {
        forgot = findViewById(R.id.forgot);
    }

    void onClickGroup() {
        binding.masukBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin();
            }
        });
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setCancelable(false);
                builder.setMessage("Untuk lupa password, segera hubungi call center BTPN di 1500300");
                builder.setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    void doLogin() {
        String username = binding.usernameLoginET.getText().toString();
        String password = binding.passwordLoginET.getText().toString();

        Login loginReq = new Login(username, password);
        dmVM.masuk(loginReq).observe(this, apiResponse -> {
            APIResponse response = apiResponse;
            while (response == null || response.equals("")) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (response.getResponse() == 200) {
                SharedPreferences sharedPreferences = getSharedPreferences("com.example.dummyapp.login", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("com.example.dummyapp.login", binding.usernameLoginET.getText().toString());
                editor.apply();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                new MaterialAlertDialogBuilder(this)
                        .setTitle("Gagal Login")
                        .setMessage("Username atau password salah")
                        .setNegativeButton("Tutup", null)
                        .show();
            }
        });
    }
}