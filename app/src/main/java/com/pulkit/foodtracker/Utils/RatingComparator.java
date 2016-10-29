package com.pulkit.foodtracker.Utils;

import com.pulkit.foodtracker.pojo.Restaurants;

import java.util.Comparator;

/**
 * Created by pulkitmital on 28/10/16.
 */

public class RatingComparator implements Comparator<Restaurants> {
    @Override
    public int compare(Restaurants restaurants, Restaurants t1) {
        String change1 = restaurants.getRating();
        String change2 = t1.getRating();
        return change2.compareTo(change1);
    }
}
