package com.mishraaditya.productclient;

import com.google.gson.annotations.SerializedName;

public class ProductModel {
    @SerializedName("id")
    int id;
    @SerializedName("title")
    String title;
    @SerializedName("image")
    String prodImage;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getProdImage() {
        return prodImage;
    }
}
