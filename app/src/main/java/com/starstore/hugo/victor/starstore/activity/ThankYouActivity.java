package com.starstore.hugo.victor.starstore.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.starstore.hugo.victor.starstore.R;

public class ThankYouActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank_you);
    }

    @Override
    protected void onStop() {
        super.onStop();

        // TERMINA A ACTIVITY
        finish();
    }

    public void backToHome(View view) {
        // INICIALIZANDO UMA INSTANCIA DE INTENT
        Intent intent = new Intent(this, MainActivity.class);

        // INICIALIZANDO A NOVA ACTIVITY
        startActivity(intent);
    }

}
