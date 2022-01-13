package com.example.walkinthepark;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PatientsFragment extends Fragment {

    View patientsView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        patientsView = inflater.inflate(R.layout.fragment_patients, container, false);
        return patientsView;
    }
}