package com.example.walkinthepark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;

public class CalibrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calibration);

        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        findViewById(R.id.imageMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        NavigationView navigationView = findViewById(R.id.navigationMenuUser);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent i;
                switch (item.getItemId()) {
                    case R.id.notasAc:
                        i = new Intent(CalibrationActivity.this, NotesActivity.class);
                        i.putExtra("fragment", "fragNT");
                        startActivity(i);
                        break;
                    case R.id.lembretesAc:
                        i = new Intent(CalibrationActivity.this, ReminderActivity.class);
                        i.putExtra("fragment", "frag1");
                        startActivity(i);
                        break;
                    case R.id.videosAc:
                        startActivity(new Intent(CalibrationActivity.this, ExerciseActivity.class));
                        break;
                    case R.id.humorAc:
                        startActivity(new Intent(CalibrationActivity.this, MoodActivity.class));
                        break;
                    case R.id.calibracaoAc:
                        startActivity(new Intent(CalibrationActivity.this, CalibrationActivity.class));
                        break;
                    case R.id.definicoesAc:
                        startActivity(new Intent(CalibrationActivity.this, SettingsActivity.class));
                        break;
                    case R.id.ajudaAc:
                        startActivity(new Intent(CalibrationActivity.this, AboutActivity.class));
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + item.getItemId());
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }
}