package com.example.walkinthepark;

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
}
