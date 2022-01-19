package com.example.walkinthepark;

import static java.security.AccessController.getContext;

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
import android.widget.Toast;

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
        Map m = new HashMap<String, Map>();

        // Inicializar fragmentos

        if (notesFragment == null) {
            notesFragment = new NotesFragment();
        }
        if (remindersFragment == null) {
            remindersFragment = new RemindersFragment();
        }
        if (exerciseFragment == null) {
            exerciseFragment = new ExerciseFragment();
        }
        if (moodsFragment == null) {
            moodsFragment = new MoodsFragment();
        }
        if (calibrationFragment == null) {
            calibrationFragment = new CalibrationFragment();
        }
        if (settingsFragment == null) {
            settingsFragment = new SettingsFragment();
        }
        if (aboutFragment == null) {
            aboutFragment = new AboutFragment();
        }
        if (userHomeFragment == null) {
            userHomeFragment = new UserHomeFragment();
        }

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
        nomeText.setText(user_name.toUpperCase());
        TextView pacienteText = headerView.findViewById(R.id.usertype);
        pacienteText.setText("Paciente");

        navigationView.getMenu().findItem(R.id.logoutAc).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Toast.makeText(getApplicationContext(), "Fez logout com sucesso", Toast.LENGTH_SHORT).show();
                Intent l = new Intent(UserHomeActivity.this, LoginActivity.class);
                l.putExtra("logout", "true");
                startActivity(l);
                finish();
                return true;
            }
        });

        navController = Navigation.findNavController(this, R.id.navHostFragmet);
        NavigationUI.setupWithNavController(navigationView, navController);

    }

    public String getCurrentUserEmail() {
        return this.user_email;
    }

}