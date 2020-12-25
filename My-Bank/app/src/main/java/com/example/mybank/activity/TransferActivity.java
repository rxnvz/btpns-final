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
//        findViewById();
//        onClickGroup();
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
            System.out.println("Isi username: " + username);
            System.out.println("Isi kode transaksi: " + kode_transaksi);
            System.out.println("Isi rekening tujuan: " + rekening_tujuan);
            System.out.println("Isi transfered money: " + trans_money);
            System.out.println("Isi duit: " + duit);

            Transfer tf = new Transfer(username, kode_transaksi, rekening_tujuan, duit);
            nbVM.transferUang(tf).observe(this, tfResponse -> {
                System.out.println("Transfer Response: " + tfResponse.getMessage());
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

//    void findViewById() {
//        tfBtn = findViewById(R.id.transferBtn);
//    }
//
//    void onClickGroup() {
//        tfBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(TransferActivity.this);
//                builder.setTitle("PIN");
//                builder.setMessage("Masukkan Pin anda:");
//
//                final EditText inputPin = new EditText(TransferActivity.this);
//                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
//                        LinearLayout.LayoutParams.MATCH_PARENT,
//                        LinearLayout.LayoutParams.MATCH_PARENT);
//                inputPin.setLayoutParams(lp);
//                inputPin.setTransformationMethod(PasswordTransformationMethod.getInstance());
//                builder.setView(inputPin);
//
//                builder.setPositiveButton("Kirim", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        AlertDialog.Builder sendTf = new AlertDialog.Builder(TransferActivity.this);
//                        sendTf.setTitle("Transfer berhasil!");
//                        sendTf.setMessage("Transfer berhasil. Silahkan kembali ke menu utama");
//                        sendTf.setPositiveButton("Oke", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                //This should've back to main menu
//                                finish();
//                            }
//                        });
//                        AlertDialog alertTf = sendTf.create();
//                        alertTf.show();
//                    }
//                });
//
//                builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                });
//
//                AlertDialog alert = builder.create();
//                alert.show();
//            }
//        });
//    }
}