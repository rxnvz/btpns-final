package com.example.mybank.networking;

import com.example.mybank.model.Login;
import com.example.mybank.model.Nasabah;
import com.example.mybank.model.APIResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface NasabahAPI {
    @POST("/register")
    Call<APIResponse> newNasabah(@Body Nasabah body);

    @POST("/login")
    Call<APIResponse> doLogin(@Body Login body);

    @PUT("/logout")
    Call<APIResponse> doLogout();

    @GET("/home/{username}")
    Call<APIResponse> getSaldo(@Path("username")String checkSaldo);

    @POST("/transfer")
    Call<APIResponse> doTransfer();

    @POST("/mutasi")
    Call<APIResponse> doMutasi();
}
