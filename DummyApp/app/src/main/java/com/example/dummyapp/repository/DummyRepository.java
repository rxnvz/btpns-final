package com.example.dummyapp.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.dummyapp.model.APIResponse;
import com.example.dummyapp.model.Login;
import com.example.dummyapp.network.DummyAPI;
import com.example.dummyapp.network.RetrofitService;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DummyRepository {
    public static DummyRepository dmRepo;

    public static DummyRepository getInstance() {
        if (dmRepo == null) {
            dmRepo = new DummyRepository();
        }
        return dmRepo;
    }

    private DummyAPI dmAPI;
    public DummyRepository() {
        dmAPI = RetrofitService.createService(DummyAPI.class);
    }

    public MutableLiveData<APIResponse> login(Login login){
        MutableLiveData<APIResponse> result = new MutableLiveData<>();

        try {
            APIResponse res = new APIResponse();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username",login.getUsername());
            jsonObject.put("password",login.getPassword());
            AndroidNetworking.put("http://10.0.2.2:8990/dum/login")
                    .addJSONObjectBody(jsonObject)
                    .setTag("login")
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                res.setStatus(response.getString("status"));
                                res.setMessage(response.getString("message"));
                                res.setResponse(response.getInt("response"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            result.setValue(res);
                        }

                        @Override
                        public void onError(ANError anError) {
                            Log.v("error:","Error Hit Api login");
                            anError.printStackTrace();
                            result.setValue(null);
                        }
                    });
        } catch (Exception e){
            result.setValue(null);
            Log.v("error:","Error Login");
            e.printStackTrace();
        }
        return result;
    }

    public MutableLiveData<APIResponse> logout(String s) {
        MutableLiveData<APIResponse> result = new MutableLiveData<>();
        APIResponse res = new APIResponse();
        AndroidNetworking.put("http://10.0.2.2:8990/dum/logout/{username}")
                .addPathParameter("username", s)
                .setTag("logout")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.v("isires:", response.toString());
                        try {
                            res.setResponse(response.getInt("response"));
                            res.setMessage(response.getString("message"));
                            res.setStatus(response.getString("status"));
                        } catch (Exception e) {
                            Log.v("error", "Error Logout: ");
                            e.printStackTrace();
                        }
                        result.setValue(res);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.v("error", "Error API");
                        anError.printStackTrace();
                    }
                });
        return result;
    }

//    public MutableLiveData<APIResponse> login (Login login) {
//        MutableLiveData<APIResponse> result = new MutableLiveData<>();
//        dmAPI.doLogin(login).enqueue(new Callback<APIResponse>() {
//            @Override
//            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
//                result.setValue(response.body());
//                System.out.println("Isi response login: " + response.body());
//            }
//
//            @Override
//            public void onFailure(Call<APIResponse> call, Throwable t) {
////                result.setValue(null);
//                System.out.println("Isian error Login: " + t.getMessage());
//            }
//        });
//        return result;
//    }

    public MutableLiveData<APIResponse> getSaldo(String s) {
        MutableLiveData<APIResponse> checkSaldo = new MutableLiveData<>();
        APIResponse res = new APIResponse();
        AndroidNetworking.get("http://10.0.2.2:8990/dum/saldo/{rekening}")
                .addPathParameter("rekening", s)
                .setTag("getSaldo")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.v("isires:", response.toString());
                        try {
                            res.setResponse(response.getInt("response"));
                            res.setMessage(response.getString("message"));
                            res.setStatus(response.getString("status"));
                        } catch (Exception e) {
                            Log.v("error", "Error Get Saldo: ");
                            e.printStackTrace();
                        }
                        checkSaldo.setValue(res);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.v("error", "Error API");
                        anError.printStackTrace();
                    }
                });
        return checkSaldo;
    }
}
