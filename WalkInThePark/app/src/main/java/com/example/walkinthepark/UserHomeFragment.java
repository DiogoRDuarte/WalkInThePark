package com.example.walkinthepark;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserHomeFragment extends Fragment {

    static View userView;

    private FirebaseDatabase db;
    private DatabaseReference refNotas;
    private DatabaseReference refReminders;
    private DatabaseReference myRef;
    Context context = getContext();
    String user_email;
    // Notas
    ArrayList<HashMap<String, String>> listaNotas;
    private ArrayList<HashMap<String, String>> notasCurrent;

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

        RecyclerView rvNotesUser = (RecyclerView) userView.findViewById(R.id.rvNotesUser);

        db = FirebaseDatabase.getInstance("https://walk-in-the-park---cm-default-rtdb.firebaseio.com/");
        refNotas = db.getReference("Note");
        refReminders = db.getReference("Reminder");
        myRef = db.getReference("User");
        Map m = new HashMap<String,Map>();
        user_email =((UserHomeActivity)getActivity()).user_email;

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    if (ds.child("email").getValue().toString().equals(user_email)) {
                        listaNotas = (ArrayList) ((Map) ds.getValue()).get("listaNotas");
                        notasCurrent = new ArrayList<>();
                        for (int i = 1; i < listaNotas.size(); i++) {
                            notasCurrent.add(listaNotas.get(i));
                        }

                        // NOTAS
                        NotesUserAdapter notesUserAdapter = new NotesUserAdapter(notasCurrent);

                        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                        layoutManager.setOrientation(RecyclerView.VERTICAL);
                        rvNotesUser.setLayoutManager(layoutManager);

                        rvNotesUser.setAdapter(notesUserAdapter);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // BUTTONS
        verLembsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("fragment", "frag1");
                /*((UserHomeActivity) getActivity()).remindersFragment.setArguments(bundle);*/
                Navigation.findNavController(userView).navigate(R.id.action_menuAc_to_lembretesAc, bundle);
                /*((UserHomeActivity) getActivity()).replaceFragment(((UserHomeActivity) getActivity()).remindersFragment);*/
            }
        });

        criarLembButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("fragment", "frag2");
                /*((UserHomeActivity) getActivity()).remindersFragment.setArguments(bundle);*/
                Navigation.findNavController(userView).navigate(R.id.action_menuAc_to_lembretesAc, bundle);
                /*((UserHomeActivity) getActivity()).replaceFragment(((UserHomeActivity) getActivity()).remindersFragment);*/
            }
        });

        criarNotaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("fragment", "fragN");
                /*((UserHomeActivity) getActivity()).notesFragment.setArguments(bundle);*/
                Navigation.findNavController(userView).navigate(R.id.action_menuAc_to_notasAc, bundle);
                /*((UserHomeActivity) getActivity()).replaceFragment(((UserHomeActivity) getActivity()).notesFragment);*/
            }
        });

        calibrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(userView).navigate(R.id.action_menuAc_to_calibracaoAc);
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
                Navigation.findNavController(userView).navigate(R.id.action_menuAc_to_videosAc);
            }
        });

        return userView;
    }
}