package com.anshulagrawal.toastdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    public void clickMe(View view) {

        TextView text = (TextView) findViewById(R.id.text);

        Toast.makeText(this, "Hi There " + text.getText().toString() + "!!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
