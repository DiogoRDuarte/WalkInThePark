package com.example.walkinthepark;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class NotesFragment extends Fragment {

    static View notesView;

    static AllNotesFragment allNotesFragment;
    static NewNoteFragment newNoteFragment;
    private ArrayList<Note> listaNotas;

    private DatabaseReference refNotes;
    private FirebaseDatabase db;

    Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        notesView = inflater.inflate(R.layout.fragment_notes, container, false);

        db = FirebaseDatabase.getInstance("https://walk-in-the-park---cm-default-rtdb.firebaseio.com/");
        refNotes = db.getReference("Note");

        listaNotas = new ArrayList<Note>();

        if(allNotesFragment == null) {
            allNotesFragment = new AllNotesFragment();
        }

        if(newNoteFragment == null) {
            newNoteFragment = new NewNoteFragment();
        }

        replaceFragment(allNotesFragment);

        String str = getArguments().getString("fragment");
        switch (str) {
            case "fragN":
                replaceFragment(newNoteFragment);
                break;
            case "fragNT":
                replaceFragment(allNotesFragment);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + str);
        }

        button = notesView.findViewById(R.id.button_notes);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(button.getText().equals("Adicionar Nota")) {
                    button.setText("Ver Notas");
                    newNoteFragment = new NewNoteFragment();
                    replaceFragment(newNoteFragment);
                } else if (button.getText().equals("Ver Notas")) {
                    button.setText("Adicionar Nota");
                    replaceFragment(allNotesFragment);
                }
            }
        });

        return notesView;
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
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