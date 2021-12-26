package com.example.walkinthepark;

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

    private DatabaseReference myRef;
    private FirebaseDatabase db;
    String user_email;
    /*private TextView mensagens;*/

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_all_reminders, container, false);

        listaLembretes =  ((RemindersFragment)getParentFragment()).listaLembretes;

        RecyclerView rvReminders = (RecyclerView) view.findViewById(R.id.rvReminders);

        /*mensagens = view.findViewById(R.id.lembretes);*/
        user_email =((UserHomeActivity)getActivity()).user_email;

        db = FirebaseDatabase.getInstance("https://walk-in-the-park---cm-default-rtdb.firebaseio.com/");
        myRef = db.getReference("User");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if (ds.child("email").getValue().toString().equals(user_email)) {
                        listaLembretes = (ArrayList) ((Map) ds.getValue()).get("listaLembretes");

                        /*StringBuilder m = new StringBuilder("");

                        for(int i = 1; i < listaLembretes.size(); i++){
                            HashMap<String, String> a = listaLembretes.get(i);

                            m.append("Data: "+ a.get("data")+"\nHora: "+a.get("hora")+"\nLembrete "+a.get("mensagem")+"\n\n");
                        }

                        if(m.toString().equals("")){
                            mensagens.setText("Não Existem Lembretes!");
                        }else
                            mensagens.setText(m.toString());*/
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        listaLembretes.remove(0);
        RemindersAdapter remindersAdapter = new RemindersAdapter(listaLembretes);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        rvReminders.setLayoutManager(layoutManager);

        rvReminders.setAdapter(remindersAdapter);

        return  view;
    }

}