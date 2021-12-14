package com.example.walkinthepark;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {
    private String nome;
    private String password;
    private boolean fisioterapeuta;
    private String fisioID;
    private String email;
    private List<Note> notas;
    private List<Reminder> lembretes;


    public User(String nome, String email, String password, String fisioID, boolean fisioSN) {
        this.nome = nome;
        this.password = password;
        this.email = email;
        this.fisioterapeuta = fisioSN;
        if(!fisioID.equals("")){
            this.fisioID = fisioID;
        }
    }

    public String getNome() {
        return this.nome;
    }

    public String getPassword(){
        return this.password;
    }

    public String getEmail() {
        return this.email;
    }

    public boolean isFisioterapeuta() {
        return fisioterapeuta;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFisioterapeuta(boolean fisioterapeuta) {
        this.fisioterapeuta = fisioterapeuta;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Map toMap() {
        HashMap result = new HashMap<>();
        result.put("nome", nome);
        result.put("email", email);
        result.put("password", password);
        result.put("fisioterapeuta", fisioterapeuta);
        result.put("fisioID", fisioID);

        return result;
    }

}