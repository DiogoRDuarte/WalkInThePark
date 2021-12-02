package com.example.walkinthepark;

public class User {
    private String nome;
    private String password;
    private int idade;
    private boolean fisioterapeuta;

    private String email;

    public User(String nome, String password, int idade, boolean fisioterapeuta){
        this.nome = nome;
        this.password = password;
        this.idade = idade;
        this.fisioterapeuta = fisioterapeuta;
    }

    public String getNome() {
        return this.nome;
    }

    public String getPassword(){
        return this.password;
    }

    public int getIdade(){
        return this.idade;
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

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}