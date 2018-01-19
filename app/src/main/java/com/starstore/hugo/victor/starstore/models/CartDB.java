package com.starstore.hugo.victor.starstore.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by Victor Hugo on 18/01/2018.
 */

@Entity(tableName = "cart")
public class CartDB {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "productName")
    private String productName;

    @ColumnInfo(name = "productPrice")
    private Double productPrice;

    @ColumnInfo(name = "productQtd")
    private Integer productQtd;

    public CartDB() {
    }

    public CartDB(String productName, Double productPrice, Integer productQtd) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQtd = productQtd;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getProductQtd() {
        return productQtd;
    }

    public void setProductQtd(Integer productQtd) {
        this.productQtd = productQtd;
    }
}
