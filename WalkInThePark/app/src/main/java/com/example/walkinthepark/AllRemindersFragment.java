package com.example.walkinthepark;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class AllRemindersFragment extends Fragment {

    private TextView mensagens;

    private ArrayList<Reminder> listaLembretes;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_all_reminders, container, false);

        // REVER!!!
        /*listaLembretes = ((ReminderActivity) getActivity()).getListaReminders();*/
        listaLembretes = new ArrayList<>();
        listaLembretes.add(new Reminder("teste", "teste", "teste"));
        mensagens = view.findViewById(R.id.lembretes);

        StringBuilder m = new StringBuilder("");

        for(Reminder r: listaLembretes){
          m.append("Data: "+r.getData()+"\nHora: "+r.getHora()+"\nLembrete "+r.getMensagem()+"\n\n");
        }

        if(m.toString().equals("")){
            mensagens.setText("Não Existem Lembretes!");
        }else
            mensagens.setText(m.toString());
        /*RecyclerView rvReminders = (RecyclerView) view.findViewById(R.id.rvReminders);
        RemindersAdapter remindersAdapter = new RemindersAdapter(listaLembretes);
        rvReminders.setAdapter(remindersAdapter);
        rvReminders.setLayoutManager(new LinearLayoutManager(this.getContext()));*/

        return  view;
    }

}