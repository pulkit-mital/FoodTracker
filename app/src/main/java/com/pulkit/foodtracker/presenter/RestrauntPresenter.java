package com.pulkit.foodtracker.presenter;

import android.graphics.Movie;
import android.support.annotation.NonNull;

import com.pulkit.foodtracker.model.RestrauntDataRepository;
import com.pulkit.foodtracker.model.RestrauntDataSource;
import com.pulkit.foodtracker.pojo.Restraunts;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pulkitmital on 27/10/16.
 */

public class RestrauntPresenter implements RestrauntContract.Presenter {


    private final RestrauntDataRepository restrauntDataRepository;
    private final RestrauntContract.View mRestrauntView;


    public RestrauntPresenter(@NonNull RestrauntDataRepository restrauntDataRepository, @NonNull RestrauntContract.View mRestrauntView) {

        if (null == restrauntDataRepository) {
            throw new IllegalArgumentException("restrauntRepository cannot be null");
        }
        if (null == mRestrauntView) {
            throw new IllegalArgumentException("restrauntView cannot be null!");
        }
        this.restrauntDataRepository = restrauntDataRepository;
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
        restrauntDataRepository.getRestraunts(new RestrauntDataSource.LoadRestrauntCallback() {


            @Override
            public void onRestrauntLoaded(ArrayList<Restraunts> restraunts) {
                mRestrauntView.hideProgress();
                mRestrauntView.showRestraunt(restraunts);
            }

            @Override
            public void onDataNotAvailable() {
                mRestrauntView.hideProgress();
                mRestrauntView.showDataNotAvailableMessage("No Restraunts Available!");
            }

        });
    }
}
