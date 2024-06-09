package com.mishraaditya.productclient.Cleints;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserAPI {
    @FormUrlEncoded
    @POST("register.php")
    Call<RegisterResponse> register(
            @Field("username") String username,
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("login.php")
    Call<LoginResponse> login(
            @Field("email") String email,
            @Field("password") String password
    );

    @GET("fetchusers.php")
    Call<FetchUserResponse> fetchUsers();

    @FormUrlEncoded
    @POST("updateuser.php")
    Call<LoginResponse> updateUserAcc(
            @Field("id") int id,
            @Field("username") String username,
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("updatepassword.php")
    Call<RegisterResponse> updateUserPassword(
            @Field("email") String email,
            @Field("current") String currentPassword,
            @Field("new") String newPassword
    );

    @FormUrlEncoded
    @POST("deleteuser.php")
    Call<RegisterResponse> deleteUser(
            @Field("id") int userId
    );
}
