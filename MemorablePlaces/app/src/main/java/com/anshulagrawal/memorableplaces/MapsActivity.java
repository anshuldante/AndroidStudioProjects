package com.anshulagrawal.memorableplaces;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.anshulagrawal.memorableplaces.entity.MemorablePlace;
import com.anshulagrawal.memorableplaces.entity.ObjectSerializer;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<MemorablePlace> memorablePlaces;

    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        index = getIntent().getIntExtra("index", 0);
        memorablePlaces = getMemorablePlaces();

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng latLng = new LatLng(memorablePlaces.get(index).getLatitude(), memorablePlaces.get(index).getLongitude());
        mMap.addMarker(new MarkerOptions().position(latLng).title(""));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                Toast.makeText(getApplicationContext(), "Location Saved!", Toast.LENGTH_LONG).show();
                try {
                    List<Address> addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);

                    MemorablePlace mapsPlace = null;
                    if (addressList != null && addressList.size() > 0) {
                        mapsPlace = new MemorablePlace(addressList.get(0).getAddressLine(0), latLng.latitude, latLng.longitude);
                    } else {
                        mapsPlace = new MemorablePlace(new Date().toString(), latLng.latitude, latLng.longitude);
                    }

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("mapPlace", mapsPlace);
                    startActivity(intent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private ArrayList<MemorablePlace> getMemorablePlaces() {
        ArrayList<MemorablePlace> memorablePlaces = new ArrayList<>();
        try {
            memorablePlaces = (ArrayList<MemorablePlace>) ObjectSerializer.deserialize(this.getSharedPreferences("com.anshulagrawal.memorableplaces", Context.MODE_PRIVATE).getString("memorablePlaces", ObjectSerializer.serialize(new ArrayList<MemorablePlace>())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return memorablePlaces;
    }
}
