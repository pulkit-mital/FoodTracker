package com.pulkit.foodtracker.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.pulkit.foodtracker.R;
import com.pulkit.foodtracker.Utils.ActivityUtils;
import com.pulkit.foodtracker.fragment.RestrauntFragment;
import com.pulkit.foodtracker.model.RestrauntDataRepository;
import com.pulkit.foodtracker.presenter.RestrauntPresenter;

public class RestrauntActivity extends AppCompatActivity {

    private static final String TAG = RestrauntActivity.class.getSimpleName();

    RestrauntPresenter restrauntPresenter;

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
     * Initializing RestrauntFragment and attaching it with the activity
     */
    private void initializeFragment() {
        RestrauntFragment restrauntFragment =
                (RestrauntFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (restrauntFragment == null) {
            // Create the fragment
            restrauntFragment = RestrauntFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), restrauntFragment, R.id.contentFrame);
        }

        // Create the presenter
        restrauntPresenter = new RestrauntPresenter(
                new RestrauntDataRepository(), restrauntFragment);
    }

}
