package com.pulkit.foodtracker.presenter;

import android.support.annotation.NonNull;

import com.pulkit.foodtracker.model.RestaurantDataRepository;
import com.pulkit.foodtracker.model.RestaurantDataSource;
import com.pulkit.foodtracker.pojo.Restaurants;

import java.util.ArrayList;

/**
 * Created by pulkitmital on 27/10/16.
 */

public class RestaurantPresenter implements RestaurantContract.Presenter {


    private final RestaurantDataRepository restaurantDataRepository;
    private final RestaurantContract.View mRestrauntView;


    public RestaurantPresenter(@NonNull RestaurantDataRepository restaurantDataRepository, @NonNull RestaurantContract.View mRestrauntView) {

        if (null == restaurantDataRepository) {
            throw new IllegalArgumentException("restrauntRepository cannot be null");
        }
        if (null == mRestrauntView) {
            throw new IllegalArgumentException("restrauntView cannot be null!");
        }
        this.restaurantDataRepository = restaurantDataRepository;
        this.mRestrauntView = mRestrauntView;

        mRestrauntView.setPresenter(this);
    }

    @Override
    public void start() {
        mRestrauntView.setTitle("Restraunt List");
        loadRestraunts();
    }

    private void loadRestraunts() {
        mRestrauntView.showProgress();
        restaurantDataRepository.getRestraunts(new RestaurantDataSource.LoadRestrauntCallback() {


            @Override
            public void onRestrauntLoaded(ArrayList<Restaurants> restraunts) {
                mRestrauntView.hideProgress();
                mRestrauntView.showRestraunt(restraunts);
            }

            @Override
            public void onDataNotAvailable() {
                mRestrauntView.hideProgress();
                mRestrauntView.showDataNotAvailableMessage("No Restaurants Available!");
            }

        });
    }
}
