package com.pulkit.foodtracker.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pulkit.foodtracker.R;
import com.pulkit.foodtracker.pojo.Restaurants;
import com.squareup.picasso.Picasso;

import java.util.Locale;

/**
 * Created by pulkitmital on 27/10/16.
 */
public class RestaurantDetailActivity extends AppCompatActivity {

    private TextView restrauntName, restrauntLocality, restrauntAddress, restrauntCost, restrauntCuisines, restrauntRating;
    private ImageView restrauntImage;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    TextView maps;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restraunt_details);
        initializeView();
        Intent intent = getIntent();
        Restaurants restaurants = (Restaurants) intent.getSerializableExtra("restraunt_pojo");
        settingViews(restaurants);
        initializeToolbar();
        initializeDirectionListener(restaurants);
    }

    private void initializeView() {
        restrauntName = (TextView) findViewById(R.id.restraunt_name);
        restrauntLocality = (TextView) findViewById(R.id.restraunt_locality);
        restrauntAddress = (TextView) findViewById(R.id.restraunt_address);
        restrauntCost = (TextView) findViewById(R.id.restraunt_cost);
        restrauntCuisines = (TextView) findViewById(R.id.restraunt_cuisines);
        restrauntImage = (ImageView) findViewById(R.id.restraunt_image);
        restrauntRating = (TextView) findViewById(R.id.restraunt_rating);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        maps = (TextView) findViewById(R.id.restraunt_directions);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
    }

    /**
     * Setting data in the view i.e showing details of restaurant
     * @param restaurants
     */
    private void settingViews(Restaurants restaurants) {
        if (restaurants != null) {
            restrauntName.setText(restaurants.getName());
            restrauntLocality.setText(restaurants.getLocality());
            restrauntAddress.setText(restaurants.getAddress());
            restrauntCost.setText(restaurants.getCost());
            restrauntCuisines.setText(restaurants.getCuisines());
            restrauntRating.setText(restaurants.getRating());
            collapsingToolbarLayout.setTitle(restaurants.getName());

            Picasso.with(this).load(restaurants.getImage()).fit().error(R.mipmap.ic_launcher).placeholder(R.mipmap.ic_launcher).into(restrauntImage);

        }
    }

    private void initializeToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    private void initializeDirectionListener(final Restaurants restaurants) {
        if (restaurants != null) {

            maps.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr=%f,%f (%s)", restaurants.getLatitude(), restaurants.getLongitude(), restaurants.getName());
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                    startActivity(intent);
                }
            });

        }
    }
}
