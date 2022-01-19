package com.example.walkinthepark;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;


public class ExerciseActivity extends Activity {

    private Button exButton;

    private static final String TAG = "SensorService";
    private SensorManager mSensorManager;
    private Sensor mHeartRateSensor;
    private Sensor mAccelerometerSensor;
    private Sensor mGyroscopeSensor;

    // tempo de exercicio
    private String time;
    private long iTime;
    private long fTime;
    private long totalTime;

    // queda
    private float x, y, z;
    private float last_x, last_y, last_z;
    private long shakeTime = -1;
    private long lastUpdate = -1;

    private static final int SHAKE_THRESHOLD = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_exercise);

        mSensorManager = ((SensorManager)getSystemService(SENSOR_SERVICE));
        mHeartRateSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
        mAccelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mGyroscopeSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        exButton = (Button) findViewById(R.id.startExercise);
        exButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt = (String) exButton.getText();
                switch (txt) {
                    case "Começar Exercício":
                        // iniciar timer
                        startTimer();
                        onResume();

                        // começar a recolher dados
                        exButton.setText("Terminar Exercício");
                        break;
                    case "Terminar Exercício":
                        // terminar timer
                        stopTimer();
                        time = getDurationBreakdown(totalTime);
                        Log.i(TAG, "-------------------" + time + "-------------------");

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
        iTime = System.currentTimeMillis();
    }

    private void stopTimer() {
        fTime = System.currentTimeMillis();
        totalTime = fTime - iTime;
    }

    public static String getDurationBreakdown(long millis) {
        if(millis < 0) {
            throw new IllegalArgumentException("Duration must be greater than zero!");
        }

        long days = TimeUnit.MILLISECONDS.toDays(millis);
        millis -= TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

        StringBuilder sb = new StringBuilder(64);
        if(hours > 0) {
            sb.append(hours);
            sb.append(" Hours ");
        }
        sb.append(minutes);
        sb.append(" Minutes ");
        sb.append(seconds);
        sb.append(" Seconds");

        return(sb.toString());
    }

    @Override
    protected void onResume() {
        super.onResume();
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
    protected void onPause() {
        super.onPause();
        if (mSensorManager != null) {
            mSensorManager.unregisterListener(heartListener, mHeartRateSensor);
            mSensorManager.unregisterListener(accelerometerListener, mAccelerometerSensor);
            mSensorManager.unregisterListener(gyroscopeListener, mGyroscopeSensor);
        }
    }

    SensorEventListener heartListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            if(sensorEvent.sensor.getType() == Sensor.TYPE_HEART_RATE) {
                int heartRate = Math.round(sensorEvent.values[0]);
                Toast.makeText(getApplicationContext(), "Ritmo Cardiaco: " + heartRate, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    SensorEventListener accelerometerListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            if(sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                long curTime = System.currentTimeMillis();
                // only allow one update every 100ms.
                if ((curTime - lastUpdate) > 100) {
                    long diffTime = (curTime - lastUpdate);
                    lastUpdate = curTime;

                    x = sensorEvent.values[SensorManager.DATA_X];
                    y = sensorEvent.values[SensorManager.DATA_Y];
                    z = sensorEvent.values[SensorManager.DATA_Z];

                    float speed = Math.abs(x + y + z - last_x - last_y - last_z) / (diffTime * 10000);

                    if (speed > SHAKE_THRESHOLD) {
                        long curTime1 = System.currentTimeMillis();
                        long diff = (curTime1 - shakeTime);
                        shakeTime = curTime1;

                        Toast.makeText(getApplicationContext(), "QUEDA!!", Toast.LENGTH_SHORT).show();
                    }
                    last_x = x;
                    last_y = y;
                    last_z = z;
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    SensorEventListener gyroscopeListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

}