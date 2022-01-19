package com.example.walkinthepark;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {
    private String nome;
    private String password;
    private boolean paciente;
    private String fisioID;
    private String email;
    private List<Note> listaNotas;
    private List<Reminder> listaLembretes;
    private List<User> listaPacientes;
    private List<Mood> listaMoods;
    private List<Exercise> listaExercicios;


    public User(String nome, String email, String password, String fisioID, boolean fisioSN) {
        this.nome = nome;
        this.password = password;
        this.email = email;
        this.paciente = fisioSN;
        this.listaPacientes = new ArrayList<User>();
        this.listaLembretes = new ArrayList<Reminder>();
        this.listaNotas = new ArrayList<Note>();
        this.listaMoods = new ArrayList<Mood>();
        this.listaExercicios = new ArrayList<Exercise>();

        this.listaNotas.add(new Note("",""));
        this.listaLembretes.add(new Reminder("", "", ""));
        this.listaMoods.add(new Mood("", -1));
        this.listaExercicios.add(new Exercise(""));

        if(!paciente)
            listaPacientes.add(new User("", "", "", "", true));

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
        return paciente;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFisioterapeuta(boolean fisioterapeuta) {
        this.paciente = fisioterapeuta;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addPaciente(User user){listaPacientes.add(user);}

    public void addMood(Mood mood){listaMoods.add(mood);}

    public void addExercise(Exercise exercise){listaExercicios.add(exercise);}


    public Map toMap() {
        HashMap result = new HashMap<>();
        result.put("nome", nome);
        result.put("email", email);
        result.put("password", password);
        result.put("paciente", paciente);
        result.put("fisioID", fisioID);
        result.put("listaPacientes", listaPacientes);
        result.put("listaNotas", this.listaNotas);
        result.put("listaLembretes", this.listaLembretes);
        result.put("listaMoods", this.listaMoods);
        result.put("listaExercicios",this.listaExercicios);
        return result;
    }


}