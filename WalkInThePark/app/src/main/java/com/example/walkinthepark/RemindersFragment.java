package com.example.walkinthepark;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RemindersFragment extends Fragment {

    static View remindersView;

    static AllRemindersFragment allRemindersFragment;
    static NewReminderFragment newReminderFragment;
    private ArrayList<Reminder> listaLembretes = new ArrayList<Reminder>();;

    private DatabaseReference refReminder;
    private FirebaseDatabase db;

    Button bAdd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        remindersView = inflater.inflate(R.layout.fragment_reminders, container, false);

        db = FirebaseDatabase.getInstance("https://walk-in-the-park---cm-default-rtdb.firebaseio.com/");
        refReminder = db.getReference("Reminder");

        if(newReminderFragment == null){
            newReminderFragment = new NewReminderFragment();
        }
        if(allRemindersFragment == null){
            allRemindersFragment = new AllRemindersFragment();
        }

        replaceFragment(allRemindersFragment);

        String str = getArguments().getString("fragment");
        switch (str) {
            case "frag1":
                replaceFragment(allRemindersFragment);
                break;
            case "frag2":
                replaceFragment(newReminderFragment);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + str);
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
        bAdd = remindersView.findViewById(R.id.button_add);
        bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bAdd.getText().equals("Adicionar")) {
                    bAdd.setText("Ver Lembretes");
                    newReminderFragment = new NewReminderFragment();
                    replaceFragment(newReminderFragment);
                } else if (bAdd.getText().equals("Ver Lembretes")) {
                    bAdd.setText("Adicionar");
                    replaceFragment(allRemindersFragment);
                }
            }
        });


        return remindersView;
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.all_reminders, fragment);
        fragmentTransaction.commit();
    }

    public ArrayList<Reminder> getListaReminders(){
        return listaLembretes;
    }

    public void adicionarLembrete(Reminder lem){
        this.listaLembretes.add(lem);
    }
}