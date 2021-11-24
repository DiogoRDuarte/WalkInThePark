package com.example.walkinthepark;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class NewNoteFragment extends Fragment {

    static View newNoteView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        newNoteView = inflater.inflate(R.layout.fragment_new_note, container, false);

        Button cancelButton = newNoteView.findViewById(R.id.buttonCancelar);
        Button addButton = newNoteView.findViewById(R.id.buttonAdicionar);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction().remove(NewNoteFragment.this).commit();
                /*getParentFragmentManager().popBackStack();*/
                ((NotesActivity)getActivity()).button.setText("Adicionar Nota");
                ((NotesActivity)getActivity()).replaceFragment(((NotesActivity)getActivity()).notesFragment);
            }
        });

        return newNoteView;
    }
}