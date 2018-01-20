package com.starstore.hugo.victor.starstore;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.starstore.hugo.victor.starstore.models.CartDB;
import com.starstore.hugo.victor.starstore.models.ProductsCatalog;
import com.starstore.hugo.victor.starstore.utils.Util;
import com.starstore.hugo.victor.starstore.utils.AsyncTaskExecutor;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Victor Hugo on 17/01/2018.
 */

public class ProductAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<ProductsCatalog> mProducts;

    public ProductAdapter(Context context, List<ProductsCatalog> products) {
        this.mContext = context;
        this.mProducts = products;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        ProductHolder holder = null;

        //Infla a view que irá aparecer na lista
        view = LayoutInflater.from(mContext).inflate(R.layout.main_line_view, parent, false);

        //Cria o viewholder
        holder = new ProductHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //Obtem o holder
        ProductHolder holderP = (ProductHolder) holder;
        ProductsCatalog product = mProducts.get(position);

        //Aqui você usa pra preencher dados na View
        Picasso.with(mContext).load(Uri.parse(product.thumbnailHd)).fit().into(holderP.ciImg_produto);
        holderP.ciImg_produto.setContentDescription(product.thumbnailHd);
        holderP.tvNome_produto.setText(product.title);
        holderP.tvVendedor_produto.setText(product.seller);

        try {
            holderP.tvPreco_produto.setText(Util.formatLocalCoin(((double) product.price) / 100, false));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    public class ProductHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_produto)
        CircleImageView ciImg_produto;
        @BindView(R.id.nome_produto)
        TextView tvNome_produto;
        @BindView(R.id.vendedor_produto)
        TextView tvVendedor_produto;
        @BindView(R.id.preco_produto)
        TextView tvPreco_produto;
        @BindView(R.id.tvQuantidade)
        TextView tvQuantidade;
        @BindView(R.id.buttons)
        LinearLayout llButtons;

        public ProductHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            llButtons.setVisibility(View.VISIBLE);
        }

        @OnClick({R.id.btnAumentar, R.id.btnDiminuir})
        public void onClick(View view) {
            String method;
            CartDB prod = new CartDB();

            String qtdeAtual = tvQuantidade.getText().toString();
            String nomeProduto = tvNome_produto.getText().toString();
            String precoProdutoStr = tvPreco_produto.getText().toString();
            String imagemProduto = (String) ciImg_produto.getContentDescription();
            precoProdutoStr = precoProdutoStr.replace("R$","").replace(".","").replace(",",".");
            Double precoProdutoDouble = Double.parseDouble(precoProdutoStr);

            //Verifica qual foi o botão pressionado
            switch (view.getId()) {
                case R.id.btnAumentar:
                    if (qtdeAtual.equals("0")) {
                        method = "insert";

                        prod.setProductName(nomeProduto);
                        prod.setProductPrice(precoProdutoDouble);
                        prod.setProductImage(imagemProduto);
                        prod.setProductQtd(1);

                        AsyncTaskExecutor task = new AsyncTaskExecutor(mContext, prod, tvQuantidade, method);
                        task.execute();
                    } else {
                        method = "update";

                        String qtde = this.tvQuantidade.getText().toString();
                        Integer qtdeInteger = Integer.parseInt(qtde);
                        qtdeInteger = qtdeInteger + 1;

                        prod.setProductName(nomeProduto);
                        prod.setProductPrice(precoProdutoDouble);
                        prod.setProductQtd(qtdeInteger);
                        prod.setProductImage(imagemProduto);

                        AsyncTaskExecutor task = new AsyncTaskExecutor(mContext, prod, tvQuantidade, method);
                        task.execute();
                    }

                    return;
                case R.id.btnDiminuir:
                    if (qtdeAtual.equals("0")) {
                        return;
                    } else if (qtdeAtual.equals("1")) {
                        method = "delete";
                        prod.setProductName(tvNome_produto.getText().toString());

                        AsyncTaskExecutor task = new AsyncTaskExecutor(mContext, prod, tvQuantidade, method);
                        task.execute();
                    } else {
                        method = "update";

                        String qtde = this.tvQuantidade.getText().toString();
                        Integer qtdeInteger = Integer.parseInt(qtde);
                        qtdeInteger = qtdeInteger - 1;

                        prod.setProductName(nomeProduto);
                        prod.setProductPrice(precoProdutoDouble);
                        prod.setProductQtd(qtdeInteger);
                        prod.setProductImage(imagemProduto);

                        AsyncTaskExecutor task = new AsyncTaskExecutor(mContext, prod, tvQuantidade, method);
                        task.execute();
                    }
                    return;
            }
        }
    }
}
