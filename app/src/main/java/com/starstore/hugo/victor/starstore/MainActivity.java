package com.starstore.hugo.victor.starstore;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

    private static final String TAG = "Victor Hugo --> ";
    private static final int PERMISSAO_SD_CARD = 1;
    private ProductAdapter mProductAdapter;

    //    Bind dos elementos usando o Butter Knife
    @BindView(R.id.recycleProducts)
    RecyclerView recycleProducts;
    @BindView(R.id.pbList)
    ProgressBar pbList;
    @BindView(R.id.tvNotProducts)
    TextView tvNotProducts;
    @BindView(R.id.floatingCart)
    FloatingActionButton floatingCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Inicializar o Butter Knife
        ButterKnife.bind(this);

//        Método para trazer os produtos da API
        getProducts(this);
    }

    public void getProducts(final Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(StoneService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        StoneService service = retrofit.create(StoneService.class);
        Call<List<ProductsCatalog>> requestProduct = service.listProducts();

        requestProduct.enqueue(new Callback<List<ProductsCatalog>>() {
            @Override
            public void onResponse(Call<List<ProductsCatalog>> call, Response<List<ProductsCatalog>> response) {
                if (!response.isSuccessful()) {
//                     Remove o carregando e exibe a lista de produtos
                    pbList.setVisibility(View.GONE);
                    tvNotProducts.setVisibility(View.VISIBLE);

                } else {
                    List<ProductsCatalog> catalog = response.body();

//                  Infla o RecyclerView com os produtos
                    mProductAdapter = new ProductAdapter(context, catalog);
                    recycleProducts.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                    recycleProducts.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
                    recycleProducts.setAdapter(mProductAdapter);

//                  Remove o carregando e exibe a lista de produtos
                    pbList.setVisibility(View.GONE);
                    recycleProducts.setVisibility(View.VISIBLE);
                    floatingCart.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<ProductsCatalog>> call, Throwable t) {
                Log.e(TAG, "Erro> " + t.getMessage());

//                Remove o carregando e exibe a lista de produtos
                pbList.setVisibility(View.GONE);
                tvNotProducts.setVisibility(View.VISIBLE);
            }
        });
    }

    public void openCart(View v) {
        Intent itCart = new Intent(this, DetailsActivity.class);
        startActivity(itCart);
    }
}
