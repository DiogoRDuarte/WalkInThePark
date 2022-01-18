package com.example.walkinthepark;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class ExerciseActivity extends Activity implements SensorEventListener {

    private Button exButton;

    private static final String TAG = "SensorService";
    SensorManager mSensorManager;

    String time;
    private static Handler handler;
    private static boolean isRunning;
    private static long initialTime;
    private static final int SECS_IN_MIN = 60;
    private static final long MILLIS_IN_SEC = 1000L;

    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(isRunning) {
                long seconds = (System.currentTimeMillis() - initialTime) / MILLIS_IN_SEC;
                time = String.format("%02d:%02d", seconds / SECS_IN_MIN, seconds % SECS_IN_MIN);
                Toast.makeText(getApplicationContext(), "Tempo: " + time, Toast.LENGTH_SHORT).show();
                handler.postDelayed(runnable, MILLIS_IN_SEC);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_exercise);

        exButton = (Button) findViewById(R.id.startExercise);
        exButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt = (String) exButton.getText();
                switch (txt) {
                    case "Começar Exercício":
                        // iniciar timer
                        startTimer();

                        // começar a recolher dados
                        iniciarSensoresEx();
                        exButton.setText("Terminar Exercício");
                        break;
                    case "Terminar Exercício":
                        // terminar timer
                        stopTimer();

                        // parar de recolher dados
                        // guardar dados
                        exButton.setText("Começar Exercício");
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + txt);
                }
            }
        });
    }

    private void startTimer() {
        isRunning = true;
        initialTime = System.currentTimeMillis();
        handler.postDelayed(runnable, MILLIS_IN_SEC);
    }

    private void stopTimer() {
        isRunning = false;
        handler.removeCallbacks(runnable);
    }

    protected void iniciarSensoresEx() {
        // SENSORES
        mSensorManager = ((SensorManager)getSystemService(SENSOR_SERVICE));
        Sensor mHeartRateSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
        Sensor mAccelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor mGyroscopeSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);


        if(mSensorManager != null) {
            if (mHeartRateSensor != null) {
                mSensorManager.registerListener((SensorEventListener) this, mHeartRateSensor, SensorManager.SENSOR_DELAY_NORMAL);
            } else {
                Log.w(TAG, "No Heartrate Sensor found");
            }

            if (mAccelerometerSensor != null) {
                mSensorManager.registerListener((SensorEventListener) this, mAccelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
            } else {
                Log.w(TAG, "No Accelerometer Sensor found");
            }

            if (mGyroscopeSensor != null) {
                mSensorManager.registerListener((SensorEventListener) this, mGyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL);
            } else {
                Log.w(TAG, "No Gyroscope Sensor found");
            }
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}