package com.example.walkinthepark;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.VideoView;

import com.google.android.material.button.MaterialButton;

public class ExerciseFragment extends Fragment {

    static View exerciseView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        exerciseView = inflater.inflate(R.layout.fragment_exercise, container, false);
        //RecyclerView rv = exerciseView.findViewById(R.id.);
        MaterialButton record = exerciseView.findViewById(R.id.button_exercises);
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                startActivityForResult(i, 1);
            }
        });

        return exerciseView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            VideoView videoView = new VideoView(getContext());
            Uri videoUri = data.getData();
            videoView.setVideoURI(videoUri);
            videoView.start();
            builder.setView(videoView).show();
            // GUARDAR O videoUri na Firebase
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}