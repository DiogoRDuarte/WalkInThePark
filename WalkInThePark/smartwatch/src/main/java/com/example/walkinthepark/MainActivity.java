package com.example.walkinthepark;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.wear.widget.WearableLinearLayoutManager;
import androidx.wear.widget.WearableRecyclerView;

import com.example.walkinthepark.ExerciseActivity;
import com.example.walkinthepark.MainMenuAdapter;
import com.google.android.gms.wearable.DataClient;
import com.google.android.gms.wearable.Wearable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends Activity implements SensorEventListener {

    private WearableRecyclerView wearableRecyclerView;

    private static final String TAG = "SensorService";
    private SensorManager mSensorManager;
    private Sensor mHeartRateSensor1;
    private Sensor mStepCountSensor;
    private Sensor mStepDetectSensor;
    private Sensor mGravitySensor;
    private int counterSteps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // SENSORES
        mSensorManager = ((SensorManager)getSystemService(SENSOR_SERVICE));
        mHeartRateSensor1 = mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
        mStepCountSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        mStepDetectSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        mGravitySensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);

        wearableRecyclerView = findViewById(R.id.recyclerView);
        wearableRecyclerView.setEdgeItemsCenteringEnabled(true);

        CustomScrollingLayoutCallback customScrollingLayoutCallback = new CustomScrollingLayoutCallback();
        wearableRecyclerView.setLayoutManager(new WearableLinearLayoutManager(this, customScrollingLayoutCallback));
        wearableRecyclerView.setCircularScrollingGestureEnabled(true);

        ArrayList<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem(R.drawable.pencil, "Notas"));
        menuItems.add(new MenuItem(R.drawable.bell, "Lembretes"));
        menuItems.add(new MenuItem(R.drawable.fitness, "Exercício"));

        wearableRecyclerView.setAdapter(new MainMenuAdapter(this, menuItems, new MainMenuAdapter.AdapterCallback() {
            @Override
            public void onItemClicked(Integer menuPosition) {
                switch (menuPosition) {
                    case 0:
                        notas();
                        break;
                    case 1:
                        lembretes();
                        break;
                    case 2:
                        comecarExercicio();
                        break;
                    default:
                        cancelMenu();
                }
            }
        }));

    }

    public void notas(){
        Toast.makeText(getApplicationContext(), "Notas", Toast.LENGTH_SHORT).show();
    }

    public void lembretes(){
        Toast.makeText(getApplicationContext(), "Lembretes", Toast.LENGTH_SHORT).show();
    }

    public void comecarExercicio(){
        Toast.makeText(getApplicationContext(), "Exercício", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, ExerciseActivity.class);
        startActivity(intent);
    }

    public void cancelMenu(){
        Toast.makeText(getApplicationContext(), "cancel", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mSensorManager != null) {
            if (mHeartRateSensor1 != null) {
                mSensorManager.registerListener((SensorEventListener) this, mHeartRateSensor1, SensorManager.SENSOR_DELAY_NORMAL);
            } else {
                Log.w(TAG, "No Heartrate Sensor found");
            }

            if (mStepCountSensor != null) {
                mSensorManager.registerListener((SensorEventListener) this, mStepCountSensor, SensorManager.SENSOR_DELAY_NORMAL);
            } else {
                Log.w(TAG, "No Step Counter Sensor found");
            }

            if (mStepDetectSensor != null) {
                mSensorManager.registerListener((SensorEventListener) this, mStepDetectSensor, SensorManager.SENSOR_DELAY_NORMAL);
            } else {
                Log.w(TAG, "No Step Detector Sensor found");
            }

            if (mGravitySensor != null) {
                mSensorManager.registerListener((SensorEventListener) this, mGravitySensor, SensorManager.SENSOR_DELAY_NORMAL);
            } else {
                Log.w(TAG, "No Gravity Sensor found");
            }
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mSensorManager != null) {
            mSensorManager.unregisterListener(heartListener1, mHeartRateSensor1);
            mSensorManager.unregisterListener(stepCounterListener, mStepCountSensor);
            mSensorManager.unregisterListener(stepDetectorListener, mStepDetectSensor);
            mSensorManager.unregisterListener(gravityListener, mGravitySensor);
        }
    }

    // saber o ritmo cardíaco ao longo do dia
    SensorEventListener heartListener1 = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    // saber o número de passos do dia
    SensorEventListener stepCounterListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            if(sensorEvent.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
                int steps = Math.round(sensorEvent.values[0]);
                counterSteps += steps;
                Toast.makeText(getApplicationContext(), "Passos: " + steps, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    SensorEventListener stepDetectorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            /*if(sensorEvent.sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
                int stepsDetector = Math.round(sensorEvent.values[0]);
                Toast.makeText(getApplicationContext(), "Passos detetados: " + stepsDetector, Toast.LENGTH_SHORT).show();
            }*/
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    SensorEventListener gravityListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    // hora para guardar dados
    private String currentTimeStr() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        return df.format(c.getTime());
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}