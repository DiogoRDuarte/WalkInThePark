package com.example.walkinthepark;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class NewMoodFragment extends Fragment {

    static View newMoodView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        newMoodView = inflater.inflate(R.layout.fragment_new_mood, container, false);
        return newMoodView;
    }
}