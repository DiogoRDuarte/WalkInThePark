package com.example.walkinthepark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof_home);

        MaterialButton verVideos = findViewById(R.id.verVideos);
        MaterialButton criarVideo = findViewById(R.id.adicionarVideo);
        MaterialButton verPacientes = findViewById(R.id.verPacientes);

        db = FirebaseDatabase.getInstance("https://walk-in-the-park---cm-default-rtdb.firebaseio.com/");
        myRef = db.getReference("User");
        Map m = new HashMap<String,Map>();
        List<HashMap<String, User>> list = new ArrayList<>();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ds : snapshot.getChildren()){
                    String sEmail = ds.child("email").getValue().toString();
                    String sPassword = ds.child("password").getValue().toString();
                    String sNome = ds.child("nome").getValue().toString();
                    String sFisio = ds.child("fisioID").getValue().toString();
                    boolean pac = ds.child("paciente").getValue(Boolean.class);
                    User u = new User(sNome, sEmail,sPassword,sFisio,pac);
                    if(pac){
                        m.put(sEmail,u.toMap());
                    }

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



    }
}