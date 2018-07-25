package com.anshulagrawal.historicalplacesdb;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQLiteDatabase db = this.openOrCreateDatabase("history", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS events (name VARCHAR, place VARCHAR)");

        db.execSQL("INSERT INTO events (name, place) VALUES ('Battle of Panipat', 'Panipat')");
        db.execSQL("INSERT INTO events (name, place) VALUES ('Battle of Buxar', 'Buxar')");


        Cursor c = db.rawQuery("SELECT * FROM events", null);

        int nameIndex = c.getColumnIndex("name");
        int placeIndex = c.getColumnIndex("place");

        Log.i("name at: ", Integer.toString(nameIndex));
        Log.i("place at: ", Integer.toString(placeIndex));
        c.moveToFirst();

        while (c.moveToNext()) {
            Log.i("Name: ", c.getString(nameIndex));
            Log.i("Place: ", c.getString(placeIndex));
        }
    }
}
