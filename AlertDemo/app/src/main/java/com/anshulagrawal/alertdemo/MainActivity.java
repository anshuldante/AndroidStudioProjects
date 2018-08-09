package com.anshulagrawal.alertdemo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonGetAlert = findViewById(R.id.button_getAlert);
        buttonGetAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartAlert();
            }
        });
    }

    private void StartAlert() {
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_launcher_background)
                .setTitle("Are you sure?")
                .setMessage("Do you definitely want to do this?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "It's done!", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("No", null).show();
    }
}
