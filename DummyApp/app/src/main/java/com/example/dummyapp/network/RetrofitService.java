package com.example.dummyapp.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
//192.168.0.147
public class RetrofitService {
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8990/dum/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }
}
