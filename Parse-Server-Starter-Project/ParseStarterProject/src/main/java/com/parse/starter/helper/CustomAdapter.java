package com.parse.starter.helper;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseFile;
import com.parse.starter.R;

import java.util.List;

public class CustomAdapter extends BaseAdapter {
    private Activity activity;
    private List<Bitmap> itemList;
    private ImageView imageView;

    public CustomAdapter(Activity activity, List<Bitmap> itemList) {
        this.itemList = itemList;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int i) {
        return itemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Log.i("List view:", itemList.get(i).toString());
        view = activity.getLayoutInflater().inflate(R.layout.userfeedlayout, null);
        imageView = (ImageView) view.findViewById(R.id.imageView2);

        imageView.setImageBitmap(itemList.get(i));
        return view;
    }
}
