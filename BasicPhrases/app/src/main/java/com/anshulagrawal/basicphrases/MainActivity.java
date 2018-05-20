package com.anshulagrawal.basicphrases;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public void playAudio(View view) {
        String ourId = view.getResources().getResourceEntryName(view.getId());
        MediaPlayer player = MediaPlayer.create(this, getResources().getIdentifier(ourId, "raw", getPackageName()));
        player.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
