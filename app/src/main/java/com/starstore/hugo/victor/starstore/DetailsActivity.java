package com.starstore.hugo.victor.starstore;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.starstore.hugo.victor.starstore.models.CartDB;
import com.starstore.hugo.victor.starstore.models.DataBase;

import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {
    // DECLARAÇÃO DE VARIÁVEIS
    private CartAdapter mCartAdapter;

    // BIND DOS ELEMENTOS
    @BindView(R.id.recycleProductsCart)
    RecyclerView recycleProductsCart;
    @BindView(R.id.pbListCart)
    ProgressBar pbListCart;
    @BindView(R.id.tvNotProducts)
    TextView tvNotProducts;
    @BindView(R.id.floatingPayment)
    FloatingActionButton floatingPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        // INICIALIZAÇÃO DO BUTTER KNIFE
        ButterKnife.bind(this);

    }

    @Override
    protected void onStart() {
        super.onStart();

        // INSTANCIANDO VARIÁVEIS
        showAllTask task = new showAllTask(this);
        CartDB[] result = new CartDB[0];

        // EXECUTANDO UMA ASYNCTASK E SALVANDO O RETORNO NUMA VARIÁVEL
        try {
            result = task.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (result.length > 0) {
            // INFLA O RECYCLERVIEW COM OS PRODUTOS
            mCartAdapter = new CartAdapter(this, result);
            recycleProductsCart.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            recycleProductsCart.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
            recycleProductsCart.setAdapter(mCartAdapter);

            // SETANDO COMO VISÍVEL OU INVISÍVEL OS ELEMENTOS
            pbListCart.setVisibility(View.GONE);
            recycleProductsCart.setVisibility(View.VISIBLE);
            floatingPayment.setVisibility(View.VISIBLE);
        } else {
            // SETANDO COMO VISÍVEL OU INVISÍVEL OS ELEMENTOS
            pbListCart.setVisibility(View.GONE);
            tvNotProducts.setVisibility(View.VISIBLE);
            recycleProductsCart.setVisibility(View.GONE);
            floatingPayment.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        // SETANDO COMO VISÍVEL OU INVISÍVEL OS ELEMENTOS
        pbListCart.setVisibility(View.VISIBLE);
        recycleProductsCart.setVisibility(View.GONE);
        floatingPayment.setVisibility(View.GONE);

    }

    // CLASSE QUE EXECUTA UMA ASYNCTASK
    public class showAllTask extends AsyncTask<Void, Void, CartDB[]> {
        private Context mContext;

        public showAllTask(Context context) {
            this.mContext = context;
        }

        @Override
        protected CartDB[] doInBackground(Void... voids) {
            // INICIALIZANDO UMA INSTANCIA DO BANCO DE DADOS E RETORNANDO TODAS OS PRODUTOS DO CARRINHO
            DataBase db = Room.databaseBuilder(mContext, DataBase.class, "cart").build();
            return db.cartDao().showCart();
        }

    }

    // CLASSE RESPONSÁVEL POR CHAMAR UMA OUTRA ACTIVITY
    public void openPayment(View v) {
        // INICIALIZANDO UMA INSTANCIA DE INTENT
        Intent intent = new Intent(this, PaymentActivity.class);

        // INICIALIZANDO A NOVA ACTIVITY
        startActivity(intent);
    }
}
