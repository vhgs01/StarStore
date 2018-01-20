package com.starstore.hugo.victor.starstore.models;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

/**
 * Created by Victor Hugo on 18/01/2018.
 */

@Dao
public interface CartDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCart(CartDB cartDB);

    @Update(onConflict = OnConflictStrategy.ROLLBACK)
    void updateProductCart(CartDB cartDB);

    @Delete
    void deleteProductCart(CartDB cartDB);

    @Query("SELECT * FROM cart")
    CartDB[] showCart();

    @Query("SELECT * from cart WHERE productName = :name")
    CartDB showProductCartByName(String name);

    @Query("DELETE FROM cart")
    void deleteAll();

}
