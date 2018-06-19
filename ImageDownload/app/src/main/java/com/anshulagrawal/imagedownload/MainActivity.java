package com.anshulagrawal.imagedownload;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void downloadImage(View view) {
        Log.i("Interaction,", "Button Clicked!");
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        try {
            Bitmap myImage = new ImageDownloader().execute("https://upload.wikimedia.org/wikipedia/en/a/af/Son_Goku_YoungAdult.PNG").get();
            imageView.setImageBitmap(myImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.connect();
                InputStream stream = urlConnection.getInputStream();

                return BitmapFactory.decodeStream(stream);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
