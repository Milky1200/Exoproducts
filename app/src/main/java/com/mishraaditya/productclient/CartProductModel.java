package com.mishraaditya.productclient;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "CartTable")
public class CartProductModel implements Parcelable {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "pid")
    private int id;

    @ColumnInfo(name="title")
    private String title;

    @ColumnInfo(name="description")
    private String description ;
    @ColumnInfo(name="category")
    private String category;
    @ColumnInfo(name="price")
    private double price ;
    @ColumnInfo(name="brand")
    private String brand;
    @ColumnInfo(name="warranty")
    private String warranty;
    @ColumnInfo(name="thumbnail")
    private String thumbnail;

    @ColumnInfo(name="quantity")
    private int quantity;

    public CartProductModel(int id, String title, String description, String category, double price, String brand, String warranty, String thumbnail, int quantity) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.price = price;
        this.brand = brand;
        this.warranty = warranty;
        this.thumbnail = thumbnail;

        this.quantity = quantity;
    }

    public CartProductModel(ProductModel productModel){
        this.id = productModel.getId();
        this.title = productModel.getTitle();
        this.description = productModel.getDescription();;
        this.category = productModel.getCategory();;
        this.price = productModel.getPrice();
        this.brand = productModel.getBrand();
        this.warranty = productModel.getWarranty();
        this.thumbnail = productModel.getThumbnail();
        this.quantity = 0;
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

    public void setPrice(double price) {
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


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "CartProductModel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", brand='" + brand + '\'' +
                ", warranty='" + warranty + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", quantity=" + quantity +
                '}';
    }

    protected CartProductModel(Parcel in) {
        id=in.readInt();
        title = in.readString();
        description = in.readString();
        category = in.readString();
        price = in.readDouble();
        brand = in.readString();
        warranty = in.readString();
        thumbnail = in.readString();
        quantity = in.readInt();
    }

    public static final Creator<CartProductModel> CREATOR = new Creator<CartProductModel>() {
        @Override
        public CartProductModel createFromParcel(Parcel in) {
            return new CartProductModel(in);
        }

        @Override
        public CartProductModel[] newArray(int size) {
            return new CartProductModel[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(category);
        dest.writeDouble(price);
        dest.writeString(brand);
        dest.writeString(warranty);
        dest.writeString(thumbnail);
        dest.writeInt(quantity);
    }
}


