package com.mishraaditya.productclient;

import retrofit2.Call;
import retrofit2.http.GET;

public interface API {
    @GET("products")
    Call<ProductResponse> getProducts();
}