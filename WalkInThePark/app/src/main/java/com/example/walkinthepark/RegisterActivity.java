package com.example.walkinthepark;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    FirebaseDatabase db;
    DatabaseReference myRef;
    User user;
    String nome;
    String password;
    int idade;
    boolean ch;
    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText e = findViewById(R.id.editTextTextPersonName);
        nome = String.valueOf(e.getText());
        EditText e1 = findViewById(R.id.editTextTextPersonName2);
        password = String.valueOf(e1.getText());
        EditText e2 = findViewById(R.id.editTextTextPersonName3);
        idade = Integer.valueOf(String.valueOf(e2.getText()));
        CheckBox c = findViewById(R.id.checkBox);
        ch = c.isSelected();
        b = findViewById(R.id.button4);
        db = FirebaseDatabase.getInstance();
        myRef = db.getReference();
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData(nome, password,idade,ch);
            }
        });
    }

    public void sendData(String nome, String password, int idade, boolean b){
        user.setNome(nome);
        user.setPassword(password);
        user.setIdade(idade);
        user.setFisioterapeuta(b);

    }
}