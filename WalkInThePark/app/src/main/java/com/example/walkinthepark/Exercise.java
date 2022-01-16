package com.example.walkinthepark;

import java.util.HashMap;
import java.util.Map;

public class Exercise {
    private String uri;
    private boolean fisio ;

    public Exercise (String recurso){
        this.uri = recurso;
        this.fisio = false;
    }

    public String getUri() {
        return this.uri;
    }

    public boolean isFisio() {
        return fisio;
    }

    public void setFisio(Boolean val){
        this.fisio = val;
    }

    public Map toExerMap(){
        HashMap res = new HashMap();
        res.put("recurso",this.uri);
        res.put("fisio",String.valueOf(this.fisio));
        return res;
    }
}
