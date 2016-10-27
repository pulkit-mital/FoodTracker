package com.pulkit.foodtracker.Utils;

import com.pulkit.foodtracker.pojo.Restraunts;

import java.util.Comparator;

/**
 * Created by pulkitmital on 28/10/16.
 */

public class RatingComparator implements Comparator<Restraunts> {
    @Override
    public int compare(Restraunts restraunts, Restraunts t1) {
        String change1 = restraunts.getRating();
        String change2 = t1.getRating();
        return change2.compareTo(change1);
    }
}
