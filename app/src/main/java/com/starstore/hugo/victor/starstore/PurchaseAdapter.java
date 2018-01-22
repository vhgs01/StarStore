package com.starstore.hugo.victor.starstore;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.starstore.hugo.victor.starstore.models.PurchasesDB;
import com.starstore.hugo.victor.starstore.utils.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Victor Hugo on 22/01/2018.
 */

// ADAPTER DA COMPRA
public class PurchaseAdapter extends RecyclerView.Adapter {
    // DECLARAÇÃO DE VARIÁVEIS
    private Context mContext;
    private PurchasesDB[] mPurchases;

    public PurchaseAdapter(Context mContext, PurchasesDB[] mPurchases) {
        this.mContext = mContext;
        this.mPurchases = mPurchases;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        PurchaseHolder holder = null;

        // INFLA A VIEW QUE IRÁ APARECER NA LISTA
        view = LayoutInflater.from(mContext).inflate(R.layout.purchases_line_view, parent, false);

        // CRIA O VIEW HOLDER
        holder = new PurchaseHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // OBTEM O HOLDER
        PurchaseHolder holderP = (PurchaseHolder) holder;
        PurchasesDB purchase = mPurchases[position];

        // PREENCHENDO OS DADOS NA VIEW
        holderP.tvCreated.setText(purchase.getPurchaseCreated());
        holderP.tvPurchaseName.setText(purchase.getPurchaseCardName());
        holderP.tvLastDigits.setText(Integer.toString(purchase.getPurchaseCardLastDigits()));

        try {
            holderP.tvTotalPurchase.setText(Util.formatLocalCoin(purchase.getPurchasePrice(), false));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return mPurchases.length;
    }

    public class PurchaseHolder extends RecyclerView.ViewHolder {
        // BIND DOS ELEMENTOS
        @BindView(R.id.tvCreated)
        TextView tvCreated;
        @BindView(R.id.tvPurchaseName)
        TextView tvPurchaseName;
        @BindView(R.id.tvLastDigits)
        TextView tvLastDigits;
        @BindView(R.id.tvTotalPurchase)
        TextView tvTotalPurchase;

        public PurchaseHolder(View itemView) {
            super(itemView);

            // INICIALIZAÇÃO DO BUTTER KNIFE
            ButterKnife.bind(this, itemView);

        }
    }
}
