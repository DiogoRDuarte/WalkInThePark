package com.example.walkinthepark;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NotesFragment extends Fragment {

    static View notesView;
    private TextView notas;
    private ArrayList<Note> listaNotas;
    private DatabaseReference refNotes;
    private FirebaseDatabase db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        notesView = inflater.inflate(R.layout.fragment_notes, container, false);
        db = FirebaseDatabase.getInstance("https://walk-in-the-park---cm-default-rtdb.firebaseio.com/");
        refNotes = db.getReference("Note");

        refNotes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    String s = ds.child("titulo").getValue().toString();
                    String s1 = ds.child("mensagem").getValue().toString();
                    listaNotas.add(new Note(s,s1));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        listaNotas = ((NotesActivity) getActivity()).getListaNotas();
        notas = notesView.findViewById(R.id.listaNotas);

        StringBuilder m = new StringBuilder("");

        for(Note r: listaNotas){
            m.append("Titulo: "+r.getTitulo()+"\n\nDescrição: "+r.getMensagem()+"\n\n\n");
        }

        if(m.toString().equals("")){
            notas.setText("Não Existem Notas!");
        }else
            notas.setText(m.toString());

        return notesView;
    }
}