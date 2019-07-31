package com.guideme.guideme.ui.dashboard;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Path;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.config.GoogleDirectionConfiguration;
import com.akexorcist.googledirection.constant.AvoidType;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.model.Step;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.snackbar.Snackbar;
import com.guideme.guideme.BuildConfig;
import com.guideme.guideme.R;
import com.guideme.guideme.data.ParseJSON;
import com.uber.sdk.android.rides.RideRequestButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookRideActivity extends FragmentActivity implements DirectionCallback, GoogleMap.OnCameraChangeListener, PlaceSelectionListener, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private RequestPermissionAction onPermissionCallBack;
    private final static int REQUEST_BULK_PERMISSION = 3006;
    private String mPlaceName;
    private String longitude = "30";
    private String latitude = "30";
    private Location lastLocation;
    private LatLng to;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_book_ride);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};

        RideRequestButton requestButton = findViewById(R.id.rideRequestButton);

        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawLocation(lastLocation.getLongitude()+"", lastLocation.getLatitude()+"", longitude, latitude);
                String address = "", Address2 = "", city = "", State = "", Country = "", County = "", PIN = "";
                try {
                    JSONObject jsonObj = ParseJSON.getJSONfromURL("https://maps.googleapis.com/maps/api/geocode/json?latlng=" + latitude + "," + longitude + "&sensor=true&key=AIzaSyBdCXqTL1firHWYqahfPkXCIoeMPlX6-II");
                    String Status = jsonObj.getString("status");
                    if (Status.equalsIgnoreCase("OK")) {
                        JSONArray Results = jsonObj.getJSONArray("results");
                        JSONObject zero = Results.getJSONObject(0);
                        JSONArray address_components = zero.getJSONArray("address_components");

                        for (int i = 0; i < address_components.length(); i++) {
                            JSONObject zero2 = address_components.getJSONObject(i);
                            String long_name = zero2.getString("long_name");
                            JSONArray mtypes = zero2.getJSONArray("types");
                            String Type = mtypes.getString(0);

                            if (TextUtils.isEmpty(long_name) == false || !long_name.equals(null) || long_name.length() > 0 || long_name != "") {
                                if (Type.equalsIgnoreCase("street_number")) {
                                    address = long_name + " ";
                                } else if (Type.equalsIgnoreCase("route")) {
                                    address = address + long_name;
                                } else if (Type.equalsIgnoreCase("sublocality")) {
                                    Address2 = long_name;
                                } else if (Type.equalsIgnoreCase("locality")) {
                                    // Address2 = Address2 + long_name + ", ";
                                    city = long_name;
                                } else if (Type.equalsIgnoreCase("administrative_area_level_2")) {
                                    County = long_name;
                                } else if (Type.equalsIgnoreCase("administrative_area_level_1")) {
                                    State = long_name;
                                } else if (Type.equalsIgnoreCase("country")) {
                                    Country = long_name;
                                } else if (Type.equalsIgnoreCase("postal_code")) {
                                    PIN = long_name;
                                }
                            }

                            // JSONArray mtypes = zero2.getJSONArray("types");
                            // String Type = mtypes.getString(0);
                            // Log.e(Type,long_name);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                String drop_address = "";
                String drop_city = "";
                try {
                    JSONObject jsonObj = ParseJSON.getJSONfromURL("https://maps.googleapis.com/maps/api/geocode/json?latlng=" + latitude + "," + longitude + "&sensor=true&key=AIzaSyBdCXqTL1firHWYqahfPkXCIoeMPlX6-II");
                    String Status = jsonObj.getString("status");
                    if (Status.equalsIgnoreCase("OK")) {
                        JSONArray Results = jsonObj.getJSONArray("results");
                        JSONObject zero = Results.getJSONObject(0);
                        JSONArray address_components = zero.getJSONArray("address_components");

                        for (int i = 0; i < address_components.length(); i++) {
                            JSONObject zero2 = address_components.getJSONObject(i);
                            String long_name = zero2.getString("long_name");
                            JSONArray mtypes = zero2.getJSONArray("types");
                            String Type = mtypes.getString(0);

                            if (TextUtils.isEmpty(long_name) == false || !long_name.equals(null) || long_name.length() > 0 || long_name != "") {
                                if (Type.equalsIgnoreCase("street_number")) {
                                    drop_address = long_name + " ";
                                } else if (Type.equalsIgnoreCase("route")) {
                                    drop_address = drop_address + long_name;
                                } else if (Type.equalsIgnoreCase("locality")) {
                                    // Address2 = Address2 + long_name + ", ";
                                    drop_city = long_name;
                                }
                            }

                            // JSONArray mtypes = zero2.getJSONArray("types");
                            // String Type = mtypes.getString(0);
                            // Log.e(Type,long_name);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                address = address.replaceAll(" ", "%20");
                city = city.replaceAll(" ", "%20");
                drop_address = drop_address.replaceAll(" ", "%20");
                drop_city = drop_city.replaceAll(" ", "%20");

//                String uri = "https://m.uber.com/ul/?client_id=evlvdjIz8Guu4Hka2dFfcoiNdm98kwry&action=setPickup&pickup[latitude]=" + lastLocation.getLatitude() + "&pickup[longitude]=-" + lastLocation.getLongitude() + "&pickup[nickname]=" + address + "&dropoff[latitude]=" + latitude + "&dropoff[longitude]=" + longitude + "&dropoff[nickname]=" + drop_address + "&product_id=a1111c8c-c720-46c3-8534-2fcdd730040d\n";
                String uri = "https://m.uber.com/ul/?client_id=evlvdjIz8Guu4Hka2dFfcoiNdm98kwry&action=setPickup&pickup[latitude]=" + lastLocation.getLatitude() + "&pickup[longitude]=" + lastLocation.getLongitude() + "&pickup[nickname]=" + city + "&pickup[formatted_address]=" + address + "&dropoff[latitude]=" + latitude + "&dropoff[longitude]=" + longitude + "&dropoff[nickname]=" + drop_city + "&dropoff[formatted_address]=" + drop_address + "&product_id=a1111c8c-c720-46c3-8534-2fcdd730040d\n";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(uri));
                startActivity(intent);

            }
        });
        getBulkPermissions(permissions, new RequestPermissionAction() {
            @Override
            public void permissionDenied() {
                // TODO: 5/27/2019 handle permission deny
                checkLocationPermission();
            }

            @Override
            public void permissionGranted() {
                // TODO: 5/27/2019 you code do further operations
            }
        });

        // Initialize Places.
        Places.initialize(getApplicationContext(), "AIzaSyBdCXqTL1firHWYqahfPkXCIoeMPlX6-II");
        // Create a new Places client instance.
        PlacesClient placesClient = Places.createClient(getApplicationContext());

        // Initialize the AutocompleteSupportFragment.
        final AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        autocompleteFragment.setHint("Enter a location");
        autocompleteFragment.setCountry("EG");

        // Specify the types of tripPlace data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.LAT_LNG));

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    Activity#requestPermissions.
                return;
            }
        }

        buildGoogleApiClient();
        mMap.setMyLocationEnabled(true);
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng latLng) {

                // Creating a marker
                MarkerOptions markerOptions = new MarkerOptions();

                // Setting the position for the marker
                markerOptions.position(latLng);
                longitude = latLng.longitude + "";
                latitude = latLng.latitude + "";
                to = latLng;
                drawLocation(lastLocation.getLongitude()+"", lastLocation.getLongitude()+"", latitude, longitude);
                // Setting the title for the marker.
                // This will be displayed on taping the marker
                markerOptions.title(latLng.latitude + " : " + latLng.longitude);

                // Clears the previously touched position
                mMap.clear();

                // Animating to the touched position
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                // Placing a marker on the touched position
                mMap.addMarker(markerOptions);
            }
        });
