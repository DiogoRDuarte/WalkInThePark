package com.example.walkinthepark;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Map;

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
    String message="";
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
    private FirebaseDatabase db;
    private DatabaseReference myRef;

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
        db = FirebaseDatabase.getInstance("https://walk-in-the-park---cm-default-rtdb.firebaseio.com/");
        myRef = db.getReference("Reminder");

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

        bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dataS = data.getText().toString();
                String horaS = hora.getText().toString();
                String text = te.getText().toString();

                if(dataS.equals("Data") || horaS.equals("Hora") || text.equals("")){
                    Toast toast = Toast.makeText(getContext(), "Escolhe uma Data Hora e Lembrete!", Toast.LENGTH_SHORT);
                    toast.show();

                }else {
                    Reminder rem = new Reminder(horaS, dataS, text);
                    ((ReminderActivity) getActivity()).adicionarLembrete(rem);

                    /*note = new Note(tituloS, notaS);
                    //((NotesActivity) getActivity()).adicionarNota(note);
                    Map noteValues = note.toMap();

                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot ds: snapshot.getChildren()){
                                String s = ds.child("titulo").getValue().toString();
                                listTitulo.add(s);
                            }
                            //mapNotes.put(titulo, noteValues);
                            //Toast.makeText(getContext(), "Nota adicionada!", Toast.LENGTH_SHORT).show();
                            //myRef.updateChildren(mapNotes);
                            if(listTitulo.contains(titulo) && a){
                                Toast.makeText(getContext(), "Ja existe uma nota com este titulo!", Toast.LENGTH_SHORT).show();
                                titulo.setText("");
                                nota.setText("");


                            }else {

                                if(a) {
                                    //myRef.child("User").child(email);
                                    mapNotes.put(tituloS, noteValues);
                                    Toast.makeText(getContext(), "Nota adicionada!", Toast.LENGTH_SHORT).show();
                                    myRef.updateChildren(mapNotes);
                                    goToMain(view);
                                    a = false;

                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });*/

                    Toast toast = Toast.makeText(getContext(), "Lembrete Adicionado!", Toast.LENGTH_SHORT);
                    toast.show();
                }

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
    /*
    private void setAlarm(String text, String date, String time) {
        AlarmManager am = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);                   //assigning alarm manager object to set alarm
        Intent intent = new Intent(getActivity().getApplicationContext(), AlarmBroadcast.class);
        intent.putExtra("event", text);                                                       //sending data to alarm class to create channel and notification
        intent.putExtra("time", date);
        intent.putExtra("date", time);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
        String dateandtime = date + " " + timeTonotify;
        DateFormat formatter = new SimpleDateFormat("d-M-yyyy hh:mm");
        try {
            Date date1 = formatter.parse(dateandtime);
            am.set(AlarmManager.RTC_WAKEUP, date1.getTime(), pendingIntent);
            Toast.makeText(getApplicationContext(), "Alarm", Toast.LENGTH_SHORT).show();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Intent intentBack = new Intent(getApplicationContext(), MainActivity.class);                //this intent will be called once the setting alarm is complete
        intentBack.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intentBack);                                                                  //navigates from adding reminder activity to mainactivity
    }*/
}