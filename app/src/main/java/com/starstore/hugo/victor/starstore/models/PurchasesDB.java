package com.starstore.hugo.victor.starstore.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by Victor Hugo on 21/01/2018.
 */

@Entity(tableName = "purchases")
public class PurchasesDB {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    @ColumnInfo(name = "purchaseCardName")
    private String purchaseCardName;

    @ColumnInfo(name = "purchasePrice")
    private Double purchasePrice;

    @ColumnInfo(name = "purchaseCardLastDigits")
    private Integer purchaseCardLastDigits;

    @ColumnInfo(name = "purchaseCreated")
    private String purchaseCreated;

    public PurchasesDB(){}

    public PurchasesDB(@NonNull int id, String purchaseCardName, Double purchasePrice, Integer purchaseCardLastDigits, String purchaseCreated) {
        this.id = id;
        this.purchaseCardName = purchaseCardName;
        this.purchasePrice = purchasePrice;
        this.purchaseCardLastDigits = purchaseCardLastDigits;
        this.purchaseCreated = purchaseCreated;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public String getPurchaseCardName() {
        return purchaseCardName;
    }

    public void setPurchaseCardName(String purchaseCardName) {
        this.purchaseCardName = purchaseCardName;
    }

    public Double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Integer getPurchaseCardLastDigits() {
        return purchaseCardLastDigits;
    }

    public void setPurchaseCardLastDigits(Integer purchaseCardLastDigits) {
        this.purchaseCardLastDigits = purchaseCardLastDigits;
    }

    public String getPurchaseCreated() {
        return purchaseCreated;
    }

    public void setPurchaseCreated(String purchaseCreated) {
        this.purchaseCreated = purchaseCreated;
    }

}
