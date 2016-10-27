package com.pulkit.foodtracker.presenter;

import com.pulkit.foodtracker.pojo.Restraunts;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pulkitmital on 27/10/16.
 */

public interface RestrauntContract {

    interface View extends BaseView<Presenter> {
        void showProgress();

        void hideProgress();

        void setTitle(String title);

        void showRestraunt(ArrayList<Restraunts> restraunts);

        void showDataNotAvailableMessage(String message);

    }

    interface Presenter extends BasePresenter {
        //TODO - if want to refresh things
    }
}
