package com.pulkit.foodtracker.presenter;

import android.content.Context;
import android.location.Location;
import android.support.annotation.NonNull;
import android.util.Log;

import com.pulkit.foodtracker.Utils.GeoLocationFinder;
import com.pulkit.foodtracker.model.GeocodeDataRepository;
import com.pulkit.foodtracker.model.GeocodeDataSource;
import com.pulkit.foodtracker.model.RestrauntDataRepository;
import com.pulkit.foodtracker.model.RestrauntDataSource;
import com.pulkit.foodtracker.pojo.Restraunts;

import java.util.ArrayList;

/**
 * Created by pulkitmital on 28/10/16.
 */

public class GeocodePresenter implements GeocodeContract.Presenter {

    private static final String TAG = GeocodePresenter.class.getSimpleName();
    private final GeocodeDataRepository geocodeDataRepository;
    private final GeocodeContract.View geoCodeView;
    private Context context;


    public GeocodePresenter(@NonNull GeocodeDataRepository geocodeDataRepository, @NonNull GeocodeContract.View geoCodeView, Context context) {

        if (null == geocodeDataRepository) {
            throw new IllegalArgumentException("geocodeRepository cannot be null");
        }
        if (null == geoCodeView) {
            throw new IllegalArgumentException("geocodeView cannot be null!");
        }
        this.geocodeDataRepository = geocodeDataRepository;
        this.geoCodeView = geoCodeView;
        this.context = context;

    }

    @Override
    public void start() {

    }


    public void setupLocation() {

        geoCodeView.showProgress();
        GeoLocationFinder.LocationResult locationResult = new GeoLocationFinder.LocationResult() {

            @Override
            public void gotLocation(Location location) {
                if (location != null) {

                    Location newLocation = new Location(location);
                    newLocation.set(location);

                    Log.d(TAG,
                            "Got coordinates, congratulations. Longitude = "
                                    + newLocation.getLongitude() + " Latitude = "
                                    + newLocation.getLatitude());
                    geoCodeView.hideProgress();
                    loadGeocodes(newLocation.getLatitude(), newLocation.getLongitude());
                } else {
                    geoCodeView.onDataNotAvailable();
                }

            }

        };
        GeoLocationFinder geoLocationFinder = new GeoLocationFinder();
        geoLocationFinder.getLocation(context,
                locationResult);
    }

    public void loadGeocodes(double latitude, double longitude) {

        geoCodeView.showProgress();
        geocodeDataRepository.getGeocodes(new GeocodeDataSource.LoadGeocodeCallback() {

            @Override
            public void onDataLoaded() {
                geoCodeView.hideProgress();
                geoCodeView.onDataLoaded();
            }

            @Override
            public void onDataNotAvailable() {
                geoCodeView.onDataNotAvailable();
            }

            @Override
            public void onNetworkError(String message) {
                geoCodeView.hideProgress();
                geoCodeView.showNetworkErrorMessage(message);
            }

        }, latitude, longitude);
    }

}
