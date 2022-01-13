package com.example.walkinthepark;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class NotesUserAdapter extends RecyclerView.Adapter<NotesUserAdapter.ViewHolder>  {

    private RecyclerViewListener listener;
    private ArrayList<HashMap<String, String>> mNotesUser;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView tituloTextView;
        private RecyclerViewListener listener;

        public ViewHolder(View itemView) {
            super(itemView);

            tituloTextView = (TextView) itemView.findViewById(R.id.txtTitulo);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition());
        }
    }

    public NotesUserAdapter(ArrayList<HashMap<String, String>> notes, RecyclerViewListener listener){
        mNotesUser = notes;
        this.listener = listener;
    }


    @Override
    public NotesUserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View notesView = inflater.inflate(R.layout.single_note_user,parent,false);
        NotesUserAdapter.ViewHolder viewHolder = new NotesUserAdapter.ViewHolder(notesView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NotesUserAdapter.ViewHolder holder, int position) {
        HashMap<String, String> note = mNotesUser.get(position);
        TextView textViewTitulo = holder.tituloTextView;
        textViewTitulo.setText(note.get("titulo"));
    }

    @Override
    public int getItemCount() {
        if(mNotesUser.size() > 3) {
            return 3;
        }
        return mNotesUser.size();
    }

    public interface RecyclerViewListener {
        void onClick(View v, int position);
    }
}
