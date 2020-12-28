package com.example.mybank.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.mybank.model.APIResponse;
import com.example.mybank.model.History;
import com.example.mybank.model.Login;
import com.example.mybank.model.MutasiResponse;
import com.example.mybank.model.Nasabah;
import com.example.mybank.model.Transfer;
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

    public MutableLiveData<APIResponse> logout(String logout) {
        MutableLiveData<APIResponse> result = new MutableLiveData<>();
        nbAPI.doLogout(logout).enqueue(new Callback<APIResponse>() {
            @Override
            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                result.setValue(response.body());
                System.out.println("Isi response logout: " + response.body());
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

    public MutableLiveData<APIResponse> doTransfer(Transfer transfer) {
        String req = new Gson().toJson(transfer);
        System.out.println("Transfer Request: " + req);
        MutableLiveData<APIResponse> result = new MutableLiveData<>();
        nbAPI.doTransfer(transfer).enqueue(new Callback<APIResponse>() {

            @Override
            public void onResponse(Call<APIResponse> call, Response<APIResponse> responses) {
                if (responses.isSuccessful()) {
                    result.setValue(responses.body());
                    System.out.println("Isi response transfer: " + responses.body());
                }
            }

            @Override
            public void onFailure(Call<APIResponse> call, Throwable t) {
                System.out.println("Error transfer -- " + t.getMessage());
            }
        });
        return result;
    }

    public MutableLiveData<MutasiResponse> getMutasi(History hs) {
        String req = new Gson().toJson(hs);
        System.out.println("Mutasi Request: " + req);
        MutableLiveData<MutasiResponse> result = new MutableLiveData<>();
        nbAPI.doMutasi(hs).enqueue(new Callback<MutasiResponse>() {
            @Override
            public void onResponse(Call<MutasiResponse> call, Response<MutasiResponse> response) {
                result.setValue(response.body());
                System.out.println("Isi response mutasi: " + response.body());
            }

            @Override
            public void onFailure(Call<MutasiResponse> call, Throwable t) {
                System.out.println("Error get Mutasi! -- " + t.getMessage());
            }
        });
        return result;
    }
}
