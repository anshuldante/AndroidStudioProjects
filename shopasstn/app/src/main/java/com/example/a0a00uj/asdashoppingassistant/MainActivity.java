package com.example.a0a00uj.asdashoppingassistant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.a0a00uj.asdashoppingassistant.entity.ItemDTO;
import com.example.a0a00uj.asdashoppingassistant.helper.CustomAdaptor;
import com.example.a0a00uj.asdashoppingassistant.singleton.AppData;
import com.example.a0a00uj.asdashoppingassistant.task.ImageDownloader;
import com.example.a0a00uj.asdashoppingassistant.task.RestGetTask;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.List;

public class MainActivity extends Activity {

    private String productId;
    private EditText barcodeEditText;
    private ListView listView;
    private ConstraintLayout barcodeContainer;
    private LinearLayout mainLinearLayout;
    private Button barcodeScanButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.a0a00uj.asdashoppingassistant.R.layout.activity_main);

        barcodeEditText = findViewById(com.example.a0a00uj.asdashoppingassistant.R.id.barcodeEditText);
        listView = findViewById(R.id.recomListView);
        barcodeContainer = findViewById(R.id.barcodeContainer);
        mainLinearLayout = findViewById(R.id.mainLinearLayout);
        barcodeScanButton = findViewById(R.id.barcodeScanButton);
        barcodeScanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new IntentIntegrator(MainActivity.this).initiateScan();
            }
        });
    }

    public void completeOrder(View view) {
        startActivity(new Intent(this, CompletionActivity.class));
    }

    public void processBarcode(View view) {
        productId = barcodeEditText.getText().toString();
        AppData.scannedBarcodeList.add(productId);
        try {
            List<ItemDTO> result = new RestGetTask().execute(AppData.SCAN_URL + productId).get();
            int size = result == null ? 0 : result.size();
            ItemDTO item = null;

            Log.i("List size: ", Integer.toString(result.size()));

            for (int i = 0; i < size; i++) {
                item = result.get(i);
                item.setBitmap(new ImageDownloader().execute(item.getImageURL()).get());
            }

            listView.setAdapter(new CustomAdaptor(this, result, true));
            barcodeContainer.setVisibility(View.GONE);
            mainLinearLayout.setVisibility(View.VISIBLE);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                productId = result.getContents();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
