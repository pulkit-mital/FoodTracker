package com.pulkit.foodtracker.model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.pulkit.foodtracker.Utils.Constants;
import com.pulkit.foodtracker.api.GeocodeApi;
import com.pulkit.foodtracker.app.App;

import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Retrofit;

/**
 * Created by pulkitmital on 28/10/16.
 */

public class GeocodeDataRepository implements GeocodeDataSource {

    private static final String TAG = "GeocodeRepository";


    private void loadGeocodesFromNetwork(@NonNull final GeocodeDataSource.LoadGeocodeCallback callback, double latitude, double longitude) {

        String base_url = Constants.BASE_URL + Constants.GEOCODE_API;
        String url = base_url + "?lat=" + latitude + "&lon=" + longitude;

        GeocodeApi geocodeApi = new GeocodeApi(new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.v(TAG, response.toString());
                callback.onDataLoaded();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.getMessage());
                callback.onNetworkError(error.getMessage());
            }
        }, url);

        App.getVolleyQueue().add(geocodeApi);
    }

    @Override
    public void getGeocodes(@NonNull GeocodeDataSource.LoadGeocodeCallback callback, double latitude, double longitude) {
        loadGeocodesFromNetwork(callback, latitude, longitude);
    }
}