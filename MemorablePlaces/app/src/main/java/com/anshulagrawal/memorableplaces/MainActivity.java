package com.anshulagrawal.memorableplaces;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.anshulagrawal.memorableplaces.entity.MemorablePlace;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private List<MemorablePlace> memorablePlaces = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (memorablePlaces.size() == 0) {
            memorablePlaces.add(new MemorablePlace("Add a new place...", 12.9659292, 77.7470741));
        }

        ArrayAdapter<MemorablePlace> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, memorablePlaces);
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                intent.putExtra("lat", memorablePlaces.get(i).getLatitude());
                intent.putExtra("long", memorablePlaces.get(i).getLongitude());
                startActivity(intent);
            }
        });

        Object place = getIntent().getSerializableExtra("place");
        if (place != null) {
            Log.i("place: ", place.toString());
        }
    }
}
