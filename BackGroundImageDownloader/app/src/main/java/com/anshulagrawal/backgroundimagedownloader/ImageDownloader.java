package com.anshulagrawal.backgroundimagedownloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageDownloader extends AsyncTask<ImageDownloadVO, Void, Bitmap> {

    private ImageDownloadVO vo;

    @Override
    protected Bitmap doInBackground(ImageDownloadVO... imageDownloadVOs) {

        try {
            vo = imageDownloadVOs[0];
            URL url = new URL(imageDownloadVOs[0].getUrl());
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.connect();
            InputStream stream = urlConnection.getInputStream();

            return BitmapFactory.decodeStream(stream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        vo.getImageView().setImageBitmap(bitmap);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
