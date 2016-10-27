package com.pulkit.foodtracker.activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by pulkitmital on 28/10/16.
 */

public class BaseActivity extends AppCompatActivity {

    ProgressDialog progressDialog;

    void showProgress(){
        if(progressDialog==null){
            progressDialog=new ProgressDialog(this);
        }
        progressDialog.setTitle("Loading....");
        progressDialog.show();

    }

    void hideProgress(){

        if(progressDialog!=null){
            progressDialog.dismiss();
        }
    }
}
