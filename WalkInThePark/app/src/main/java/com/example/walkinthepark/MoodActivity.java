package com.example.walkinthepark;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class MoodActivity extends AppCompatActivity {

    public static Fragment moodsFragment;
    public static Fragment newMoodFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood);

        if (newMoodFragment== null){
            newMoodFragment = new NewMoodFragment();
        }

        if (moodsFragment== null){
            moodsFragment = new MoodsFragment();
        }
        replaceFragment(moodsFragment);
    }

    public void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flFragment, fragment);
        fragmentTransaction.commit();

    }
}