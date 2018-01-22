package com.starstore.hugo.victor.starstore;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.starstore.hugo.victor.starstore.models.DataBase;
import com.starstore.hugo.victor.starstore.models.PurchasesDB;
import com.starstore.hugo.victor.starstore.utils.AsyncTaskPurchaseExecutor;
import com.starstore.hugo.victor.starstore.utils.MaskHelper;
import com.starstore.hugo.victor.starstore.utils.Util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PaymentActivity extends AppCompatActivity {
    // BIND DOS ELEMENTOS
    @BindView(R.id.tiCardNumber)
    EditText tiCardNumber;
    @BindView(R.id.tiCardExpirationDate)
    EditText tiCardExpirationDate;
    @BindView(R.id.tiCardCvv)
    EditText tiCardCvv;
    @BindView(R.id.tiCardName)
    EditText tiCardName;
    @BindView(R.id.tvTotal)
    TextView tvTotal;
    @BindView(R.id.btPagar)
    CircularProgressButton btPagar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        // INICIALIZAÇÃO DO BUTTER KNIFE
        ButterKnife.bind(this);

        // INSTANCIANDO E EXECUTANDO UMA ASYNCTASK
        showTotal task = new showTotal(this, tvTotal);
        task.execute();

        // ADICIONANDO OS WATCHERS
        tiCardNumber.addTextChangedListener(new MaskHelper.NumberCard(tiCardNumber));
        tiCardExpirationDate.addTextChangedListener(new MaskHelper.ExpirationDateCard(tiCardExpirationDate));

        // ADICIONA O LISTENER PARA O BOTÃO DE PAGAR
        btPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // CASO PASSE POR TODAS AS VALIDAÇÕES ELE CHAMA UMA ASYNCTASK
                if (validateNumber() && validateName() && validateExpirationDate() && validateCvv()) {
                    // INSTANCIANDO UMA ASYNCTASK
                    simulatePayment simulateTask = new simulatePayment(view.getContext());

                    // INICIANDO A ANIMAÇÃO DO BOTÃO
                    btPagar.startAnimation();

                    // EXECUTANDO UMA ASYNCTASK
                    simulateTask.execute();
                }
            }
        });

    }

    // ASYNCTASK PARA BUSCAR NO BANCO LOCAL O VALOR TOTAL DO CARRINHO E EXIBI-LO
    public class showTotal extends AsyncTask<Void, Void, String> {
        // DECLARAÇÃO DE VARIÁVEIS
        private Context mContext;
        private TextView mTvTotal;

        // CONSTRUTOR
        public showTotal(Context context, TextView tvTotal) {
            this.mContext = context;
            this.mTvTotal = tvTotal;
        }

        @Override
        protected String doInBackground(Void... voids) {
            // INICIALIZANDO UMA INSTANCIA DO BANCO DE DADOS E RETORNANDO O VALOR TOTAL DOS PRODUTOS DO CARRINHO
            DataBase db = Room.databaseBuilder(mContext, DataBase.class, "cart").build();
            return db.cartDao().showCartTotal();
        }

        @Override
        protected void onPostExecute(String value) {
            super.onPostExecute(value);

            // DECLARAÇÃO DE VARIÁVEIS
            Double valueDouble = Double.parseDouble(value);

            // SETA O VALOR TOTAL DO CARRINHO PASSANDO COMO PARÂMETRO O RETORNO DE UMA FUNÇÃO DE FORMATAÇÃO
            try {
                mTvTotal.setText(Util.formatLocalCoin((valueDouble), false));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // ASYNCTASK PARA SIMULAR O PAGAMENTO E SALVAR AS INFORMAÇÕES NO BANCO LOCAL
    public class simulatePayment extends AsyncTask<String, String, String> {
        // DECLARAÇÃO DE VARIÁVEIS
        private Context mContext;

        public simulatePayment(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                // INSTANCIANDO VARIÁVEL
                PurchasesDB purchase = new PurchasesDB();

                // DECLARAÇÃO DE VARIÁVEIS
                String method = "insert";
                String totalStr = tvTotal.getText().toString().replace("R$", "").replace(".", "").replace(",", ".");
                Double totalDouble = Double.parseDouble(totalStr);
                Date dateNow = Calendar.getInstance().getTime();
                String dateFormated = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(dateNow);

                // CRIANDO UM OBJETO DO TIPO PURCHASE
                purchase.setPurchaseCardName(tiCardName.getText().toString());
                purchase.setPurchasePrice(totalDouble);
                purchase.setPurchaseCreated(dateFormated);
                purchase.setPurchaseCardLastDigits(Integer.parseInt(tiCardNumber.getText().toString().substring(tiCardNumber.length() - 4)));

                // INSTANCIANDO E EXECUTANDO UMA ASYNCTASK
                AsyncTaskPurchaseExecutor taskPurchase = new AsyncTaskPurchaseExecutor(mContext, purchase, method);
                taskPurchase.execute();

                // ADICIONANDO UM SLEEP DE 3 SEGUNDOS
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "done";
        }

        @Override
        protected void onPostExecute(String value) {
            super.onPostExecute(value);

            if (value.equals("done")) {
                // DEFINE COMO DONE O BOTÃO E SETA ALGUNS ATRIBUTOS
                btPagar.doneLoadingAnimation(Color.parseColor("#333639"), BitmapFactory.decodeResource(getResources(), R.drawable.ic_done_white_48dp));

                // FINALIZA A INTENT ATUAL
                finish();

                // INICIALIZANDO UMA INSTANCIA DE INTENT
                Intent intent = new Intent(mContext, PurchasesActivity.class);

                // INICIALIZANDO A NOVA ACTIVITY
                startActivity(intent);
            }
        }
    }

    // FUNÇÃO QUE VALIDA O NÚMERO DO CARTÃO
    private boolean validateNumber() {
        if (tiCardNumber.getText().toString().isEmpty() || tiCardNumber.length() < 16) {
            tiCardNumber.setError(getString(R.string.errorCardNumber));
            return false;
        } else {
            tiCardNumber.setError(null);
            return true;
        }
    }

    // FUNÇÃO QUE VALIDA O NOME DO CARTÃO
    private boolean validateName() {
        if (tiCardName.getText().toString().isEmpty()) {
            tiCardName.setError(getString(R.string.errorCardName));
            return false;
        } else {
            tiCardName.setError(null);
            return true;
        }
    }

    // FUNÇÃO QUE VALIDA A DATA DE EXPIRAÇÃO DO CARTÃO
    private boolean validateExpirationDate() {
        if (tiCardExpirationDate.getText().toString().isEmpty() || tiCardExpirationDate.length() < 5) {
            tiCardExpirationDate.setError(getString(R.string.errorCardExpirationDate));
            return false;
        } else {
            tiCardExpirationDate.setError(null);
            return true;
        }
    }

    // FUNÇÃO QUE VALIDA O CVV DO CARTÃO
    private boolean validateCvv() {
        if (tiCardCvv.getText().toString().isEmpty() || tiCardCvv.length() < 3) {
            tiCardCvv.setError(getString(R.string.errorCardCvv));
            return false;
        } else {
            tiCardCvv.setError(null);
            return true;
        }
    }

}
