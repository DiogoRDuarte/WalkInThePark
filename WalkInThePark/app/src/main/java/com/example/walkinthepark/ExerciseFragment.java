package com.example.walkinthepark;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ExerciseFragment extends Fragment {

    static View exerciseView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        exerciseView = inflater.inflate(R.layout.fragment_exercise, container, false);
        return exerciseView;
    }
}