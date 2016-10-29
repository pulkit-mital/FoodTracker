package com.pulkit.foodtracker.model;

import android.support.annotation.NonNull;

import com.pulkit.foodtracker.pojo.Restaurants;

import java.util.ArrayList;

/**
 * Created by pulkitmital on 27/10/16.
 */

public interface RestaurantDataSource {

    interface LoadRestrauntCallback {

        void onRestrauntLoaded(ArrayList<Restaurants> restraunts);

        void onDataNotAvailable();

    }

    interface GetRestrauntCallback {

        void onRestrauntLoaded(Restaurants restaurants);

        void onDataNotAvailable();
    }

    void getRestraunts(@NonNull LoadRestrauntCallback callback);

    void getRestraunt(@NonNull String taskId, GetRestrauntCallback callback);
}
