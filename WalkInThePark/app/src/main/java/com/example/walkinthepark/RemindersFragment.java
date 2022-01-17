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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RemindersFragment extends Fragment {

    static View remindersView;

    static AllRemindersFragment allRemindersFragment;
    static NewReminderFragment newReminderFragment;
    ArrayList<HashMap<String, String>> listaLembretes = new ArrayList<>();;
    String user_email;

    private DatabaseReference myRef;
    private FirebaseDatabase db;

    Button bAdd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        remindersView = inflater.inflate(R.layout.fragment_reminders, container, false);
        user_email =((UserHomeActivity)getActivity()).user_email;
        db = FirebaseDatabase.getInstance("https://walk-in-the-park---cm-default-rtdb.firebaseio.com/");
        myRef = db.getReference("User");

        if(newReminderFragment == null){
            newReminderFragment = new NewReminderFragment();
        }
        if(allRemindersFragment == null){
            allRemindersFragment = new AllRemindersFragment();
        }

        bAdd = remindersView.findViewById(R.id.button_add);
        replaceFragment(allRemindersFragment);

        if (getArguments().getString("fragment") != null) {
            String str = getArguments().getString("fragment");
            switch (str) {
                case "frag1":
                    bAdd.setText("Adicionar");
                    replaceFragment(allRemindersFragment);
                    break;
                case "frag2":
                    bAdd.setText("Ver Lembretes");


                    if(getArguments().getString("data") != null &&
                            getArguments().getString("hora") != null &&
                            getArguments().getString("mensagem") != null) {
                        Bundle b = new Bundle();
                        b.putString("data2" , getArguments().getString("data"));
                        b.putString("hora2" , getArguments().getString("hora"));
                        b.putString("mensagem2" , getArguments().getString("mensagem"));
                        newReminderFragment.setArguments(b);
                    }

                    replaceFragment(newReminderFragment);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + str);
            }
        }

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    if (ds.child("email").getValue().toString().equals(user_email)) {
                        listaLembretes = (ArrayList) ((Map) ds.getValue()).get("listaLembretes");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}