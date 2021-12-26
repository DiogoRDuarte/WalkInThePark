package com.example.walkinthepark;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AllNotesFragment extends Fragment {

    static View allNotesView;
    private TextView notas;

    ArrayList<HashMap<String, String>> listaNotas;
    private DatabaseReference myRef;
    private FirebaseDatabase db;
    String user_email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        allNotesView = inflater.inflate(R.layout.fragment_all_notes, container, false);
        notas = allNotesView.findViewById(R.id.listaNotas);
        listaNotas =  ((NotesFragment)getParentFragment()).listaNotas;
        user_email =((UserHomeActivity)getActivity()).user_email;
        db = FirebaseDatabase.getInstance("https://walk-in-the-park---cm-default-rtdb.firebaseio.com/");
        myRef = db.getReference("User");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    if (ds.child("email").getValue().toString().equals(user_email)) {
                        listaNotas = (ArrayList) ((Map) ds.getValue()).get("listaNotas");

                        StringBuilder m = new StringBuilder("");

                        for(int i = 1; i < listaNotas.size(); i++){
                            HashMap<String, String> a = listaNotas.get(i);

                            m.append("Titulo: "+ a.get("titulo")+"\nNota: "+a.get("mensagem")+"\n\n");
                        }

                        if(m.toString().equals("")){
                            notas.setText("Não Existem Lembretes!");
                        }else
                            notas.setText(m.toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return allNotesView;
    }
}