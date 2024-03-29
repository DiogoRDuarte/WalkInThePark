package com.example.walkinthepark;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ExerciseFragment extends Fragment {
    Uri videoUri;
    static View exerciseView;
    private FirebaseDatabase db;
    private DatabaseReference ref;
    private ProgressDialog progressDialog;
    private String user_email;
    private String nomeF;
    private String emailF;
    private String passwordF;
    private boolean p = true;
    private MediaController mController;
    ArrayList<HashMap<String, String>> listaEx;
    private Map mapUsers = new HashMap<String, User>();
    SimpleExoPlayer exoPlayer;
    SimpleExoPlayerView exoPlayerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        exerciseView = inflater.inflate(R.layout.fragment_exercise, container, false);

        MaterialButton record = exerciseView.findViewById(R.id.button_exercises);
        db = FirebaseDatabase.getInstance("https://walk-in-the-park---cm-default-rtdb.firebaseio.com/");
        ref = db.getReference("User");
        this.user_email = ((UserHomeActivity) getActivity()).user_email;
        mController = new MediaController(this.getContext());
        exoPlayerView = exerciseView.findViewById(R.id.videoView);

        ref.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds : snapshot.getChildren()) {
                    if (ds.child("email").getValue().toString().equals(user_email)) {
                        emailF = ds.child("fisioID").getValue().toString();
                    }
                }

                for (DataSnapshot ds2 : snapshot.getChildren()) {
                    try {
                        if (ds2.child("email").getValue().toString().equals(emailF)) {
                            listaEx = (ArrayList) ((Map) ds2.getValue()).get("listaExercicios");
                            if (listaEx.get(1) != null) {
                                try {
                                    BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
                                    TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
                                    exoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
                                    Uri video = Uri.parse(listaEx.get(1).get("recurso"));
                                    DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");
                                    ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
                                    MediaSource mediaSource = new ExtractorMediaSource(video, dataSourceFactory, extractorsFactory, null, null);
                                    exoPlayerView.setPlayer(exoPlayer);
                                    exoPlayer.prepare(mediaSource);
                                    exoPlayer.setPlayWhenReady(true);
                                } catch (Exception e) {

                                }
                                Uri video = Uri.parse(listaEx.get(1).get("recurso"));
                            }
                        }

                    } catch (Exception e) {

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(getContext());
                upVideo();
            }
        });

        return exerciseView;
    }

    private void upVideo() {
        Intent i = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(i, 1);
    }

    private String getTipo(Uri video) {
        ContentResolver c = getContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(c.getType(video));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            VideoView videoView = new VideoView(getContext());
            videoUri = data.getData();
            videoView.setVideoURI(videoUri);
            videoView.start();
            builder.setTitle("Submeter Vídeo");
            builder.setMessage("Tem a certeza de que quer submeter o vídeo?");
            builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    progressDialog.setTitle("A carregar...");
                    progressDialog.show();
                    mandarVideo();
                }
            });
            builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(getContext(), "NÃO", Toast.LENGTH_SHORT).show();
                }
            });

            AlertDialog al = builder.create();
            al.setOnShowListener(new DialogInterface.OnShowListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onShow(DialogInterface dialogInterface) {
                    al.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(R.color.red);
                    al.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(R.color.green);
                }
            });

            al.show();
            // GUARDAR O videoUri na Firebase
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void mandarVideo() {
        if (videoUri != null) {
            /*db = FirebaseDatabase.getInstance("https://walk-in-the-park---cm-default-rtdb.firebaseio.com/");
            ref = db.getReference("User");*/
            final StorageReference reference = FirebaseStorage.getInstance("gs://walk-in-the-park---cm.appspot.com").getReference("Files/" + System.currentTimeMillis() + "." + getTipo(videoUri));
            reference.putFile(videoUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
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
                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot ds : snapshot.getChildren()) {
                                if (ds.child("email").getValue().equals(user_email)) {
                                    nomeF = ds.child("nome").getValue().toString();
                                    emailF = ds.child("email").getValue().toString();
                                    passwordF = ds.child("password").getValue().toString();
                                    ArrayList a = (ArrayList) ds.child("listaExercicios").getValue();
                                    a.add(e.toExerMap());

                                    HashMap result = new HashMap<>();
                                    result.put("nome", nomeF);
                                    result.put("email", emailF);
                                    result.put("password", passwordF);
                                    result.put("paciente", ds.child("paciente").getValue());
                                    result.put("fisioID", ds.child("fisioID").getValue());
                                    result.put("listaNotas", ds.child("listaNotas").getValue());
                                    result.put("listaLembretes", ds.child("listaLembretes").getValue());
                                    result.put("listaMoods", ds.child("listaMoods").getValue());
                                    //result.put("listaPacientes",ds.child("listaPacientes").getValue());
                                    result.put("listaExercicios", a);
                                    mapUsers.put(user_email, result);
                                }
                            }
                            if (p) {
                                Toast.makeText(getContext(), "Video adicionada!", Toast.LENGTH_SHORT).show();
                                ref.updateChildren(mapUsers);
                                p = false;

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Video Uploaded!!", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Carregado " + (int) progress + "%");
                }
            });
        }
    }
}