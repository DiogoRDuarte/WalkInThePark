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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MoodsPatientFragment extends Fragment {

    static View moodsPatientView;
    ArrayList<HashMap<String, String>> listaMoods;
    private DatabaseReference myRef;
    private FirebaseDatabase db;
    private Context context = this.getContext();
    String user_email;
    String user_name;
    TextView titulo;
    private ArrayList<HashMap<String, String>> moodsCurrent;
    private MoodAdapter.RecyclerViewListener listenerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        moodsPatientView =  inflater.inflate(R.layout.fragment_moods_patient, container, false);
        titulo = moodsPatientView.findViewById(R.id.nomePaciente);

        RecyclerView rvMoods = (RecyclerView) moodsPatientView.findViewById(R.id.rvMoods);

        db = FirebaseDatabase.getInstance("https://walk-in-the-park---cm-default-rtdb.firebaseio.com/");
        myRef = db.getReference("User");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                moodsCurrent = new ArrayList<>();
                for(DataSnapshot ds : snapshot.getChildren()){
                    Bundle bundle = getArguments();
                    if (bundle != null) {
                        user_email = (bundle.getString("email"));
                        user_name = (bundle.getString("nome"));
                        titulo.setText("Moods do Paciente " + user_name);
                    }
                    if (ds.child("email").getValue().toString().equals(user_email)) {
                        listaMoods = (ArrayList) ((Map) ds.getValue()).get("listaMoods");

                        for (int i = 1; i < listaMoods.size(); i++) {
                            moodsCurrent.add(listaMoods.get(i));
                        }

                        MoodAdapter moodAdapter = new MoodAdapter(moodsCurrent,listenerAdapter,user_email);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                        layoutManager.setOrientation(RecyclerView.VERTICAL);
                        rvMoods.setLayoutManager(layoutManager);
                        rvMoods.setAdapter(moodAdapter);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return moodsPatientView;
    }
}