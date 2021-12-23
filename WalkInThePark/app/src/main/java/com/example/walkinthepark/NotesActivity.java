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
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NotesActivity extends AppCompatActivity {

    Button button;
    static NotesFragment notesFragment;
    static NewNoteFragment newNoteFragment;
    private ArrayList<Note> listaNotas;

    private DatabaseReference refNotes;
    private FirebaseDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

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
                        i = new Intent(NotesActivity.this, NotesActivity.class);
                        i.putExtra("fragment", "fragNT");
                        startActivity(i);
                        break;
                    case R.id.lembretesAc:
                        i = new Intent(NotesActivity.this, ReminderActivity.class);
                        i.putExtra("fragment", "frag1");
                        startActivity(i);
                        break;
                    case R.id.videosAc:
                        startActivity(new Intent(NotesActivity.this, ExerciseActivity.class));
                        break;
                    case R.id.humorAc:
                        startActivity(new Intent(NotesActivity.this, MoodActivity.class));
                        break;
                    case R.id.calibracaoAc:
                        startActivity(new Intent(NotesActivity.this, CalibrationActivity.class));
                        break;
                    case R.id.definicoesAc:
                        startActivity(new Intent(NotesActivity.this, SettingsActivity.class));
                        break;
                    case R.id.ajudaAc:
                        startActivity(new Intent(NotesActivity.this, AboutActivity.class));
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + item.getItemId());
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        db = FirebaseDatabase.getInstance("https://walk-in-the-park---cm-default-rtdb.firebaseio.com/");
        refNotes = db.getReference("Note");


        listaNotas = new ArrayList<Note>();

        if(notesFragment == null) {
            notesFragment = new NotesFragment();
        }

        if(newNoteFragment == null) {
            newNoteFragment = new NewNoteFragment();
        }

        replaceFragment(notesFragment);

        String intentFragment = getIntent().getExtras().getString("fragment");
        switch (intentFragment) {
            case "fragN":
                replaceFragment(newNoteFragment);
                break;
            case "fragNT":
                replaceFragment(notesFragment);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + intentFragment);
        }

        button = findViewById(R.id.button_notes);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(button.getText().equals("Adicionar Nota")) {
                    button.setText("Ver Notas");
                    newNoteFragment = new NewNoteFragment();
                    replaceFragment(newNoteFragment);
                } else if (button.getText().equals("Ver Notas")) {
                    button.setText("Adicionar Nota");
                    replaceFragment(notesFragment);
                }
            }
        });

    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_notes, fragment);
        fragmentTransaction.commit();
    }

    public ArrayList<Note> getListaNotas(){
        return listaNotas;
    }

    public void adicionarNota(Note nota){
        this.listaNotas.add(nota);
    }

}