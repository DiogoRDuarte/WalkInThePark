package com.example.walkinthepark;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

public class MyListenerService extends WearableListenerService {

    private static final String TAG = "Listener: ";
    private static final String WEARABLE_DATA_PATH = "/wearable/data/path";

    @Override
    public void onDataChanged(@NonNull DataEventBuffer dataEvents) {
        DataMap dataMap;
        for(DataEvent dataEvent : dataEvents) {
            if(dataEvent.getType() == DataEvent.TYPE_CHANGED) {
                String path = dataEvent.getDataItem().getUri().getPath();
                if (path.equals(WEARABLE_DATA_PATH)) {
                    dataMap = DataMapItem.fromDataItem(dataEvent.getDataItem()).getDataMap();
                    Log.v(TAG, "DataMap received on SW: " + dataMap);

                    Intent i = new Intent(this, NotesActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    String[] s = new String[4];
                    s[0] = String.valueOf(dataMap.getLong("time"));
                    s[1] = dataMap.getString("one");
                    s[2] = dataMap.getString("two");
                    s[3] = dataMap.getString("three");

                    i.putExtra("dataMap", s);
                    startActivity(i);

                }
            }
        }
        super.onDataChanged(dataEvents);
    }

}
