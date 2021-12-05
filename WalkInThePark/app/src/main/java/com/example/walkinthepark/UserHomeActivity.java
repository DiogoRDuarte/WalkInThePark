package com.example.walkinthepark;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

public class UserHomeActivity extends AppCompatActivity {

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

        // BUTTONS
        verLembsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserHomeActivity.this, ReminderActivity.class);
                i.putExtra("fragment", "frag1");
                startActivity(i);
                finish();
            }
        });

        criarLembButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserHomeActivity.this, ReminderActivity.class);
                i.putExtra("fragment", "frag2");
                startActivity(i);
                finish();
            }
        });

        criarNotaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserHomeActivity.this, NotesActivity.class);
                i.putExtra("fragment", "fragN");
                startActivity(i);
                finish();
            }
        });

        calibrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserHomeActivity.this, CalibrationActivity.class);
                startActivity(i);
                finish();
            }
        });

        editar1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ALTERAR
                /*Intent i = new Intent(UserHomeActivity.this, CalibrationActivity.class);
                startActivity(i);
                finish();*/
            }
        });

        editar2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ALTERAR
                /*Intent i = new Intent(UserHomeActivity.this, CalibrationActivity.class);
                startActivity(i);
                finish();*/
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