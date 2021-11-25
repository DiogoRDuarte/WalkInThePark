package com.example.walkinthepark;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import org.w3c.dom.Text;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewReminderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewReminderFragment extends Fragment {
    Button bDate;
    Button bTime;
    Button bAdd;
    TextView teste;
    EditText te;
    String time ="";
    String message;
    String date ="";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View view;
    private TextView hora;
    private TextView data;

    public NewReminderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewReminderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewReminderFragment newInstance(String param1, String param2) {
        NewReminderFragment fragment = new NewReminderFragment();
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
        view = inflater.inflate(R.layout.fragment_new_reminder, container, false);
        bDate = (Button) view.findViewById(R.id.buttonDate);
        bTime = (Button) view.findViewById(R.id.buttonTime);
        bAdd = (Button) view.findViewById(R.id.buttonAdd);
        data = (TextView) view.findViewById(R.id.textData);
        bDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDate();

            }
        });
        hora = (TextView) view.findViewById(R.id.textHora);
        bTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectTime();
            }
        });
        te = (EditText) view.findViewById(R.id.message);
    //TODO: solve EditText problem
        teste = view.findViewById(R.id.define);

        bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message = te.getText().toString();
                teste.setText(""+message + " " +time + " "+ date);
            }
        });
        return view;
    }


    private void selectTime() {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this.getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                time = hour + ":" + minute;
                hora.setText(time+"h");
            }
        },hour,minute,true);
        timePickerDialog.show();
    }

    private void selectDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this.getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                date = day+"-"+(month+1)+"-"+year;
                data.setText(date);
            }
        },year,month,day);
        datePickerDialog.show();
    }
}