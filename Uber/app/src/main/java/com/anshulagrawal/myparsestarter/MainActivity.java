package com.anshulagrawal.myparsestarter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

import com.parse.LogInCallback;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Anonymous login

        ParseUser.logOut();
        if (ParseUser.getCurrentUser() == null) {
            anonymousLogin();
        } else {
            if (ParseUser.getCurrentUser().get("isDriver") != null) {
                Log.i("Info:", "Already logged in");
                getStarted(null);
            } else {
                ParseUser.logOut();
                anonymousLogin();
            }
        }
    }

    public void getStarted(View view) {
        ParseUser user = ParseUser.getCurrentUser();

        final Switch driverSwitch = findViewById(R.id.switch1);
        user.put("isDriver", driverSwitch.isChecked());
        Log.i("User Type:", Boolean.toString(user.getBoolean("isDriver")));
        ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                redirectToUserActivity(driverSwitch.isChecked());
            }
        });

    }

    private void anonymousLogin() {
        ParseAnonymousUtils.logIn(new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e == null) {
                    Log.i("Info:", "Login Successful");
                } else {
                    Log.i("Info:", "Login failed");
                }
            }
        });
    }

    private void redirectToUserActivity(boolean isDriver) {
        if (isDriver) {
            Intent intent = new Intent(getApplicationContext(), DriverActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(getApplicationContext(), RiderActivity.class);
            startActivity(intent);
        }
    }
}
