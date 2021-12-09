package com.example.walkinthepark;

import java.util.HashMap;
import java.util.Map;

public class Note {
    private String titulo;
    private String mensagem;

    public Note(String titulo, String mensagem){
        this.titulo = titulo;
        this.mensagem = mensagem;
    }

    public String toString(){
        return this.mensagem + " " + this.titulo + " " + this.mensagem;
    }

    public String getTitulo(){
        return this.titulo;
    }

    public String getMensagem(){
        return  this.mensagem;
    }

    public Map toMap(){
        HashMap h = new HashMap<>();
        h.put("titulo",titulo);
        h.put("mensagem",mensagem);
        return h;
    }
}
