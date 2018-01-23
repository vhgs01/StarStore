package com.starstore.hugo.victor.starstore.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.starstore.hugo.victor.starstore.R;
import com.starstore.hugo.victor.starstore.data.models.CartDB;
import com.starstore.hugo.victor.starstore.data.catalogs.ProductsCatalog;
import com.starstore.hugo.victor.starstore.util.Util;
import com.starstore.hugo.victor.starstore.util.AsyncTaskCartExecutor;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Victor Hugo on 17/01/2018.
 */

// ADAPTER DO PRODUTO
public class ProductAdapter extends RecyclerView.Adapter {
    // DECLARAÇÃO DE VARIÁVEIS
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

        // INFLA A VIEW QUE IRÁ APARECER NA LISTA
        view = LayoutInflater.from(mContext).inflate(R.layout.main_line_view, parent, false);

        // CRIA O VIEW HOLDER
        holder = new ProductHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // OBTEM O HOLDER
        ProductHolder holderP = (ProductHolder) holder;
        ProductsCatalog product = mProducts.get(position);

        // PREENCHENDO OS DADOS NA VIEW
        Picasso.with(mContext).load(Uri.parse(product.thumbnailHd)).fit().into(holderP.ciImg_produto);
        holderP.ciImg_produto.setContentDescription(product.thumbnailHd);
        holderP.tvNome_produto.setText(product.title);
        holderP.tvVendedor_produto.setText(product.seller);

        // FUNÇÃO PARA VERIFICAR SE TEM O PRODUTO NO BANCO E RETORNA SUA QUANTIDADE
        getProductQtd(product.title, holderP.tvQuantidade, "showByName");

        // PREENCHENDO OS DADOS NA VIEW
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
        // BIND DOS ELEMENTOS
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

            // INICIALIZAÇÃO DO BUTTER KNIFE
            ButterKnife.bind(this, itemView);

            // SETANDO COMO VISÍVEL O ELEMENTO
            llButtons.setVisibility(View.VISIBLE);
        }

        // CLICK DOS BOTÕES AUMENTAR E DIMINUIR
        @OnClick({R.id.btnAumentar, R.id.btnDiminuir})
        public void onClick(View view) {
            // DECLARAÇÃO DE VARIÁVEIS
            String method;
            CartDB prod = new CartDB();

            String qtdeAtual = tvQuantidade.getText().toString();
            String nomeProduto = tvNome_produto.getText().toString();
            String precoProdutoStr = tvPreco_produto.getText().toString();
            String imagemProduto = (String) ciImg_produto.getContentDescription();
            precoProdutoStr = precoProdutoStr.replace("R$","").replace(".","").replace(",",".");
            Double precoProdutoDouble = Double.parseDouble(precoProdutoStr);

            // VERIFICA QUAL BOTÃO FOI CLICADO
            switch (view.getId()) {
                case R.id.btnAumentar:
                    if (qtdeAtual.equals("0")) {
                        // DECLARAÇÃO DE VARIÁVEIS
                        method = "insert";

                        // CRIANDO UM OBJETO DO TIPO CART
                        prod.setProductName(nomeProduto);
                        prod.setProductPrice(precoProdutoDouble);
                        prod.setProductImage(imagemProduto);
                        prod.setProductQtd(1);

                        // INSTANCIANDO E EXECUTANDO UMA ASYNCTASK
                        AsyncTaskCartExecutor task = new AsyncTaskCartExecutor(mContext, prod, tvQuantidade, method);
                        task.execute();
                    } else {
                        // DECLARAÇÃO DE VARIÁVEIS
                        method = "update";
                        String qtde = this.tvQuantidade.getText().toString();
                        Integer qtdeInteger = Integer.parseInt(qtde);
                        qtdeInteger = qtdeInteger + 1;

                        // CRIANDO UM OBJETO DO TIPO CART
                        prod.setProductName(nomeProduto);
                        prod.setProductPrice(precoProdutoDouble);
                        prod.setProductQtd(qtdeInteger);
                        prod.setProductImage(imagemProduto);

                        // INSTANCIANDO E EXECUTANDO UMA ASYNCTASK
                        AsyncTaskCartExecutor task = new AsyncTaskCartExecutor(mContext, prod, tvQuantidade, method);
                        task.execute();
                    }

                    return;
                case R.id.btnDiminuir:
                    if (qtdeAtual.equals("0")) {
                        return;
                    } else if (qtdeAtual.equals("1")) {
                        // DECLARAÇÃO DE VARIÁVEIS
                        method = "delete";

                        // CRIANDO UM OBJETO DO TIPO CART
                        prod.setProductName(tvNome_produto.getText().toString());

                        // INSTANCIANDO E EXECUTANDO UMA ASYNCTASK
                        AsyncTaskCartExecutor task = new AsyncTaskCartExecutor(mContext, prod, tvQuantidade, method);
                        task.execute();
                    } else {
                        // DECLARAÇÃO DE VARIÁVEIS
                        method = "update";
                        String qtde = this.tvQuantidade.getText().toString();
                        Integer qtdeInteger = Integer.parseInt(qtde);
                        qtdeInteger = qtdeInteger - 1;

                        // CRIANDO UM OBJETO DO TIPO CART
                        prod.setProductName(nomeProduto);
                        prod.setProductPrice(precoProdutoDouble);
                        prod.setProductQtd(qtdeInteger);
                        prod.setProductImage(imagemProduto);

                        // INSTANCIANDO E EXECUTANDO UMA ASYNCTASK
                        AsyncTaskCartExecutor task = new AsyncTaskCartExecutor(mContext, prod, tvQuantidade, method);
                        task.execute();
                    }
                    return;
            }
        }
    }

    // FUNÇÃO RESPONSÁVEL POR TRAZER A QUANTIDADE DO PRODUTO NO CARRINHO
    public void getProductQtd(String productName, TextView tvQuantidade, String method) {
        // INSTANCIANDO VARIÁVEL
        CartDB prod = new CartDB();

        // CRIANDO UM OBJETO DO TIPO CART
        prod.setProductName(productName);

        // INSTANCIANDO E EXECUTANDO UMA ASYNCTASK
        AsyncTaskCartExecutor task = new AsyncTaskCartExecutor(mContext, prod, tvQuantidade, method);
        task.execute();
    }
}
