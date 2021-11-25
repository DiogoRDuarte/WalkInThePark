package com.example.walkinthepark;

public class Reminder {
    private String hora;
    private String data;
    private String mensagem;

    public Reminder(String hora, String data, String mensagem){
        this.data = data;
        this.hora = hora;
        this.mensagem = mensagem;
    }

    public String toString(){
        return this.mensagem + " " + this.data + " " + this.hora;
    }


    public String getHora(){
        return this.hora;
    }

    public String getData(){
        return this.data;
    }

    public String getMensagem(){
        return  this.mensagem;
    }
}
