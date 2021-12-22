package com.example.walkinthepark;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewNoteFragment extends Fragment {
    private Map mapNotes = new HashMap<String, Note>();
    static View newNoteView;
    private TextView titulo;
    private TextView nota;
    private Note note;
    private FirebaseDatabase db;
    private DatabaseReference myRef;
    private List<String> listTitulo = new ArrayList<String>();
    private String tituloS;
    private String notaS;
    private boolean a = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        newNoteView = inflater.inflate(R.layout.fragment_new_note, container, false);
        Button cancelButton = newNoteView.findViewById(R.id.buttonCancelar);
        Button addButton = newNoteView.findViewById(R.id.buttonAdicionar);

        titulo = (EditText) newNoteView.findViewById(R.id.tituloNota);
        nota = (EditText) newNoteView.findViewById(R.id.descricaoNota);
        db = FirebaseDatabase.getInstance("https://walk-in-the-park---cm-default-rtdb.firebaseio.com/");
        myRef = db.getReference("Note");
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tituloS = titulo.getText().toString();
                notaS = nota.getText().toString();

                if(tituloS.equals("") || notaS.equals("")){
                    Toast toast = Toast.makeText(getContext(), "Escolhe um Titulo e uma Nota!", Toast.LENGTH_SHORT);
                    toast.show();

                }else {
                    note = new Note(tituloS, notaS);
                    //((NotesActivity) getActivity()).adicionarNota(note);
                    Map noteValues = note.toMap();

                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot ds: snapshot.getChildren()){
                                String s = ds.child("titulo").getValue().toString();
                                listTitulo.add(s);
                            }
                            //mapNotes.put(titulo, noteValues);
                            //Toast.makeText(getContext(), "Nota adicionada!", Toast.LENGTH_SHORT).show();
                            //myRef.updateChildren(mapNotes);
                            if(listTitulo.contains(titulo) && a){
                                Toast.makeText(getContext(), "Ja existe uma nota com este titulo!", Toast.LENGTH_SHORT).show();
                                titulo.setText("");
                                nota.setText("");


                            }else {

                                if(a) {
                                    //myRef.child("User").child(email);
                                    mapNotes.put(tituloS, noteValues);
                                    Toast.makeText(getContext(), "Nota adicionada!", Toast.LENGTH_SHORT).show();
                                    myRef.updateChildren(mapNotes);
                                    goToMain(view);
                                    a = false;

                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction().remove(NewNoteFragment.this).commit();
                ((NotesActivity)getActivity()).button.setText("Adicionar Nota");
                ((NotesActivity)getActivity()).replaceFragment(((NotesActivity)getActivity()).notesFragment);
            }
        });

        return newNoteView;
    }

    private void goToMain(View view) {
        Intent i = new Intent(getActivity(), UserHomeActivity.class);
        startActivity(i);
    }


}