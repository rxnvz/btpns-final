package com.example.mybank.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mybank.R;
import com.example.mybank.databinding.ActivityRegisterBinding;
import com.example.mybank.databinding.ActivityTransferBinding;
import com.example.mybank.model.APIResponse;
import com.example.mybank.model.Transfer;
import com.example.mybank.viewmodels.NasabahViewModel;

public class TransferActivity extends AppCompatActivity {
    private NasabahViewModel nbVM;
    private ActivityTransferBinding binding;

    private Button tfBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        binding = ActivityTransferBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        init();
        onClickGroup();
    }

    void init() {
        nbVM = ViewModelProviders.of(this).get(NasabahViewModel.class);
        nbVM.init();

    }

    void onClickGroup() {
        binding.transferBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doTransfer();
            }
        });
    }

    private void doTransfer() {
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.mybank.login", Context.MODE_PRIVATE);

        String username = sharedPreferences.getString("com.example.mybank.login", "");
        String kode_transaksi = binding.kodeBankTfET.getText().toString();
        String rekening_tujuan = binding.rekeningTfET.getText().toString();
        String trans_money = binding.uangTfET.getText().toString();
        int duit = Integer.parseInt(trans_money);

        if (kode_transaksi.equals("") || rekening_tujuan.equals("") || trans_money.equals("")) {
            Toast.makeText(getApplicationContext(), "Lengkapi data transfer!!", Toast.LENGTH_SHORT).show();
        } else {
            Transfer tf = new Transfer(username, kode_transaksi, rekening_tujuan, duit);
            nbVM.transferUang(tf).observe(this, tfResponse -> {
                Log.v("Transfer Response: ", tfResponse.getMessage());
                APIResponse response = tfResponse;
                if (response.getResponse() == 200) {
                    moveToHome("Sukses", "Transfer uang berhasil!", 200);
                }
            });
        }
    }

    void moveToHome(String status, String message, int code) {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("status", status);
        bundle.putString("message", message);
        bundle.putInt("code", code);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}