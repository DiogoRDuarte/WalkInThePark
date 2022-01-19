package com.example.walkinthepark;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;


public class RegisterActivity extends AppCompatActivity {
    private FirebaseDatabase db;
    private DatabaseReference myRef;
    private List<String> listEmails = new ArrayList<String>();
    private User user;
    private String nome;
    private String email;
    private String password;
    private String fisioID;
    private Map mapUsers = new HashMap<String, User>();
    boolean pat;
    boolean a = true;

    private String nomeF;
    private String emailF;
    private String passwordF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

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
                goToPhyMain(view);
            }
        });

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio_fisio) {
                    eToken.setVisibility(View.GONE);
                    Toast a = Toast.makeText(getApplicationContext(), "Esquerda", Toast.LENGTH_SHORT);
                    a.show();
                } else {
                    eToken.setVisibility(View.VISIBLE);
                    Toast b = Toast.makeText(getApplicationContext(), "Direita", Toast.LENGTH_SHORT);
                    b.show();
                }
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


                if (nome.equals("") || email.equals("") || password.equals("")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Preenche os campos obrigatorios!", Toast.LENGTH_SHORT);
                    toast.show();


                } else {
                    user = new User(nome, email, password, fisioID, pat);
                    Map userValues = user.toMap();

                    myRef.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot ds : snapshot.getChildren()) {
                                String s = ds.child("email").getValue().toString();
                                listEmails.add(s);
                            }


                            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                            if (!email.trim().matches(emailPattern)) {
                                Toast.makeText(getApplicationContext(), "Por favor insira um Email no formato: exemplo@gmail.com", Toast.LENGTH_LONG).show();
                            } else if (listEmails.contains(email) && a) {
                                Toast.makeText(getApplicationContext(), "Ja existe um utilizador com este email!", Toast.LENGTH_SHORT).show();
                                eNome.setText("");
                                ePass.setText("");
                                eMail.setText("");
                                eToken.setText("");

                            } else if (!fisioID.equals("") && !listEmails.contains(fisioID)) {
                                Toast.makeText(getApplicationContext(), "Não Existe um Fisioterapeuta com esse email!", Toast.LENGTH_SHORT).show();
                                eToken.setText("");

                            } else {

                                if (a) {

                                    email = encodeForFirebaseKey(email);
                                    fisioID = encodeForFirebaseKey(fisioID);

                                    if (!fisioID.equals("")) {
                                        for (DataSnapshot ds : snapshot.getChildren()) {

                                            fisioID = decodeFromFirebaseKey(fisioID);

                                            if (ds.child("email").getValue().toString().equals(fisioID)) {
                                                fisioID = encodeForFirebaseKey(fisioID);
                                                nomeF = ds.child("nome").getValue().toString();
                                                emailF = ds.child("email").getValue().toString();
                                                passwordF = ds.child("password").getValue().toString();
                                                ArrayList a = (ArrayList) ((Map) ds.getValue()).get("listaPacientes");
                                                a.add(user.toMap());


                                                HashMap result = new HashMap<>();
                                                result.put("nome", nomeF);
                                                result.put("email", emailF);
                                                result.put("password", passwordF);
                                                result.put("paciente", false);
                                                result.put("fisioID", "");
                                                result.put("listaPacientes", a);
                                                result.put("listaNotas", ds.child("listaNotas").getValue());
                                                result.put("listaLembretes", ds.child("listaLembretes").getValue());
                                                result.put("listaMoods", ds.child("listaMoods").getValue());
                                                result.put("listaExercicios", ds.child("listaExercicios").getValue());

                                                mapUsers.put(fisioID, result);
                                            }
                                        }
                                    }

                                    mapUsers.put(email, userValues);
                                    Toast.makeText(getApplicationContext(), "Registo bem-sucedido!", Toast.LENGTH_SHORT).show();
                                }

                                myRef.updateChildren(mapUsers);

                                if (!pat) {
                                    goToPhyMain(view);
                                } else {
                                    goToPatMain(view);
                                }

                                a = false;

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

    public void goToPatMain(View view) {
        Intent i = new Intent(this, UserHomeActivity.class);
        i.putExtra("user_email", email + "");
        i.putExtra("user_name", nome + "");
        startActivity(i);
        finish();
    }

    public void goToPhyMain(View view) {
        Intent i = new Intent(this, ProfHomeActivity.class);
        i.putExtra("user_email", email + "");
        i.putExtra("user_name", nome + "");
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


    public static String decodeFromFirebaseKey(String s) {
        int i = 0;
        int ni;
        String res = "";
        while ((ni = s.indexOf("_", i)) != -1) {
            res += s.substring(i, ni);
            if (ni + 1 < s.length()) {
                char nc = s.charAt(ni + 1);
                if (nc == '_') {
                    res += '_';
                } else if (nc == 'P') {
                    res += '.';
                } else if (nc == 'D') {
                    res += '$';
                } else if (nc == 'H') {
                    res += '#';
                } else if (nc == 'O') {
                    res += '[';
                } else if (nc == 'C') {
                    res += ']';
                } else if (nc == 'S') {
                    res += '/';
                } else {
                    // this case is due to bad encoding
                }
                i = ni + 2;
            } else {
                // this case is due to bad encoding
                break;
            }
        }
        res += s.substring(i);
        return res;
    }
}