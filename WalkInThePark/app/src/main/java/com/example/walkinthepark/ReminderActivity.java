package com.example.walkinthepark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ReminderActivity extends AppCompatActivity {
    private static ReminderFragment reminderFragment;
    private static NewReminderFragment newReminderFragment;
    private ArrayList<Reminder> listaLembretes = new ArrayList<Reminder>();;

    private DatabaseReference refReminder;
    private FirebaseDatabase db;

    Button bAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        db = FirebaseDatabase.getInstance("https://walk-in-the-park---cm-default-rtdb.firebaseio.com/");
        refReminder = db.getReference("Reminder");



        if(newReminderFragment == null){
            newReminderFragment = new NewReminderFragment();
        }

        if(reminderFragment == null){
            reminderFragment = new ReminderFragment();
        }

        replaceFragment(reminderFragment);

        String intentFragment = getIntent().getExtras().getString("fragment");
        switch (intentFragment) {
            case "frag1":
                replaceFragment(reminderFragment);
                break;
            case "frag2":
                replaceFragment(newReminderFragment);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + intentFragment);
        }
        refReminder.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    String s = ds.child("mensagem").getValue().toString();
                    String hora = ds.child("hora").getValue().toString();
                    String data = ds.child("data").getValue().toString();
                    Reminder r = new Reminder(hora,data,s);
                    listaLembretes.add(r);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        bAdd = findViewById(R.id.button_add);
        bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bAdd.getText().equals("Adicionar")) {
                    bAdd.setText("Ver Lembretes");
                    newReminderFragment = new NewReminderFragment();
                    replaceFragment(newReminderFragment);
                } else if (bAdd.getText().equals("Ver Lembretes")) {
                    bAdd.setText("Adicionar");
                    replaceFragment(reminderFragment);
                }
            }
        });



    }


    private void replaceFragment(Fragment reminderFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.all_reminders,reminderFragment);
        fragmentTransaction.commit();
    }

    public ArrayList<Reminder> getListaReminders(){
        return listaLembretes;
    }

    public void adicionarLembrete(Reminder lem){
        this.listaLembretes.add(lem);
    }
}