package com.example.walkinthepark;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ReminderActivity extends AppCompatActivity {
    private static ReminderFragment reminderFragment;
    private static NewReminderFragment newReminderFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        if(newReminderFragment == null){
            newReminderFragment = new NewReminderFragment();
        }

        if(reminderFragment == null){
            reminderFragment = new ReminderFragment();
        }
        replaceFragment(reminderFragment);

        Button bAdd = findViewById(R.id.button_add);
        bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void replaceFragment(ReminderFragment reminderFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.all_reminders,reminderFragment);
        fragmentTransaction.commit();
    }
}