package com.anshulagrawal.gridlayoutdemo;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Map<String, Integer> audioIdMap;

    MediaPlayer mediaPlayer;

    public void playAudio(View view) {
        String uriString = "android.resource://" + getPackageName() + "/" + audioIdMap.get(view.getTag());

        mediaPlayer = MediaPlayer.create(this, Uri.parse(uriString));
        mediaPlayer.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        audioIdMap = new HashMap<>();
        audioIdMap.put("doyouspeakenglish", R.raw.doyouspeakenglish);
        audioIdMap.put("goodevening", R.raw.goodevening);
        audioIdMap.put("hello", R.raw.hello);
        audioIdMap.put("howareyou", R.raw.howareyou);
        audioIdMap.put("ilivein", R.raw.ilivein);
        audioIdMap.put("please", R.raw.please);
        audioIdMap.put("mynameis", R.raw.mynameis);
        audioIdMap.put("welcome", R.raw.welcome);
    }
}
