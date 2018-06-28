package com.example.a0a00uj.asdashoppingassistant.helper;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a0a00uj.asdashoppingassistant.R;
import com.example.a0a00uj.asdashoppingassistant.entity.ItemDTO;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

public class CustomAdaptor extends BaseAdapter {
    private Activity activity;
    private int size;
    private List<ItemDTO> itemList;
    private ImageView productImage;
    private TextView name;
    private TextView brandName;
    private TextView price;
    private TextView weight;
    private TextView distance;
    private boolean showDistance;

    public CustomAdaptor(Activity activity, List<ItemDTO> itemList, boolean showDistance) {
        this.itemList = itemList;
        this.activity = activity;
        this.showDistance = showDistance;
        size = itemList == null ? 0 : itemList.size();
    }

    @Override
    public int getCount() {
        return size;
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
        view = activity.getLayoutInflater().inflate(R.layout.listview_layout, null);
        productImage = view.findViewById(R.id.productImage);
        name = view.findViewById(R.id.name);
        brandName = view.findViewById(R.id.brandName);
        weight = view.findViewById(R.id.weight);
        price = view.findViewById(R.id.price);
        distance = view.findViewById(R.id.distance);

        ItemDTO item = (ItemDTO) getItem(i);

        productImage.setImageBitmap(item.getBitmap());
        name.setText(item.getName());
        brandName.setText(item.getBrandName());
        price.setText(item.getPrice());
        weight.setText(item.getWeight());
        if (showDistance) {
            BigDecimal bigDecimal = new BigDecimal(item.getDistance() / 100.0);
            bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_DOWN);
            distance.setText(bigDecimal.toString() + " meters");
        } else {
            distance.setVisibility(View.GONE);
        }
        return view;
    }
}