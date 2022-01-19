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
    SensorManager mSensorManager;

    // fall
    private float x, y, z;
    private float last_x, last_y, last_z;
    private long shakeTime = -1;
    private long lastUpdate = -1;

    private static final int SHAKE_THRESHOLD = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        startSensores();
        stopSensores();

    }

    public void notas(){
        /*Log.i("ACTION", "action_1()");*/
        Toast.makeText(getApplicationContext(), "Notas", Toast.LENGTH_SHORT).show();
    }

    public void lembretes(){
        /*Log.i("ACTION", "action_2()");*/
        Toast.makeText(getApplicationContext(), "Lembretes", Toast.LENGTH_SHORT).show();
    }

    public void comecarExercicio(){
        /*Log.i("ACTION", "action_3()");*/
        Toast.makeText(getApplicationContext(), "Exercício", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, ExerciseActivity.class);
        startActivity(intent);
    }

    public void cancelMenu(){
        /*Log.i("ACTION", "cancelMenu()");*/
        Toast.makeText(getApplicationContext(), "cancel", Toast.LENGTH_SHORT).show();
    }

    protected void startSensores() {
        // SENSORES
        mSensorManager = ((SensorManager)getSystemService(SENSOR_SERVICE));
        Sensor mHeartRateSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
        Sensor mStepCountSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        Sensor mStepDetectSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        Sensor mGravitySensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);


        if(mSensorManager != null) {
            if (mHeartRateSensor != null) {
                mSensorManager.registerListener((SensorEventListener) this, mHeartRateSensor, SensorManager.SENSOR_DELAY_NORMAL);
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

    private void stopSensores() {
        if(mSensorManager != null) {
            mSensorManager.unregisterListener(this);
        }
    }

    // hora para guardar dados
    private String currentTimeStr() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        return df.format(c.getTime());
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        /*// detetar uma queda
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
        }*/
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}