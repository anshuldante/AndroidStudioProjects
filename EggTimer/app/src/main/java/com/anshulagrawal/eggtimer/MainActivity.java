package com.anshulagrawal.eggtimer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer player;
    private TextView textView;
    private boolean isTimerRunning;
    private CountDownTimer countDownTimer;
    private SeekBar seekBar;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

        button = findViewById(R.id.button);

        seekBar = findViewById(R.id.seekBar);
        seekBar.setMax(600);
        seekBar.setProgress(30);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress < 1) {
                    progress = 1;
                    seekBar.setProgress(progress);
                }
                textView.setText(parseTime(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                flipSwitch(button);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (isTimerRunning)
                    flipSwitch(button);
            }
        });
    }

    public void flipSwitch(View view) {
        button.setText(isTimerRunning ? "GO!" : "STOP");
        if (isTimerRunning) {
            textView.setText(parseTime(30));
            countDownTimer.cancel();
            seekBar.setProgress(30);
        } else {
            startTimer(seekBar.getProgress());
        }
        seekBar.setEnabled(isTimerRunning);
        isTimerRunning = !isTimerRunning;
    }

    private void startTimer(int progress) {
        countDownTimer = new CountDownTimer(progress * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                textView.setText(parseTime((int) Math.ceil(millisUntilFinished / 1000)));
            }

            @Override
            public void onFinish() {
                player = MediaPlayer.create(MainActivity.this, R.raw.horn);
                player.start();
                textView.setText(parseTime(30));
                flipSwitch(button);
            }
        }.start();
    }

    private String parseTime(int time) {
        StringBuilder builder = new StringBuilder();
        builder.append(time / 60).append(":");
        if (time % 60 < 10) {
            builder.append(0);
        }
        builder.append(time % 60);
        return builder.toString();
    }
}
