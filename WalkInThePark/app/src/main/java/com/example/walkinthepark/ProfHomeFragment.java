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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfHomeFragment extends Fragment {

    static View profView;

    private DatabaseReference myRef;
    private FirebaseDatabase db;
    private ArrayList<Map> pacientes;

    private UsersAdapter.RecyclerViewListener listenerAdapter;
    private Context context = getContext();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        profView = inflater.inflate(R.layout.fragment_prof_home, container, false);

        MaterialButton verVideos = profView.findViewById(R.id.verVideos);
        MaterialButton criarVideo = profView.findViewById(R.id.adicionarVideo);
        MaterialButton verPacientes = profView.findViewById(R.id.verPacientes);

        RecyclerView rvExerciciosProf = (RecyclerView) profView.findViewById(R.id.rvExerciciosProf);
        RecyclerView rvPacientesProf = (RecyclerView) profView.findViewById(R.id.rvPacientesProf);

        /*TextView pc1 = profView.findViewById(R.id.paciente1Name);
        TextView pc2 = profView.findViewById(R.id.paciente2Name);
        TextView pc3 = profView.findViewById(R.id.paciente3Name);
        TextView pc4 = profView.findViewById(R.id.paciente4Name);*/

        String user_email = ((ProfHomeActivity) getActivity()).getIntent().getStringExtra("user_email");
        db = FirebaseDatabase.getInstance("https://walk-in-the-park---cm-default-rtdb.firebaseio.com/");
        myRef = db.getReference("User");
        Map m = new HashMap<String,Map>();
        ArrayList<String> listNomesPacs = new ArrayList<>();

        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if (ds.child("email").getValue().toString().equals(user_email)) {
                        pacientes = (ArrayList<Map>) ds.child("listaPacientes").getValue();
                    }
                }

                if (pacientes != null && pacientes.size() > 1) {

                    for (int i = 1; i < pacientes.size(); i++) {
                        if(pacientes.get(i) != null) {
                            String pn = pacientes.get(i).get("nome") + "";
                            listNomesPacs.add(pn);
                        }
                    }

                    UsersAdapter usersAdapter = new UsersAdapter(listNomesPacs, listenerAdapter);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                    layoutManager.setOrientation(RecyclerView.VERTICAL);
                    rvPacientesProf.setLayoutManager(layoutManager);
                    
                    rvPacientesProf.setAdapter(usersAdapter);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // BUTTONS
        /*verVideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfHomeActivity.this, VideosActivity.class);
                i.putExtra("fragment", "frag1");
                startActivity(i);
            }
        });*/

        /*criarVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfHomeActivity.this, VideosActivity.class);
                i.putExtra("fragment", "frag2");
                startActivity(i);
            }
        });*/

        /*verPacientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfHomeActivity.this, PacientesActivity.class);
                i.putExtra("fragment", "frag1");
                startActivity(i);
            }
        });*/


        return profView;
    }
}