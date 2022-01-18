package com.example.smartwatch;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.smartwatch.databinding.ActivityExerciseBinding;

public class ExerciseActivity extends Activity {

    private Button exButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_exercise);

        exButton = (Button) findViewById(R.id.startExercise);
        exButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt = (String) exButton.getText();
                switch (txt) {
                    case "Começar Exercício":
                        // começar a recolher dados
                        exButton.setText("Terminar Exercício");
                        break;
                    case "Terminar Exercício":
                        // parar de recolher dados
                        // guardar dados
                        exButton.setText("Começar Exercício");
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + txt);
                }
            }
        });
    }
}