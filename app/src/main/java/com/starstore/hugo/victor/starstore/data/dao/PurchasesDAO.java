package com.starstore.hugo.victor.starstore.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.starstore.hugo.victor.starstore.data.models.PurchasesDB;

/**
 * Created by Victor Hugo on 21/01/2018.
 */

@Dao
public interface PurchasesDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPurchase(PurchasesDB purchasesDB);

    @Query("SELECT * FROM purchases")
    PurchasesDB[] showAllPurchases();

    @Query("DELETE FROM purchases")
    void deleteAllPurchases();

}
