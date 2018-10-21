package com.anshulagrawal.backgroundimagedownloader;

import android.widget.ImageView;

public class ImageDownloadVO {

    private String url;
    private ImageView imageView;

    public ImageDownloadVO(ImageView imageView, String url) {
        this.imageView = imageView;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }
}
