package com.anshulagrawal.multiactivityapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent intent = getIntent();
        Log.i("username: ", intent.getStringExtra("username"));
        Toast.makeText(this, intent.getStringExtra("username"), Toast.LENGTH_SHORT).show();
    }

    public void toFirstActivity(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
