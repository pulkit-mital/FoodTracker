package com.pulkit.foodtracker.model;

import android.support.annotation.NonNull;

import com.pulkit.foodtracker.app.App;
import com.pulkit.foodtracker.pojo.Restaurants;

import java.util.ArrayList;

/**
 * Created by pulkitmital on 27/10/16.
 */

public class RestaurantDataRepository implements RestaurantDataSource {


    private static final String TAG = RestaurantDataRepository.class.getSimpleName();

    @Override
    public void getRestraunts(@NonNull LoadRestrauntCallback callback) {
        loadRestrauntFromDb(callback);
    }


    @Override
    public void getRestraunt(@NonNull String taskId, GetRestrauntCallback callback) {

    }

    private void loadRestrauntFromDb(LoadRestrauntCallback callback) {

        ArrayList<Restaurants> restrauntLists = App.getDb().getRestraunts();

        if (restrauntLists != null && restrauntLists.size() == 0) {
            callback.onDataNotAvailable();
        } else if (restrauntLists != null && restrauntLists.size() > 0) {
            callback.onRestrauntLoaded(restrauntLists);
        }

    }

}
