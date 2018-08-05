package com.anshulagrawal.myparseproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class MainActivity extends AppCompatActivity {
    private Switch userTypeSwitch;


    public void switchUserType(View view) {
        if (userTypeSwitch.isChecked()) {
            Log.i("User type: ", "Rider");
        } else {
            Log.i("User type: ", "Driver");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        userTypeSwitch = findViewById(R.id.userSwitch);

//        ParseUser.enableAutomaticUser();
//
//        ParseObject object = new ParseObject("ExampleObject");
//        object.put("myNumber", "456");
//        object.put("myString", "anshul");
//
//        object.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(ParseException ex) {
//                if (ex == null) {
//                    Log.i("Parse Result", "Successful!");
//                } else {
//                    Log.i("Parse Result", "Failed with " + ex.toString());
//                }
//            }
//        });


        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }
}
