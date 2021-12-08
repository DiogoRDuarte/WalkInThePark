package com.example.walkinthepark;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;


public class StartActivity extends AppCompatActivity {
    private FirebaseDatabase db;
    private DatabaseReference myRef;
    private List<String> listEmails = new ArrayList<String>();
    private User user;
    private String nome;
    private String email;
    private String password;
    private String fisioID;
    private Map mapUsers = new HashMap<String, User>();
    boolean fisio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        EditText eNome = findViewById(R.id.editTextName);
        EditText eMail = findViewById(R.id.editTextEmail);
        EditText ePass = findViewById(R.id.editTextPassword);
        EditText eToken = findViewById(R.id.editTextTokenFisio);
        RadioButton rb = findViewById(R.id.radio_paciente);
        CircularProgressButton button = findViewById(R.id.cirRegisterButton);

        db = FirebaseDatabase.getInstance("https://walk-in-the-park---cm-default-rtdb.firebaseio.com/");
        myRef = db.getReference("User");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nome = String.valueOf(eNome.getText());
                email = String.valueOf(eMail.getText());
                password = String.valueOf(ePass.getText());
                fisioID = String.valueOf(eToken.getText());
                fisio = rb.isSelected();

                if (nome.equals("") || email.equals("") || password.equals("")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Preenche os campos obrigatorios!", Toast.LENGTH_SHORT);
                    toast.show();


                } else {
                    user = new User(nome, email, password, fisioID, fisio);
                    Map userValues = user.toMap();

                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot ds: snapshot.getChildren()){
                                String s = ds.child("email").getValue().toString();
                                listEmails.add(s);
                            }


                            if(listEmails.contains(email)){
                                Toast.makeText(getApplicationContext(), "Ja existe um utilizador com este email!", Toast.LENGTH_SHORT).show();
                                eNome.setText("");
                                ePass.setText("");
                                eMail.setText("");

                            }else {


                                //myRef.child("User").child(email);
                                mapUsers.put(email, userValues);
                                Toast.makeText(getApplicationContext(), "Registo bem-sucedido!", Toast.LENGTH_SHORT).show();
                                myRef.updateChildren(mapUsers);
                                goToMain(view);

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });


    }


    public void goToLogin(View view) {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    public void goToMain(View view) {
        Intent i = new Intent(this, UserHomeActivity.class);
        startActivity(i);
        finish();
    }


}