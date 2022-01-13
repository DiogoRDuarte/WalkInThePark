package com.example.walkinthepark;

import java.util.HashMap;
import java.util.Map;

public class Mood {
    private String hora;
    private int mood;

    public Mood(String hora, int mood){
        this.mood = mood;
        this.hora = hora;
    }

    public String getHora(){return this.hora;}
    public int getMood() {return mood;}

    public String toString(){
        return this.hora + " " + this.mood;}


    public Map toMap(){
        HashMap h = new HashMap<>();
        h.put("hora",hora);
        h.put("mood",mood);
        return h;
    }
}
