package com.example.mybank.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mybank.R;
import com.example.mybank.databinding.ActivityLoginBinding;
import com.example.mybank.model.APIResponse;
import com.example.mybank.model.Login;
import com.example.mybank.viewmodels.NasabahViewModel;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private NasabahViewModel nbVM;

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
        nbVM = ViewModelProviders.of(this).get(NasabahViewModel.class);
        nbVM.init();
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
        nbVM.masuk(loginReq).observe(this, apiResponse -> {
            APIResponse response = apiResponse;

            if (response.getResponse() == 200) {
                SharedPreferences sharedPreferences = getSharedPreferences("com.example.mybank.login", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("com.example.mybank.login", binding.usernameLoginET.getText().toString());
                editor.apply();
            }
        });
    }
}