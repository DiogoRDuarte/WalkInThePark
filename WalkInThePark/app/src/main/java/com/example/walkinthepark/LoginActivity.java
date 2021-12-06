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

    private User user;
    private String email;
    private String password;
    List<User> listUsers = new ArrayList<User>();
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
                    user = getUser();
                    /* DEIXO EM COMENTARIO P DEPOIS TESTAR
                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            listUsers.clear();
                            for(DataSnapshot postSnapshot: snapshot.getChildren()){
                                User user = postSnapshot.getValue(User.class);
                                listUsers.add(user);
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(LoginActivity.this, "Erro", Toast.LENGTH_SHORT).show();
                        }
                    });

                    for (User u : listUsers){
                        if((email.equals(u.getEmail()) && !password.equals(u.getPassword())) || (!email.equals(u.getEmail()) && password.equals(u.getPassword())) ){
                            Toast.makeText(LoginActivity.this, "Email ou password incorretos", Toast.LENGTH_SHORT).show();
                        }
                    }*/

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