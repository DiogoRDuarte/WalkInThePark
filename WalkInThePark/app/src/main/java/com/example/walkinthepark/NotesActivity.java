package com.example.walkinthepark;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NotesActivity extends AppCompatActivity {

    Button button;
    static NotesFragment notesFragment;
    static NewNoteFragment newNoteFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        if(notesFragment == null) {
            notesFragment = new NotesFragment();
            replaceFragment(notesFragment);
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

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_notes, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


}