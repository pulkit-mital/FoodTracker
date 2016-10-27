package com.pulkit.foodtracker.model;

import android.support.annotation.NonNull;

import com.pulkit.foodtracker.pojo.Restraunts;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pulkitmital on 27/10/16.
 */

public interface RestrauntDataSource {

    interface LoadRestrauntCallback {

        void onRestrauntLoaded(ArrayList<Restraunts> restraunts);

        void onDataNotAvailable();

    }

    interface GetRestrauntCallback {

        void onRestrauntLoaded(Restraunts restraunts);

        void onDataNotAvailable();
    }

    void getRestraunts(@NonNull LoadRestrauntCallback callback);

    void getRestraunt(@NonNull String taskId, GetRestrauntCallback callback);
}
