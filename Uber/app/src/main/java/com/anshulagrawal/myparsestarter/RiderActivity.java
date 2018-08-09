package com.anshulagrawal.myparsestarter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.IOException;
import java.util.List;

public class RiderActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private LocationManager locationManager;
    private LocationListener locationListener;
    private Button button;
    private TextView infoTextView;
    private Location currentLocation;
    private CameraUpdate cameraUpdate;

    private Handler handler = new Handler();

    private boolean isRideRequested;
    private boolean driverFound;

    public void logoutRider(View view) {
        ParseUser.logOut();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        button = findViewById(R.id.riderButton);
        infoTextView = findViewById(R.id.infoTextView);

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Requests");
        query.whereEqualTo("user", ParseUser.getCurrentUser().getUsername());

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null && objects.size() > 0) {
                    button.setText("CANCEL UBER");

                    isRideRequested = true;
                    checkForUpdates();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                }
            }
        }
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
    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                currentLocation = location;
                Log.i("Location changed", location.toString());
                updateMap();
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };
        if (Build.VERSION.SDK_INT < 23) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 0, locationListener);
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 1, locationListener);
                Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (lastKnownLocation != null) {
                    currentLocation = lastKnownLocation;
                    updateMap();
                }
            }
        }
    }


    public void processRideAction(View view) {
        if (isRideRequested) {
            button.setText("GET AN UBER");

            deleteRequests();
        } else {
            button.setText("CANCEL UBER");
            ParseObject object = new ParseObject("Requests");
            object.put("user", ParseUser.getCurrentUser().getUsername());
            object.put("latitude", currentLocation.getLatitude());
            object.put("longitude", currentLocation.getLongitude());

            object.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        Log.i("Request Save ", "successful");
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                checkForUpdates();
                            }
                        }, 2000);
                    } else {
                        Log.i("Request Save failed!", e.getMessage());
                    }
                }
            });
        }
        isRideRequested = !isRideRequested;
    }

    private void updateMap() {
        if (!driverFound) {
            LatLng location = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());

            mMap.clear();
            mMap.addMarker(new MarkerOptions().position(location).title("Marker in current location").
                    icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 17));
        }
    }

    private void checkForUpdates() {
        Log.i("Inside CheckForUpdates", "message");
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Requests");
        query.whereEqualTo("user", ParseUser.getCurrentUser().getUsername());
        query.whereExists("driver");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null && objects.size() > 0) {
                    ParseQuery<ParseUser> query = ParseUser.getQuery();

                    query.whereEqualTo("username", objects.get(0).getString("driver"));

                    query.findInBackground(new FindCallback<ParseUser>() {
                        @Override
                        public void done(List<ParseUser> objects, ParseException e) {
                            if (e == null && objects.size() > 0) {
                                driverFound = true;
                                Location driverLocation = new Location(LocationManager.GPS_PROVIDER);
                                driverLocation.setLatitude(objects.get(0).getDouble("latitude"));
                                driverLocation.setLongitude(objects.get(0).getDouble("longitude"));

                                Log.i("Distance in meters: ", Double.toString(driverLocation.distanceTo(currentLocation)));
                                double distance = driverLocation.distanceTo(currentLocation) / 1000.0;
                                if (distance < 0.01) {
                                    infoTextView.setText("Your driver is here!");

                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            button.setVisibility(View.VISIBLE);
                                            button.setText("Call An Uber");
                                            driverFound = false;
                                            isRideRequested = false;
                                            infoTextView.setText("");
                                            deleteRequests();
                                        }
                                    }, 5000);
                                } else {

                                    button.setVisibility(View.INVISIBLE);
                                    infoTextView.setText("Your Driver is " + Double.toString(distance) + " KM away");


                                    LatLngBounds.Builder builder = new LatLngBounds.Builder();

                                    LatLng userLatLng = new LatLng(driverLocation.getLatitude(), driverLocation.getLongitude());
                                    LatLng driverLatLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                                    builder.include(userLatLng);
                                    builder.include(driverLatLng);
                                    LatLngBounds bounds = builder.build();
                                    Log.i("bounds: ", bounds.toString());
                                    cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 40);

                                    mMap.addMarker(new MarkerOptions().position(userLatLng).title("Your location"));
                                    mMap.addMarker(new MarkerOptions().position(driverLatLng).title("Driver's location"));
                                    mMap.animateCamera(cameraUpdate);
                                }
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        checkForUpdates();
                                    }
                                }, 2000);
                            }
                        }
                    });

                }
            }
        });
    }

    private void deleteRequests() {
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Requests");
        query.whereEqualTo("user", ParseUser.getCurrentUser().getUsername());

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null && objects.size() > 0) {
                    for (ParseObject object : objects) {
                        object.deleteInBackground(new DeleteCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    Log.i("Request delete ", "successful");
                                } else {
                                    Log.i("Request delete ", "failed");
                                }
                            }
                        });
                    }
                }
            }
        });
    }
}
