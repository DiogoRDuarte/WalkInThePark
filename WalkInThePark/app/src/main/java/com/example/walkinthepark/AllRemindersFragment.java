package com.example.walkinthepark;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AllRemindersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllRemindersFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView mensagens;

    private ArrayList<Reminder> listaLembretes;
    private View view;

    public AllRemindersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReminderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AllRemindersFragment newInstance(String param1, String param2) {
        AllRemindersFragment fragment = new AllRemindersFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

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