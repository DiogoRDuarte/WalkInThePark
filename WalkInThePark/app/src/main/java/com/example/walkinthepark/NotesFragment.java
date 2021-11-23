package com.example.walkinthepark;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class NotesFragment extends Fragment {

    static View notesView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        notesView = inflater.inflate(R.layout.fragment_notes, container, false);
        return notesView;
    }
}