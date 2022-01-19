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

public class RemindersAdapter extends RecyclerView.Adapter<RemindersAdapter.ViewHolder> {
    //private final Context cont;
    private final RecyclerViewListener listener;
    private ArrayList<HashMap<String, String>> mReminders;
    private Map mapUsers = new HashMap<String, User>();
    private boolean p = true;
    private int position;
    private String s;
    private String nomeF;
    private String emailF;
    private String passwordF;
    private FirebaseDatabase db;
    private DatabaseReference myRef;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView dataTextView;
        public TextView horaTextView;
        public TextView mensagemTextView;
        public ImageButton deleteButton;

        public ViewHolder(View itemView) {
            super(itemView);

            dataTextView = (TextView) itemView.findViewById(R.id.txtDate);
            horaTextView = (TextView) itemView.findViewById(R.id.txtTime);
            mensagemTextView = (TextView) itemView.findViewById(R.id.txtTitle);
            deleteButton = (ImageButton) itemView.findViewById(R.id.deleteButton);

        }
    }

    public RemindersAdapter(ArrayList<HashMap<String, String>> reminders, RecyclerViewListener listener, String mail){
        this.s = mail;
        this.listener = listener;
        mReminders = reminders;
    }


    @Override
    public RemindersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View reminderView = inflater.inflate(R.layout.single_reminder,parent,false);
        RemindersAdapter.ViewHolder viewHolder = new RemindersAdapter.ViewHolder(reminderView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RemindersAdapter.ViewHolder holder, int position) {
        int position2 = position;
        HashMap<String, String> reminder = mReminders.get(position2);
        TextView textViewData = holder.dataTextView;
        textViewData.setText(reminder.get("data"));
        TextView textViewHora = holder.horaTextView;
        textViewHora.setText(reminder.get("hora"));
        TextView textViewMensagem = holder.mensagemTextView;
        textViewMensagem.setText(reminder.get("mensagem"));
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

                                ArrayList a = (ArrayList) ((Map) ds.getValue()).get("listaLembretes");
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
                                result.put("listaNotas", ds.child("listaNotas").getValue());
                                result.put("listaLembretes", a);
                                result.put("listaMoods", ds.child("listaMoods").getValue());
                                result.put("listaExercicios", ds.child("listaExercicios").getValue());
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
                mReminders.remove(position2);
                notifyItemRemoved(position2);
                notifyItemRangeChanged(position2, mReminders.size());
                //holder.itemView.setVisibility(View.GONE);

            }
        });
    }


    @Override
    public int getItemCount() {
        return mReminders.size();
    }

    public interface RecyclerViewListener {
        void onClick(View v, int position);
    }
}
