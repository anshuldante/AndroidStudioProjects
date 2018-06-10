package com.anshulagrawal.hideshowsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button button1;
    private Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button2.setVisibility(View.INVISIBLE);
    }

    public void toggle(View view) {
        Log.i("View ID:", Integer.toString(view.getId()));
        Log.i("button1 ID: ", Integer.toString(view.getId()));
        if (view.getId() == button1.getId()) {
            button1.setVisibility(View.INVISIBLE);
            button2.setVisibility(View.VISIBLE);
        } else {
            button1.setVisibility(View.VISIBLE);
            button2.setVisibility(View.INVISIBLE);
        }
    }
}
