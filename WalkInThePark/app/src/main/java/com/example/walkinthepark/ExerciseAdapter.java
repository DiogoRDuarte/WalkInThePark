package com.example.walkinthepark;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ViewHolder> {
    private final String s;
    private ExerciseAdapter.RecyclerViewListener listener;
    private ArrayList<HashMap<String, String>> mExercicios;

    public ExerciseAdapter(ArrayList<HashMap<String,String>> ex, RecyclerViewListener listener, String mail){
        this.mExercicios = ex;
        this.listener = listener;
        this.s = mail;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View videoView = inflater.inflate(R.layout.single_video,parent,false);
        ExerciseAdapter.ViewHolder viewHolder = new ExerciseAdapter.ViewHolder(videoView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int position2 = position;
        HashMap<String, String> video = mExercicios.get(position2);
        String s = video.get("recurso");
        Bitmap b = ThumbnailUtils.createVideoThumbnail(s,MediaStore.Images.Thumbnails.MINI_KIND);
        BitmapDrawable a = new BitmapDrawable(b);
        VideoView videoView = holder.videoView;
        videoView.setBackgroundDrawable(a);
        //VideoView videoView = holder.videoView;
        //Uri uri = Uri.parse(video.get("recurso"));
        //videoView.setVideoURI(uri);
        //videoView.start();
    }

    @Override
    public int getItemCount() {
        return mExercicios.size();
    }

    public interface RecyclerViewListener {
        void onClick(View v, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public VideoView videoView;
        public TextView textView;
        public ViewHolder(View itemView){
            super(itemView);
            videoView = (VideoView) itemView.findViewById(R.id.imgVideos);
            textView = (TextView) itemView.findViewById(R.id.titVideo);

        }
        @Override
        public void onClick(View view) {
            if(listener != null && getAdapterPosition() != RecyclerView.NO_POSITION){
                listener.onClick(view, getAdapterPosition());
            }
        }
    }
}
