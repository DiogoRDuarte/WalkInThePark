package com.example.walkinthepark;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        getSupportActionBar().hide();
    }

    /**
     * Metodo que permite ir para a atividade principal
     * @param view vista da atividade principal
     */
    public void goToMain (View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}