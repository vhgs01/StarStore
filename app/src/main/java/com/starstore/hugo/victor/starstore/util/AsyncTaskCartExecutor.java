package com.starstore.hugo.victor.starstore.util;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import com.starstore.hugo.victor.starstore.data.models.CartDB;
import com.starstore.hugo.victor.starstore.data.models.DataBase;

/**
 * Created by Victor Hugo on 19/01/2018.
 */

// CLASSE QUE EXECUTA ALGUMAS FUNÇÕES DO BANCO DE DADOS ASSINCRONAMENTE
public class AsyncTaskCartExecutor extends AsyncTask<Void, Void, Integer> {
    // DECLARAÇÃO DE VARIÁVEIS
    private Context mContext;
    private CartDB mCartDB;
    private TextView tvQuantidade;
    private String mMethod;

    public AsyncTaskCartExecutor(Context context, CartDB cartDB, TextView tvQuantidade, String method) {
        this.mContext = context;
        this.mCartDB = cartDB;
        this.tvQuantidade = tvQuantidade;
        this.mMethod = method;
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        // INSTANCIANDO VARIÁVEIS
        CartDB product;

        // INICIALIZANDO UMA INSTANCIA DO BANCO DE DADOS
        DataBase db = Room.databaseBuilder(mContext, DataBase.class, "cart").build();

        // PARA CADA MÉTODO EXECUTA UMA AÇÃO DIFERENTE NO BANCO E RETORNA OS DADOS
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
        // SETANDO UM VALOR NO TEXT VIEW
        tvQuantidade.setText(Integer.toString(value));
    }

}
