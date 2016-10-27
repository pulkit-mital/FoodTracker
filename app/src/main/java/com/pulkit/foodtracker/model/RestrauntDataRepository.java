package com.pulkit.foodtracker.model;

import android.support.annotation.NonNull;

import com.pulkit.foodtracker.app.App;
import com.pulkit.foodtracker.pojo.Restraunts;

import java.util.ArrayList;

/**
 * Created by pulkitmital on 27/10/16.
 */

public class RestrauntDataRepository implements RestrauntDataSource {


    private static final String TAG = RestrauntDataRepository.class.getSimpleName();

    @Override
    public void getRestraunts(@NonNull LoadRestrauntCallback callback) {
        loadRestrauntFromDb(callback);
    }


    @Override
    public void getRestraunt(@NonNull String taskId, GetRestrauntCallback callback) {

    }

    private void loadRestrauntFromDb(LoadRestrauntCallback callback) {

        ArrayList<Restraunts> restrauntLists = App.getDb().getRestraunts();

        if (restrauntLists != null && restrauntLists.size() == 0) {
            callback.onDataNotAvailable();
        } else if (restrauntLists != null && restrauntLists.size() > 0) {
            callback.onRestrauntLoaded(restrauntLists);
        }

    }

}
