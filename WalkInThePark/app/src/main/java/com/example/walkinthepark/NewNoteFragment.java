package com.example.walkinthepark;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NewNoteFragment extends Fragment {

    static View newNoteView;
    private TextView titulo;
    private TextView nota;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        newNoteView = inflater.inflate(R.layout.fragment_new_note, container, false);
        Button cancelButton = newNoteView.findViewById(R.id.buttonCancelar);
        Button addButton = newNoteView.findViewById(R.id.buttonAdicionar);

        titulo = (EditText) newNoteView.findViewById(R.id.tituloNota);
        nota = (EditText) newNoteView.findViewById(R.id.descricaoNota);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tituloS = titulo.getText().toString();
                String notaS = nota.getText().toString();

                if(tituloS.equals("") || notaS.equals("")){
                    Toast toast = Toast.makeText(getContext(), "Escolhe um Titulo e uma Nota!", Toast.LENGTH_SHORT);
                    toast.show();

                }else {
                    Note note = new Note(tituloS, notaS);
                    ((NotesActivity) getActivity()).adicionarNota(note);

                    Toast toast = Toast.makeText(getContext(), "Nota Adicionada!", Toast.LENGTH_SHORT);
                    toast.show();
                }

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction().remove(NewNoteFragment.this).commit();
                ((NotesActivity)getActivity()).button.setText("Adicionar Nota");
                ((NotesActivity)getActivity()).replaceFragment(((NotesActivity)getActivity()).notesFragment);
            }
        });

        return newNoteView;
    }
}