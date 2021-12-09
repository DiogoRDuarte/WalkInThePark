package com.example.walkinthepark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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