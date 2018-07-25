package com.anshulagrawal.dbdemo;

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

        try {

            SQLiteDatabase db = this.openOrCreateDatabase("Users", MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS users (name VARCHAR, age INT(3))");
            db.execSQL("INSERT INTO users (name, age) VALUES ('Anshul',29)");
            db.execSQL("INSERT INTO users (name, age) VALUES ('Atharv',1)");

            Cursor c = db.rawQuery("SELECT * FROM users", null);

            int nameIndex = c.getColumnIndex("name");
            int ageIndex = c.getColumnIndex("age");

            c.moveToFirst();

            while (c != null) {
                Log.i("Name: ", c.getString(nameIndex));
                Log.i("Age: ", Integer.toString(c.getInt(ageIndex)));
                c.moveToNext();
            }



        } catch (Exception e) {
        }
    }
}
