package com.example.walkinthepark;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    private ArrayList<String> mUsers;
    private Map mapUsers = new HashMap<String, User>();
    private boolean p = true;
    private String s;
    private FirebaseDatabase db;
    private DatabaseReference myRef;

    private RecyclerViewListener listener;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView pacNameTextView;
        public ImageButton editButton;
        public ImageButton statisticButton;

        public ViewHolder(View itemView) {
            super(itemView);

            pacNameTextView = (TextView) itemView.findViewById(R.id.PcNome);
            editButton = (ImageButton) itemView.findViewById(R.id.editButton);
            statisticButton = (ImageButton) itemView.findViewById(R.id.statisticButton);

        }
    }

    public UsersAdapter(ArrayList<String> users, RecyclerViewListener listener){
        mUsers = users;
        this.listener = listener;
    }


    @Override
    public UsersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View userView = inflater.inflate(R.layout.single_paciente_user,parent,false);
        ViewHolder viewHolder = new ViewHolder(userView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(UsersAdapter.ViewHolder holder, int position) {
        String nomeUser = mUsers.get(position);
        TextView pacName = holder.pacNameTextView;
        pacName.setText(nomeUser);

        ImageButton edButton = holder.editButton;
        ImageButton statsButton = holder.statisticButton;

        db = FirebaseDatabase.getInstance("https://walk-in-the-park---cm-default-rtdb.firebaseio.com/");
        myRef = db.getReference("User");
        /*edButton.setOnClickListener(new View.OnClickListener() {
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

                                mapUsers.put(s, result);
                            }
                        }
                        if(p) {
                            //Toast.makeText(getContext(), "Nota adicionada!", Toast.LENGTH_SHORT).show();
                            myRef.updateChildren(mapUsers);
                            p = false;

                            *//*goToMain(view);*//*
                            *//*((NotesFragment)getParentFragment()).button.setText("Adicionar Nota");
                            ((NotesFragment)getParentFragment()).replaceFragment(((NotesFragment)getParentFragment()).allNotesFragment);*//*
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
        });*/
    }


    @Override
    public int getItemCount() {
        if(mUsers.size() > 5) {
            return 5;
        }
        return mUsers.size();
    }

    public interface RecyclerViewListener {
        void onClick(View v, int position);
    }
}
