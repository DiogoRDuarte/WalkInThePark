package com.example.walkinthepark;

public class User {
    private String nome;
    private String password;
    private boolean fisioterapeuta;
    private String fisioID;
    private String email;


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

}