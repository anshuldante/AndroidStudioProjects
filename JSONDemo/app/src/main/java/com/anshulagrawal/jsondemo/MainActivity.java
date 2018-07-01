package com.anshulagrawal.jsondemo;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DownloadTask task = new DownloadTask();

        task.execute("http://jsonplaceholder.typicode.com/posts/1");
    }

    public class DownloadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            StringBuilder builder = new StringBuilder();
            URL url = null;
            HttpURLConnection conn = null;

            try {
                url = new URL(strings[0]);
                conn = (HttpURLConnection) url.openConnection();
                InputStream in = conn.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while (data != -1) {
                    char current = (char) data;
                    builder.append(current);
                    data = reader.read();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return builder.toString();
        }

        @Override
        public void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject json = new JSONObject(s);
                String body = json.getString("body");

                Log.i("Body: ", body);
            } catch (Exception e) {
            }
        }
    }
}
