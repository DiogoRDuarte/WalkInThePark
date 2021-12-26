package com.example.walkinthepark;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class CalibrationFragment extends Fragment {

    static View calibrationView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        calibrationView = inflater.inflate(R.layout.fragment_calibration, container, false);

        ImageView i = calibrationView.findViewById(R.id.imageViewC);

        return calibrationView;
    }
}