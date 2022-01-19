package com.example.walkinthepark;

import static android.content.Context.ALARM_SERVICE;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.text.SimpleDateFormat;


public class NewReminderFragment extends Fragment {

    Button bDate;
    Button bTime;
    Button bAdd;
    Button bCancel;
    TextView teste;
    EditText te;
    String time = "";
    String message = "";
    String date = "";

    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;

    String user_email;
    private View view;
    private TextView hora;
    private TextView data;
    private FirebaseDatabase db;
    private Reminder rem;
    private DatabaseReference myRef;
    private List<String> listReminder = new ArrayList<String>();
    private boolean p = true;
    private Map mapUsers = new HashMap<String, User>();

    private String nomeF;
    private String emailF;
    private String passwordF;

    //Notificacoes

    private int notificationId = 1;

    public NewReminderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        user_email = ((UserHomeActivity) getActivity()).user_email;

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_new_reminder, container, false);
        bDate = (Button) view.findViewById(R.id.buttonDate);
        bTime = (Button) view.findViewById(R.id.buttonTime);
        bAdd = (Button) view.findViewById(R.id.buttonAdd);
        bCancel = (Button) view.findViewById(R.id.buttonCancelar);
        data = (TextView) view.findViewById(R.id.textData);
        db = FirebaseDatabase.getInstance("https://walk-in-the-park---cm-default-rtdb.firebaseio.com/");
        myRef = db.getReference("User");

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
                String textS = te.getText().toString();

                if (dataS.equals("Data") || horaS.equals("Hora") || textS.equals("")) {
                    Toast toast = Toast.makeText(getContext(), "Escolhe uma Data Hora e Lembrete!", Toast.LENGTH_SHORT);
                    toast.show();

                } else {
                    rem = new Reminder(horaS, dataS, textS);

                    Map reminderValues = rem.toMap();

                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot ds : snapshot.getChildren()) {

                                if (ds.child("email").getValue().toString().equals(user_email)) {
                                    nomeF = ds.child("nome").getValue().toString();
                                    emailF = ds.child("email").getValue().toString();
                                    passwordF = ds.child("password").getValue().toString();
                                    ArrayList a = (ArrayList) ((Map) ds.getValue()).get("listaLembretes");
                                    a.add(rem.toMap());

                                    HashMap result = new HashMap<>();
                                    result.put("nome", nomeF);
                                    result.put("email", emailF);
                                    result.put("password", passwordF);
                                    result.put("paciente", true);
                                    result.put("fisioID", ds.child("fisioID").getValue());
                                    result.put("listaNotas", ds.child("listaNotas").getValue());
                                    result.put("listaMoods", ds.child("listaMoods").getValue());
                                    result.put("listaLembretes", a);
                                    result.put("listaExercicios", ds.child("listaExercicios").getValue());
                                    mapUsers.put(user_email, result);
                                }

                            }
                            if (p) {
                                Toast.makeText(getContext(), "Lembrete adicionado!", Toast.LENGTH_SHORT).show();
                                myRef.updateChildren(mapUsers);
                                p = false;

                                ((RemindersFragment) getParentFragment()).replaceFragment(((RemindersFragment) getParentFragment()).allRemindersFragment);

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

                Intent intent = new Intent(NewReminderFragment.this.getContext(), AlarmReceiver.class);
                intent.putExtra("notificationId", notificationId);
                intent.putExtra("message", te.getText().toString());

                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        NewReminderFragment.this.getContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT
                );

                AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(ALARM_SERVICE);

                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                String timeSsp = dataS + " " + horaS;
                timeSsp = timeSsp.substring(0, timeSsp.length() - 1);
                Date dateAuxD = null;
                try {
                    dateAuxD = formatter.parse(timeSsp);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                long millis = dateAuxD.getTime();

                alarmManager.set(AlarmManager.RTC_WAKEUP, millis, pendingIntent);

            }
        });

        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction().remove(NewReminderFragment.this).commit();
                ((RemindersFragment) getParentFragment()).bAdd.setText("Adicionar");
                ((RemindersFragment) getParentFragment()).replaceFragment(((RemindersFragment) getParentFragment()).allRemindersFragment);
            }
        });

        return view;
    }

    private void goToMain(View view) {
        Intent i = new Intent(getActivity(), UserHomeActivity.class);
        i.putExtra("user_email", user_email + "");
        startActivity(i);
    }


    private void selectTime() {
        Calendar cal = Calendar.getInstance();
        hour = cal.get(Calendar.HOUR_OF_DAY);
        minute = cal.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this.getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                time = hour + ":" + minute + ":00";
                hora.setText(time + "h");
            }
        }, hour, minute, true);
        timePickerDialog.show();
    }

    private void selectDate() {
        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this.getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                date = day + "-" + (month + 1) + "-" + year;
                data.setText(date);
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        data.setText("");
        hora.setText("");
        te.setText("");

        Bundle bundle = getArguments();
        if (bundle != null) {
            data.setText(bundle.getString("data2"));
            hora.setText(bundle.getString("hora2"));
            te.setText(bundle.getString("mensagem2"));
        }
    }

}