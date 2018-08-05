package com.anshulagrawal.myparsestarter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {


    private Switch driverSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        driverSwitch = findViewById(R.id.switch1);
    }

    public void getStarted(View view) {
        Log.i("Switch mode:", Boolean.toString(driverSwitch.isChecked()));
    }
}
