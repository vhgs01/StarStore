package com.starstore.hugo.victor.starstore;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.starstore.hugo.victor.starstore.models.ProductsCatalog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    // DECLARAÇÃO DE VARIÁVEIS
    private ProductAdapter mProductAdapter;

    // BIND DOS ELEMENTOS
    @BindView(R.id.recycleProducts)
    RecyclerView rvRecycleProducts;
    @BindView(R.id.pbList)
    ProgressBar pbList;
    @BindView(R.id.tvNotProducts)
    TextView tvNotProducts;
    @BindView(R.id.floatingCart)
    FloatingActionButton fabFloatingCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // INICIALIZAÇÃO DO BUTTER KNIFE
        ButterKnife.bind(this);

    }

    @Override
    protected void onStart() {
        super.onStart();

        // MÉTODO PARA TRAZER OS PRODUTOS DA API
        getProducts(this);
    }

    @Override
    protected void onStop() {
        super.onStop();

        // SETANDO COMO VISÍVEL OU INVISÍVEL OS ELEMENTOS
        pbList.setVisibility(View.VISIBLE);
        rvRecycleProducts.setVisibility(View.GONE);
        fabFloatingCart.setVisibility(View.GONE);

    }

    // CLASSE RESPONSÁVEL POR TRAZER OS PRODUTOS DA API E INFLAR O RECYCLERVIEW
    public void getProducts(final Context context) {

        // CRIA UMA INSTANCIA DO RETROFIT
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(StoneService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        StoneService service = retrofit.create(StoneService.class);
        Call<List<ProductsCatalog>> requestProduct = service.listProducts();

        // PARA CADA ELEMENTO QUE TROUXER DA API ELE INFLA O RECYCLERVIEW
        requestProduct.enqueue(new Callback<List<ProductsCatalog>>() {
            @Override
            public void onResponse(Call<List<ProductsCatalog>> call, Response<List<ProductsCatalog>> response) {

                if (!response.isSuccessful()) {
                    // SETANDO COMO VISÍVEL OU INVISÍVEL OS ELEMENTOS
                    pbList.setVisibility(View.GONE);
                    tvNotProducts.setVisibility(View.VISIBLE);

                } else {
                    // CRIA UMA LISTA DE PRODUCTSCATALOG COM O RESPONSE DO REQUEST
                    List<ProductsCatalog> catalog = response.body();

                    // INFLA O RECYCLERVIEW COM OS PRODUTOS
                    mProductAdapter = new ProductAdapter(context, catalog);
                    rvRecycleProducts.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                    rvRecycleProducts.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
                    rvRecycleProducts.setAdapter(mProductAdapter);

                    // SETANDO COMO VISÍVEL OU INVISÍVEL OS ELEMENTOS
                    pbList.setVisibility(View.GONE);
                    rvRecycleProducts.setVisibility(View.VISIBLE);
                    fabFloatingCart.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<ProductsCatalog>> call, Throwable t) {
                // SETANDO COMO VISÍVEL OU INVISÍVEL OS ELEMENTOS
                pbList.setVisibility(View.GONE);
                tvNotProducts.setVisibility(View.VISIBLE);
            }
        });
    }

    // CLASSE RESPONSÁVEL POR CHAMAR UMA OUTRA ACTIVITY
    public void openCart(View v) {
        // INICIALIZANDO UMA INSTANCIA DE INTENT
        Intent itCart = new Intent(this, DetailsActivity.class);

        // INICIALIZANDO A NOVA ACTIVITY
        startActivity(itCart);
    }
}
