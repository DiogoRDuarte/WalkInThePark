package com.example.walkinthepark;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.core.content.ContextCompat;


public class StartActivity extends Activity {

    private static int TIME_OUT = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_start);

        /*if(ContextCompat.checkSelfPermission(StartActivity.this, Manifest.permission.ACTIVITY_RECOGNITION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(), "Permissão recusada", Toast.LENGTH_SHORT).show();
        }*/



        /** Setting timeout */
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, TIME_OUT);
    }
}