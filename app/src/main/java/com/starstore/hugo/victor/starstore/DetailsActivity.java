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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.starstore.hugo.victor.starstore.models.CartDB;
import com.starstore.hugo.victor.starstore.models.DataBase;

import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {
    private CartAdapter mCartAdapter;

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

        ButterKnife.bind(this);

        showAllTask task = new showAllTask(this);
        CartDB[] result = new CartDB[0];

        try {
            result = task.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

//                  Remove o carregando e exibe a lista de produtos
        pbListCart.setVisibility(View.GONE);

        if (result.length > 0) {
//                  Infla o RecyclerView com os produtos
            mCartAdapter = new CartAdapter(this, result);
            recycleProductsCart.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            recycleProductsCart.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
            recycleProductsCart.setAdapter(mCartAdapter);

            recycleProductsCart.setVisibility(View.VISIBLE);
            floatingPayment.setVisibility(View.VISIBLE);
        } else {
            tvNotProducts.setVisibility(View.VISIBLE);
        }

    }

    public class showAllTask extends AsyncTask<Void, Void, CartDB[]> {
        private Context mContext;

        public showAllTask(Context context) {
            this.mContext = context;
        }

        @Override
        protected CartDB[] doInBackground(Void... voids) {
            DataBase db = Room.databaseBuilder(mContext, DataBase.class, "cart").fallbackToDestructiveMigration().build();
//            db.cartDao().deleteAll();
            return db.cartDao().showCart();
        }

    }

    public void openPayment(View v) {
        Intent intent = new Intent(this, PaymentActivity.class);
        startActivity(intent);
    }
}
