package com.example.walkinthepark;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AllRemindersFragment extends Fragment {

    ArrayList<HashMap<String, String>> listaLembretes;
    private RemindersAdapter.RecyclerViewListener listenerAdapter;
    private DatabaseReference myRef;
    private FirebaseDatabase db;
    String user_email;

    private ArrayList<HashMap<String, String>> lembretesCurrent;
    private Context context = this.getContext();
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_all_reminders, container, false);


        db = FirebaseDatabase.getInstance("https://walk-in-the-park---cm-default-rtdb.firebaseio.com/");
        myRef = db.getReference("User");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lembretesCurrent = new ArrayList<>();
                RecyclerView rvReminders = (RecyclerView) view.findViewById(R.id.rvReminders);
                if(user_email == null)
                user_email =((UserHomeActivity)getActivity()).user_email;
                
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if (ds.child("email").getValue().toString().equals(user_email)) {
                        listaLembretes = (ArrayList) ((Map) ds.getValue()).get("listaLembretes");
                        for (int i = 1; i < listaLembretes.size(); i++) {
                            lembretesCurrent.add(listaLembretes.get(i));
                        }

                        RemindersAdapter remindersAdapter = new RemindersAdapter(lembretesCurrent,listenerAdapter,user_email);

                        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                        layoutManager.setOrientation(RecyclerView.VERTICAL);
                        rvReminders.setLayoutManager(layoutManager);

                        rvReminders.setAdapter(remindersAdapter);

                    }
                }

            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return  view;
    }

}