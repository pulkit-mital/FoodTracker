package com.pulkit.foodtracker.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.pulkit.foodtracker.R;
import com.pulkit.foodtracker.model.GeocodeDataRepository;
import com.pulkit.foodtracker.presenter.GeocodeContract;
import com.pulkit.foodtracker.presenter.GeocodePresenter;
import com.pulkit.foodtracker.presenter.RestaurantContract;

/**
 * Created by pulkitmital on 27/10/16.
 */

public class SplashActivity extends BaseActivity implements View.OnClickListener, GeocodeContract.View {

    private static final String TAG = "SplashScreenActivity";
    private static final int PERMISSION_REQUEST_CODE = 100;
    GeocodePresenter geocodePresenter;


    private Button buttonContinue;
    private Button buttonLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeView();
        geocodePresenter = new GeocodePresenter(new GeocodeDataRepository(), this, this);
        requestLocationPermissions();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    private void initializeView() {
        buttonContinue = (Button) findViewById(R.id.btn_continue);
        buttonLocation = (Button) findViewById(R.id.btn_get_location);
        buttonContinue.setEnabled(false);
        buttonContinue.setOnClickListener(this);
        buttonLocation.setOnClickListener(this);
    }

    private void requestLocationPermissions() {

        if (!isLocationPermissionGranted()) {
            requestingPermission();
        } else {

            geocodePresenter.setupLocation();
        }

    }

    private boolean isLocationPermissionGranted() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        //If permission is granted returning true
        if (result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED)
            return true;

        //If permission is not granted returning false
        return false;
    }

    private void requestingPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {

            new AlertDialog.Builder(this)
                    .setTitle("Location Permission")
                    .setMessage("Please Grant this Location permission to use this application")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            ActivityCompat.requestPermissions(SplashActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_CODE);

                        }
                    }).setNegativeButton("cancel", null).show();

        } else {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_CODE);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_continue:
                Intent intent = new Intent(this, RestaurantActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_get_location:
                requestLocationPermissions();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                    geocodePresenter.setupLocation();
                } else {

                    Toast.makeText(this, "Permission Denied, You cannot access location data.", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }

    @Override
    public void showProgress() {
        super.showProgress();
    }

    @Override
    public void hideProgress() {
        super.hideProgress();
    }

    @Override
    public void showNetworkErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDataLoaded() {
        buttonContinue.setEnabled(true);
    }

    @Override
    public void onDataNotAvailable() {
        Toast.makeText(this, "Data not Available", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(RestaurantContract.Presenter presenter) {
        //TODO
    }


}
