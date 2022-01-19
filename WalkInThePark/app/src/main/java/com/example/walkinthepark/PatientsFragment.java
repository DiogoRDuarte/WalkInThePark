package com.example.walkinthepark;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class PatientsFragment extends Fragment {

    View patientsView;

    private DatabaseReference myRef;
    private FirebaseDatabase db;
    private Context context = this.getContext();
    private ArrayList<Map> pacientes;

    private PatientsAdapter.RecyclerViewListener listenerAdapter;
    String prof_email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        patientsView = inflater.inflate(R.layout.fragment_patients, container, false);

        RecyclerView rvPacientes = (RecyclerView) patientsView.findViewById(R.id.rvPacientesProf);

        db = FirebaseDatabase.getInstance("https://walk-in-the-park---cm-default-rtdb.firebaseio.com/");
        myRef = db.getReference("User");
        ArrayList<String> listNomesPacs = new ArrayList<>();

        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if(prof_email== null) prof_email = ((ProfHomeActivity)getActivity()).prof_email;

                    if (ds.child("email").getValue().toString().equals(prof_email)) {
                        pacientes = (ArrayList<Map>) ds.child("listaPacientes").getValue();
                    }
                }

                if (pacientes != null && pacientes.size() > 1) {

                    for (int i = 1; i < pacientes.size(); i++) {
                        if (pacientes.get(i) != null) {
                            String pn = pacientes.get(i).get("nome") + "";
                            listNomesPacs.add(pn);
                        }
                    }

                    PatientsAdapter patientsAdapter = new PatientsAdapter(listNomesPacs, listenerAdapter);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                    layoutManager.setOrientation(RecyclerView.VERTICAL);
                    rvPacientes.setLayoutManager(layoutManager);

                    rvPacientes.setAdapter(patientsAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return patientsView;
    }
}