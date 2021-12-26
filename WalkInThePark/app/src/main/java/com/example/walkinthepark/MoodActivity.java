package com.example.walkinthepark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class MoodActivity extends AppCompatActivity {

    public static Fragment moodsFragment;
    public static Fragment newMoodFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood);

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
                        i = new Intent(MoodActivity.this, NotesActivity.class);
                        i.putExtra("fragment", "fragNT");
                        startActivity(i);
                        break;
                    case R.id.lembretesAc:
                        i = new Intent(MoodActivity.this, ReminderActivity.class);
                        i.putExtra("fragment", "frag1");
                        startActivity(i);
                        break;
                    case R.id.videosAc:
                        startActivity(new Intent(MoodActivity.this, ExerciseActivity.class));
                        break;
                    case R.id.humorAc:
                        startActivity(new Intent(MoodActivity.this, MoodActivity.class));
                        break;
                    case R.id.calibracaoAc:
                        startActivity(new Intent(MoodActivity.this, CalibrationActivity.class));
                        break;
                    case R.id.definicoesAc:
                        startActivity(new Intent(MoodActivity.this, SettingsActivity.class));
                        break;
                    case R.id.ajudaAc:
                        startActivity(new Intent(MoodActivity.this, AboutActivity.class));
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + item.getItemId());
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        if (newMoodFragment== null){
            newMoodFragment = new NewMoodFragment();
        }

        if (moodsFragment== null){
            moodsFragment = new AllMoodsFragment();
        }
        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton2);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(moodsFragment);
            }
        });

    }

    public void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flFragment, fragment);
        fragmentTransaction.commit();

    }
}