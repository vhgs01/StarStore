package com.starstore.hugo.victor.starstore.util;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;

import com.starstore.hugo.victor.starstore.data.models.DataBase;
import com.starstore.hugo.victor.starstore.data.models.PurchasesDB;

/**
 * Created by Victor Hugo on 21/01/2018.
 */

// CLASSE QUE EXECUTA ALGUMAS FUNÇÕES DO BANCO DE DADOS ASSINCRONAMENTE
public class AsyncTaskPurchaseExecutor extends AsyncTask<Void, Void, Boolean> {
    private Context mContext;
    private PurchasesDB mPurchaseDB;
    private String method;

    public AsyncTaskPurchaseExecutor(Context mContext, PurchasesDB mPurchaseDB, String method) {
        this.mContext = mContext;
        this.mPurchaseDB = mPurchaseDB;
        this.method = method;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        DataBase dbPurchase = Room.databaseBuilder(mContext, DataBase.class, "purchases").build();
        DataBase dbCart = Room.databaseBuilder(mContext, DataBase.class, "cart").build();

        switch (method) {
            case "insert":
                dbPurchase.purchasesDAO().insertPurchase(mPurchaseDB);
                dbCart.cartDao().deleteAll();
                return true;
        }
        return true;
    }
}
