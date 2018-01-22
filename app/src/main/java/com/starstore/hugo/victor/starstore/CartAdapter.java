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
import com.starstore.hugo.victor.starstore.utils.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Victor Hugo on 19/01/2018.
 */

// ADAPTER DO CARRINHO
public class CartAdapter extends RecyclerView.Adapter {
    // DECLARAÇÃO DE VARIÁVEIS
    private Context mContext;
    private CartDB[] mProducts;

    // CONSTRUTOR
    public CartAdapter(Context context, CartDB[] products) {
        this.mContext = context;
        this.mProducts = products;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        ProductCartHolder holder = null;

        // INFLA A VIEW QUE IRÁ APARECER NA LISTA
        view = LayoutInflater.from(mContext).inflate(R.layout.main_line_view, parent, false);

        // CRIA O VIEW HOLDER
        holder = new ProductCartHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // OBTEM O HOLDER
        ProductCartHolder holderP = (ProductCartHolder) holder;
        CartDB product = mProducts[position];

        // PREENCHENDO OS DADOS NA VIEW
        try {
            Picasso.with(mContext).load(Uri.parse(product.getProductImage())).fit().into(holderP.ciImg_produto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holderP.tvNome_produto.setText(product.getProductName());
        holderP.tvQtdeProdutoCarrinho.setText(product.getProductQtd().toString() + " un.");

        try {
            holderP.tvPreco_produto.setText(Util.formatLocalCoin(product.getProductPrice(), false));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mProducts.length;
    }

    public class ProductCartHolder extends RecyclerView.ViewHolder{
        // BIND DOS ELEMENTOS
        @BindView(R.id.img_produto)
        CircleImageView ciImg_produto;
        @BindView(R.id.nome_produto)
        TextView tvNome_produto;
        @BindView(R.id.vendedor_produto)
        TextView tvVendedor_produto;
        @BindView(R.id.preco_produto)
        TextView tvPreco_produto;
        @BindView(R.id.llQuantidadeCarrinho)
        LinearLayout llQuantidadeCarrinho;
        @BindView(R.id.tvQtdeProdutoCarrinho)
        TextView tvQtdeProdutoCarrinho;

        public ProductCartHolder(View itemView) {
            super(itemView);

            // INICIALIZAÇÃO DO BUTTER KNIFE
            ButterKnife.bind(this, itemView);

            // SETANDO COMO VISÍVEL O ELEMENTO
            llQuantidadeCarrinho.setVisibility(View.VISIBLE);
        }
    }
}
