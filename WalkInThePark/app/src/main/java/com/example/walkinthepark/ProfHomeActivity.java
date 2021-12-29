package com.example.walkinthepark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicMarkableReference;

public class ProfHomeActivity extends AppCompatActivity {

    private DatabaseReference myRef;
    private FirebaseDatabase db;
    private  ArrayList<Map> pacientes;
    String prof_email;

    // Fragmentos
    static ProfHomeFragment profHomeFragment;
    static CalibrationFragment calibrationFragment;
    static SettingsFragment settingsFragment;
    static AboutFragment aboutFragment;
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof_home);

        prof_email = getIntent().getStringExtra("user_email");
        db = FirebaseDatabase.getInstance("https://walk-in-the-park---cm-default-rtdb.firebaseio.com/");
        myRef = db.getReference("User");
        Map m = new HashMap<String,Map>();
        List<HashMap<String, User>> list = new ArrayList<>();

        // Inicializar fragmentos
        if(profHomeFragment == null) {
            profHomeFragment = new ProfHomeFragment();
        }
        if(calibrationFragment == null) {
            calibrationFragment = new CalibrationFragment();
        }
        if(settingsFragment == null) {
            settingsFragment = new SettingsFragment();
        }
        if(aboutFragment == null) {
            aboutFragment = new AboutFragment();
        }

        /*replaceFragment(profHomeFragment);*/

        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        findViewById(R.id.imageMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        NavigationView navigationView = findViewById(R.id.navigationMenuFisio);
        /*navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Bundle bundle;
                switch (item.getItemId()) {
                    case R.id.menuAc:
                        replaceFragment(profHomeFragment);
                    case R.id.calibracaoAc:
                        replaceFragment(calibrationFragment);
                        break;
                    case R.id.definicoesAc:
                        replaceFragment(settingsFragment);
                        break;
                    case R.id.ajudaAc:
                        replaceFragment(aboutFragment);
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + item.getItemId());
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });*/

        navController = Navigation.findNavController(this, R.id.navProfFragment);
        NavigationUI.setupWithNavController(navigationView, navController);

    }

    /*public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_prof, fragment);
        fragmentTransaction.commit();
    }*/

    public String getCurrentProfEmail(){
        return this.prof_email;
    }


}