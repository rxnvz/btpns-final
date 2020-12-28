package com.example.dummyapp.network;

import com.example.dummyapp.model.APIResponse;
import com.example.dummyapp.model.Login;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface DummyAPI {
    @PUT("login")
    Call<APIResponse> doLogin(@Body Login body);

    @GET("/saldo/{rekening}")
    Call<APIResponse> getSaldo(@Path("rekening") String rekening);

    @PUT("/logout/{username}")
    Call<APIResponse> doLogout(@Path("username") String username);
}
