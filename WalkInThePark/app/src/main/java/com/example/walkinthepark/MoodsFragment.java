package com.example.walkinthepark;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;

public class MoodsFragment extends Fragment {

    static View moodsView;

    static AllMoodsFragment allMoodsFragment;
    static NewMoodFragment newMoodFragment;

    MaterialButton buttonM;

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

        buttonM = moodsView.findViewById(R.id.button_moods);
        replaceFragment(allMoodsFragment);
        if(getArguments().getString("fragment") != null){
            String str = getArguments().getString("fragment");
            switch (str) {
                case "fragNM":
                    buttonM.setText("Ver Humores");
                    replaceFragment(newMoodFragment);
                    break;
                case "fragM":
                    buttonM.setText("Adicionar Humor");
                    replaceFragment(allMoodsFragment);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + str);
            }
        }


        buttonM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(buttonM.getText().equals("Adicionar Humor")) {
                    buttonM.setText("Ver Humores");
                    newMoodFragment = new NewMoodFragment();
                    replaceFragment(newMoodFragment);
                } else if (buttonM.getText().equals("Ver Humores")) {
                    buttonM.setText("Adicionar Humor");
                    replaceFragment(allMoodsFragment);
                }
            }
        });

        return moodsView;
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_moods, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();
    }
}