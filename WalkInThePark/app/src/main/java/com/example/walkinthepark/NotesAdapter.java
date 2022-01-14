package com.example.walkinthepark;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private String s;
    private ArrayList<HashMap<String, String>> mNotes;
    private int position;
    private FirebaseDatabase db;
    private DatabaseReference myRef;
    private String nomeF;
    private String emailF;
    private String passwordF;
    private boolean p = true;
    private Map mapUsers = new HashMap<String, User>();

    private RecyclerViewListener listener;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView tituloTextView;
        public TextView mensagemTextView;
        public ImageButton deleteButton;

        public ViewHolder(View itemView) {
            super(itemView);

            tituloTextView = (TextView) itemView.findViewById(R.id.txtTitulo);
            mensagemTextView = (TextView) itemView.findViewById(R.id.txtMensagem);
            deleteButton = (ImageButton) itemView.findViewById(R.id.deleteButton);
        }

        @Override
        public void onClick(View v) {
            if(listener != null && getAdapterPosition() != RecyclerView.NO_POSITION){
                listener.onClick(v, getAdapterPosition());
            }
        }
    }

    public NotesAdapter(ArrayList<HashMap<String, String>> notes, RecyclerViewListener listener){
        mNotes = notes;
        this.listener = listener;
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
        int position2 = position;
        HashMap<String, String> note = mNotes.get(position2);
        TextView textViewTitulo = holder.tituloTextView;
        textViewTitulo.setText(note.get("titulo"));
        TextView textViewMensagem = holder.mensagemTextView;
        textViewMensagem.setText(note.get("mensagem"));
        ImageButton delButton = holder.deleteButton;
        db = FirebaseDatabase.getInstance("https://walk-in-the-park---cm-default-rtdb.firebaseio.com/");
        myRef = db.getReference("User");
        delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //HashMap<String, String> rem = mNotes.get(holder.getAdapterPosition());
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds : snapshot.getChildren()){
                            if(s.equals(ds.child("email").getValue().toString())){
                                nomeF = ds.child("nome").getValue().toString();
                                emailF = ds.child("email").getValue().toString();
                                passwordF = ds.child("password").getValue().toString();

                                ArrayList a = (ArrayList) ((Map) ds.getValue()).get("listaNotas");
                                //a.add(put("",""));
                                try{
                                    a.remove(position+1);
                                    notifyItemRemoved(position);
                                    notifyItemRangeChanged(position,getItemCount()+1);
                                }catch(IndexOutOfBoundsException e){
                                    System.out.println("a");
                                }


                                HashMap result = new HashMap<>();
                                result.put("nome", nomeF);
                                result.put("email", emailF);
                                result.put("password", passwordF);
                                result.put("paciente", true);
                                result.put("fisioID", ds.child("fisioID").getValue());
                                result.put("listaNotas", a);
                                result.put("listaLembretes", ds.child("listaLembretes").getValue());
                                result.put("listaMoods", ds.child("listaMoods").getValue());

                                mapUsers.put(s, result);
                            }
                        }
                        if(p) {
                            //Toast.makeText(getContext(), "Nota adicionada!", Toast.LENGTH_SHORT).show();
                            myRef.updateChildren(mapUsers);
                            p = false;

                            /*goToMain(view);*/
                            /*((NotesFragment)getParentFragment()).button.setText("Adicionar Nota");
                            ((NotesFragment)getParentFragment()).replaceFragment(((NotesFragment)getParentFragment()).allNotesFragment);*/
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                mNotes.remove(position2);
                notifyItemRemoved(position2);
                notifyItemRangeChanged(position2, mNotes.size());
                //holder.itemView.setVisibility(View.GONE);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    public interface RecyclerViewListener {
        void onClick(View v, int position);
    }
}

