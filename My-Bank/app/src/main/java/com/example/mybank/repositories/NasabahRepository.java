package com.example.mybank.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.mybank.model.APIResponse;
import com.example.mybank.model.Login;
import com.example.mybank.model.Nasabah;
import com.example.mybank.networking.NasabahAPI;
import com.example.mybank.networking.RetrofitService;
import com.google.gson.Gson;
import com.google.gson.internal.bind.util.ISO8601Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NasabahRepository {
    public static NasabahRepository nbRepo;

    public static NasabahRepository getInstance() {
        if (nbRepo == null) {
            nbRepo = new NasabahRepository();
        }
        return nbRepo;
    }

    private NasabahAPI nbAPI;
    public NasabahRepository() {
        nbAPI = RetrofitService.createService(NasabahAPI.class);
    }

    public MutableLiveData<APIResponse> doRegister(Nasabah nb) {
        String req = new Gson().toJson(nb);
        System.out.println("Register request: " + req);
        MutableLiveData<APIResponse> result = new MutableLiveData<>();
        nbAPI.newNasabah(nb).enqueue(new Callback<APIResponse>() {

            @Override
            public void onResponse(Call<APIResponse> call, Response<APIResponse> responses) {
                if (responses.isSuccessful()) {
                    result.setValue(responses.body());
                    System.out.println("Isi response register: " + responses.body());
                }
            }

            @Override
            public void onFailure(Call<APIResponse> call, Throwable t) {
                System.out.println("Error register");
                System.out.println("Isian error regis: " + t.getMessage());
            }
        });
        return result;
    }

    public MutableLiveData<APIResponse> login(Login login) {
        MutableLiveData<APIResponse> result = new MutableLiveData<>();
        nbAPI.doLogin(login).enqueue(new Callback<APIResponse>() {
            @Override
            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                result.setValue(response.body());
                System.out.println("Isi response login: " + response.body());
            }

            @Override
            public void onFailure(Call<APIResponse> call, Throwable t) {
                result.setValue(null);
                System.out.println("Isian error Login: " + t.getMessage());
            }
        });
        return result;
    }

    public MutableLiveData<APIResponse> getSaldo(String s) {
        MutableLiveData<APIResponse> checkSaldo = new MutableLiveData<>();
        nbAPI.getSaldo(s).enqueue(new Callback<APIResponse>() {
            @Override
            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                checkSaldo.setValue(response.body());
                System.out.println("Berhasil uhuy");
            }

            @Override
            public void onFailure(Call<APIResponse> call, Throwable t) {
                checkSaldo.setValue(null);
                System.out.println("Isian error cek saldo: " + t.getMessage());
            }
        });
        return checkSaldo;
    }
}
