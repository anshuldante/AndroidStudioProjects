package com.anshulagrawal.splitscreendemo;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {


    @Override
    public void onMultiWindowModeChanged(boolean isInMultiWindowMode, Configuration newConfig) {
        super.onMultiWindowModeChanged(isInMultiWindowMode, newConfig);

        // Multi window mode has changed

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (isInMultiWindowMode()) {
            // Is in Multi window mode
            Log.i("Multi window mode", "Enabled!");
        }

        if (isInPictureInPictureMode()) {
            // Is in picture in picture mode
            Log.i("Picture in picture mode", "Enabled!");
        }
    }
}
