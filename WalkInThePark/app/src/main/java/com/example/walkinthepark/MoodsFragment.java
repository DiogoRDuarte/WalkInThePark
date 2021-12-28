package com.example.walkinthepark;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MoodsFragment extends Fragment {

    static View moodsView;

    static AllMoodsFragment allMoodsFragment;
    static NewMoodFragment newMoodFragment;

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

        replaceFragment(allMoodsFragment);

        String str = getArguments().getString("fragment");
        switch (str) {
            case "fragNM":
                replaceFragment(newMoodFragment);
                break;
            case "fragM":
                replaceFragment(allMoodsFragment);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + str);
        }

        FloatingActionButton floatingActionButton = moodsView.findViewById(R.id.floatingActionButton2);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(newMoodFragment);
            }
        });

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