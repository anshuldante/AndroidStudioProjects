package com.anshulagrawal.newsreaderapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> titles;
    private ArrayList<String> content;

    private int itemsToFetch = 5;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        installTrustAllCert();
        listView = findViewById(R.id.listview);
        titles = new ArrayList<>();
        content = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, titles);

        listView.setAdapter(adapter);

        db = this.openOrCreateDatabase("Acticles", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS articles (id INTEGER PRIMARY KEY, articleId INTEGER, title VARCHAR, content VARCHAR)");

        updateListview();
        APIContentDownloader downloader = new APIContentDownloader();

        try {
//            downloader.execute("https://hacker-news.firebaseio.com/v0/topstories.json?print=pretty");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void installTrustAllCert() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }
            };

            // Install the all-trusting trust manager
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            // Create all-trusting host name verifier
            HostnameVerifier allHostsValid = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };

            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateListview() {
        Log.i("Update list view called", "");
        Cursor cursor = db.rawQuery("SELECT * FROM articles", null);
        int contentIndex = cursor.getColumnIndex("content");
        int titleIndex = cursor.getColumnIndex("title");

        if (cursor.moveToFirst()) {
            titles.clear();
            content.clear();

            do {
                titles.add(cursor.getString(titleIndex));
                content.add(cursor.getString(contentIndex));
            } while (cursor.moveToNext());

            adapter.notifyDataSetChanged();
        }
    }


    class APIContentDownloader extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            StringBuilder builder = new StringBuilder();
            URL url = null;
            HttpURLConnection conn = null;
            InputStreamReader reader = null;

            try {
                url = new URL(strings[0]);
                conn = (HttpURLConnection) url.openConnection();
                InputStream in = conn.getInputStream();
                reader = new InputStreamReader(in);
                int data = reader.read();

                while (data != -1) {
                    char current = (char) data;
                    builder.append(current);
                    data = reader.read();
                }

                JSONArray jsonArray = new JSONArray(builder.toString());
                int size = jsonArray.length() < itemsToFetch ? jsonArray.length() : itemsToFetch;

                db.execSQL("DELETE FROM articles");
                for (int i = 0; i < size; i++) {
                    Log.i("Reading article number ", Integer.toString(i));
                    String articleId = jsonArray.getString(i);
                    url = new URL("https://hacker-news.firebaseio.com/v0/item/8863.json?print=pretty".replace("8863", articleId));
                    conn = (HttpURLConnection) url.openConnection();
                    in = conn.getInputStream();
                    reader = new InputStreamReader(in);

                    data = reader.read();

                    builder = new StringBuilder();
                    while (data != -1) {
                        builder.append((char) data);
                        data = reader.read();
                    }

                    JSONObject jsonObject = new JSONObject(builder.toString());

                    if (!jsonObject.isNull("title") && !jsonObject.isNull("url")) {
                        String articleTitle = jsonObject.getString("title");
                        String articleUrl = jsonObject.getString("url");


                        url = new URL(articleUrl);
                        conn = (HttpURLConnection) url.openConnection();
                        in = conn.getInputStream();
                        reader = new InputStreamReader(in);
                        data = reader.read();

                        builder = new StringBuilder();
                        while (data != -1) {
                            builder.append((char) data);
                            data = reader.read();
                        }

                        String sql = "INSERT INTO articles (articleId, title, content) values (?, ?, ?)";

                        SQLiteStatement statement = db.compileStatement(sql);

                        statement.bindString(1, articleId);
                        statement.bindString(2, articleTitle);
                        statement.bindString(3, builder.toString());
                        statement.execute();
                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            updateListview();
        }
    }
}
