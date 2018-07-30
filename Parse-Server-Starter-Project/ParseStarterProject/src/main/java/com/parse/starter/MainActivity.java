/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class MainActivity extends AppCompatActivity implements View.OnKeyListener, View.OnClickListener {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button button;
    private TextView textView;
    private boolean isNewUser = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameEditText = (EditText) findViewById(R.id.username);
        passwordEditText = (EditText) findViewById(R.id.password);
        button = (Button) findViewById(R.id.login);
        textView = (TextView) findViewById(R.id.loginMode);

        passwordEditText.setOnKeyListener(this);

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.backgroundRelativeLayout);
        relativeLayout.setOnClickListener(this);

        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setOnClickListener(this);

        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }

    // Submit on enter
    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
            onClick(view);
        }
        return false;
    }

    public void execLogin(View view) {
        final String username = usernameEditText.getText().toString();
        final String password = passwordEditText.getText().toString();

        if ("".equals(username) || "".equals(password)) {
            Toast.makeText(getApplicationContext(), "Username & password are required fields", Toast.LENGTH_LONG).show();
        } else {
            if (isNewUser) {
                ParseUser user = new ParseUser();
                user.setUsername(username);
                user.setPassword(password);

                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Toast.makeText(getApplicationContext(), "Signed Up successfully!", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            } else {
                ParseUser.logInInBackground(username, password, new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (user != null) {
                            Toast.makeText(getApplicationContext(), "Welcome " + user.getUsername(), Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Invalid Username/password!!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.loginMode) {
            if (isNewUser) {
                button.setText("LOGIN");
                textView.setText("Or, SignUP");
                isNewUser = false;
            } else {
                button.setText("Or, SignUP");
                textView.setText("LOGIN");
                isNewUser = true;
            }
        } else if (view.getId() == R.id.imageView || view.getId() == R.id.backgroundRelativeLayout) {
            // Disable keyboard
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


}