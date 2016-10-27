package com.pulkit.foodtracker.Utils;

/**
 * Created by pulkitmital on 27/10/16.
 */


import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class GeoLocationFinder {
    private static final String TAG = "GeoLocationFinder";
    Timer locationTimer;
    LocationManager locationManager;
    Location location;
    private static final int min_update_time = 20000; // in msec
    private static final int min_distance_for_update = 10; // in meters
    LocationResult locationResult;
    boolean gps_enabled = Boolean.FALSE;
    boolean network_enabled = Boolean.FALSE;

    public boolean getLocation(Context ctx, LocationResult result) {
        locationResult = result;

        if (locationManager == null) {
            locationManager = (LocationManager) ctx
                    .getSystemService(Context.LOCATION_SERVICE);
        }

        try {
            gps_enabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {
            Log.d(TAG, "GPS enabled exception: " + e.getMessage());
        }

        try {
            network_enabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception e) {
            Log.d(TAG, "Network enabled exception: " + e.getMessage());
        }

        if (!gps_enabled && !network_enabled) {
            Log.d(TAG, "You are doomed boy!!");
            return Boolean.FALSE;
        }

        if (gps_enabled) {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, min_update_time,
                    min_distance_for_update, locationListenerGps);
        }

        if (network_enabled) {
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, min_update_time,
                    min_distance_for_update, locationListenerNetwork);
        }

        locationTimer = new Timer();
        locationTimer.schedule(new GetLastLocation(), 20000);
        return Boolean.TRUE;
    }

    LocationListener locationListenerGps = new LocationListener() {
        public void onLocationChanged(Location location) {
            locationTimer.cancel();
            locationResult.gotLocation(location);
            locationManager.removeUpdates(this);
            locationManager.removeUpdates(locationListenerGps);
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.d(TAG, "GPS provider disabled" + provider);

        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.d(TAG, "GPS provider enabled" + provider);

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.d(TAG, "GPS status changed");

        }


    };

    LocationListener locationListenerNetwork = new LocationListener() {
        public void onLocationChanged(Location location) {
            locationTimer.cancel();
            locationResult.gotLocation(location);
            locationManager.removeUpdates(this);
            locationManager.removeUpdates(locationListenerNetwork);
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.d(TAG, "Network provider disabled. " + provider);

        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.d(TAG, "Network provider enabled. " + provider);

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.d(TAG, "Network status changed.");

        }
    };

    private class GetLastLocation extends TimerTask {

        @Override
        public void run() {
            locationManager.removeUpdates(locationListenerGps);
            locationManager.removeUpdates(locationListenerNetwork);
            Location net_loc = null, gps_loc = null;
            if (gps_enabled) {
                gps_loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
            if (network_enabled) {
                net_loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
            if (gps_loc != null && net_loc != null) {
                if (gps_loc.getTime() > net_loc.getTime()) {
                    locationResult.gotLocation(gps_loc);
                } else {
                    locationResult.gotLocation(net_loc);
                }
                return;
            }

            if (gps_loc != null) {
                locationResult.gotLocation(gps_loc);
                return;
            }

            if (net_loc != null) {
                locationResult.gotLocation(net_loc);
                return;
            }

            locationResult.gotLocation(null);

        }

    }

    public static abstract class LocationResult {
        public abstract void gotLocation(Location location);
    }
}
