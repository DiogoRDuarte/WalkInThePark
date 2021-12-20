package com.example.walkinthepark;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;


public class StartActivity extends AppCompatActivity {
    private FirebaseDatabase db;
    private DatabaseReference myRef;
    private List<String> listEmails = new ArrayList<String>();
    private List<User> listUsers = new ArrayList<User>();
    private User user;
    private String nome;
    private String email;
    private String password;
    private String fisioID;
    private Map mapUsers = new HashMap<String, User>();
    private Map u;
    //boolean fisio;
    boolean pat;
    boolean a = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        EditText eNome = findViewById(R.id.editTextName);
        EditText eMail = findViewById(R.id.editTextEmail);
        EditText ePass = findViewById(R.id.editTextPassword);
        EditText eToken = findViewById(R.id.editTextTokenFisio);
        RadioButton rb = findViewById(R.id.radio_paciente);
        RadioButton rb1 = findViewById(R.id.radio_fisio);
        CircularProgressButton button = findViewById(R.id.cirRegisterButton);

        ImageView iv = findViewById(R.id.witp);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToPatMain(view);
            }
        });

        db = FirebaseDatabase.getInstance("https://walk-in-the-park---cm-default-rtdb.firebaseio.com/");
        myRef = db.getReference("User");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nome = String.valueOf(eNome.getText());
                email = String.valueOf(eMail.getText());
                password = String.valueOf(ePass.getText());
                fisioID = String.valueOf(eToken.getText());
                pat = rb.isChecked();
                //fisio = rb1.isChecked();

                if (nome.equals("") || email.equals("") || password.equals("")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Preenche os campos obrigatorios!", Toast.LENGTH_SHORT);
                    toast.show();


                } else {
                    user = new User(nome, email, password, fisioID, pat);
                    Map userValues = user.toMap();

                    myRef.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot ds: snapshot.getChildren()){
                                String s = ds.child("email").getValue().toString();
                                listEmails.add(s);
                            }


                            if(listEmails.contains(email) && a){
                                Toast.makeText(getApplicationContext(), "Ja existe um utilizador com este email!", Toast.LENGTH_SHORT).show();
                                eNome.setText("");
                                ePass.setText("");
                                eMail.setText("");
                                eToken.setText("");

                            }else if(!fisioID.equals("") && !listEmails.contains(fisioID)) {
                                Toast.makeText(getApplicationContext(), "Não Existe um Fisioterapeuta com esse email!", Toast.LENGTH_SHORT).show();
                                eToken.setText("");

                            }else{

                                if(a) {
                                    User f;
                                    //myRef.child("User").child(email);
                                    mapUsers.put(email, userValues);
                                   // User fiso = (User) mapUsers.get(fisioID);
                                   // fiso.addPaciente(user);
                                    Toast.makeText(getApplicationContext(), "Registo bem-sucedido!", Toast.LENGTH_SHORT).show();

                                 //   if(!fisioID.equals("")){
                                 //       for(User u: listUsers){
                                  //          if(u.getEmail().equals(fisioID)){
                                 //               u.addPaciente(user);
                                 //           }
                                 //       }
                                 //   }

                                    myRef.updateChildren(mapUsers);

                                    if(!pat){
                                        goToPhyMain(view);
                                    }else{
                                        goToPatMain(view);
                                    }

                                    a = false;

                                }
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
        /*finish();*/
    }

    public void goToPatMain(View view) {
        Intent i = new Intent(this, UserHomeActivity.class);
        i.putExtra("nome",nome);
        startActivity(i);
        /*finish();*/
    }

    public void goToPhyMain(View view) {
        Intent i = new Intent(this, ProfHomeActivity.class);
        i.putExtra("nome",nome);
        startActivity(i);
        /*finish();*/
    }


}