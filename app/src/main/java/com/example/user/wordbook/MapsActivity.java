package com.example.user.wordbook;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {


    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        mMap.setMyLocationEnabled(true);
    }


    public void findLocation(View view) {
        EditText e = (EditText) findViewById(R.id.locationToFind);
        String s = e.getText().toString();
        List<Address> addresses = null;

        Geocoder geocoder = new Geocoder(this);
        try {
            addresses = geocoder.getFromLocationName(s, 1);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        Address a = addresses.get(0);
        LatLng lt = new LatLng(a.getLatitude(), a.getLongitude());
        mMap.addMarker(new MarkerOptions().position(lt).title("Marker in " + s));
        mMap.animateCamera(CameraUpdateFactory.newLatLng(lt));

    }


public  void changeType(View view)
{
    if(mMap.getMapType()==GoogleMap.MAP_TYPE_NORMAL)
    {
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
    }
    else
    {
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

}


}
