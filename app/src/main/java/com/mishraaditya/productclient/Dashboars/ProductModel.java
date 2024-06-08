package com.mishraaditya.productclient.Dashboars;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductModel {

    @SerializedName("id")
    int id;
    @SerializedName("title")
    String title;
    @SerializedName("description")
    String description;
    @SerializedName("category")
    String category;
    @SerializedName("price")
    double price;
    @SerializedName("brand")
    String brand;
    @SerializedName("warrantyInformation")
    String warranty;
    @SerializedName("thumbnail")
    String thumbnail;

    /*@SerializedName("images")
    List<String> prodImages;*/

    @SerializedName("stocks")
    int quantity;

    public ProductModel(int id, String title,
                        String description, String category, int price, String brand, String warranty, String thumbnail,int quantity) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.price = price;
        this.brand = brand;
        this.warranty = warranty;
        this.thumbnail = thumbnail;
        //this.prodImages = prodImages;
        this.quantity=quantity;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getWarranty() {
        return warranty;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    /*public List<String> getProdImages() {
        return prodImages;
    }

    public void setProdImages(List<String> prodImages) {
        this.prodImages = prodImages;
    }*/
}
