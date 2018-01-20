package com.starstore.hugo.victor.starstore.utils;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import com.starstore.hugo.victor.starstore.models.CartDB;
import com.starstore.hugo.victor.starstore.models.DataBase;

/**
 * Created by Victor Hugo on 19/01/2018.
 */

public class AsyncTaskExecutor extends AsyncTask<Void, Void, Integer> {

    private Context mContext;
    private CartDB mCartDB;
    private TextView tvQuantidade;
    private String mMethod;

    public AsyncTaskExecutor(Context context, CartDB cartDB, TextView tvQuantidade, String method) {
        this.mContext = context;
        this.mCartDB = cartDB;
        this.tvQuantidade = tvQuantidade;
        this.mMethod = method;
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        CartDB product;

        DataBase db = Room.databaseBuilder(mContext, DataBase.class, "cart").build();

        switch (mMethod) {
            case "insert":
                db.cartDao().insertCart(mCartDB);
                product = db.cartDao().showProductCartByName(mCartDB.getProductName());
                return product.getProductQtd();
            case "update":
                db.cartDao().updateProductCart(mCartDB);
                product = db.cartDao().showProductCartByName(mCartDB.getProductName());
                return product.getProductQtd();
            case "delete":
                db.cartDao().deleteProductCart(mCartDB);
                return 0;
            case "showByName":
                CartDB result = db.cartDao().showProductCartByName(mCartDB.getProductName());
                if (result != null) {
                    return result.getProductQtd();
                } else {
                    return 0;
                }
            default:
                return 0;
        }

    }

    @Override
    protected void onPostExecute(Integer value) {
        super.onPostExecute(value);
        tvQuantidade.setText(Integer.toString(value));
    }

}
