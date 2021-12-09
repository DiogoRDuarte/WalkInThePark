package com.example.walkinthepark;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;

public class ProfHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof_home);

        MaterialButton verVideos = findViewById(R.id.verVideos);
        MaterialButton criarVideo = findViewById(R.id.adicionarVideo);
        MaterialButton verPacientes = findViewById(R.id.verPacientes);

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