package com.mishraaditya.productclient;

import android.content.Context;

import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.Room;
import androidx.room.RoomDatabase;
@Database(entities = {CartProductModel.class},version = 1)
public abstract class CartDataBase extends RoomDatabase {

    public abstract CartDao cartDao();
    private static volatile CartDataBase INSTANCE;

    static CartDataBase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (CartDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    CartDataBase.class, "Cart_DB")
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
