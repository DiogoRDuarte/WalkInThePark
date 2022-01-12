package com.example.walkinthepark;

import static android.app.Activity.RESULT_OK;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;

import java.net.URI;


public class ExerciseProfFragment extends Fragment {

    static View exerView;
    int SELECT_VIDEO = 200;

    RecyclerView recyclerView;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        exerView = inflater.inflate(R.layout.fragment_exercise_prof, container, false);

        // FAZER ADAPTER PARA MOSTRAR VIDEOS
        // AARANJAR FORMA DE PODER ADICIONAR VIDEO A PACIENTES E APAGAR VIDEOS
        recyclerView = exerView.findViewById(R.id.rvExercisesProf);
        GridLayoutManager layoutManager = new GridLayoutManager(this.getContext(), 2);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        /*recyclerView.setAdapter(exAdapter);*/

        MaterialButton upload = exerView.findViewById(R.id.button_upload_ex);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(getActivity());
                upVideo();
            }
        });

        return exerView;
    }

    private void upVideo() {
        Intent i = new Intent();
        i.setType("video/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(i, "Selecione um Vídeo"), SELECT_VIDEO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == SELECT_VIDEO && resultCode == RESULT_OK) {
            Uri selectedVideo = data.getData();
            progressDialog.setTitle("A carregar...");
            progressDialog.show();
            // ADICIONAR VIDEO À FIREBASE!!
        }
    }
}



