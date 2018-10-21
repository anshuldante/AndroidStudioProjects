package com.anshulagrawal.backgroundimagedownloader;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CustomAdaptor extends BaseAdapter {

    private Activity activity;
    private List<String> urlList;
    private ImageView imageView;
    private TextView textView;
    private int size;

    public CustomAdaptor(Activity activity, List<String> urlList) {
        this.activity = activity;
        this.urlList = urlList;
        this.size = urlList.size();
    }

    @Override
    public int getCount() {
        return size;
    }

    @Override
    public Object getItem(int i) {
        return urlList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = activity.getLayoutInflater().inflate(R.layout.cust_list_view, null);

        imageView = view.findViewById(R.id.imageView);

        textView = view.findViewById(R.id.textView);

        textView.setText(urlList.get(i));

        new ImageDownloader().execute(new ImageDownloadVO(imageView, urlList.get(i)));

        return view;
    }
}
