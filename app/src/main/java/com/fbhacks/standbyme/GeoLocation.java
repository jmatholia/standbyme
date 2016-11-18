package com.fbhacks.standbyme;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

public class GeoLocation extends Activity {

    private LocationManager locationManagaer = null;
    private LocationListener locationListener = null;
    String locationProvider = LocationManager.GPS_PROVIDER;
    public static final int MY_PERMISSIONS_REQUEST_COARSE_LOC_PERM = 2;
    public static final int MY_PERMISSIONS_REQUEST_FINE_LOC_PERM = 3;

    private Button btnGetLocation = null;
    private EditText editLocation = null;
    private ProgressBar pb = null;

    private static final String TAG = "---------------- Debug";
    private Boolean flag = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo_location);

        // Acquire a reference to the system Location Manager
        locationManagaer = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        Log.d(TAG, "Starting Location");
                // Or use LocationManager.GPS_PROVIDER

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST_COARSE_LOC_PERM);
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_FINE_LOC_PERM);
            Log.d(TAG, "No Permission");
            return;
        }


        // Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
        };

        sendLocation();

        // Register the listener with the Location Manager to receive location updates
        // locationManager.requestLocationUpdates(locationProvider, 0, 0, locationListener);
//        locationManagaer.requestLocationUpdates(LocationManager
//                .GPS_PROVIDER, 5000, 10, locationListener);

    }

    public Location sendLocation() throws SecurityException{
        android.location.Location lastKnownLocation = locationManagaer.getLastKnownLocation(locationProvider);
        Log.d(TAG, "Lat: " + lastKnownLocation.getLatitude() + " Long: " + lastKnownLocation.getLongitude());
        return lastKnownLocation;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_COARSE_LOC_PERM: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        sendLocation();
                    }

                } else {
                    Log.d(TAG, "Permission denied");
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            case MY_PERMISSIONS_REQUEST_FINE_LOC_PERM: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        sendLocation();
                    }

                } else {
                    Log.d(TAG, "Permission denied");
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }



}
