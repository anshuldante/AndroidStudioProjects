package com.anshulagrawal.animations;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private boolean isGokuTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*ImageView goku1 = findViewById(R.id.goku1);
        ImageView goku2 = findViewById(R.id.goku2);
        ImageView imageView = isGokuTwo ? goku2 : goku1;
        imageView.setTranslationX(-1000f);*/
    }

    public void fade(View view) {
        ImageView goku1 = findViewById(R.id.goku1);
        ImageView goku2 = findViewById(R.id.goku2);

        ImageView imageViewPrimary = isGokuTwo ? goku2 : goku1;
        ImageView imageViewSecondary = isGokuTwo ? goku1 : goku2;

        isGokuTwo = !isGokuTwo;

        imageViewPrimary.animate().alpha(0f).setDuration(2000);
        imageViewSecondary.animate().alpha(1f).setDuration(2000);

        imageViewPrimary.animate().rotation(3600f).scaleX(0.5f).scaleY(0.5f)
                .translationYBy(10f).translationX(10f).setDuration(2000);

        imageViewPrimary.animate().rotation(-3600f)
                .scaleX(1f).scaleY(1f).translationYBy(-10f).translationX(-10f).setDuration(2000);

    }
}
