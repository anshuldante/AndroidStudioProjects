package com.anshulagrawal.whatstheweather;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView display;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        display = findViewById(R.id.RESULT);
        editText = findViewById(R.id.city);
    }

    public class WeatherFetcher extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            URL url = null;
            HttpURLConnection connection = null;
            StringBuilder builder = new StringBuilder();
            try {
                url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + strings[0] + "&APPID=467c089ac4c7cc69972513b818fa359f");
                connection = (HttpURLConnection) url.openConnection();
                InputStream in = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while (data != -1) {
                    builder.append((char) data);
                    data = reader.read();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return builder.toString();
        }

        @Override
        public void onPostExecute(String result) {
            try {
                JSONObject json = new JSONObject(result);
                JSONObject object = json.getJSONArray("weather").getJSONObject(0);
                String displayText = object.getString("main") + " : " + object.getString("description");
                Log.i("Weather report:", displayText);
                display.setText(displayText);
            } catch (Exception e) {
                display.setText("Invalid City!!");
            }
        }
    }

    public void onClick(View view) {
        Log.i("Insie button click:", Integer.toString(view.getId()));
        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        WeatherFetcher weatherFetcher = new WeatherFetcher();
        weatherFetcher.execute(editText.getText().toString());
    }
}
