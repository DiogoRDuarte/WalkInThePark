package com.example.walkinthepark;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MoodsFragment extends Fragment {

    static View moodsView;

    static AllMoodsFragment allMoodsFragment;
    static NewMoodFragment newMoodFragment;

    MaterialButton button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        moodsView = inflater.inflate(R.layout.fragment_moods, container, false);

        if (newMoodFragment== null){
            newMoodFragment = new NewMoodFragment();
        }
        if (allMoodsFragment== null){
            allMoodsFragment = new AllMoodsFragment();
        }

        button = moodsView.findViewById(R.id.button_moods);
        replaceFragment(allMoodsFragment);

        String str = getArguments().getString("fragment");
        switch (str) {
            case "fragNM":
                button.setText("Ver Humores");
                replaceFragment(newMoodFragment);
                break;
            case "fragM":
                button.setText("Adicionar Humor");
                replaceFragment(allMoodsFragment);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + str);
        }

        return moodsView;
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_moods, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}