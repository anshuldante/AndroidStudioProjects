package com.anshulagrawal.higherorlower;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {


    int randomNumber;

    public void guessNumber(View view) {
        int number = Integer.parseInt(((TextView) findViewById(R.id.editText)).getText().toString());

        if (number == randomNumber) {
            Toast.makeText(this, "That's correct!!!", Toast.LENGTH_SHORT).show();
        } else if (number < randomNumber) {
            Toast.makeText(this, "Lower :(", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Higher :(", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Random random = new Random();
        randomNumber = random.nextInt(20) + 1;
        setContentView(R.layout.activity_main);
    }
}
