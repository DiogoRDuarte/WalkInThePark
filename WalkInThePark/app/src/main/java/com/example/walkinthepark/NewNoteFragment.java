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

    static View newNoteView;
    private TextView titulo;
    private TextView nota;
    private Note note;
    private FirebaseDatabase db;
    private DatabaseReference myRef;
    private List<String> listTitulo = new ArrayList<String>();
    private String tituloS;
    private String notaS;
    private Map mapUsers = new HashMap<String, User>();
    private boolean p = true;
    String user_email;
    private String nomeF;
    private String emailF;
    private String passwordF;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        newNoteView = inflater.inflate(R.layout.fragment_new_note, container, false);
        Button cancelButton = newNoteView.findViewById(R.id.buttonCancelar);
        Button addButton = newNoteView.findViewById(R.id.buttonAdicionar);
        user_email =((UserHomeActivity)getActivity()).user_email;
        titulo = (EditText) newNoteView.findViewById(R.id.tituloNota);
        nota = (EditText) newNoteView.findViewById(R.id.descricaoNota);
        db = FirebaseDatabase.getInstance("https://walk-in-the-park---cm-default-rtdb.firebaseio.com/");
        myRef = db.getReference("User");


        Bundle bundle = getArguments();
        if (bundle != null) {
            titulo.setText(bundle.getString("titulo2"));
            nota.setText( bundle.getString("mensagem2"));
        }


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tituloS = titulo.getText().toString();
                notaS = nota.getText().toString();

                if(tituloS.equals("") || notaS.equals("")){
                    Toast toast = Toast.makeText(getContext(), "Escolhe um Titulo e uma Nota!", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    note = new Note(tituloS, notaS);

                    Map noteValues = note.toMap();

                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot ds: snapshot.getChildren()){
                                if(ds.child("email").getValue().equals(user_email)){
                                    nomeF = ds.child("nome").getValue().toString();
                                    emailF = ds.child("email").getValue().toString();
                                    passwordF = ds.child("password").getValue().toString();
                                    ArrayList a = (ArrayList) ((Map) ds.getValue()).get("listaNotas");
                                    a.add(note.toMap());

                                    HashMap result = new HashMap<>();
                                    result.put("nome", nomeF);
                                    result.put("email", emailF);
                                    result.put("password", passwordF);
                                    result.put("paciente", true);
                                    result.put("fisioID", ds.child("fisioID").getValue());
                                    result.put("listaNotas", a);
                                    result.put("listaLembretes", ds.child("listaLembretes").getValue());
                                    result.put("listaMoods", ds.child("listaMoods").getValue());

                                    mapUsers.put(user_email, result);
                                }
                            }

                                if(p) {
                                    Toast.makeText(getContext(), "Nota adicionada!", Toast.LENGTH_SHORT).show();
                                    myRef.updateChildren(mapUsers);
                                    p = false;

                                    /*goToMain(view);*/
                                    ((NotesFragment)getParentFragment()).button.setText("Adicionar Nota");
                                    ((NotesFragment)getParentFragment()).replaceFragment(((NotesFragment)getParentFragment()).allNotesFragment);
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
                ((NotesFragment)getParentFragment()).button.setText("Adicionar Nota");
                ((NotesFragment)getParentFragment()).replaceFragment(((NotesFragment)getParentFragment()).allNotesFragment);
            }
        });

        return newNoteView;
    }

    private void goToMain(View view) {
        Intent i = new Intent(getActivity(), UserHomeActivity.class);
        startActivity(i);
    }


}