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
    private SaveSharedPreference ssp;
    private List<String> listEmails = new ArrayList<String>();


    private String nome;
    private boolean paciente;
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

        ////////////////////////////// esta logado //////////////////

        String houveLogout = getIntent().getStringExtra("logout");
        if (houveLogout != null){
            ssp.setUserName(this, ""); // tirar
            ssp.setUserEmail(this, ""); // tirar
        }

        if(SaveSharedPreference.getUserName(LoginActivity.this).length() != 0 &&
                SaveSharedPreference.getUserEmail(LoginActivity.this).length() != 0)
        {
            String nameSsp = SaveSharedPreference.getUserName(LoginActivity.this);
            String emailSsp = SaveSharedPreference.getUserEmail(LoginActivity.this);
            log = true;
            Toast.makeText(getApplicationContext(), "Bem vindo "+nameSsp+"!", Toast.LENGTH_SHORT).show();

            if (SaveSharedPreference.getRole(LoginActivity.this)){ //eh Paciente
                Intent i = new Intent(this, UserHomeActivity.class);
                i.putExtra("user_email", emailSsp+"");
                i.putExtra("user_name", nameSsp+"");
                startActivity(i);
                finish();

            } else { //eh Prof
                Intent i = new Intent(this, ProfHomeActivity.class);
                i.putExtra("user_email", emailSsp+"");
                i.putExtra("user_name", nameSsp+"");
                startActivity(i);
                finish();
            }
        }

        /////////////////////////////

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = String.valueOf(eMail.getText());
                password = String.valueOf(ePass.getText());
                if(email.equals("") || password.equals("")){
                    Toast toast = Toast.makeText(getApplicationContext(), "Preenche os campos obrigatorios!", Toast.LENGTH_SHORT);
                    toast.show();

                }else {
//                    email = encodeForFirebaseKey(email);
                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot ds: snapshot.getChildren()){
                                String s = ds.child("email").getValue().toString();
                                String p = ds.child("password").getValue().toString();
                                nome = ds.child("nome").getValue().toString();
                                paciente = ds.child("paciente").getValue(Boolean.class);
                                if(email.equals(s) && password.equals(p)){
                                    log = true;
                                    Toast.makeText(getApplicationContext(), "Bem vindo "+nome+"!", Toast.LENGTH_SHORT).show();
                                    if(paciente == true){
                                        ssp.setUserName(view.getContext(), nome);
                                        ssp.setUserEmail(view.getContext(), s);
                                        ssp.setRole(view.getContext(), true);
                                       goToPatMain(view);
                                       break;
                                    }else{
                                        ssp.setUserName(view.getContext(), nome);
                                        ssp.setUserEmail(view.getContext(), s);
                                        ssp.setRole(view.getContext(), false);
                                    goToPhyMain(view);
                                    break;}
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
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
        finish();
    }

    public void goToPatMain(View view) {
        Intent i = new Intent(this, UserHomeActivity.class);
        i.putExtra("user_email", email+"");
        i.putExtra("user_name", nome+"");
        startActivity(i);
        finish();
    }

    public void goToPhyMain(View view) {
        Intent i = new Intent(this, ProfHomeActivity.class);
        i.putExtra("user_email", email+"");
        i.putExtra("user_name", nome+"");
        startActivity(i);
        finish();
    }
    public static String encodeForFirebaseKey(String s) {
        return s
                .replace("_", "__")
                .replace(".", "_P")
                .replace("$", "_D")
                .replace("#", "_H")
                .replace("[", "_O")
                .replace("]", "_C")
                .replace("/", "_S")
                ;
    }
}

