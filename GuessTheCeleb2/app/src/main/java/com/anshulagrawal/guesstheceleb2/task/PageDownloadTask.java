package com.anshulagrawal.guesstheceleb2.task;

import android.os.AsyncTask;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class PageDownloadTask extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... strings) {
        StringBuilder builder = new StringBuilder();

        URL url = null;
        HttpURLConnection urlConn = null;

        try {
            url = new URL(strings[0]);
            urlConn = (HttpURLConnection) url.openConnection();
            InputStream in = urlConn.getInputStream();
            InputStreamReader reader = new InputStreamReader(in);
            int data = 0;

            while (data != -1) {
                builder.append((char) data);
                data = reader.read();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.toString();
    }
}