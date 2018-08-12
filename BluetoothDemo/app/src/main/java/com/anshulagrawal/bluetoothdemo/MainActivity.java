package com.anshulagrawal.bluetoothdemo;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {


    private BluetoothAdapter ba = BluetoothAdapter.getDefaultAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ba.isEnabled()) {
            Toast.makeText(getApplicationContext(), "Bluetooth is on!!", Toast.LENGTH_LONG).show();
        } else {
            Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(i);
            if (ba.isEnabled()) {
                Toast.makeText(getApplicationContext(), "Bluetooth turned on!!", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void turnBluetoothOff(View view) {
        ba.disable();
        if (ba.isEnabled()) {
            Toast.makeText(getApplicationContext(), "Bluetooth could no be disabled", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Bluetooth turned off", Toast.LENGTH_LONG).show();
        }
    }

    public void findDoscoverableDevices(View view) {
        Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        startActivity(i);
    }

    public void viewPairedDevices(View view) {
        Set<BluetoothDevice> pairedDevices = ba.getBondedDevices();
        ArrayList<String> bdList = new ArrayList<>();

        for (BluetoothDevice bd : pairedDevices) {
            bdList.add(bd.getName());
        }

        ListView listView = findViewById(R.id.listView);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, bdList);

        listView.setAdapter(adapter);
    }
}
