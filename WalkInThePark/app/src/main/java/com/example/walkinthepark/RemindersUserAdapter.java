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
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView dataTextView;
        public TextView horaTextView;
        public TextView mensagemTextView;
        public MaterialButton editarButton;

        public ViewHolder(View itemView) {
            super(itemView);

            dataTextView = (TextView) itemView.findViewById(R.id.dataLemb);
            horaTextView = (TextView) itemView.findViewById(R.id.horaLemb);
            mensagemTextView = (TextView) itemView.findViewById(R.id.msgLemb);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(listener != null && getAdapterPosition() != RecyclerView.NO_POSITION){
                listener.onClick(v, getAdapterPosition());
            }
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
        HashMap<String, String> reminder = mRemindersUser.get(position);
        TextView textViewData = holder.dataTextView;
        textViewData.setText(reminder.get("data"));
        TextView textViewHora = holder.horaTextView;
        textViewHora.setText(reminder.get("hora"));
        TextView textViewMensagem = holder.mensagemTextView;
        textViewMensagem.setText(reminder.get("mensagem"));

        db = FirebaseDatabase.getInstance("https://walk-in-the-park---cm-default-rtdb.firebaseio.com/");
        myRef = db.getReference("User");
    }


    @Override
    public int getItemCount() {
        if(mRemindersUser.size() > 2) {
            return 2;
        }
        return mRemindersUser.size();
    }

    public interface RecyclerViewListener {
        void onClick(View v, int position);
    }
}
