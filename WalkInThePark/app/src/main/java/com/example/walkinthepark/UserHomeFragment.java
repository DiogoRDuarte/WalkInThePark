package com.example.walkinthepark;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class UserHomeFragment extends Fragment {

    static View userView;

    private FirebaseDatabase db;
    private DatabaseReference refNotas;
    private DatabaseReference refReminders;
    private DatabaseReference myRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        userView = inflater.inflate(R.layout.fragment_user_home, container, false);

        MaterialButton verLembsButton = userView.findViewById(R.id.verLembretes);
        MaterialButton criarLembButton = userView.findViewById(R.id.adicionarLembrete);
        MaterialButton criarNotaButton = userView.findViewById(R.id.adicionarNota);
        MaterialButton calibrarButton = userView.findViewById(R.id.calibrar);
        MaterialButton editar1Button = userView.findViewById(R.id.editar1);
        MaterialButton editar2Button = userView.findViewById(R.id.editar2);

        MaterialCardView videoCard = userView.findViewById(R.id.video);
        db = FirebaseDatabase.getInstance("https://walk-in-the-park---cm-default-rtdb.firebaseio.com/");
        refNotas = db.getReference("Note");
        refReminders = db.getReference("Reminder");
        myRef = db.getReference("User");
        Map m = new HashMap<String,Map>();


        // BUTTONS
        verLembsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("fragment", "frag1");
                ((UserHomeActivity) getActivity()).remindersFragment.setArguments(bundle);
                ((UserHomeActivity) getActivity()).replaceFragment(((UserHomeActivity) getActivity()).remindersFragment);
            }
        });

        criarLembButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("fragment", "frag2");
                ((UserHomeActivity) getActivity()).remindersFragment.setArguments(bundle);
                ((UserHomeActivity) getActivity()).replaceFragment(((UserHomeActivity) getActivity()).remindersFragment);
            }
        });

        criarNotaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("fragment", "fragN");
                ((UserHomeActivity) getActivity()).notesFragment.setArguments(bundle);
                ((UserHomeActivity) getActivity()).replaceFragment(((UserHomeActivity) getActivity()).notesFragment);
            }
        });

        calibrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        editar1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        editar2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        // CARDS
        videoCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return userView;
    }
}