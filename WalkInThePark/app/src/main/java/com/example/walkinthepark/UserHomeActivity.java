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

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class UserHomeActivity extends AppCompatActivity {

    private DatabaseReference refNotas;
    private DatabaseReference refReminders;
    private FirebaseDatabase db;
    private DatabaseReference myRef;
    String user_email;
    String user_name;
    boolean paciente = true;

    // Fragmentos
    static UserHomeFragment userHomeFragment;
    static NotesFragment notesFragment;
    static RemindersFragment remindersFragment;
    static ExerciseFragment exerciseFragment;
    static MoodsFragment moodsFragment;
    static CalibrationFragment calibrationFragment;
    static SettingsFragment settingsFragment;
    static AboutFragment aboutFragment;
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        user_email = getIntent().getStringExtra("user_email");
        user_name = getIntent().getStringExtra("user_name");

        db = FirebaseDatabase.getInstance("https://walk-in-the-park---cm-default-rtdb.firebaseio.com/");
        refNotas = db.getReference("Note");
        refReminders = db.getReference("Reminder");
        myRef = db.getReference("User");
        Map m = new HashMap<String,Map>();

        // Inicializar fragmentos

        if(notesFragment == null) {
            notesFragment = new NotesFragment();
        }
        if(remindersFragment == null) {
            remindersFragment = new RemindersFragment();
        }
        if(exerciseFragment == null) {
            exerciseFragment = new ExerciseFragment();
        }
        if(moodsFragment == null) {
            moodsFragment = new MoodsFragment();
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
        if(userHomeFragment == null) {
            userHomeFragment = new UserHomeFragment();
        }

        /*replaceFragment(userHomeFragment);*/

        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        findViewById(R.id.imageMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        NavigationView navigationView = findViewById(R.id.navigationMenuUser);

        View headerView = navigationView.getHeaderView(0);
        TextView nomeText = headerView.findViewById(R.id.username);
        //nomeText.setText(user_name.toUpperCase());
        TextView pacienteText = headerView.findViewById(R.id.usertype);
        pacienteText.setText("Paciente");


        /*navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Bundle bundle;
                switch (item.getItemId()) {
                    case R.id.menuAc:
                        replaceFragment(userHomeFragment);
                    case R.id.notasAc:
                        bundle = new Bundle();
                        bundle.putString("fragment", "fragNT");
                        notesFragment.setArguments(bundle);
                        replaceFragment(notesFragment);
                        break;
                    case R.id.lembretesAc:
                        bundle = new Bundle();
                        bundle.putString("fragment", "frag1");
                        remindersFragment.setArguments(bundle);
                        replaceFragment(remindersFragment);
                        break;
                    case R.id.videosAc:
                        replaceFragment(exerciseFragment);
                        break;
                    case R.id.humorAc:
                        bundle = new Bundle();
                        bundle.putString("fragment", "fragM");
                        moodsFragment.setArguments(bundle);
                        replaceFragment(moodsFragment);
                        break;
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

        navController = Navigation.findNavController(this, R.id.navHostFragmet);
        NavigationUI.setupWithNavController(navigationView, navController);

    }

    /*public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_user, fragment);
        *//*fragmentTransaction.addToBackStack(null);*//*
        fragmentTransaction.commit();
    }*/

    public String getCurrentUserEmail(){
        return this.user_email;
    }



}