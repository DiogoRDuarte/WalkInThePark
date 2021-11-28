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
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView dataTextView;
        public TextView horaTextView;
        public TextView mensagemTextView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
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
        textViewData.setText(reminder.getHora());
        TextView textViewMensagem = holder.mensagemTextView;
        textViewData.setText(reminder.getMensagem());
    }

    @Override
    public int getItemCount() {
        return mReminders.size();
    }
}
