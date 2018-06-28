package com.example.a0a00uj.asdashoppingassistant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.a0a00uj.asdashoppingassistant.entity.ItemDTO;
import com.example.a0a00uj.asdashoppingassistant.helper.CustomAdaptor;
import com.example.a0a00uj.asdashoppingassistant.singleton.AppData;
import com.example.a0a00uj.asdashoppingassistant.task.ImageDownloader;
import com.example.a0a00uj.asdashoppingassistant.task.RestGetTask;

import java.util.List;

public class OrderActivity extends Activity {

    private String email;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        email = getIntent().getExtras().getString("email");
        listView = findViewById(R.id.cartContent);

        try {
            List<ItemDTO> itemList = new RestGetTask().execute(AppData.LOGIN_URL_PART_1 + email + AppData.LOGIN_URL_PART_2).get();
            int size = itemList == null ? 0 : itemList.size();
            ItemDTO item = null;

            for (int i = 0; i < size; i++) {
                item = itemList.get(i);
                item.setBitmap(new ImageDownloader().execute(item.getImageURL()).get());
            }

            listView.setAdapter(new CustomAdaptor(this, itemList, true));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadBarcodeScreen(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }
}
