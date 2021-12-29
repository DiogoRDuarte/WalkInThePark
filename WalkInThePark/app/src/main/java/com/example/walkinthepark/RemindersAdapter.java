package com.example.walkinthepark;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RemindersAdapter extends RecyclerView.Adapter<RemindersAdapter.ViewHolder> {

    private ArrayList<HashMap<String, String>> mReminders;

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

    public RemindersAdapter(ArrayList<HashMap<String, String>> reminders){
        mReminders = reminders;
    }


    @Override
    public RemindersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View reminderView = inflater.inflate(R.layout.single_reminder,parent,false);
        ViewHolder viewHolder = new ViewHolder(reminderView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RemindersAdapter.ViewHolder holder, int position) {
        HashMap<String, String> reminder = mReminders.get(position);
        TextView textViewData = holder.dataTextView;
        textViewData.setText(reminder.get("data"));
        TextView textViewHora = holder.horaTextView;
        textViewHora.setText(reminder.get("hora"));
        TextView textViewMensagem = holder.mensagemTextView;
        textViewMensagem.setText(reminder.get("mensagem"));
    }

    @Override
    public int getItemCount() {
        return mReminders.size();
    }
}
