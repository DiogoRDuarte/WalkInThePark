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

public class MoodAdapter extends RecyclerView.Adapter<MoodAdapter.ViewHolder> {

    private final Context cont;
    private String s;
    private ArrayList<HashMap<String, String>> mMoods;
    private int position;
    private FirebaseDatabase db;
    private DatabaseReference myRef;
    private String nomeF;
    private String emailF;
    private String passwordF;
    private boolean a = true;
    private Map mapUsers = new HashMap<String, User>();

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView moodTextView;
        public TextView horaTextView;
        public ImageButton deleteButton;

        public ViewHolder(View itemView) {
            super(itemView);

            moodTextView = (TextView) itemView.findViewById(R.id.txtMood);
            horaTextView = (TextView) itemView.findViewById(R.id.txtHora);
            deleteButton = (ImageButton) itemView.findViewById(R.id.deleteButton);
        }
    }

    public MoodAdapter(ArrayList<HashMap<String, String>> moods, Context c){
        mMoods = moods;
        cont = c;
        if(c instanceof UserHomeActivity){
            s = ((UserHomeActivity) c).getCurrentUserEmail();
        }
    }


    @Override
    public MoodAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View notesView = inflater.inflate(R.layout.single_mood,parent,false);
        MoodAdapter.ViewHolder viewHolder = new MoodAdapter.ViewHolder(notesView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MoodAdapter.ViewHolder holder, int position) {
        int position2 = position;
        HashMap<String, String> mood = mMoods.get(position2);
        TextView textViewMood = holder.moodTextView;
        textViewMood.setText(mood.get("mood"));
        TextView textViewHora = holder.horaTextView;
        textViewHora.setText(mood.get("hora"));
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

                                ArrayList a = (ArrayList) ((Map) ds.getValue()).get("listaMoods");
                                //a.add(put("",""));
                                a.remove(position2);

                                HashMap result = new HashMap<>();
                                result.put("nome", nomeF);
                                result.put("email", emailF);
                                result.put("password", passwordF);
                                result.put("paciente", true);
                                result.put("fisioID", ds.child("fisioID").getValue());
                                result.put("listaNotas", ds.child("listaNotas").getValue());
                                result.put("listaLembretes", ds.child("listaLembretes").getValue());
                                result.put("listaMoods", a);

                                mapUsers.put(s, result);
                            }
                        }
                        if(a) {
                            //Toast.makeText(getContext(), "Nota adicionada!", Toast.LENGTH_SHORT).show();
                            myRef.updateChildren(mapUsers);
                            a = false;

                            /*goToMain(view);*/
                            /*((NotesFragment)getParentFragment()).button.setText("Adicionar Nota");
                            ((NotesFragment)getParentFragment()).replaceFragment(((NotesFragment)getParentFragment()).allNotesFragment);*/
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                mMoods.remove(position2);
                notifyItemRemoved(position2);
                notifyItemRangeChanged(position2, mMoods.size());
                holder.itemView.setVisibility(View.GONE);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mMoods.size();
    }
}

