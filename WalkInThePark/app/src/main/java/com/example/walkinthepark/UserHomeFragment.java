package com.example.walkinthepark;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.warkiz.widget.IndicatorSeekBar;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UserHomeFragment extends Fragment {

    static View userView;

    private FirebaseDatabase db;
    private DatabaseReference refNotas;
    private DatabaseReference refReminders;
    private DatabaseReference myRef;
    private String user_name;
    private Context context = getContext();
    private Map mapUsers = new HashMap<String, User>();
    private String user_email;
    private String nomeF;
    private String emailF;
    private String passwordF;
    private boolean flag = true;
    private int nMoods;

    private NotesUserAdapter.RecyclerViewListener listenerAdapter;
    private RemindersUserAdapter.RecyclerViewListener listenerAdapterII;
    private RemindersUserAdapter.RecyclerViewListener listenerAdapterIII;
    // Notas
    ArrayList<HashMap<String, String>> listaNotas;
    ArrayList<HashMap<String, String>> listaLembretes;

    private ArrayList<HashMap<String, String>> notasCurrent;
    private ArrayList<HashMap<String, String>> lembretesCurrent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        userView = inflater.inflate(R.layout.fragment_user_home, container, false);


        MaterialButton verLembsButton = userView.findViewById(R.id.verLembretes);
        MaterialButton criarLembButton = userView.findViewById(R.id.adicionarLembrete);
        MaterialButton criarNotaButton = userView.findViewById(R.id.adicionarNota);
        MaterialButton calibrarButton = userView.findViewById(R.id.calibrar);
        /*MaterialButton editar1Button = userView.findViewById(R.id.editar1);
        MaterialButton editar2Button = userView.findViewById(R.id.editar2);*/
        MaterialButton submeterMood = userView.findViewById(R.id.submeterMoods);
        MaterialCardView videoCard = userView.findViewById(R.id.video);
        MaterialButton verMood = userView.findViewById(R.id.verMood);
        IndicatorSeekBar barraMood = userView.findViewById(R.id.barraMood);
        RecyclerView rvNotesUser = (RecyclerView) userView.findViewById(R.id.rvNotesUser);
        RecyclerView rvRems = (RecyclerView) userView.findViewById(R.id.rvLembretes);
        /*RecyclerView rvRem2 = (RecyclerView) userView.findViewById(R.id.lembrete2);*/
        db = FirebaseDatabase.getInstance("https://walk-in-the-park---cm-default-rtdb.firebaseio.com/");
        refNotas = db.getReference("Note");
        refReminders = db.getReference("Reminder");
        myRef = db.getReference("User");
        Map m = new HashMap<String,Map>();


        //Notas
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    user_email =((UserHomeActivity)getActivity()).user_email;
                    user_name =((UserHomeActivity)getActivity()).user_name;

                    if (ds.child("email").getValue().toString().equals(user_email)) {
                        listaNotas = (ArrayList<HashMap<String, String>>) ds.child("listaNotas").getValue();
                        listaLembretes = (ArrayList<HashMap<String, String>>) ds.child("listaLembretes").getValue();
                        notasCurrent = new ArrayList<>();
                        lembretesCurrent = new ArrayList<>();
                        for (int i = 1; i < listaNotas.size(); i++) {
                            notasCurrent.add(listaNotas.get(i));
                        }
                        for (int i = 1; i < listaLembretes.size(); i++) {
                            lembretesCurrent.add(listaLembretes.get(i));
                        }
                        // NOTAS
                        if(lembretesCurrent.size() > 0){
                            HashMap<String,String> primeiro = lembretesCurrent.get(0);
                            //HashMap<String,String> segundo = lembretesCurrent.get(1);
                            RemindersUserAdapter remindersUserAdapterI = new RemindersUserAdapter(primeiro,listenerAdapterII);
                            //RemindersUserAdapter remindersUserAdapterII = new RemindersUserAdapter(segundo,listenerAdapterIII);
                            LinearLayoutManager man = new LinearLayoutManager(context);
                            //LinearLayoutManager manI = new LinearLayoutManager(context);
                            man.setOrientation(RecyclerView.HORIZONTAL);
                            //manI.setOrientation(RecyclerView.VERTICAL);
                            rvRems.setLayoutManager(man);
                            rvRems.setAdapter(remindersUserAdapterI);
                            //rvRem2.setLayoutManager(manI);
                            //rvRem2.setAdapter(remindersUserAdapterII);
                        }

                        setOnClickListener();
                        NotesUserAdapter notesUserAdapter = new NotesUserAdapter(notasCurrent, listenerAdapter);

                        LinearLayoutManager layoutManager = new LinearLayoutManager(context);

                        layoutManager.setOrientation(RecyclerView.VERTICAL);

                        rvNotesUser.setLayoutManager(layoutManager);
                        rvNotesUser.setAdapter(notesUserAdapter);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Moods
        ValueEventListener  listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int mood = barraMood.getProgress();
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                Date date = new Date();
                String hora = formatter.format(date);
                Mood newMood = new Mood(hora, mood);

                for (DataSnapshot ds : snapshot.getChildren()) {
                    if (ds.child("email").getValue().toString().equals(user_email) ) {
                        ArrayList a = (ArrayList) ((Map) ds.getValue()).get("listaMoods");
                            nomeF = ds.child("nome").getValue().toString();
                            emailF = ds.child("email").getValue().toString();
                            passwordF = ds.child("password").getValue().toString();
                            a.add(newMood.toMap());

                            HashMap result = new HashMap<>();
                            result.put("nome", nomeF);
                            result.put("email", emailF);
                            result.put("password", passwordF);
                            result.put("paciente", true);
                            result.put("fisioID", ds.child("fisioID").getValue());
                            result.put("listaMoods", a);
                            result.put("listaLembretes", ds.child("listaLembretes").getValue());
                            result.put("listaNotas", ds.child("listaNotas").getValue());

                            mapUsers.put(user_email, result);
                            Toast.makeText(getContext(), "Moods adicionado!", Toast.LENGTH_SHORT).show();
                            myRef.updateChildren(mapUsers);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "CANCELO!", Toast.LENGTH_LONG).show();
            }
        };


        // BUTTONS
        submeterMood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.addListenerForSingleValueEvent(listener);
            }
        });


        verLembsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("fragment", "frag1");
                /*((UserHomeActivity) getActivity()).remindersFragment.setArguments(bundle);*/
                Navigation.findNavController(userView).navigate(R.id.action_menuAc_to_lembretesAc, bundle);
                /*((UserHomeActivity) getActivity()).replaceFragment(((UserHomeActivity) getActivity()).remindersFragment);*/
            }
        });

        criarLembButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("fragment", "frag2");
                /*((UserHomeActivity) getActivity()).remindersFragment.setArguments(bundle);*/
                Navigation.findNavController(userView).navigate(R.id.action_menuAc_to_lembretesAc, bundle);
                /*((UserHomeActivity) getActivity()).replaceFragment(((UserHomeActivity) getActivity()).remindersFragment);*/
            }
        });

        criarNotaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("fragment", "fragN");
                /*((UserHomeActivity) getActivity()).notesFragment.setArguments(bundle);*/
                Navigation.findNavController(userView).navigate(R.id.action_menuAc_to_notasAc, bundle);
                /*((UserHomeActivity) getActivity()).replaceFragment(((UserHomeActivity) getActivity()).notesFragment);*/
            }
        });

        calibrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(userView).navigate(R.id.action_menuAc_to_calibracaoAc);
            }
        });

        /*editar1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        editar2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/

        // CARDS
        videoCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(userView).navigate(R.id.action_menuAc_to_videosAc);
            }
        });

        verMood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(userView).navigate(R.id.action_menuAc_to_humorAc);
            }
        });

        return userView;
    }

    private void setOnClickListener() {
        listenerAdapter = new NotesUserAdapter.RecyclerViewListener() {
            @Override
            public void onClick(View v, int position) {

                if(listenerAdapter != null){
                    //FAZER

                    Bundle bundle = new Bundle();
                    bundle.putString("fragment", "fragN");
                    bundle.putString("titulo", listaNotas.get(position+1).get("titulo"));
                    bundle.putString("mensagem", listaNotas.get(position+1).get("mensagem"));
                    Navigation.findNavController(userView).navigate(R.id.action_menuAc_to_notasAc, bundle);
                }

            }
        };
    }

    private void adicionaMood(Mood newMood) {



    }
}