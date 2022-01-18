package com.example.walkinthepark;

import static android.app.Activity.RESULT_OK;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ExerciseProfFragment extends Fragment {

    static View exerView;
    int SELECT_VIDEO = 200;
    FirebaseDatabase db;
    DatabaseReference ref;
    DatabaseReference reference1;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    Uri video;
    String prof_email;
    private Map mapUsers = new HashMap<String, User>();
    private String nomeF;
    private String emailF;
    private String passwordF;
    private boolean p = true;
    ArrayList<HashMap<String, String>> listaExercises;
    private ArrayList<HashMap<String, String>> exercisesCurrent;
    private ExerciseAdapter.RecyclerViewListener listenerAdapter;
    private Context context = this.getContext();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        exerView = inflater.inflate(R.layout.fragment_exercise_prof, container, false);

        // FAZER ADAPTER PARA MOSTRAR VIDEOS
        // AARANJAR FORMA DE PODER ADICIONAR VIDEO A PACIENTES E APAGAR VIDEOS
        //recyclerView = exerView.findViewById(R.id.rvExercisesProf);
        //GridLayoutManager layoutManager = new GridLayoutManager(this.getContext(), 2);
        RecyclerView recyclerView = exerView.findViewById(R.id.rvExercisesProf);
        db = FirebaseDatabase.getInstance("https://walk-in-the-park---cm-default-rtdb.firebaseio.com/");
        ref = db.getReference("User");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    prof_email = ((ProfHomeActivity) getActivity()).prof_email;
                    if(ds.child("email").getValue().equals(prof_email)){
                        listaExercises = (ArrayList<HashMap<String, String>>) ds.child("listaExercicios").getValue();
                        exercisesCurrent = new ArrayList<>();

                        for (int i = 1; i < listaExercises.size(); i++) {
                            exercisesCurrent.add(listaExercises.get(i));
                        }
                        //setOnClickListener();
                        ExerciseAdapter exerciseAdapter = new ExerciseAdapter(exercisesCurrent, listenerAdapter,prof_email);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                        layoutManager.setOrientation(RecyclerView.VERTICAL);
                        recyclerView.setLayoutManager(layoutManager);

                        recyclerView.setAdapter(exerciseAdapter);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //GridLayoutManager layoutManager = new GridLayoutManager(this.getContext(), 2);

        reference1 = FirebaseDatabase.getInstance("https://walk-in-the-park---cm-default-rtdb.firebaseio.com/").getReference("User");
        /*recyclerView.setAdapter(exAdapter);*/
        this.prof_email = ((ProfHomeActivity) getActivity()).prof_email;

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

        startActivityForResult(i,5);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 5 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            video = data.getData();
            progressDialog.setTitle("A carregar...");
            progressDialog.show();
            mandarVideo();
            // ADICIONAR VIDEO À FIREBASE!!
        }
    }

    private String getTipo(Uri video){
        ContentResolver c = getContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(c.getType(video));
    }
    private void mandarVideo(){
        if(video != null){
            /*db = FirebaseDatabase.getInstance("https://walk-in-the-park---cm-default-rtdb.firebaseio.com/");
            ref = db.getReference("User");*/
            final StorageReference reference = FirebaseStorage.getInstance("gs://walk-in-the-park---cm.appspot.com").getReference("Files/" + System.currentTimeMillis() + "." + getTipo(video));
            reference.putFile(video).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!uriTask.isSuccessful()) ;
                    // get the link of video
                    String downloadUri = uriTask.getResult().toString();

                    /*HashMap<String, String> map = new HashMap<>();
                    map.put("videolink", downloadUri);*/
                    Exercise e = new Exercise(downloadUri);
                    //HashMap<String, String> map = (HashMap<String, String>) e.toExerMap();
                    reference1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot ds : snapshot.getChildren()){
                                if(ds.child("email").getValue().equals(prof_email)){
                                    nomeF = ds.child("nome").getValue().toString();
                                    emailF = ds.child("email").getValue().toString();
                                    passwordF = ds.child("password").getValue().toString();
                                    ArrayList a = (ArrayList) ds.child("listaExercicios").getValue();
                                    a.add(e.toExerMap());

                                    HashMap result = new HashMap<>();
                                    result.put("nome", nomeF);
                                    result.put("email", emailF);
                                    result.put("password", passwordF);
                                    result.put("paciente", false);
                                    result.put("fisioID", ds.child("fisioID").getValue());
                                    result.put("listaNotas", ds.child("listaNotas").getValue());
                                    result.put("listaLembretes", ds.child("listaLembretes").getValue());
                                    result.put("listaMoods", ds.child("listaMoods").getValue());
                                    result.put("listaPacientes",ds.child("listaPacientes").getValue());
                                    result.put("listaExercicios",a);

                                    mapUsers.put(prof_email, result);
                                }
                            }
                            if(p) {
                                Toast.makeText(getContext(), "Video adicionada!", Toast.LENGTH_SHORT).show();
                                reference1.updateChildren(mapUsers);
                                p= false;

                                /*goToMain(view);*/
                                //((NotesFragment)getParentFragment()).button.setText("Adicionar Nota");
                                //((NotesFragment)getParentFragment()).replaceFragment(((NotesFragment)getParentFragment()).allNotesFragment);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    // Video uploaded successfully
                    // Dismiss dialog
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Video Uploaded!!", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Error, Image not uploaded
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                // Progress Listener for loading
                // percentage on the dialog box
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    // show the progress bar
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploaded " + (int) progress + "%");
                }
            });
        }
    }
}




