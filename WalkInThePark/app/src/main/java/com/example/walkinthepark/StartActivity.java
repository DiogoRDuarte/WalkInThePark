package com.example.walkinthepark;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;


public class StartActivity extends AppCompatActivity {
    private FirebaseDatabase db;
    private DatabaseReference myRef;

    private User user;
    private String nome;
    private String email;
    private String password;
    private String fisioID;
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
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

}