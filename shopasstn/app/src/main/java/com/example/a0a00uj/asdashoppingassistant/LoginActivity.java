package com.example.a0a00uj.asdashoppingassistant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends Activity {

    private Map<String, String> emailPhoneMap;
    private EditText emailEditText;
    private EditText phoneEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.a0a00uj.asdashoppingassistant.R.layout.activity_login);

        emailPhoneMap = new HashMap<>();
        emailPhoneMap.put("shyamalendu.tripathy@walmartlabs.com", "9717297235");
        emailPhoneMap.put("anshul.agrawal@walmartlabs.com", "7795238483");

        emailEditText = findViewById(com.example.a0a00uj.asdashoppingassistant.R.id.email);
        phoneEditText = findViewById(R.id.phone);
    }

    public void login(View view) {
        String email = ((EditText) findViewById(com.example.a0a00uj.asdashoppingassistant.R.id.email)).getText().toString();
        String password = ((EditText) findViewById(com.example.a0a00uj.asdashoppingassistant.R.id.phone)).getText().toString();

        String expected = emailPhoneMap.get(email);
        if (password.equals(expected)) {
            Bundle bundle = new Bundle();
            bundle.putString("email", email);
            startActivity(new Intent(this, OrderActivity.class).putExtras(bundle));
            phoneEditText.setError(null);
            emailEditText.setError(null);
        } else if (expected == null) {
            Toast.makeText(this, "Invalid Email ID!!!", Toast.LENGTH_LONG).show();
            emailEditText.setError("Invalid Email ID!!!");
            phoneEditText.setError(null);
            emailEditText.requestFocus();
        } else {
            Toast.makeText(this, "Invalid Invalid Email/mobile combination!!", Toast.LENGTH_LONG).show();
            phoneEditText.setError("Invalid Email/mobile combination!!");
            phoneEditText.requestFocus();
        }
    }
}