//        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
//        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        // to set the center of the screen point to the location
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//        mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
    }


    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        lastLocation = location;
//        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//        // to set the center of the screen point to the location
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//        mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            } else {
                checkLocationPermission();
            }
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);


//        mLocationRequest = new LocationRequest();
//        //refresh location interval
//        mLocationRequest.setInterval(1000);
//        mLocationRequest.setFastestInterval(1000);
//        // Best location, Drain battery :(
//        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    Activity#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for Activity#requestPermissions for more details.
//            return;
//        }
//


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    //////////////////////////////////
    //PERMISSION RELATED METHODS
    private boolean checkBulkPermissions(String[] permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String permission : permissions) {
                if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
            return true;
        } else {
            return true;
        }
    }

    public void getBulkPermissions(String[] permissions, RequestPermissionAction onPermissionCallBack) {
        this.onPermissionCallBack = onPermissionCallBack;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!checkBulkPermissions(permissions)) {
                requestPermissions(permissions, REQUEST_BULK_PERMISSION);
                return;
            }
        }
        if (onPermissionCallBack != null)
            onPermissionCallBack.permissionGranted();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (onPermissionCallBack != null)
                onPermissionCallBack.permissionGranted();
        } else if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
            if (onPermissionCallBack != null)
                onPermissionCallBack.permissionDenied();
        }
    }

    @Override
    public void onPlaceSelected(@NonNull Place place) {
        if (mMap == null) {
            return;
        }
        final LatLng latLng = place.getLatLng();
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        longitude = latLng.longitude + "";
        latitude = latLng.latitude + "";
        drawLocation(lastLocation.getLongitude()+"", lastLocation.getLongitude()+"", latitude, longitude);

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
        mMap.setOnCameraChangeListener(this);
    }

    @Override
    public void onError(@NonNull Status status) {
        Log.e(BuildConfig.BUILD_TYPE, "onError: Status = " + status.toString());

        Toast.makeText(this, "Place selection failed: " + status.getStatusMessage(),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        mMap.addMarker(new MarkerOptions().position(cameraPosition.target).title(mPlaceName));

        mMap.setOnCameraChangeListener(null);
    }

    @Override
    public void onDirectionSuccess(Direction direction, String rawBody) {
//        Snackbar.make(btnRequestDirection, "Success with status : " + direction.getStatus(), Snackbar.LENGTH_SHORT).show();
        if (direction.isOK()) {
            Route route = direction.getRouteList().get(0);
            int legCount = route.getLegList().size();
            for (int index = 0; index < legCount; index++) {
                Leg leg = route.getLegList().get(index);
                mMap.addMarker(new MarkerOptions().position(leg.getStartLocation().getCoordination()));
                if (index == legCount - 1) {
                    mMap.addMarker(new MarkerOptions().position(leg.getEndLocation().getCoordination()));
                }
                List<Step> stepList = leg.getStepList();
                ArrayList<PolylineOptions> polylineOptionList = DirectionConverter.createTransitPolyline(this, stepList, 5, getResources().getColor(R.color.colorAccent), 3, Color.BLUE);
                for (PolylineOptions polylineOption : polylineOptionList) {
                    mMap.addPolyline(polylineOption);
                }
            }
            setCameraWithCoordinationBounds(route);
//            btnRequestDirection.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDirectionFailure(Throwable t) {

    }

    public interface RequestPermissionAction {
        void permissionDenied();

        void permissionGranted();
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                new AlertDialog.Builder(this)
                        .setTitle("give permission")
                        .setMessage("give permission message")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(BookRideActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                            }
                        })
                        .create()
                        .show();
            } else {
                ActivityCompat.requestPermissions(BookRideActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void drawLocation(String fromLong, String fromLat, String toLong, String toLat){
        GoogleDirectionConfiguration.getInstance().setLogEnabled(true);
        LatLng from = new LatLng(Double.parseDouble(fromLat), Double.parseDouble(fromLong));
        LatLng to = new LatLng(Double.parseDouble(toLat), Double.parseDouble(toLong));
        GoogleDirection.withServerKey("AIzaSyBdCXqTL1firHWYqahfPkXCIoeMPlX6-II")
                .from(from)
                .to(to)
                .transportMode(TransportMode.DRIVING)
                .execute(this);
    }

    private void setCameraWithCoordinationBounds(Route route) {
        LatLng southwest = route.getBound().getSouthwestCoordination().getCoordination();
        LatLng northeast = route.getBound().getNortheastCoordination().getCoordination();
        LatLngBounds bounds = new LatLngBounds(southwest, northeast);
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
    }

}



