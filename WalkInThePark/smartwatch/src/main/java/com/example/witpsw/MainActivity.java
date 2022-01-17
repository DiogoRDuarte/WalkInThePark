package com.example.witpsw;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.wear.widget.WearableLinearLayoutManager;
import androidx.wear.widget.WearableRecyclerView;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private WearableRecyclerView wearableRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wearableRecyclerView = findViewById(R.id.recyclerView);
        wearableRecyclerView.setEdgeItemsCenteringEnabled(true);
        wearableRecyclerView.setLayoutManager(new WearableLinearLayoutManager(this));

       /* CustomScrollingLayoutCallback customScrollingLayoutCallback = new CustomScrollingLayoutCallback();
        wearableRecyclerView.setLayoutManager(new WearableLinearLayoutManager(this, customScrollingLayoutCallback));
        wearableRecyclerView.setCircularScrollingGestureEnabled(true);*/

        ArrayList<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem(R.drawable.pencil, "Notas"));
        menuItems.add(new MenuItem(R.drawable.pencil, "Lembretes"));
        menuItems.add(new MenuItem(R.drawable.pencil, "Item3"));
        menuItems.add(new MenuItem(R.drawable.pencil, "Item4"));
        menuItems.add(new MenuItem(R.drawable.pencil, "Item5"));

        wearableRecyclerView.setAdapter(new MainMenuAdapter(this, menuItems, new MainMenuAdapter.AdapterCallback() {
            @Override
            public void onItemClicked(Integer menuPosition) {
                switch (menuPosition) {
                    case 0:
                        action_1();
                        break;
                    case 1:
                        action_2();
                        break;
                    case 2:
                        action_3();
                        break;
                    case 3:
                        action_4();
                        break;
                    case 4:
                        action_5();
                        break;
                    default:
                        cancelMenu();
                }
            }
        }));

    }

    public void action_1(){
        /*Log.i("ACTION", "action_1()");*/
        Toast.makeText(getApplicationContext(), "notas", Toast.LENGTH_SHORT);
    }

    public void action_2(){
        /*Log.i("ACTION", "action_2()");*/
        Toast.makeText(getApplicationContext(), "lembretes", Toast.LENGTH_SHORT);
    }

    public void action_3(){
        /*Log.i("ACTION", "action_3()");*/
        Toast.makeText(getApplicationContext(), "item3", Toast.LENGTH_SHORT);
    }

    public void action_4(){
        /*Log.i("ACTION", "action_4()");*/
        Toast.makeText(getApplicationContext(), "item4", Toast.LENGTH_SHORT);
    }

    public void action_5(){
        /*Log.i("ACTION", "action_5()");*/
        Toast.makeText(getApplicationContext(), "item5", Toast.LENGTH_SHORT);
    }

    public void cancelMenu(){
        /*Log.i("ACTION", "cancelMenu()");*/
        Toast.makeText(getApplicationContext(), "cancel", Toast.LENGTH_SHORT);
    }

}