package com.example.mybank.networking;

import com.example.mybank.model.Nasabah;
import com.example.mybank.model.SingleNasabah;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface NasabahAPI {
    @POST("register")
    Call<SingleNasabah> newNasabah(@Body Nasabah body);
}
