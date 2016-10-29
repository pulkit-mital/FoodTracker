package com.pulkit.foodtracker.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.pulkit.foodtracker.R;
import com.pulkit.foodtracker.Utils.ActivityUtils;
import com.pulkit.foodtracker.fragment.RestaurantFragment;
import com.pulkit.foodtracker.model.RestaurantDataRepository;
import com.pulkit.foodtracker.presenter.RestaurantPresenter;

public class RestaurantActivity extends AppCompatActivity {

    private static final String TAG = RestaurantActivity.class.getSimpleName();

    RestaurantPresenter restaurantPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restraunt);
        initializeToolbar();
        initializeFragment();
    }

    /**
     * initializing toolbar component
     */
    private void initializeToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    /**
     * Initializing RestaurantFragment and attaching it with the activity
     */
    private void initializeFragment() {
        RestaurantFragment restaurantFragment =
                (RestaurantFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (restaurantFragment == null) {
            // Create the fragment
            restaurantFragment = RestaurantFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), restaurantFragment, R.id.contentFrame);
        }

        // Create the presenter
        restaurantPresenter = new RestaurantPresenter(
                new RestaurantDataRepository(), restaurantFragment);
    }

}
