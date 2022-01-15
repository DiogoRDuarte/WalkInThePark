package com.example.walkinthepark;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RemindersUserAdapter extends RecyclerView.Adapter<RemindersUserAdapter.ViewHolder> {

    private ArrayList<HashMap<String, String>> mRemindersUser;
    private Map mapUsers = new HashMap<String, User>();
    private boolean a = true;
    private int position;
    private String s;
    private String nomeF;
    private String emailF;
    private String passwordF;
    private FirebaseDatabase db;
    private DatabaseReference myRef;
    private RecyclerViewListener listener;
    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView dataTextView;
        public TextView horaTextView;
        public TextView mensagemTextView;
        public MaterialButton editarButton;

        public ViewHolder(View itemView) {
            super(itemView);

            dataTextView = (TextView) itemView.findViewById(R.id.dataLemb);
            horaTextView = (TextView) itemView.findViewById(R.id.horaLemb);
            mensagemTextView = (TextView) itemView.findViewById(R.id.msgLemb);
            editarButton = (MaterialButton) itemView.findViewById(R.id.editarLemb);

        }
    }

    public RemindersUserAdapter(ArrayList<HashMap<String, String>> reminder, RecyclerViewListener listener){
        mRemindersUser = reminder;
        this.listener = listener;
    }


    @Override
    public RemindersUserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View reminderView = inflater.inflate(R.layout.single_reminder_user,parent,false);
        RemindersUserAdapter.ViewHolder viewHolder = new RemindersUserAdapter.ViewHolder(reminderView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RemindersUserAdapter.ViewHolder holder, int position) {
        int position2 = position;
        HashMap<String, String> reminder = mRemindersUser.get(position2);
        TextView textViewData = holder.dataTextView;
        textViewData.setText(reminder.get("data"));
        TextView textViewHora = holder.horaTextView;
        textViewHora.setText(reminder.get("hora"));
        TextView textViewMensagem = holder.mensagemTextView;
        textViewMensagem.setText(reminder.get("mensagem"));
        MaterialButton edButton = holder.editarButton;

        db = FirebaseDatabase.getInstance("https://walk-in-the-park---cm-default-rtdb.firebaseio.com/");
        myRef = db.getReference("User");

        edButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*//HashMap<String, String> rem = mNotes.get(holder.getAdapterPosition());
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds : snapshot.getChildren()){
                            if(s.equals(ds.child("email").getValue().toString())){
                                nomeF = ds.child("nome").getValue().toString();
                                emailF = ds.child("email").getValue().toString();
                                passwordF = ds.child("password").getValue().toString();

                                ArrayList a = (ArrayList) ((Map) ds.getValue()).get("listaLembretes");
                                //a.add(put("",""));
                                try{
                                    a.remove(holder.getAdapterPosition());
                                }catch(IndexOutOfBoundsException e){
                                    System.out.println("a");
                                }

                                HashMap result = new HashMap<>();
                                result.put("nome", nomeF);
                                result.put("email", emailF);
                                result.put("password", passwordF);
                                result.put("paciente", true);
                                result.put("fisioID", ds.child("fisioID").getValue());
                                result.put("listaNotas", ds.child("listaNotas").getValue());
                                result.put("listaLembretes", a);
                                result.put("listaMoods", ds.child("listaMoods").getValue());

                                mapUsers.put(s, result);
                            }
                        }
                        if(a) {
                            //Toast.makeText(getContext(), "Nota adicionada!", Toast.LENGTH_SHORT).show();
                            myRef.updateChildren(mapUsers);
                            a = false;

                            *//*goToMain(view);*//*
                            *//*((NotesFragment)getParentFragment()).button.setText("Adicionar Nota");
                            ((NotesFragment)getParentFragment()).replaceFragment(((NotesFragment)getParentFragment()).allNotesFragment);*//*
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                mRemindersUser.remove(position2);
                notifyItemRemoved(position2);
                notifyItemRangeChanged(position2, mRemindersUser.size());
                holder.itemView.setVisibility(View.GONE);*/

            }
        });
    }


    @Override
    public int getItemCount() {
        /*if(mRemindersUser.size() > 2) {
            return 2;
        }*/
        return mRemindersUser.size();
    }

    public interface RecyclerViewListener {
        void onClick(View v, int position);
    }
}
