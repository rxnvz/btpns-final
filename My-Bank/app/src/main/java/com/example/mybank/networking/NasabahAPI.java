package com.example.mybank.networking;

import com.example.mybank.model.History;
import com.example.mybank.model.Login;
import com.example.mybank.model.MutasiResponse;
import com.example.mybank.model.Nasabah;
import com.example.mybank.model.APIResponse;
import com.example.mybank.model.Transfer;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface NasabahAPI {
    @POST("/register")
    Call<APIResponse> newNasabah(@Body Nasabah body);

    @PUT("/login")
    Call<APIResponse> doLogin(@Body Login body);

    @PUT("/logout/{username}")
    Call<APIResponse> doLogout(@Path("username") String username);

    @GET("/home/{username}")
    Call<APIResponse> getSaldo(@Path("username")String checkSaldo);

    @POST("/transfer")
    Call<APIResponse> doTransfer(@Body Transfer body);

    @POST("/mutasi")
    Call<MutasiResponse> doMutasi(@Body History body);
}
