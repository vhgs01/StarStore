package com.starstore.hugo.victor.starstore.activity;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.starstore.hugo.victor.starstore.R;
import com.starstore.hugo.victor.starstore.adapter.PurchaseAdapter;
import com.starstore.hugo.victor.starstore.data.models.DataBase;
import com.starstore.hugo.victor.starstore.data.models.PurchasesDB;

import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PurchasesActivity extends AppCompatActivity {
    // DECLARAÇÃO DE VARIÁVEIS
    private PurchaseAdapter mPurchaseAdapter;

    // BIND DOS ELEMENTOS
    @BindView(R.id.recyclePurchasesCart)
    RecyclerView recyclePurchasesCart;
    @BindView(R.id.pbListPurchases)
    ProgressBar pbListPurchases;
    @BindView(R.id.tvNotPurchases)
    TextView tvNotPurchases;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchases);

        // INICIALIZAÇÃO DO BUTTER KNIFE
        ButterKnife.bind(this);

    }

    @Override
    protected void onStart() {
        super.onStart();

        // INSTANCIANDO VARIÁVEIS
        showAllPurchases task = new showAllPurchases(this);
        PurchasesDB[] result = new PurchasesDB[0];

        // EXECUTANDO UMA ASYNCTASK E SALVANDO O RETORNO NUMA VARIÁVEL
        try {
            result = task.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (result.length > 0) {
            // INFLA O RECYCLERVIEW COM AS COMPRAS
            mPurchaseAdapter = new PurchaseAdapter(this, result);
            recyclePurchasesCart.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            recyclePurchasesCart.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
            recyclePurchasesCart.setAdapter(mPurchaseAdapter);

            // SETANDO COMO VISÍVEL OU INVISÍVEL OS ELEMENTOS
            pbListPurchases.setVisibility(View.GONE);
            recyclePurchasesCart.setVisibility(View.VISIBLE);
        } else {
            // SETANDO COMO VISÍVEL OU INVISÍVEL OS ELEMENTOS
            pbListPurchases.setVisibility(View.GONE);
            tvNotPurchases.setVisibility(View.VISIBLE);
            recyclePurchasesCart.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        // SETANDO COMO VISÍVEL OU INVISÍVEL OS ELEMENTOS
        pbListPurchases.setVisibility(View.VISIBLE);
        recyclePurchasesCart.setVisibility(View.GONE);

    }

    // CLASSE QUE EXECUTA UMA ASYNCTASK
    public class showAllPurchases extends AsyncTask<Void, Void, PurchasesDB[]> {
        private Context mContext;

        public showAllPurchases(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        protected PurchasesDB[] doInBackground(Void... voids) {
            // INICIALIZANDO UMA INSTANCIA DO BANCO DE DADOS E RETORNANDO TODAS AS COMPRAS
            DataBase db = Room.databaseBuilder(mContext, DataBase.class, "purchases").build();
            return db.purchasesDAO().showAllPurchases();
        }
    }
}
