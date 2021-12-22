package com.example.walkinthepark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class UserHomeActivity extends AppCompatActivity {
    private DatabaseReference refNotas;
    private DatabaseReference refReminders;
    private FirebaseDatabase db;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        MaterialButton verLembsButton = findViewById(R.id.verLembretes);
        MaterialButton criarLembButton = findViewById(R.id.adicionarLembrete);
        MaterialButton criarNotaButton = findViewById(R.id.adicionarNota);
        MaterialButton calibrarButton = findViewById(R.id.calibrar);
        MaterialButton editar1Button = findViewById(R.id.editar1);
        MaterialButton editar2Button = findViewById(R.id.editar2);

        MaterialCardView videoCard = findViewById(R.id.video);
        db = FirebaseDatabase.getInstance("https://walk-in-the-park---cm-default-rtdb.firebaseio.com/");
        refNotas = db.getReference("Note");
        refReminders = db.getReference("Reminder");
        myRef = db.getReference("User");
        Map m = new HashMap<String,Map>();
        String s = "email";
        /*myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    if(ds.child("email").getValue().toString().equals(s)){
                        Map<String, User> m = (Map<String, User>) ds.getValue();
                        User u = m.get(s);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
        // BUTTONS
        verLembsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserHomeActivity.this, ReminderActivity.class);
                i.putExtra("fragment", "frag1");
                startActivity(i);
            }
        });

        criarLembButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserHomeActivity.this, ReminderActivity.class);
                i.putExtra("fragment", "frag2");
                startActivity(i);
            }
        });

        criarNotaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserHomeActivity.this, NotesActivity.class);
                i.putExtra("fragment", "fragN");
                startActivity(i);
            }
        });

        calibrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserHomeActivity.this, CalibrationActivity.class);
                startActivity(i);
            }
        });

        editar1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ALTERAR
                /*Intent i = new Intent(UserHomeActivity.this, CalibrationActivity.class);
                startActivity(i);*/
            }
        });

        editar2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ALTERAR
                /*Intent i = new Intent(UserHomeActivity.this, CalibrationActivity.class);
                startActivity(i);*/
            }
        });

        // CARDS
        videoCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ALTERAR
            }
        });
    }
}