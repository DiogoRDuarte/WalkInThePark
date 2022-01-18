package com.example.walkinthepark;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.walkinthepark.databinding.ActivityNotesBinding;

public class NotesActivity extends Activity {

    private TextView mTextView;
    private ActivityNotesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        mTextView = findViewById(R.id.text);
        String[] dataMap = getIntent().getStringArrayExtra("dataMap");
        if (dataMap != null) {
            StringBuilder sb = new StringBuilder();
            for (String s : dataMap) {
                sb.append(s + ", ");
            }
            mTextView.setText(sb.toString());
        } else {
            mTextView.setText("NÃO FUNCIONA!");
        }
    }
}