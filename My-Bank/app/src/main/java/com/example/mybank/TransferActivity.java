package com.example.mybank;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class TransferActivity extends AppCompatActivity {

    private Button tfBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        findViewById();
        onClickGroup();
    }

    void findViewById() {
        tfBtn = findViewById(R.id.transferBtn);
    }

    void onClickGroup() {
        tfBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TransferActivity.this);
                builder.setTitle("PIN");
                builder.setMessage("Masukkan Pin anda:");

                final EditText inputPin = new EditText(TransferActivity.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                inputPin.setLayoutParams(lp);
                inputPin.setTransformationMethod(PasswordTransformationMethod.getInstance());
                builder.setView(inputPin);

                builder.setPositiveButton("Kirim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder sendTf = new AlertDialog.Builder(TransferActivity.this);
                        sendTf.setTitle("Transfer berhasil!");
                        sendTf.setMessage("Transfer berhasil. Silahkan kembali ke menu utama");
                        sendTf.setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //This should've back to main menu
                                finish();
                            }
                        });
                        AlertDialog alertTf = sendTf.create();
                        alertTf.show();
                    }
                });

                builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
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
}