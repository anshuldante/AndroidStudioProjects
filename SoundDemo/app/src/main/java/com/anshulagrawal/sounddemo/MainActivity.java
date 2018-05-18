package com.anshulagrawal.sounddemo;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer player;
    private AudioManager audioManager;

    public void play(View view) {
        player.start();
    }

    public void pause(View view) {
        player.pause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        player = MediaPlayer.create(this, R.raw.laugh);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        SeekBar volumeSeekBar = (SeekBar) findViewById(R.id.seekBar);
        volumeSeekBar.setMax(maxVolume);
        volumeSeekBar.setProgress(currentVolume);

        volumeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i("Seekbar Values: ", Integer.toString(progress));
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });


        final SeekBar audioSeekBar = (SeekBar) findViewById(R.id.seekBar2);
        audioSeekBar.setMax(player.getDuration());
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                audioSeekBar.setProgress(player.getCurrentPosition());
            }
        }, 0, 100);

        audioSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                player.seekTo(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                player.pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                player.start();
            }
        });

        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.laugh);
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(this, uri);
        String durationStr = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        int millSecond = Integer.parseInt(durationStr);
        Log.i("MediaMetaDataReceiver: ", Integer.toString(millSecond));

    }
}
