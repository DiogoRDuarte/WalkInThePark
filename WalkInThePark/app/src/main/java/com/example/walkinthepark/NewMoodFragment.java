package com.example.walkinthepark;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class NewMoodFragment extends Fragment {

    static View newMoodView;
    private RadioGroup radioGroup;
    private MaterialButton addButton;
    private MaterialButton cancelButton;
    private RadioButton radioButton;

    private String user_email;
    private Map mapUsers = new HashMap<String, User>();
    private FirebaseDatabase db;
    private DatabaseReference myRef;

    private String nomeF;
    private String emailF;
    private String passwordF;

    private boolean p = true;
    private int mood;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        newMoodView = inflater.inflate(R.layout.fragment_new_mood, container, false);

        user_email =((UserHomeActivity)getActivity()).user_email;
        db = FirebaseDatabase.getInstance("https://walk-in-the-park---cm-default-rtdb.firebaseio.com/");
        myRef = db.getReference("User");

        radioGroup = newMoodView.findViewById(R.id.rgMoods);
        addButton = newMoodView.findViewById(R.id.buttonAdicionar);
        cancelButton = newMoodView.findViewById(R.id.buttonCancelar);

        // adicionar mood
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int radioId = radioGroup.getCheckedRadioButtonId();
                radioButton = newMoodView.findViewById(radioId);
                String txt = (String) radioButton.getText();

                switch (txt) {
                    case "Magoado":
                        mood = 1;
                        break;
                    case "Chateado":
                        mood = 2;
                        break;
                    case "Triste":
                        mood = 3;
                        break;
                    case "Neutro":
                        mood = 4;
                        break;
                    case "Feliz":
                        mood = 5;
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + txt);
                }

                if(mood < 1 || mood > 5) {
                    Toast toast = Toast.makeText(getContext(), "Erro a selecionar o Humor!", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
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
                                    result.put("listaNotas", ds.child("listaNotas").getValue());
                                    result.put("listaLembretes", ds.child("listaLembretes").getValue());
                                    result.put("listaMoods", a);
                                    result.put("listaExercicios",ds.child("listaLembretes").getValue());

                                    mapUsers.put(user_email, result);
                                    Toast.makeText(getContext(), "Moods adicionado!", Toast.LENGTH_SHORT).show();
                                    myRef.updateChildren(mapUsers);
                                }
                            }

                            if(p) {
                                Toast.makeText(getContext(), "Humor adicionado!", Toast.LENGTH_SHORT).show();
                                myRef.updateChildren(mapUsers);
                                p = false;

                                ((MoodsFragment)getParentFragment()).button.setText("Adicionar Humor");
                                ((MoodsFragment)getParentFragment()).replaceFragment(((MoodsFragment)getParentFragment()).allMoodsFragment);
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(getContext(), "Erro a adicionar Humor à bd!", Toast.LENGTH_LONG).show();
                        }
                    });
                }

            }
        });

        // cancelar a adição de mood
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction().remove(NewMoodFragment.this).commit();
                ((MoodsFragment)getParentFragment()).button.setText("Adicionar Humor");
                ((MoodsFragment)getParentFragment()).replaceFragment(((MoodsFragment)getParentFragment()).allMoodsFragment);
            }
        });

        return newMoodView;
    }
}