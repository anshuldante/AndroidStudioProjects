package com.anshulagrawal.dbadvanced;

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

        SQLiteDatabase db = this.openOrCreateDatabase("Users", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS newUsers (name VARCHAR, age INT(3), id INTEGER PRIMARY KEY)");

//      db.execSQL("INSERT INTO newUsers (name, age) VALUES ('Anshul',29)");
//      db.execSQL("DELETE FROM newUsers where id = 1");

        Cursor c = db.rawQuery("SELECT * FROM newUsers", null);

        int nameIndex = c.getColumnIndex("name");
        int ageIndex = c.getColumnIndex("age");
        int idIndex = c.getColumnIndex("id");

        c.moveToFirst();

        while (c.moveToNext()) {
            Log.i("UserResults - Id: ", Integer.toString(c.getInt(idIndex)));
            Log.i("UserResults - Name: ", c.getString(nameIndex));
            Log.i("UserResults - Age: ", Integer.toString(c.getInt(ageIndex)));

        }
    }
}
