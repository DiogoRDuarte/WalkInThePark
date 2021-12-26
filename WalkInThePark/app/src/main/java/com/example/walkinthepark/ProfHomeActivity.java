package com.example.walkinthepark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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
import java.util.concurrent.atomic.AtomicMarkableReference;

public class ProfHomeActivity extends AppCompatActivity {

    private DatabaseReference myRef;
    private FirebaseDatabase db;
    private  ArrayList<Map> pacientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof_home);

        MaterialButton verVideos = findViewById(R.id.verVideos);
        MaterialButton criarVideo = findViewById(R.id.adicionarVideo);
        MaterialButton verPacientes = findViewById(R.id.verPacientes);

        TextView pc1 = findViewById(R.id.paciente1Name);
        TextView pc2 = findViewById(R.id.paciente2Name);
        TextView pc3 = findViewById(R.id.paciente3Name);
        TextView pc4 = findViewById(R.id.paciente4Name);

        String user_email = getIntent().getStringExtra("user_email");
        db = FirebaseDatabase.getInstance("https://walk-in-the-park---cm-default-rtdb.firebaseio.com/");
        myRef = db.getReference("User");
        Map m = new HashMap<String,Map>();
        List<HashMap<String, User>> list = new ArrayList<>();

        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if(ds.child("email").getValue().toString().equals(user_email)){
                        pacientes = (ArrayList<Map>) ds.child("listaPacientes").getValue();
                    }
                }

                if(pacientes.get(0) != null)
                    pc1.setText(pacientes.get(0).get("nome")+"");
                if(pacientes.get(1) != null)
                    pc2.setText(pacientes.get(1).get("nome")+"");
                if(pacientes.get(2) != null)
                    pc3.setText(pacientes.get(2).get("nome")+"");
                if(pacientes.get(3) != null)
                    pc4.setText(pacientes.get(3).get("nome")+"");



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


    }
}