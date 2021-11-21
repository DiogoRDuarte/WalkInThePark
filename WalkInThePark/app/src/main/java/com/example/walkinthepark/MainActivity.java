package com.example.walkinthepark;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Metodo que permite ir para a atividade de exercicios
     * @param view vista da atividade de exercicios
     */
    public void goToExercise (View view){
        Intent i = new Intent(this, ExerciseActivity.class);
        startActivity(i);
    }

    /**
     * Metodo que permite ir para a atividade de anotacoes
     * @param view vista da atividade de anotacoes
     */
    public void goToAnnotation (View view){
        Intent i = new Intent(this, AnnotationActivity.class);
        startActivity(i);
    }

    /**
     * Metodo que permite ir para a atividade de rastreio de humor
     * @param view vista da atividade de rastreio de humor
     */
    public void goToMood (View view){
        Intent i = new Intent(this, MoodActivity.class);
        startActivity(i);
    }

    /**
     * Metodo que permite ir para a atividade de lembretes
     * @param view vista da atividade de lembretes
     */
    public void goToReminder (View view){
        Intent i = new Intent(this, ReminderActivity.class);
        startActivity(i);
    }

    /**
     * Metodo que permite ir para a atividade de calibracao
     * @param view vista da atividade calibracao
     */
    public void goToCalibration (View view){
        Intent i = new Intent(this, CalibrationActivity.class);
        startActivity(i);
    }

    /**
     * Metodo que permite ir para a atividade de definicoes
     * @param view vista da atividade de definicoes
     */
    public void goToSettings (View view){
        Intent i = new Intent(this, SettingsActivity.class);
        startActivity(i);
    }

    /**
     * Metodo que permite ir para a atividade de ajuda/sobre
     * @param view vista da atividade de ajuda/sobre
     */
    public void goToHelp (View view){
        Intent i = new Intent(this, AboutActivity.class);
        startActivity(i);
    }


}