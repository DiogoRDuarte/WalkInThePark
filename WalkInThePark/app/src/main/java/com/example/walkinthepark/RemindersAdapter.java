package com.example.walkinthepark;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RemindersAdapter extends RecyclerView.Adapter<RemindersAdapter.ViewHolder> {

    private List<Reminder> mReminders;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView dataTextView;
        public TextView horaTextView;
        public TextView mensagemTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            dataTextView = (TextView) itemView.findViewById(R.id.txtDate);
            horaTextView = (TextView) itemView.findViewById(R.id.txtTime);
            mensagemTextView = (TextView) itemView.findViewById(R.id.txtTitle);
        }
    }

    public RemindersAdapter(List<Reminder> reminders){
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
        Reminder reminder = mReminders.get(position);
        TextView textViewData = holder.dataTextView;
        textViewData.setText(reminder.getData());
        TextView textViewHora = holder.horaTextView;
        textViewHora.setText(reminder.getHora());
        TextView textViewMensagem = holder.mensagemTextView;
        textViewMensagem.setText(reminder.getMensagem());
    }

    @Override
    public int getItemCount() {
        return mReminders.size();
    }
}
