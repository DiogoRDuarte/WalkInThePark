package com.example.walkinthepark;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class LoginActivity extends AppCompatActivity {
    private FirebaseDatabase db;
    private DatabaseReference myRef;

    private User user;
    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText eMail = findViewById(R.id.editTextEmail);
        EditText ePass = findViewById(R.id.editTextPassword);

        CircularProgressButton button = findViewById(R.id.cirRegisterButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = String.valueOf(eMail.getText());
                password = String.valueOf(ePass.getText());

                if(email.equals("") || password.equals("")){
                    Toast toast = Toast.makeText(getApplicationContext(), "Preenche os campos obrigatorios!", Toast.LENGTH_SHORT);
                    toast.show();

                }else {
                    user = getUser();

                    //VERIFICAR SE EXISTE

                    goToMain(view);
                }

            }

        });
    }

    private User getUser() {
        return null;
    }

    public void goToRegister (View view){
        Intent i = new Intent(this, StartActivity.class);
        startActivity(i);
        finish();
    }

    public void goToMain(View view) {
        Intent i = new Intent(this, UserHomeActivity.class);
        startActivity(i);
        finish();
    }
}