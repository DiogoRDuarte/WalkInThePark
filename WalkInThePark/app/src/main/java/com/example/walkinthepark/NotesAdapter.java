package com.example.walkinthepark;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private ArrayList<HashMap<String, String>> mNotes;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tituloTextView;
        public TextView mensagemTextView;
        public ImageButton deleteButton;

        public ViewHolder(View itemView) {
            super(itemView);

            tituloTextView = (TextView) itemView.findViewById(R.id.txtTitulo);
            mensagemTextView = (TextView) itemView.findViewById(R.id.txtMensagem);
            deleteButton = (ImageButton) itemView.findViewById(R.id.deleteButton);
        }
    }

    public NotesAdapter(ArrayList<HashMap<String, String>> notes){
        mNotes = notes;
    }


    @Override
    public NotesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View notesView = inflater.inflate(R.layout.single_note,parent,false);
        NotesAdapter.ViewHolder viewHolder = new NotesAdapter.ViewHolder(notesView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NotesAdapter.ViewHolder holder, int position) {
        HashMap<String, String> note = mNotes.get(position);
        TextView textViewTitulo = holder.tituloTextView;
        textViewTitulo.setText(note.get("titulo"));
        TextView textViewMensagem = holder.mensagemTextView;
        textViewMensagem.setText(note.get("mensagem"));
        ImageButton delButton = holder.deleteButton;
        delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //HashMap<String, String> rem = mNotes.get(holder.getAdapterPosition());
                mNotes.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mNotes.size());
                holder.itemView.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }
}

