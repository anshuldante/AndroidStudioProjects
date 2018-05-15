package com.anshulagrawal.animations;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private boolean isGokuOne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void fade(View view) {
        float goku1Float = isGokuOne ? 1f : 0f;
        float goku2Float = isGokuOne ? 0f : 1f;
        isGokuOne = !isGokuOne;
        ImageView goku1 = findViewById(R.id.goku1);
        goku1.animate().alpha(goku1Float).setDuration(2000);
        ImageView goku2 = findViewById(R.id.goku2);
        goku2.animate().alpha(goku2Float).setDuration(2000);
    }
}
