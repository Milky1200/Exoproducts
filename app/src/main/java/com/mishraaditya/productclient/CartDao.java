package com.mishraaditya.productclient;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import retrofit2.http.DELETE;

@Dao
public interface CartDao {

    @Insert
    Void AddToCart(CartProductModel cartProductModel);

    @Query("SELECT * From CartTable")
    List<CartProductModel> getAllProductFromCart();
    @Query("SELECT * FROM CartTable WHERE pid LIKE :id")
    CartProductModel getProductById(int id);
    @Query("UPDATE CartTable SET quantity=:currQuantity WHERE pid LIKE:id")
    void updateProductQuantity(int id,int currQuantity);
    @Query("DELETE FROM CartTable WHERE pid LIKE:id")
    void deleteProductFromCart(int id);
}
