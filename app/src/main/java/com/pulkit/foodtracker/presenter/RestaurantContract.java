package com.pulkit.foodtracker.presenter;

import com.pulkit.foodtracker.pojo.Restaurants;

import java.util.ArrayList;

/**
 * Created by pulkitmital on 27/10/16.
 */

public interface RestaurantContract {

    interface View extends BaseView<Presenter> {
        void showProgress();

        void hideProgress();

        void setTitle(String title);

        void showRestraunt(ArrayList<Restaurants> restraunts);

        void showDataNotAvailableMessage(String message);

    }

    interface Presenter extends BasePresenter {
        //TODO - if want to refresh things
    }
}
