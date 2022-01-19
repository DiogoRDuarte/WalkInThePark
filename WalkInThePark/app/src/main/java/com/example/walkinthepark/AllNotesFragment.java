package com.example.walkinthepark;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
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

    private DatabaseReference myRef;
    private FirebaseDatabase db;
    private Context context = this.getContext();
    String user_email;

    private NotesAdapter.RecyclerViewListener listenerAdapter;

    ArrayList<HashMap<String, String>> listaNotas;
    private ArrayList<HashMap<String, String>> notasCurrent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        allNotesView = inflater.inflate(R.layout.fragment_all_notes, container, false);
        RecyclerView rvNotes = (RecyclerView) allNotesView.findViewById(R.id.rvNotes);

        db = FirebaseDatabase.getInstance("https://walk-in-the-park---cm-default-rtdb.firebaseio.com/");
        myRef = db.getReference("User");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    if(user_email == null)
                    user_email =((UserHomeActivity)getActivity()).user_email;
                    if (ds.child("email").getValue().toString().equals(user_email)) {
                        listaNotas = (ArrayList<HashMap<String, String>>) ds.child("listaNotas").getValue();
                        notasCurrent = new ArrayList<>();
                        for (int i = 1; i < listaNotas.size(); i++) {
                            notasCurrent.add(listaNotas.get(i));
                        }

                        setOnClickListener();
                        NotesAdapter notesAdapter = new NotesAdapter(notasCurrent, listenerAdapter,user_email);

                        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                        layoutManager.setOrientation(RecyclerView.VERTICAL);
                        rvNotes.setLayoutManager(layoutManager);

                        rvNotes.setAdapter(notesAdapter);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return allNotesView;
    }

    private void setOnClickListener() {
        listenerAdapter = new NotesAdapter.RecyclerViewListener() {
            @Override
            public void onClick(View v, int position) {

                if(listenerAdapter != null){

                    Bundle bundle = new Bundle();
                    bundle.putString("titulo2", listaNotas.get(position+1).get("titulo"));
                    bundle.putString("mensagem2", listaNotas.get(position+1).get("mensagem"));

                    ((NotesFragment)getParentFragment()).button.setText("Ver Notas");
                    (((NotesFragment)getParentFragment()).newNoteFragment).setArguments(bundle);
                    ((NotesFragment)getParentFragment()).replaceFragment(((NotesFragment)getParentFragment()).newNoteFragment);

                }

            }
        };
    }
}