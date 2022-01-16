package com.example.walkinthepark;

import java.util.HashMap;
import java.util.Map;

public class Exercise {
    private String uri;

    public Exercise (String recurso){
        this.uri = recurso;
    }

    public String getUri() {
        return this.uri;
    }

    public Map toExerMap(){
        HashMap res = new HashMap();
        res.put("recurso",uri);
        return res;
    }
}
