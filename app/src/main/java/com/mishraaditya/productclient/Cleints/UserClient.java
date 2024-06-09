package com.mishraaditya.productclient.Cleints;

import com.mishraaditya.productclient.RetrofitClient;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserClient {
    public static String BASE_URL="http://192.168.1.37/userApi/";
    private static UserClient userClient;
    private static Retrofit retrofit2;


    private UserClient(){
        retrofit2=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    //Singleton Class to check whether object is created or not.

    public static synchronized UserClient getInstance(){

        if(userClient==null){
            return userClient=new UserClient();
        }
        return userClient;
    }

    public UserAPI getApi(){
        return retrofit2.create(UserAPI.class);
    }
}
