package com.guideme.guideme.ui.trip_creation;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.model.Place;
import com.guideme.guideme.R;
//import com.rtchagas.pingplacepicker.PingPlacePicker;

public class OrderCabActivity extends FragmentActivity implements OnMapReadyCallback {
    private int REQUEST_PLACE_PICKER = 1001;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_cab);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
//        showPlacePicker();
    }

//    private void showPlacePicker() {
//        PingPlacePicker.IntentBuilder builder = new PingPlacePicker.IntentBuilder();
//        builder.setAndroidApiKey("YOUR_ANDROID_API_KEY")
//                .setMapsApiKey("YOUR_MAPS_API_KEY");
//
//        // If you want to set a initial location rather then the current device location.
//        // NOTE: enable_nearby_search MUST be true.
//        // builder.setLatLng(new LatLng(37.4219999, -122.0862462))
//
//        try {
//            Intent placeIntent = builder.build(this);
//            startActivityForResult(placeIntent, REQUEST_PLACE_PICKER);
//        }
//        catch (Exception ex) {
//            // Google Play services is not available...
//        }
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if ((requestCode == REQUEST_PLACE_PICKER) && (resultCode == RESULT_OK)) {
//            Place place = PingPlacePicker.getPlace(data);
//            if (place != null) {
//                Toast.makeText(this, "You selected the place: " + place.getName(), Toast.LENGTH_SHORT).show();
//            }
//        }
//    }



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

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
