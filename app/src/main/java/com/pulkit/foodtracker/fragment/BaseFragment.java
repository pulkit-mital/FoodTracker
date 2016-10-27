package com.pulkit.foodtracker.fragment;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;

/**
 * Created by pulkitmital on 27/10/16.
 */

public class BaseFragment extends Fragment {

    ProgressDialog progressDialog;

    void showProgress() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getContext());
        }
        progressDialog.setTitle("Loading....");
        progressDialog.show();

    }

    void hideProgress() {

        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
