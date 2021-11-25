package com.example.walkinthepark;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class NotesFragment extends Fragment {

    static View notesView;
    private TextView notas;
    private ArrayList<Note> listaNotas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        notesView = inflater.inflate(R.layout.fragment_notes, container, false);


        listaNotas = ((NotesActivity) getActivity()).getListaNotas();
        notas = notesView.findViewById(R.id.listaNotas);

        StringBuilder m = new StringBuilder("");

        for(Note r: listaNotas){
            m.append("Titulo: "+r.getTitulo()+"\n\nDescrição: "+r.getMensagem()+"\n\n\n");
        }

        if(m.toString().equals("")){
            notas.setText("Não Existem Lembretes!");
        }else
            notas.setText(m.toString());

        return notesView;
    }
}