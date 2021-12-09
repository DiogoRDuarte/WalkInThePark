package com.example.walkinthepark;

import java.util.HashMap;
import java.util.Map;

public class Reminder {
    private String hora;
    private String data;
    private String mensagem;

    public Reminder(String hora, String data, String mensagem){
        this.data = data;
        this.hora = hora;
        this.mensagem = mensagem;
    }
    //Descricao Textual
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

    public Map toMap(){
        HashMap h = new HashMap<>();
        h.put("hora",hora);
        h.put("data",data);
        h.put("mensagem",mensagem);
        return h;
    }
}
