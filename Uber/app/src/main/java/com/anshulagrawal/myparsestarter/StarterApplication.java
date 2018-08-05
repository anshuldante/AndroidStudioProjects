package com.anshulagrawal.myparsestarter;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;


public class StarterApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        // Add your initialization code here
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("6ac00f223cf6ffe7f0b7fd7c54a253140b896d86")
                .clientKey("6eba7ad9a361c8e5171434bca601b9e4967a928e")
                .server("http://ec2-13-58-23-129.us-east-2.compute.amazonaws.com/parse/")
                .build()
        );


//        ParseUser user = new ParseUser();
//        user.setUsername("test");
//        user.setPassword("test");
//        try {
//            user.signUp();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);

    }
}

