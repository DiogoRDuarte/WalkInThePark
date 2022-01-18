package com.example.walkinthepark;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainMenuAdapter extends RecyclerView.Adapter<MainMenuAdapter.RecyclerViewHolder> {

    private ArrayList<MenuItem> dataSource = new ArrayList<MenuItem>();

    public interface AdapterCallback {
        void onItemClicked(Integer menuPosition);
    }

    private AdapterCallback callback;
    private String drawableIcon;
    private Context context;

    public MainMenuAdapter(Context context, ArrayList<MenuItem> dataArgs, AdapterCallback callback) {
        this.context = context;
        this.dataSource = dataArgs;
        this.callback = callback;
    }

    @NonNull
    @Override
    public MainMenuAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context c = parent.getContext();
        LayoutInflater i = LayoutInflater.from(c);
        View view = i.inflate(R.layout.list_item, parent, false);
        MainMenuAdapter.RecyclerViewHolder viewHolder = new MainMenuAdapter.RecyclerViewHolder(view);
        return new RecyclerViewHolder(view);
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout menuContainer;
        TextView menuItem;
        ImageView menuIcon;

        public RecyclerViewHolder(View view) {
            super(view);
            menuContainer = (RelativeLayout) view.findViewById(R.id.menu_container);
            menuItem = (TextView) view.findViewById(R.id.menu_item);
            menuIcon = (ImageView) view.findViewById(R.id.menu_icon);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        MenuItem data_provider = dataSource.get(position);

        holder.menuItem.setText(data_provider.getText());
        holder.menuIcon.setImageResource(data_provider.getImage());
        holder.menuContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                if (callback != null) {
                    callback.onItemClicked(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }
}

class MenuItem {
    private String text;
    private int image;

    public MenuItem(int image, String text) {
        this.image = image;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public int getImage() {
        return image;
    }
}

