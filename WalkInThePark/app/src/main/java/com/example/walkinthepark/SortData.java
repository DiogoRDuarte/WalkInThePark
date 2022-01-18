package com.example.walkinthepark;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;

public class SortData implements Comparator<HashMap<String, String>> {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int compare(HashMap<String, String> h1, HashMap<String, String> h2) {

        String d1 = h1.get("data");
        String[] p1 = d1.split("-");
        LocalDate data1 = LocalDate.of(Integer.parseInt(p1[2]), Integer.parseInt(p1[1]), Integer.parseInt(p1[0]));

        String d2 = h2.get("data");
        String[] p2 = d2.split("-");
        LocalDate data2 = LocalDate.of(Integer.parseInt(p2[2]), Integer.parseInt(p2[1]), Integer.parseInt(p2[0]));

        int cmpD = data1.compareTo(data2);
        if(cmpD != 0) {
            return cmpD;
        } else {
            String t1 = h1.get("hora");
            String[] s1 = t1.split(":");
            StringBuilder sb = new StringBuilder(s1[2]);
            sb.deleteCharAt(sb.length()-1);
            LocalTime hora1 = LocalTime.of(Integer.parseInt(s1[0]), Integer.parseInt(s1[1]), Integer.parseInt(sb.toString()));

            String t2 = h2.get("hora");
            String[] s2 = t2.split(":");
            StringBuilder sb1 = new StringBuilder(s2[2]);
            sb1.deleteCharAt(sb1.length()-1);
            LocalTime hora2 = LocalTime.of(Integer.parseInt(s2[0]), Integer.parseInt(s2[1]), Integer.parseInt(sb1.toString()));

            int cmpH = hora1.compareTo(hora2);

            return cmpH;
        }
    }
}
