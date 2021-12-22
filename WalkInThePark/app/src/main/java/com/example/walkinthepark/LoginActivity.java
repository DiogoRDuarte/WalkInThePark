package com.example.walkinthepark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class LoginActivity extends AppCompatActivity {
    private FirebaseDatabase db;
    private DatabaseReference myRef;
    private List<String> listEmails = new ArrayList<String>();


    private User user;
    private String n;
    private String email;
    private String password;
    boolean log = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText eMail = findViewById(R.id.editTextEmail);
        EditText ePass = findViewById(R.id.editTextPassword);

        CircularProgressButton button = findViewById(R.id.cirRegisterButton);
        db = FirebaseDatabase.getInstance("https://walk-in-the-park---cm-default-rtdb.firebaseio.com/");
        myRef = db.getReference("User");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = String.valueOf(eMail.getText());
                password = String.valueOf(ePass.getText());
                if(email.equals("") || password.equals("")){
                    Toast toast = Toast.makeText(getApplicationContext(), "Preenche os campos obrigatorios!", Toast.LENGTH_SHORT);
                    toast.show();

                }else {
                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot ds: snapshot.getChildren()){
                                String s = ds.child("email").getValue().toString();
                                String p = ds.child("password").getValue().toString();
                                String n = ds.child("nome").getValue().toString();
                                boolean b = ds.child("paciente").getValue(Boolean.class);
                                if(email.equals(s) && password.equals(p)){
                                    log = true;
                                    Toast.makeText(getApplicationContext(), "Bem vindo "+n+"!", Toast.LENGTH_SHORT).show();
                                    if(b == true){
                                       goToPatMain(view);
                                    }else
                                    goToPhyMain(view);
                                }
                            }

                            if(!log) {
                                Toast.makeText(getApplicationContext(), "Email ou password errados!", Toast.LENGTH_SHORT).show();
                                ePass.setText("");
                                eMail.setText("");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(LoginActivity.this, "Erro", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }


    public void goToRegister (View view){
        Intent i = new Intent(this, StartActivity.class);
        startActivity(i);
        finish();
    }

    public void goToPatMain(View view) {
        Intent i = new Intent(this, UserHomeActivity.class);
        i.putExtra("user", n);
        startActivity(i);
        finish();
    }

    public void goToPhyMain(View view) {
        Intent i = new Intent(this, ProfHomeActivity.class);
        i.putExtra("user", n);
        startActivity(i);
        finish();
    }
}