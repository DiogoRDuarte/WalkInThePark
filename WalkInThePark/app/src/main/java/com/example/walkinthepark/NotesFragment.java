package com.example.walkinthepark;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NotesFragment extends Fragment {

    static View notesView;

    static AllNotesFragment allNotesFragment;
    static NewNoteFragment newNoteFragment;
    ArrayList<HashMap<String, String>> listaNotas = new ArrayList<>();;
    String user_email;

    private DatabaseReference myRef;
    private FirebaseDatabase db;

    Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        notesView = inflater.inflate(R.layout.fragment_notes, container, false);
        user_email =((UserHomeActivity)getActivity()).user_email;
        db = FirebaseDatabase.getInstance("https://walk-in-the-park---cm-default-rtdb.firebaseio.com/");
        myRef = db.getReference("User");

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void replaceFragment(Fragment fragment) {
        /*FragmentManager fragmentManager = getActivity().getSupportFragmentManager();*/
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_notes, fragment);
        fragmentTransaction.commit();
    }


}