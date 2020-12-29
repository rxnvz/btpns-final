package com.example.mybank.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mybank.R;
import com.example.mybank.databinding.ActivityLoginBinding;
import com.example.mybank.model.APIResponse;
import com.example.mybank.model.Login;
import com.example.mybank.viewmodels.NasabahViewModel;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    String TAG = LoginActivity.class.getSimpleName();
    String SITE_KEY = "6LfclRgaAAAAAHZ2jJLXMQsVhhDyJoGYnzr5hwoD";
    String SECRET_KEY = "6LfclRgaAAAAAFLpCPrRFXPD_ku5rNUddJ97e7jF";
    RequestQueue queue;

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
        queue = Volley.newRequestQueue(getApplicationContext());
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
//                doLogin();
                SafetyNet.getClient(LoginActivity.this).verifyWithRecaptcha(SITE_KEY)
                        .addOnSuccessListener(LoginActivity.this, new OnSuccessListener<SafetyNetApi.RecaptchaTokenResponse>() {
                            @Override
                            public void onSuccess(SafetyNetApi.RecaptchaTokenResponse response) {
                                if (!response.getTokenResult().isEmpty()) {
                                    handleSiteVerify(response.getTokenResult());
                                }
                            }
                        })
                        .addOnFailureListener(LoginActivity.this, new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                if (e instanceof ApiException) {
                                    ApiException apiException = (ApiException) e;
                                    Log.v(TAG, "Error message: " + CommonStatusCodes.getStatusCodeString(apiException.getStatusCode()));
                                } else {
                                    Log.v(TAG, "Unknown type of error: " + e.getMessage());
                                }
                            }
                        });
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

    protected  void handleSiteVerify(final String responseToken){
        //it is google recaptcha siteverify server
        //you can place your server url
        String url = "https://www.google.com/recaptcha/api/siteverify";
        StringRequest request = new StringRequest(com.android.volley.Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getBoolean("success")){
                                if(binding.usernameLoginET.getText().toString().equals("") || binding.passwordLoginET.getText().toString().equals("")){
                                    Toast.makeText(getApplicationContext(),"Masukkan username dan password!",Toast.LENGTH_LONG).show();
                                }else {
                                    doLogin();
                                }
                            }
                            else{
                                Toast.makeText(getApplicationContext(),String.valueOf(jsonObject.getString("error-codes")),Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception ex) {
                            Log.v(TAG, "JSON exception: " + ex.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v(TAG, "Error message: " + error.getMessage());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("secret", SECRET_KEY);
                params.put("response", responseToken);
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                1000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
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
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
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