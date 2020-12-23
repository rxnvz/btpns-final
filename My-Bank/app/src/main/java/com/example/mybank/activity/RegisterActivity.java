package com.example.mybank.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.mybank.R;
import com.example.mybank.databinding.ActivityRegisterBinding;
import com.example.mybank.model.APIResponse;
import com.example.mybank.model.Nasabah;
import com.example.mybank.repositories.NasabahRepository;
import com.example.mybank.viewmodels.NasabahViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RegisterActivity extends AppCompatActivity {
    private NasabahViewModel nbVM;
    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        init();
        onClickGroup();
    }

    public void init() {
        nbVM = ViewModelProviders.of(this).get(NasabahViewModel.class);
        nbVM.init();
    }

    void onClickGroup() {
        binding.regisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doRegister();
            }
        });
    }

    private void doRegister() {
        try {
            String nama = binding.namaRegisET.getText().toString();
            String nik = binding.nikRegisET.getText().toString();
            String lahir = binding.lahirRegisET.getText().toString();
            String gender = binding.genderRegisET.getText().toString();
            String telp = binding.telpRegisET.getText().toString();
            String alamat = binding.alamatRegisET.getText().toString();
            String email = binding.mailRegisET.getText().toString();
            String username = binding.unameRegisET.getText().toString();
            String password = binding.passwordRegisET.getText().toString();

            if (nama.equals("") || nik.equals("") || lahir.equals("") || gender.equals("") || telp.equals("") || alamat.equals("") || email.equals("") || username.equals("") || password.equals("")) {
                Toast.makeText(getApplicationContext(), "Lengkapi data anda!!", Toast.LENGTH_SHORT).show();
            } else {
                Nasabah nb = new Nasabah(nama, email, username, password, gender, nik, lahir, telp, alamat);
                nbVM.registrasi(nb).observe(this, nbResponse ->{
                    System.out.println("Registrasi response: " + nbResponse.getMessage());
                    APIResponse response = nbResponse;
                    if (response.getResponse() == 200) {
                        moveToLogin("Sukses", "Pendaftaran berhasil!", 200);
                    }
                });
            }
        } catch (Exception e) {
            System.out.println("ERROR DATE: " + e);;
        }

    }

    void moveToLogin(String status, String message, int code){
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("status", status);
        bundle.putString("message", message);
        bundle.putInt("code", code);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}