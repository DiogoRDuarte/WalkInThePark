package com.example.witpsw;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.wear.widget.drawer.WearableActionDrawerView;
import androidx.wear.widget.drawer.WearableDrawerLayout;
import androidx.wear.widget.drawer.WearableNavigationDrawerView;

import com.example.witpsw.databinding.ActivityMainBinding;

public class MainActivity extends Activity implements MenuItem.OnMenuItemClickListener {

    static View mainView;

    private WearableDrawerLayout wearableDrawerLayout;
    private WearableNavigationDrawerView wearableNavigationDrawerView;
    private WearableActionDrawerView wearableActionDrawerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainView = findViewById(R.id.drawerLayout);
        setContentView(mainView);

        // top navigation drawer
        wearableNavigationDrawerView = (WearableNavigationDrawerView) findViewById(R.id.topNavigationDrawer);
        /*wearableNavigationDrawerView.setAdapter();*/
        wearableNavigationDrawerView.getController().peekDrawer();
        wearableActionDrawerView = (WearableActionDrawerView) findViewById(R.id.bottomActionDrawer);
        wearableActionDrawerView.getController().peekDrawer();
        wearableActionDrawerView.setOnMenuItemClickListener(this);

    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        return false;
    }
}