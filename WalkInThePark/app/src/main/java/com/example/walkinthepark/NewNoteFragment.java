package com.example.walkinthepark;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class NewNoteFragment extends Fragment {

    static View newNoteView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        newNoteView = inflater.inflate(R.layout.fragment_new_note, container, false);
        return newNoteView;
    }
}