package com.example.walkinthepark;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    ArrayList<HashMap<String, String>> listaNotas;

    private DatabaseReference myRef;
    private FirebaseDatabase db;
    String user_email;
    private ArrayList<HashMap<String, String>> notasCurrent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        allNotesView = inflater.inflate(R.layout.fragment_all_notes, container, false);


        listaNotas =  ((NotesFragment)getParentFragment()).listaNotas;
        notasCurrent = new ArrayList<>();
        user_email =((UserHomeActivity)getActivity()).user_email;

        RecyclerView rvNotes = (RecyclerView) allNotesView.findViewById(R.id.rvNotes);

        db = FirebaseDatabase.getInstance("https://walk-in-the-park---cm-default-rtdb.firebaseio.com/");
        myRef = db.getReference("User");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    if (ds.child("email").getValue().toString().equals(user_email)) {
                        listaNotas = (ArrayList) ((Map) ds.getValue()).get("listaNotas");

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        for (int i = 1; i < listaNotas.size(); i++) {
            notasCurrent.add(listaNotas.get(i));
        }

        NotesAdapter notesAdapter = new NotesAdapter(notasCurrent);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        rvNotes.setLayoutManager(layoutManager);

        rvNotes.setAdapter(notesAdapter);

        return allNotesView;
    }
}