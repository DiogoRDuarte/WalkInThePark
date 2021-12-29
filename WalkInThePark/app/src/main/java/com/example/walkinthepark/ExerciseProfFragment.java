package com.example.walkinthepark;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ExerciseProfFragment extends Fragment {

    View exerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        exerView = inflater.inflate(R.layout.fragment_exercise_prof, container, false);
        return exerView;
    }
}