package com.anshulagrawal.memorableplaces;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.anshulagrawal.memorableplaces.entity.MemorablePlace;
import com.anshulagrawal.memorableplaces.entity.ObjectSerializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = this.getSharedPreferences("com.anshulagrawal.memorableplaces", Context.MODE_PRIVATE);

        final ArrayList<MemorablePlace> memorablePlaces = getMemorablePlaces();

        if (memorablePlaces.size() == 0) {
            memorablePlaces.add(new MemorablePlace("Add a new place...", 12.9659292, 77.7470741));
            writeMemorablePlaces(memorablePlaces);
        }

        ArrayAdapter<MemorablePlace> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, memorablePlaces);
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                intent.putExtra("index", i);
                startActivity(intent);
            }
        });

        Object mapPlace = getIntent().getSerializableExtra("mapPlace");
        if (mapPlace != null) {
            Log.i("place: ", mapPlace.toString());
            memorablePlaces.add((MemorablePlace) mapPlace);
            writeMemorablePlaces(memorablePlaces);
        }
    }

    private ArrayList<MemorablePlace> getMemorablePlaces() {
        ArrayList<MemorablePlace> memorablePlaces = new ArrayList<>();
        try {
            memorablePlaces = (ArrayList<MemorablePlace>) ObjectSerializer.deserialize(sharedPreferences.getString("memorablePlaces", ObjectSerializer.serialize(new ArrayList<MemorablePlace>())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return memorablePlaces;
    }

    private void writeMemorablePlaces(ArrayList<MemorablePlace> memorablePlaces) {
        try {
            sharedPreferences.edit().putString("memorablePlaces", ObjectSerializer.serialize(memorablePlaces)).apply();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
