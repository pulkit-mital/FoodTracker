package com.pulkit.foodtracker.model;

import android.support.annotation.NonNull;

/**
 * Created by pulkitmital on 28/10/16.
 */

public interface GeocodeDataSource {

    interface LoadGeocodeCallback {

        void onDataLoaded();

        void onDataNotAvailable();

        void onNetworkError(String message);

    }

    void getGeocodes(@NonNull GeocodeDataSource.LoadGeocodeCallback callback, double latitude, double longitude);


}
