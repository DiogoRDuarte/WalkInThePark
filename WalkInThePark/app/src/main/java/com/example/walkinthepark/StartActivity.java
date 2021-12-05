package com.example.walkinthepark;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;


public class StartActivity extends AppCompatActivity {
    private FirebaseDatabase db;
    private DatabaseReference myRef;

    private User user;
    private String nome;
    private String email;
    private String password;
    private String fisioID;
    private List<User> listUser = new ArrayList<User>();
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

                if(nome.equals("") || email.equals("") || password.equals("")){
                    Toast toast = Toast.makeText(getApplicationContext(), "Preenche os campos obrigatorios!", Toast.LENGTH_SHORT);
                    toast.show();

                }else {
                    user = new User(nome, email, password, fisioID, fisio);
                    listUser.add(user);
                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            myRef.setValue(listUser);
                            Toast.makeText(StartActivity.this, "Adicionado à bd", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(StartActivity.this, "Nao Adicionado à bd", Toast.LENGTH_SHORT).show();
                        }
                    });
                    //GUARDAR NO FIREBASE!!
                    //VERIFICAR SE NÃO EXISTE UM EMAIL IGUAL

                    goToMain(view);
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