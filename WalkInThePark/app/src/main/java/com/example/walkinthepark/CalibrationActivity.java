package com.example.walkinthepark;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class CalibrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calibration);

        ImageView i = findViewById(R.id.imageViewC);
        /*i.setOnTouchListener(new SwipeListener(CalibrationActivity.this){
            public void onSwipeTop() {
                Toast.makeText(CalibrationActivity.this, "top", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeRight() {
                Toast.makeText(CalibrationActivity.this, "right", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeLeft() {
                Toast.makeText(CalibrationActivity.this, "left", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeBottom() {
                Toast.makeText(CalibrationActivity.this, "bottom", Toast.LENGTH_SHORT).show();
            }
        });*/
    }
}