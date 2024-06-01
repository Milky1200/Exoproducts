package com.mishraaditya.productclient;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL="https://fakestoreapi.com/";
    private static Retrofit retrofit;
    private static RetrofitClient retrofitClient;

    public RetrofitClient() {
        retrofit=new Retrofit.Builder().baseUrl(BASE_URL).
                addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized RetrofitClient getInstance(){
        if (retrofitClient==null){
            return retrofitClient=new RetrofitClient();
        }
        return retrofitClient;
    }

    public API getApi(){
        return retrofit.create(API.class);
    }
}
