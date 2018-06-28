package com.example.a0a00uj.asdashoppingassistant;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.a0a00uj.asdashoppingassistant.entity.ItemDTO;
import com.example.a0a00uj.asdashoppingassistant.helper.CustomAdaptor;
import com.example.a0a00uj.asdashoppingassistant.singleton.AppData;
import com.example.a0a00uj.asdashoppingassistant.task.ImageDownloader;
import com.example.a0a00uj.asdashoppingassistant.task.RestGetTask;

import java.util.List;

public class CompletionActivity extends Activity {

    private static final StringBuilder url = new StringBuilder(AppData.COMPLETE_URL);

    private ListView completionListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completion);
        completionListView = (ListView) findViewById(R.id.completionListView);

        int size = AppData.scannedBarcodeList.size();
        Log.i("Scanned barcodes: ", AppData.scannedBarcodeList.toString());
        for (int i = 0; i < size; i++) {
            url.append(AppData.scannedBarcodeList.get(i)).append("%7C");
        }
        try {
            List<ItemDTO> itemList = new RestGetTask().execute(url.toString()).get();
            size = itemList == null ? 0 : itemList.size();
            ItemDTO item = null;

            for (int i = 0; i < size; i++) {
                item = itemList.get(i);
                item.setBitmap(new ImageDownloader().execute(item.getImageURL()).get());
            }

            completionListView.setAdapter(new CustomAdaptor(this, itemList, true));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
