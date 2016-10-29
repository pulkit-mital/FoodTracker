package com.pulkit.foodtracker.presenter;

/**
 * Created by pulkitmital on 28/10/16.
 */

public interface GeocodeContract {

    interface View extends BaseView<RestaurantContract.Presenter> {
        void showProgress();

        void hideProgress();

        void showNetworkErrorMessage(String message);

        void onDataLoaded();

        void onDataNotAvailable();
    }

    interface Presenter extends BasePresenter {
        //TODO - if want to refresh things
    }
}
