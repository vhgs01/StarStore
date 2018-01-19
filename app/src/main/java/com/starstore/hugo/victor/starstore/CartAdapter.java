package com.starstore.hugo.victor.starstore;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.starstore.hugo.victor.starstore.models.ProductsCatalog;
import com.starstore.hugo.victor.starstore.utils.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Victor Hugo on 19/01/2018.
 */

public class CartAdapter extends RecyclerView.Adapter{
    private Context mContext;
    private List<ProductsCatalog> mProducts;

    public CartAdapter(Context context, List<ProductsCatalog> products) {
        this.mContext = context;
        this.mProducts = products;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        ProductCartHolder holder = null;

        //Infla a view que irá aparecer na lista
        view = LayoutInflater.from(mContext).inflate(R.layout.main_line_view, parent, false);

        //Cria o viewholder
        holder = new ProductCartHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //Obtem o holder
        ProductCartHolder holderP = (ProductCartHolder) holder;
        ProductsCatalog product = mProducts.get(position);

        //Aqui você usa pra preencher dados na View
        Picasso.with(mContext).load(Uri.parse(product.thumbnailHd)).fit().into(holderP.ciImg_produto);
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

    public class ProductCartHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.img_produto)
        CircleImageView ciImg_produto;
        @BindView(R.id.nome_produto)
        TextView tvNome_produto;
        @BindView(R.id.vendedor_produto)
        TextView tvVendedor_produto;
        @BindView(R.id.preco_produto)
        TextView tvPreco_produto;

        public ProductCartHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
